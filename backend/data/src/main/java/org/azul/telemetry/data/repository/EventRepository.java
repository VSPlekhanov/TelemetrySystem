package org.azul.telemetry.data.repository;

import org.azul.telemetry.data.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>Event</code> JPA repository.
 *
 * @see Event
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
