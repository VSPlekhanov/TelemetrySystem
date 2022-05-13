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
public class UpdateEventDto extends EventDto {
    @NotNull
    @JsonProperty("runtimeMetrics")
    Map<String, String> runtimeMetrics;
}
