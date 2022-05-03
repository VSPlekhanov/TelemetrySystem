package org.azul.telemetry.data.model.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Data
public class ShutdownEventData extends EventData {
    String reason;
    Integer exitCode;
}
