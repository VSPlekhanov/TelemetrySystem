package org.azul.telemetry.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UpdateEventDto extends EventDto {
    @NotNull
    Map<String, String> runtimeMetrics;
}
