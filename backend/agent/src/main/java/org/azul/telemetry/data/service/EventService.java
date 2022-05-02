package org.azul.telemetry.data.service;

import org.azul.telemetry.data.model.EventDto;

public interface EventService {

    void addEvent(EventDto eventDto);

}
