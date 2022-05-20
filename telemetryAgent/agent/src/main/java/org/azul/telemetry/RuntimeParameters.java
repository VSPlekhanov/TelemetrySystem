package org.azul.telemetry;

import java.util.Map;


public class RuntimeParameters {

    private String clientId;
    private String authToken;
    private final Map<String, String> environmentVariables;
    private final Map<String, String> systemProperties;
    private int initialDelayMs;
    private int telemetryIntervalMs;
    private String url;
    private boolean enabled;


    public RuntimeParameters(RuntimeParamsCollector collectedParams) {
        clientId = collectedParams.getIdProperty();
        authToken = collectedParams.getAuthTokenProperty();
        environmentVariables = collectedParams.getEnvironmentVars();
        systemProperties = collectedParams.getSystemProperties();
        initialDelayMs = collectedParams.getInitialDelayMs();
        telemetryIntervalMs = collectedParams.getTelemetryIntervalMs();
        url = collectedParams.getUrlProperty();
        enabled = collectedParams.getEnabledProperty();
    }

    public String getClientId() {
        return clientId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public int getInitialDelayMs() {
        return initialDelayMs;
    }

    public int getTelemetryIntervalMs() {
        return telemetryIntervalMs;
    }

    public String getUrl() {
        return url;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setInitialDelayMs(int initialDelayMs) {
        this.initialDelayMs = initialDelayMs;
    }

    public void setTelemetryIntervalMs(int telemetryIntervalMs) {
        this.telemetryIntervalMs = telemetryIntervalMs;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}