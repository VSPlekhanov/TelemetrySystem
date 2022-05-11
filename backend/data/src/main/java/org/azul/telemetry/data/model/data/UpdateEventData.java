package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Value
public class UpdateEventData extends EventData {
    Map<String, String> runtimeMetrics;
}
