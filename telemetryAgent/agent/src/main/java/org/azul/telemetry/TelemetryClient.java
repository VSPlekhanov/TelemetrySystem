package org.azul.telemetry;

import org.azul.telemetry.entity.ClassInfo;
import org.azul.telemetry.entity.EventType;
import org.azul.telemetry.entity.events.Classload;
import org.azul.telemetry.entity.events.Shutdown;
import org.azul.telemetry.entity.events.Starting;
import org.azul.telemetry.entity.events.Update;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.instrument.Instrumentation;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TelemetryClient {

    private static final String USER_AGENT = "Telemetry Client";
    private static final Logger logger = Logger.getLogger(TelemetryClient.class.getName());
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(TelemetryClient::createDaemonThread);

    private static final int READ_TIMEOUT = 2000;
    private static final int CONNECT_TIMEOUT = 1000;

    private final RuntimeParameters props;
    private final String clientId;
    private final String authToken;
    private final URL telemetryUrl;
    private boolean isEnabled;
    private final Instrumentation instrumentation;

    TelemetryClient(RuntimeParameters props, Instrumentation instrumentation) {
        this.props = props;
        this.instrumentation = instrumentation;

        this.clientId = props.getClientId();
        this.authToken = props.getAuthToken();
        this.isEnabled = props.isEnabled();
        this.telemetryUrl = createUrl(props.getUrl());
    }

    private static Thread createDaemonThread(Runnable task) {
        Thread thread = new Thread(task);
        thread.setName("Telemetry Thread");
        thread.setDaemon(true);
        return thread;
    }

    public void start() throws IOException {
        if (isEnabled) {
            logger.info("Starting telemetry client");

            sendEventNotification(EventType.STARTUP);
            scheduledExecutor.scheduleAtFixedRate(
                    this::updateMetrics,
                    props.getInitialDelayMs(),
                    props.getTelemetryIntervalMs(),
                    TimeUnit.MILLISECONDS
            );
        } else {
            logger.warning("Telemetry agent is disabled");
        }
    }

    public void stop() throws IOException {
        logger.info("Stopping telemetry client with properties: " + props);
        if (isEnabled) {
            isEnabled = false;
            sendEventNotification(EventType.CLASSLOAD);
            sendEventNotification(EventType.SHUTDOWN);
            scheduledExecutor.shutdown();
        }
    }

    private void updateMetrics() {
        try {
            sendEventNotification(EventType.CLASSLOAD);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    private void sendEventNotification(EventType eventType) throws IOException {
        String content = "";

        if (eventType == EventType.STARTUP) {
            content = new Starting(clientId, authToken, isEnabled,
                    props.getEnvironmentVariables(),
                    props.getSystemProperties()).toString();

        } else if (eventType == EventType.SHUTDOWN) {
            content = new Shutdown(clientId, authToken, isEnabled).toString();

        } else if (eventType == EventType.UPDATE) {
            content = new Update(clientId, authToken, isEnabled,
                    props.getEnvironmentVariables(),
                    props.getSystemProperties()).toString();

        } else if (eventType == EventType.CLASSLOAD) {
            List<ClassInfo> loadedClasses = Arrays.stream(instrumentation.getAllLoadedClasses())
                    .map(clazz -> new ClassInfo(clazz.getName())).toList();
            content = new Classload(clientId, authToken, isEnabled,
                    loadedClasses).toString();
        }

        try {
            HttpURLConnection connection = createConnection(telemetryUrl, content);
            int responseCode = connectAndGetResponseCode(connection);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                logger.warning("Error response from telemetry server - response code: " + responseCode);
            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    private int connectAndGetResponseCode(HttpURLConnection connection) throws IOException {
        connection.connect();
        int responseCode = connection.getResponseCode();
        connection.disconnect();
        return responseCode;
    }

    private HttpURLConnection createConnection(URL url, String serializedContent) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = serializedContent.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        return connection;
    }

    private URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}