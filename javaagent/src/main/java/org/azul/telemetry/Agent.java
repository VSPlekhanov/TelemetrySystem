package org.azul.telemetry;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Agent for collecting and saving runtime JVM parameters
 * at the start of application.
 */
public class Agent {
    private static final Logger logger = Logger.getLogger(Agent.class.getName());
    private static TelemetryClient client;

    /**
     * Collect data from runtime and saves it to JSON file.
     *
     * @param args args passed from command line.
     */
    public static void premain(String args) {
        try {
            logger.info("Agent started with premain method, args: " + args);
            client = configureClient();
            client.start();

            Thread shutdownThread = new Thread(Agent::stop);
            Runtime.getRuntime().addShutdownHook(shutdownThread);
        } catch (Exception e) {
            logger.warning("Failed to start client, agent will be disabled");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void stop() {
        if (client != null) {
            logger.info("Stopping client");
            try {
                client.stop();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private static TelemetryClient configureClient() {
        RuntimeParamsCollector paramsCollector = new RuntimeParamsCollector();
        RuntimeParameters params = new RuntimeParameters(paramsCollector);

        return new TelemetryClient(params);
    }
}