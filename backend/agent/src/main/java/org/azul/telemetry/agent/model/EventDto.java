package org.azul.telemetry.agent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.azul.telemetry.data.model.EventType;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@SuperBuilder
@Jacksonized
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShutdownEventDto.class, name = EventType.Names.SHUTDOWN),
        @JsonSubTypes.Type(value = StartEventDto.class, name = EventType.Names.STARTUP),
        @JsonSubTypes.Type(value = UpdateEventDto.class, name = EventType.Names.UPDATE)
})
public class EventDto {
    @NotNull
    @JsonProperty("clientId")
    Long clientId;

    @NotNull
    @JsonProperty("authToken")
    String authToken;

    @NotNull
    @JsonProperty("@type")
    EventType eventType;

    @NotNull
    @JsonProperty("createdAt")
    Timestamp createdAt;
}
