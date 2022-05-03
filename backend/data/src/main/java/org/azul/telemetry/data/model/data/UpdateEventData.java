package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Data
public class UpdateEventData extends EventData {
    Map<String, String> runtimeMetrics;
}
