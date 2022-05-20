package org.azul.telemetry.entity.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.azul.telemetry.entity.EventType;

public class Shutdown extends Event {
    // TODO: research for API for getting exit code
    private static final String reason = "Program finished with exit code 0";
    private static final int exitCode = 0;


    public Shutdown(String clientId, String authToken, boolean isEnabled) {
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
            ((ObjectNode) json).put("reason", reason);
            ((ObjectNode) json).put("exitCode", exitCode);

            jsonResult = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
