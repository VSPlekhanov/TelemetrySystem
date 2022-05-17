package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@JsonSerialize
@Value
public class ClassLoadEventData extends EventData {
    List<LoadedClassInfo> loadedClasses;

    @Value
    @Jacksonized
    @Builder
    @JsonSerialize
    public static class LoadedClassInfo {
        @NotNull
        @JsonProperty("className")
        String className;
    }
}
