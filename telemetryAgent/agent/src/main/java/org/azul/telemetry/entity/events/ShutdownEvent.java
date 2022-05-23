package org.azul.telemetry.entity.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ShutdownEvent extends Event {

    public ShutdownEvent(String clientId, String authToken, boolean isEnabled) {
        super(clientId, authToken, isEnabled);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";

        try {
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);
            JsonNode json = mapper.readTree(jsonResult);

            ((ObjectNode) json).put("@type", String.valueOf(EventType.SHUTDOWN));

            jsonResult = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
