package org.azul.telemetry.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.azul.telemetry.data.model.EventDto;
import org.azul.telemetry.data.model.data.ShutdownEventData;
import org.azul.telemetry.data.model.data.StartEventData;
import org.azul.telemetry.data.model.data.UpdateEventData;
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
        ObjectMapper mapper = new ObjectMapper();
        String eventData = eventDto.getEventData();
        try {
            switch (eventDto.getEventType()) {
                case STARTUP:
                    mapper.readValue(eventData, StartEventData.class);
                    break;
                case UPDATE:
                    mapper.readValue(eventData, UpdateEventData.class);
                    break;
                case SHUTDOWN:
                    mapper.readValue(eventData, ShutdownEventData.class);
                    break;
                default:
                    throw new RuntimeException("Unsupported event type: " + eventDto.getEventType());
            }
            Event event = Mapper.eventDtoToEventMapper(eventDto, userRepository.getById(eventDto.getClientId()));
            eventRepository.save(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    String.format("Invalid data for %s event type. %s ", eventDto.getEventType(), e.getMessage()));
        }
    }
}
