package org.azul.telemetry.agent.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.azul.telemetry.agent.model.EventDto;
import org.azul.telemetry.agent.util.Mapper;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.repository.EventRepository;
import org.azul.telemetry.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Value
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {

    EventRepository eventRepository;

    UserRepository userRepository;

    Validator validator;

    @Override
    public void addEvent(EventDto eventDto) {
        try {
            validate(eventDto);
            Event event = Mapper.eventDtoToEventMapper(eventDto, userRepository.getById(eventDto.getClientId()));
            System.out.println(event);
            eventRepository.save(event);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("Invalid data for %s event type. %s ", eventDto.getEventType(), e.getMessage()));
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
