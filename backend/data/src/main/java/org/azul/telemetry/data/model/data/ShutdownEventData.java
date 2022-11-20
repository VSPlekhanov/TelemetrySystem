package org.azul.telemetry.data.model.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

@Builder
@JsonSerialize
@Value
public class ShutdownEventData implements EventData {
    String reason;
    Integer exitCode;
}
