package org.azul.telemetry.entity.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.sql.Timestamp;
import java.time.Instant;

public class Event {
    protected final String clientId;
    protected final String authToken;
    protected final boolean isEnabled;
    protected final String createdAt;

    public Event(String clientId, String authToken, boolean isEnabled) {
        this.clientId = clientId;
        this.authToken = authToken;
        this.isEnabled = isEnabled;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Instant instant = timestamp.toInstant();
        this.createdAt = instant.toString();
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";

        try {
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(clientId);

            JsonNode json = mapper.readTree(jsonResult);

            ((ObjectNode) json).put("createdAt", createdAt);
            ((ObjectNode) json).put("authToken", authToken);
            ((ObjectNode) json).put("isEnabled", isEnabled);

            jsonResult = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
