package org.azul.telemetry.web.colntrollers.api.admin;

import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.service.EventService;
import org.azul.telemetry.web.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * API controller for events access.
 */
@RestController
@RequestMapping("/api/admin/event")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private final EventService eventService;

    // TODO: 25.05.2022 temporary fix, need to do in on front
    private static EventDto fixInfoLength(EventDto eventDto) {
        if (eventDto.getAdditional().length() > 1000) {
            eventDto.setAdditional(eventDto.getAdditional().substring(0, 1000) + "...");
        }
        return eventDto;
    }

    @GetMapping(
            path = "/list",
            produces = APPLICATION_JSON_VALUE
    )
    public List<EventDto> list() {
        return eventService.getAll().stream().map(EventDto::new).map(EventController::fixInfoLength).collect(Collectors.toList());
    }
}
