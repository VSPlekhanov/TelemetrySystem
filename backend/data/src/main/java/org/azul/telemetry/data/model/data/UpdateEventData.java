package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

/**
 * TODO.
 */
@Builder
@JsonSerialize
@Value
public class UpdateEventData implements EventData {
    Map<String, String> runtimeMetrics;
}
