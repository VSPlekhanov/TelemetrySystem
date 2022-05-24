package org.azul.telemetry;

import org.azul.telemetry.entity.VMInfo;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Collects runtime parameters (such as environment and JVM system properties)
 * at every instantiation.
 */
public class RuntimeParamsCollector {

    private static final Logger logger = Logger.getLogger(Agent.class.getName());

    private final Map<String, String> environmentVars;
    private final Map<String, String> systemProperties;

    private final VMInfo vmInfo;

    private final static String ID_PROPERTY = "telemetry.agent.id";
    private final static String ENABLED_PROPERTY = "telemetry.agent.enabled";
    private final static String URL_PROPERTY = "telemetry.agent.url";
    private final static String AUTH_TOKEN_PROPERTY = "telemetry.agent.authtoken";
    private final static String INITIAL_DELAY_PROPERTY = "telemetry.agent.initial_delay";
    private final static String TELEMETRY_INTERVAL_PROPERTY = "telemetry.agent.telemetry_interval";
    private final static String VERSION_PROPERTY = "telemetry.agent.version";

    private static final String AGENT_CONFIG = "agent\\src\\main\\resources\\agent.config";
    private static final Properties configProperties = new Properties();

    /**
     * Collects runtime parameters.
     */
    public RuntimeParamsCollector() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

        vmInfo = new VMInfo(
                runtimeMxBean.getVmName(),
                runtimeMxBean.getVmVendor(),
                runtimeMxBean.getVmVersion()
        );

        environmentVars = System.getenv();
        systemProperties = runtimeMxBean.getSystemProperties();


        try (FileInputStream fis = new FileInputStream(AGENT_CONFIG)) {
            configProperties.load(fis);
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
        }
    }

    public Map<String, String> getEnvironmentVars() {
        return environmentVars;

    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public String getIdProperty() {
        String idProperty = "";

        try {
            idProperty = environmentVars.get(ID_PROPERTY);
        } catch (Exception ignored) {
        }
        return idProperty;
    }

    public String getAuthTokenProperty() {
        String authTokenProperty = "";

        try {
            authTokenProperty = environmentVars.get(AUTH_TOKEN_PROPERTY);
        } catch(Exception ignored) {
        }

        return authTokenProperty;
    }

    public int getInitialDelayMs() {
        int initialDelay = 0;

        try {
            initialDelay = Integer.parseInt(environmentVars.get(INITIAL_DELAY_PROPERTY));
        } catch (NumberFormatException ignored) {
        }

        return initialDelay;
    }

    public int getTelemetryIntervalMs() {
        int telemetryIntervalMs = 1000;

        try {
            telemetryIntervalMs = Integer.parseInt(environmentVars.get(TELEMETRY_INTERVAL_PROPERTY));
        } catch (Exception ignored) {
        }

        return telemetryIntervalMs;
    }

    public String getUrlProperty() {
        return configProperties.getProperty(URL_PROPERTY);
    }

    public String getVersion() {
        return configProperties.getProperty(VERSION_PROPERTY);
    }

    public boolean getEnabledProperty() {
        boolean isEnabled = true;

        try {
            isEnabled = Boolean.parseBoolean(environmentVars.get(ENABLED_PROPERTY));
        } catch(Exception ignored) {
        }

        return isEnabled;
    }

    public VMInfo getVmInfo() {
        return vmInfo;
    }
}