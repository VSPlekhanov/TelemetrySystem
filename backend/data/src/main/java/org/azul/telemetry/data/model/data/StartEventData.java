package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Value
public class StartEventData extends EventData {
    @JsonProperty
    Map<String, String> environmentVariables;
    @JsonProperty
    Map<String, String> systemProperties;
}

