package org.azul.telemetry.data.service;

import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.repository.EventRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for <code>Event</code> access.
 *
 * @see Event
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {
    private final EventRepository eventRepository;

    /**
     * Persists event.
     *
     * @param event event to save.
     *              Must be fully populated including valid <code>User</code> instance.
     * @return Saved event.
     */
    @NotNull
    public Event save(@NotNull Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
