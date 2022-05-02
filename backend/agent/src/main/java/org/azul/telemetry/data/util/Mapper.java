package org.azul.telemetry.data.util;

import org.azul.telemetry.data.model.EventDto;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.model.entity.User;

public class Mapper {
    public static Event eventDtoToEventMapper(EventDto dto, User user) {
        return Event.builder()
                .eventType(dto.getEventType())
                .eventData(dto.getEventData())
                .createdAt(dto.getCreatedAt())
                .user(user)
                .build();
    }
}
