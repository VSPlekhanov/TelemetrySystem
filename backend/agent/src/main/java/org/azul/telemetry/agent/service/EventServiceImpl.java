package org.azul.telemetry.agent.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.azul.telemetry.agent.model.EventDto;
import org.azul.telemetry.agent.util.Mapper;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Value
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    org.azul.telemetry.data.service.EventService eventService;

    UserService userService;

    Validator validator;

    @Override
    public void addEvent(EventDto eventDto) {
        try {
            validate(eventDto);
            Event event = Mapper.eventDtoToEventMapper(
                eventDto,
                userService.getById(eventDto.getClientId())
            );
            eventService.save(event);
        } catch (Exception e) {
            throw new RuntimeException(String.format(
                "Invalid data for %s event type. %s ",
                eventDto.getEventType(),
                e.getMessage()
            ), e);
        }
    }

    private void validate(EventDto dto) {
        Set<ConstraintViolation<EventDto>> violations;
        violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            List<String> messages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            throw new RuntimeException("Violations: " + String.join(",", messages));
        }
    }

}
