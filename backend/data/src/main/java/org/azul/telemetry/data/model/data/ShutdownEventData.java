package org.azul.telemetry.data.model.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

/**
 * Payload of event that agent sends on application shutdown.
 */
@Builder
@JsonSerialize
@Value
public class ShutdownEventData implements EventData {
    String reason;
    Integer exitCode;
}
