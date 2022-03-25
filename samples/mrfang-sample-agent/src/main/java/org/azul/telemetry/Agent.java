package org.azul.telemetry;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
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
    public static void premain(String args) throws InterruptedException {
        final var data = new RuntimeParamsCollector();
        final var directory = System.getProperty("user.home") + "/.azul-telemetry/";
        final var fileName = data.getProcessName()
            + "_"
            + new Random().nextInt()
            +  ".json";
        logger.debug("Agent started for process " + data.getProcessName());

        try {
            if (!Files.exists(Path.of(directory))) {
                logger.info(directory + " doesn't exists. Creating...");
                Files.createDirectories(Path.of(directory));
            }

            Files.createFile(Path.of(directory, fileName));
            new ObjectMapper().writeValue(
                new File(directory + fileName),
                new RuntimeParameters(data)
            );
        } catch (IOException e) {
            logger.error("Cannot start agent. Reason: " + e);
        }
    }
}
