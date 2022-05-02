package org.azul.telemetry.data.model.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode
@Data
public class StartEventData{
    Map<String, String> environmentVariables;
    Map<String, String> systemProperties;
}

