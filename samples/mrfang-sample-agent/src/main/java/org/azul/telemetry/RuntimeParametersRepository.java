package org.azul.telemetry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Repository for runtime parameters.
 * Using sqlite DB to save data.
 * Allows only to write data because reading is not needed for agent.
 */
public class RuntimeParametersRepository {
    private static final Logger logger = LogManager.getLogger(RuntimeParametersRepository.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String DIRECTORY = System.getProperty("user.home") + "/.azul-telemetry/";
    private static final String FILENAME = "db.sqlite3";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + DIRECTORY + FILENAME;

    private static final String ENSURE_SCHEMA_QUERY =
        "SELECT id, name, environment, system_properties FROM telemetry_data";
    private static final String CREATE_SCHEMA_QUERY =
        "CREATE TABLE IF NOT EXISTS telemetry_data("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "name TEXT NOT NULL, "
            + "environment TEXT NOT NULL, "
            + "system_properties TEXT NOT NULL)";

    private static RuntimeParametersRepository INSTANCE = null;

    private RuntimeParametersRepository() throws RuntimeException {
        try {
            if (!Files.exists(Path.of(DIRECTORY, FILENAME))) {
                logger.info(DIRECTORY + FILENAME + " does not exists. Creating...");

                Files.createDirectories(Path.of(DIRECTORY));
                Files.createFile(Path.of(DIRECTORY, FILENAME));
            }

            ensureSchema();
        } catch (IOException | RuntimeException e) {
            logger.error("Failed to init repository. Reason: " + e);
            throw new RuntimeException(e);
        }

    }

    private void ensureSchema() throws RuntimeException {
        try (var connection = DriverManager.getConnection(CONNECTION_STRING);) {
            // Try to select necessary fields
            var statement = connection.createStatement();
            statement.executeQuery(ENSURE_SCHEMA_QUERY);
        } catch (SQLException suppress) {
            // If it fails, try to create table
            logger.info("Table telemetry_data not exists. Creating...");

            try (var connection = DriverManager.getConnection(CONNECTION_STRING)) {
                var statement = connection.createStatement();
                statement.executeUpdate(CREATE_SCHEMA_QUERY);
            } catch (SQLException e) {
                logger.error("Failed to ensure schema. Reason: " + e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Returns singleton of repository.
     *
     * @return singleton
     * @throws RuntimeException On first call if failed to create repository
     */
    public static RuntimeParametersRepository getInstance() throws RuntimeException {
        if (INSTANCE == null) {
            INSTANCE = new RuntimeParametersRepository();
        }

        return INSTANCE;
    }

    /**
     * Saves given object to DB.
     *
     * @param parameters Parameters to same
     * @return {@code true} if data was saved and {@code false} in other case
     */
    public boolean saveParams(@NotNull RuntimeParameters parameters) {
        logger.debug("Saving parameters: " + parameters);

        try (var connection = DriverManager.getConnection(CONNECTION_STRING)) {
            var statement = connection.createStatement();
            statement.executeUpdate(
                "INSERT INTO telemetry_data (name, environment, system_properties) VALUES ("
                    + "'" + parameters.name + "', "
                    + "'" + mapper.writeValueAsString(parameters.environment) + "', "
                    + "'" + mapper.writeValueAsString(parameters.systemProperties) + "')"
            );
        } catch (SQLException | JsonProcessingException e) {
            logger.error("Failed to save data. Reason: " + e);
            return false;
        }

        return true;
    }
}
