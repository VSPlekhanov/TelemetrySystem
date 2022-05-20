package org.azul.telemetry.data.model.data;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Payload of event that agent sends on application shutdown.
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Value
public class ShutdownEventData extends EventData {
    String reason;
    Integer exitCode;
}
