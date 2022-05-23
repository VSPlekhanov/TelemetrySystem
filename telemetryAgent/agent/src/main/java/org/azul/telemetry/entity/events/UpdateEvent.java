package org.azul.telemetry.entity.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;

public class UpdateEvent extends Event {

    public final Map<String, String> environmentVariables;
    public final Map<String, String> systemProperties;

    public UpdateEvent(String clientId, String authToken, boolean isEnabled,
                       Map<String, String> environmentVariables,
                       Map<String, String> systemProperties) {

        super(clientId, authToken, isEnabled);
        this.environmentVariables = environmentVariables;
        this.systemProperties = systemProperties;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";

        try {
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);
            JsonNode json = mapper.readTree(jsonResult);

            ((ObjectNode) json).put("@type", String.valueOf(EventType.UPDATE));

            String envVarsJson = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(environmentVariables);

            String sysPropsJson = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(systemProperties);

            JsonNode tempJson = mapper.readTree(envVarsJson);
            ((ObjectNode) json).put("environmentVariables", tempJson);

            tempJson = mapper.readTree(sysPropsJson);
            ((ObjectNode) json).put("systemProperties", tempJson);

            jsonResult = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
