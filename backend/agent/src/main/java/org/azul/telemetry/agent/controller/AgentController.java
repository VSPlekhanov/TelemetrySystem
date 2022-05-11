package org.azul.telemetry.agent.controller;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.azul.telemetry.agent.model.EventDto;
import org.azul.telemetry.agent.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Value
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AgentController {
    EventService eventService;

    @PostMapping("/event")
    public void addEvent(@Valid @RequestBody EventDto eventDto) {
        eventService.addEvent(eventDto);
    }

}
