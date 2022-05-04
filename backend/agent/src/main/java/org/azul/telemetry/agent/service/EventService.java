package org.azul.telemetry.agent.service;

import org.azul.telemetry.agent.model.EventDto;
import org.springframework.stereotype.Service;

@Service
public interface EventService {

    void addEvent(EventDto eventDto);

}
