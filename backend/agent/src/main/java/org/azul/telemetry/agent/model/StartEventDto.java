package org.azul.telemetry.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Value
@Jacksonized
@SuperBuilder
public class StartEventDto extends EventDto {
    @NotNull
    @JsonProperty("environmentVariables")
    Map<String, String> environmentVariables;

    @NotNull
    @JsonProperty("systemProperties")
    Map<String, String> systemProperties;
}

