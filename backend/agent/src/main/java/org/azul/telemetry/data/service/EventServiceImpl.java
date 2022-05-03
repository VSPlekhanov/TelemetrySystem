package org.azul.telemetry.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.azul.telemetry.data.model.EventDto;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.repository.EventRepository;
import org.azul.telemetry.data.repository.UserRepository;
import org.azul.telemetry.data.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void addEvent(EventDto eventDto) {
        try {
            Event event = Mapper.eventDtoToEventMapper(eventDto, userRepository.getById(eventDto.getClientId()));
            eventRepository.save(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    String.format("Invalid data for %s event type. %s ", eventDto.getEventType(), e.getMessage()));
        }
    }
}
