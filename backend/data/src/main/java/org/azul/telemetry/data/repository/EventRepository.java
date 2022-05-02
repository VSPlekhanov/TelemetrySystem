package org.azul.telemetry.data.repository;

import org.azul.telemetry.data.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
