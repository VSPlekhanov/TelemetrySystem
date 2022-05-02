package org.azul.telemetry.data.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class EventDto {
    @NotNull
    Long clientId;

    @NotNull
    String authToken;

    @NotNull
    EventType eventType;

    @NotNull
    Timestamp createdAt;

    @NotNull
    String eventData;
}
