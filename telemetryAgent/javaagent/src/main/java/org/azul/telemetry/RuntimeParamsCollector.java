package org.azul.telemetry;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;

/**
 * Collects runtime parameters (such as environment and JVM system properties)
 * at every instantiation.
 */
public class RuntimeParamsCollector {
    private final Map<String, String> environmentVars;
    private final Map<String, String> systemProperties;

    private final static String ID_PROPERTY = "telemetry.agent.id";
    private final static String ENABLED_PROPERTY = "telemetry.agent.enabled";
    private final static String URL_PROPERTY = "telemetry.agent.url";
    private final static String AUTH_TOKEN_PROPERTY = "telemetry.agent.authtoken";
    private final static String INITIAL_DELAY_PROPERTY = "telemetry.agent.initial_delay";
    private final static String TELEMETRY_INTERVAL_PROPERTY = "telemetry.agent.telemetry_interval";

    /**
     * Collects runtime parameters.
     */
    public RuntimeParamsCollector() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        environmentVars = System.getenv();
        System.out.println(environmentVars);
        systemProperties = runtimeMxBean.getSystemProperties();
    }

    public Map<String, String> getEnvironmentVars() {
        return environmentVars;

    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public String getIdProperty() {
        return environmentVars.get(ID_PROPERTY);
    }

    public int getInitialDelayMs() {
        return Integer.parseInt(environmentVars.get(INITIAL_DELAY_PROPERTY));
    }

    public int getTelemetryIntervalMs() {
        return Integer.parseInt(environmentVars.get(TELEMETRY_INTERVAL_PROPERTY));
    }

    public String getUrlProperty() {
        return environmentVars.get(URL_PROPERTY);
    }

    public String getAuthTokenProperty() {
        return environmentVars.get(AUTH_TOKEN_PROPERTY);
    }

    public boolean getEnabledProperty() {
        return Boolean.parseBoolean(environmentVars.get(ENABLED_PROPERTY));
    }
}