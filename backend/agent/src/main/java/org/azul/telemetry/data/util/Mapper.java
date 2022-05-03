package org.azul.telemetry.data.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.azul.telemetry.data.model.EventDto;
import org.azul.telemetry.data.model.ShutdownEventDto;
import org.azul.telemetry.data.model.StartEventDto;
import org.azul.telemetry.data.model.UpdateEventDto;
import org.azul.telemetry.data.model.data.EventData;
import org.azul.telemetry.data.model.data.ShutdownEventData;
import org.azul.telemetry.data.model.data.StartEventData;
import org.azul.telemetry.data.model.data.UpdateEventData;
import org.azul.telemetry.data.model.entity.Event;
import org.azul.telemetry.data.model.entity.User;

public class Mapper {
    public static Event eventDtoToEventMapper(EventDto dto, User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(dto);
        System.out.println(getEventDataFromEventDto(dto));
        String eventData = mapper.writeValueAsString(getEventDataFromEventDto(dto));
        return Event.builder()
                .eventType(dto.getEventType())
                .eventData(eventData)
                .createdAt(dto.getCreatedAt())
                .user(user)
                .build();
    }

    public static EventData getEventDataFromEventDto(EventDto dto){
        switch (dto.getEventType()){
            case SHUTDOWN:
                ShutdownEventDto shutdownEventDto = (ShutdownEventDto) dto;
                return ShutdownEventData.builder()
                        .reason(shutdownEventDto.getReason())
                        .exitCode(shutdownEventDto.getExitCode())
                        .build();
            case STARTUP:
                StartEventDto startEventDto = (StartEventDto) dto;
                return StartEventData.builder()
                        .environmentVariables(startEventDto.getEnvironmentVariables())
                        .systemProperties(startEventDto.getSystemProperties())
                        .build();
            case UPDATE:
                UpdateEventDto updateEventDto = (UpdateEventDto) dto;
                return UpdateEventData.builder()
                        .runtimeMetrics(updateEventDto.getRuntimeMetrics())
                        .build();
        }
        throw new RuntimeException("Unknown event type: " + dto.getEventType());
    }
}
