package org.azul.telemetry.agent.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StartEventDto extends EventDto {
    @NotNull
    Map<String, String> environmentVariables;

    @NotNull
    Map<String, String> systemProperties;
}

