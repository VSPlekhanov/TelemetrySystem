package org.azul.telemetry.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.azul.telemetry.data.model.data.ClassLoadEventData;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Value
@Jacksonized
@SuperBuilder
public class ClassLoadEventDto extends EventDto {
    @NotNull
    @JsonProperty("loadedClasses")
    @Valid
    List<ClassLoadEventData.LoadedClassInfo> loadedClasses;
}
