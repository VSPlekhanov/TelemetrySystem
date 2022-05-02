package org.azul.telemetry.data.controller;

import org.azul.telemetry.data.model.EventDto;
import org.azul.telemetry.data.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AgentController {
    @Autowired
    EventService eventService;

    @PostMapping("/event")
    public void addEvent(@Valid @RequestBody EventDto eventDto) {
        eventService.addEvent(eventDto);
    }

}
