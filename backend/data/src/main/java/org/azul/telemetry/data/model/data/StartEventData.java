package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * Payload of event that agent sends on application start.
 */
@Builder
@JsonSerialize
@Value
public class StartEventData implements EventData {
    @JsonProperty
    Map<String, String> environmentVariables;
    @JsonProperty
    Map<String, String> systemProperties;
    @JsonProperty
    Map<String, String> vmInfo;
}

