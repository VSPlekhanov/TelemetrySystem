package org.azul.telemetry.web.dto;

import java.util.Date;
import lombok.Data;
import org.azul.telemetry.data.model.entity.Event;
import org.jetbrains.annotations.NotNull;

/**
 * DTO object for transferring <code>Event</code> over HTTP.
 *
 * @see Event
 */
@Data
public class EventDto {
    @NotNull
    private final String type;
    @NotNull
    private final Date createdAt;
    @NotNull
    private final UserDto user;
    @NotNull
    private final String additional;

    /**
     * Creates DTO object from <code>Event</code>.
     *
     * @param event object to create DTO from
     */
    public EventDto(@NotNull Event event) {
        type = event.getEventType().toString();
        createdAt = event.getCreatedAt();
        user = new UserDto(event.getUser());
        additional = event.getEventData().toString();
    }
}
