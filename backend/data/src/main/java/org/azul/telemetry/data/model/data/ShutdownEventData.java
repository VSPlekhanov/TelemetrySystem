package org.azul.telemetry.data.model.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Value
public class ShutdownEventData extends EventData {
    String reason;
    Integer exitCode;
}
