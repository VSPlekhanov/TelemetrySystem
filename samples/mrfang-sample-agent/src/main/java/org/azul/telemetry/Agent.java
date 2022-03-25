package org.azul.telemetry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Agent for collecting and saving runtime JVM parameters
 * at the start of application.
 */
public class Agent {
    private static final Logger logger = LogManager.getLogger(Agent.class);

    /**
     * Collect data from runtime and saves it to JSON file.
     *
     * @param args args passed from command line.
     */
    public static void premain(String args) {
        final var data = new RuntimeParamsCollector();
        logger.debug("Agent for " + data.getProcessName() + "started");

        new Thread(() -> {
            try {
                final var repository = RuntimeParametersRepository.getInstance();
                repository.saveParams(new RuntimeParameters(data));
            } catch (RuntimeException e) {
                logger.error("Cannot save data. Reason: " + e);
            }
        }).start();
    }
}
