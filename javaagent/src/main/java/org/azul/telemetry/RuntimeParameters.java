package org.azul.telemetry;

import java.util.Map;

/**
 * POJO for runtime parameters.
 */
public class RuntimeParameters {
    public final String id;
    public final String authToken;
    public final Map<String, String> environment;
    public final Map<String, String> systemProperties;
    public final int initialDelayMs;
    public final int telemetryIntervalMs;
    public final String url;
    public final boolean enabled;

    /**
     * Saves runtime parameters from given collector.
     *
     * @param collectedParams collector with runtime params
     */
    public RuntimeParameters(RuntimeParamsCollector collectedParams) {
        id = collectedParams.getIdProperty();
        authToken = collectedParams.getAuthTokenProperty();
        environment = collectedParams.getEnvironmentVars();
        systemProperties = collectedParams.getSystemProperties();
        initialDelayMs = collectedParams.getInitialDelayMs();
        telemetryIntervalMs = collectedParams.getTelemetryIntervalMs();
        url = collectedParams.getUrlProperty();
        enabled = collectedParams.getEnabledProperty();
    }
}