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

    @GetMapping(
            path = "/list",
            produces = APPLICATION_JSON_VALUE
    )
    public List<EventDto> list() {
        return eventService.getAll().stream().map(EventDto::new).collect(Collectors.toList());
    }
}
