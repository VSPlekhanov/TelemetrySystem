package org.azul.telemetry.agent.service;

import org.azul.telemetry.agent.model.EventDto;

public interface EventService {

    void addEvent(EventDto eventDto);

}
