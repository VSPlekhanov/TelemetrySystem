package org.azul.telemetry.agent.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
public class ShutdownEventDto extends EventDto {
    @NotNull
    @JsonProperty("reason")
    String reason;

    @NotNull
    @JsonProperty("exitCode")
    Integer exitCode;
}
