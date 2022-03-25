package org.azul.telemetry;

import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * POJO for runtime parameters.
 */
public class RuntimeParameters {
    public final String name;
    public final Map<String, String> environment;
    public final Map<String, String> systemProperties;

    /**
     * Saves runtime parameters from given collector.
     *
     * @param collectedParams collector with runtime params
     */
    public RuntimeParameters(@NotNull RuntimeParamsCollector collectedParams) {
        name = collectedParams.getProcessName();
        environment = collectedParams.getEnvironmentVars();
        systemProperties = collectedParams.getSystemProperties();
    }
}
