package org.azul.telemetry.agent.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Value
@Jacksonized
@SuperBuilder
public class ShutdownEventDto extends EventDto {
    @NotNull
    @JsonProperty("enabled")
    boolean enabled;
}
