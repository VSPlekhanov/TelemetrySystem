package org.azul.telemetry.agent.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ShutdownEventDto extends EventDto {
    @NotNull
    String reason;

    @NotNull
    Integer exitCode;
}
