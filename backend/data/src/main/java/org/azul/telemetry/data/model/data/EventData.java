package org.azul.telemetry.data.model.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.azul.telemetry.data.model.EventType;

//@JsonSubTypes({
//        @JsonSubTypes.Type(value=ShutdownEventData.class, name = EventType.Names.SHUTDOWN),
//        @JsonSubTypes.Type(value=StartEventData.class, name = EventType.Names.STARTUP),
//        @JsonSubTypes.Type(value=UpdateEventData.class, name = EventType.Names.UPDATE)
//})
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, visible = true)
public class EventData {}
