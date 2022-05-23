package org.azul.telemetry;


import org.azul.telemetry.utils.Utils;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Agent {

    private static final Logger logger = Logger.getLogger(Agent.class.getName());
    private static TelemetryClient client;
    private static Instrumentation instrumentation;


    public static void premain(String args, Instrumentation inst) {
        try {
            logger.info("Telemetry Agent started with premain method, args: " + args);

            instrumentation = inst;

            client = configureClient(args);
            client.start();

            // send shutdown notification before exit
            Thread shutdownThread = new Thread(Agent::stop);
            Runtime.getRuntime().addShutdownHook(shutdownThread);
        } catch (Exception e) {
            logger.warning("Failed to start client");
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void stop() {
        if (client != null && client.isEnabled()) {
            logger.info("Stopping client");
            try {
                client.stop();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private static TelemetryClient configureClient(String cmdLineArgs) {
        Map<String, String> parsedArguments = Utils.parseCmdLineArgs(cmdLineArgs);

        RuntimeParamsCollector paramsCollector = new RuntimeParamsCollector();
        RuntimeParameters params = new RuntimeParameters(paramsCollector);

        if (parsedArguments.containsKey("clientId")) {
            params.setClientId(parsedArguments.get("clientId"));
        }

        if (parsedArguments.containsKey("authToken")) {
            params.setAuthToken(parsedArguments.get("authToken"));
        }

        if (parsedArguments.containsKey("enabled")) {
            params.setEnabled(Boolean.parseBoolean(parsedArguments.get("enabled")));
        }

        if (parsedArguments.containsKey("initialDelayMs")) {
            params.setInitialDelayMs(Integer.parseInt(parsedArguments.get("initialDelayMs")));
        }

        if (parsedArguments.containsKey("telemetryIntervalMs")) {
            params.setTelemetryIntervalMs(Integer.parseInt(parsedArguments.get("telemetryIntervalMs")));
        }

        return new TelemetryClient(params, instrumentation);
    }
}