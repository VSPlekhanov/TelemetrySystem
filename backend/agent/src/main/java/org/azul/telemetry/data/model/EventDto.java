package org.azul.telemetry.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value= ShutdownEventDto.class, name = EventType.Names.SHUTDOWN),
        @JsonSubTypes.Type(value= StartEventDto.class, name = EventType.Names.STARTUP),
        @JsonSubTypes.Type(value= UpdateEventDto.class, name = EventType.Names.UPDATE)
})
public class EventDto {
    @NotNull
    Long clientId;

    @NotNull
    String authToken;

    @NotNull
    @JsonProperty("@type")
    EventType eventType;

    @NotNull
    Timestamp createdAt;
}
