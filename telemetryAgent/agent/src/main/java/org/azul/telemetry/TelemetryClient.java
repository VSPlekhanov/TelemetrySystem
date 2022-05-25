package org.azul.telemetry;

import org.azul.telemetry.entity.events.EventType;
import org.azul.telemetry.entity.events.ClassloadEvent;
import org.azul.telemetry.entity.events.ShutdownEvent;
import org.azul.telemetry.entity.events.StartingEvent;
import org.azul.telemetry.entity.events.UpdateEvent;
import org.azul.telemetry.utils.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.instrument.Instrumentation;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class TelemetryClient {

    private static final Logger logger = Logger.getLogger(TelemetryClient.class.getName());
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor(TelemetryClient::createDaemonThread);

    private static final String USER_AGENT = "Telemetry Client";
    private static final int READ_TIMEOUT = 20000;
    private static final int CONNECT_TIMEOUT = 20000;

    private final RuntimeParameters props;
    private final String clientId;
    private final String authToken;
    private final URL telemetryUrl;
    private boolean isEnabled;
    private final Instrumentation instrumentation;

    private String vmId = "unknown";

    TelemetryClient(RuntimeParameters props, Instrumentation instrumentation) {
        this.props = props;
        this.instrumentation = instrumentation;

        this.clientId = props.getClientId();
        this.authToken = props.getAuthToken();
        this.isEnabled = props.isEnabled();
        this.telemetryUrl = createUrl(props.getUrl());

        if (clientId.isEmpty() || authToken.isEmpty()) {
            throw new IllegalArgumentException("Client id and authorization token must be configured");
        }
    }

    private static Thread createDaemonThread(Runnable task) {
        Thread thread = new Thread(task);
        thread.setName("Telemetry Thread");
        thread.setDaemon(true);
        return thread;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void start() throws IOException {
        if (isEnabled) {
            logger.info("Starting telemetry client");

            sendEventNotification(EventType.STARTUP);
            sendEventNotification(EventType.CLASSLOAD);

            try {
                scheduledExecutor.scheduleAtFixedRate(
                    this::updateMetrics,
                    props.getInitialDelayMs(),
                    props.getTelemetryIntervalMs(),
                    TimeUnit.MILLISECONDS
                );
            } catch (Error ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stop() throws IOException {
        if (isEnabled) {
            logger.info("Stopping telemetry client with properties: " + props);

            isEnabled = false;
            sendEventNotification(EventType.SHUTDOWN);
            scheduledExecutor.shutdown();
        }
    }

    private void updateMetrics() {
        try {
            sendEventNotification(EventType.CLASSLOAD);
        } catch (IOException ex) {
            logger.severe("Failed to update metrics: " + ex.getMessage());
        }
    }

    private void sendEventNotification(EventType eventType) throws IOException {
        String content = "";

        if (eventType == EventType.STARTUP) {
            content = new StartingEvent(clientId, authToken, isEnabled,
                    props.getEnvironmentVariables(),
                    props.getSystemProperties(),
                    props.getVmInfo()).toString();

        } else if (eventType == EventType.SHUTDOWN) {
            content = new ShutdownEvent(clientId, authToken, isEnabled).toString();

        } else if (eventType == EventType.UPDATE) {
            content = new UpdateEvent(clientId, authToken, isEnabled,
                    props.getEnvironmentVariables(),
                    props.getSystemProperties()).toString();

        } else if (eventType == EventType.CLASSLOAD) {
            Class[] loadedClasses = instrumentation.getAllLoadedClasses();
            content = new ClassloadEvent(clientId, authToken,
                    isEnabled, loadedClasses).toString();

        }

        content = Utils.addAttributeToJson(content, Map.entry("version", props.getVersion()));
        content = Utils.addAttributeToJson(content, Map.entry("vmId", vmId));

        try {
            HttpURLConnection connection = createConnection(telemetryUrl, content);
            connection.connect();

            int responseCode = connection.getResponseCode();

            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Object responseContent = connection.getContent();
                setVmId(responseContent);
                sendEventNotification(eventType);

            } else if (responseCode != HttpURLConnection.HTTP_OK) {
                logger.warning("Error response from telemetry server. Response code: " + responseCode);
            }
        } catch (Error ex) {
            logger.severe("Failed to send notification to server: " + ex.getMessage());
        }
    }

    private void setVmId(Object responseContent) {
        if (vmId.equals("unknown") && responseContent instanceof String) {
            vmId = (String) responseContent;
        }
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
}