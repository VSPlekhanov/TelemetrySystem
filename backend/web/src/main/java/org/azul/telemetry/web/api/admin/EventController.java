package org.azul.telemetry.web.api.admin;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/event")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventController {
    private final EventService eventService;

    @GetMapping(
        path = "/list",
        produces = APPLICATION_JSON_VALUE
    )
    public List<Event> list() {
        return eventService.getAll();
    }
}
