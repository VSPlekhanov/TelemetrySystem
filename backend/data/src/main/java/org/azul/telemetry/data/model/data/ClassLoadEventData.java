package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@JsonSerialize
@Value
public class ClassLoadEventData implements EventData {
    List<LoadedClassInfo> loadedClasses;

    @Value
    @Jacksonized
    @Builder
    @JsonSerialize
    public static class LoadedClassInfo {
        @JsonProperty("className")
        String className;
    }
}
