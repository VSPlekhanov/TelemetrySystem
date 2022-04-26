package org.azul.telemetry;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
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

    TelemetryClient(RuntimeParameters props) {
        this.props = Objects.requireNonNull(props);
        this.clientId = props.id;
        this.authToken = props.authToken;
        this.isEnabled = props.enabled;
        this.telemetryUrl = createUrl(props.url);
    }

    private static Thread createDaemonThread(Runnable task) {
        Thread thread = new Thread(task);
        thread.setName("Telemetry Thread");
        thread.setDaemon(true);
        return thread;
    }

    public void start() throws IOException {
        if (isEnabled) {
            logger.info("Starting telemetry client with properties: " + props);
            sendEventNotification(EventType.STARTING);
            scheduledExecutor.scheduleAtFixedRate(this::updateMetrics, props.initialDelayMs, props.telemetryIntervalMs, TimeUnit.MILLISECONDS);
        } else {
            logger.warning("Telemetry agent is disabled");
        }
    }

    public void stop() throws IOException {
        if (isEnabled) {
            isEnabled = false;
            logger.info("Stopping telemetry client with properties: " + props);
            scheduledExecutor.shutdown();
            sendEventNotification(EventType.SHUT_DOWN);
        }
    }

    // TODO: implement metrics updating in daemon
    private void updateMetrics() {
    }

    private void sendEventNotification(EventType eventType) throws IOException {
        HttpURLConnection connection = createConnection(telemetryUrl, eventType);
        int responseCode = connectAndGetResponseCode(connection);
        if (responseCode != HttpURLConnection.HTTP_OK) {
            logger.warning("Error response from telemetry server - response code: " + responseCode);
        }
    }

    private int connectAndGetResponseCode(HttpURLConnection connection) throws IOException {
        connection.connect();
        int responseCode = connection.getResponseCode();
        connection.disconnect();
        return responseCode;
    }

    private HttpURLConnection createConnection(URL url, EventType eventType) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.addRequestProperty("event-type", eventType.name());

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(props);

        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonResult.getBytes(StandardCharsets.UTF_8);
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