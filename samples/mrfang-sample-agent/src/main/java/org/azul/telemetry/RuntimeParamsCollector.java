package org.azul.telemetry;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;

/**
 * Collects runtime parameters (such as environment and JVM system properties)
 * at every instantiation.
 */
public class RuntimeParamsCollector {
    private final String processName;
    private final Map<String, String> environmentVars;
    private final Map<String, String> systemProperties;

    /**
     * Collects runtime parameters.
     */
    public RuntimeParamsCollector() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        processName = runtimeMxBean.getName();
        environmentVars = System.getenv();
        systemProperties = runtimeMxBean.getSystemProperties();
    }

    public Map<String, String> getEnvironmentVars() {
        return environmentVars;
    }

    public Map<String, String> getSystemProperties() {
        return systemProperties;
    }

    public String getProcessName() {
        return processName;
    }
}
