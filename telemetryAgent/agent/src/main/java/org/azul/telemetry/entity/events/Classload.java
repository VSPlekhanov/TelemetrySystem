package org.azul.telemetry.entity.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.azul.telemetry.entity.ClassInfo;
import org.azul.telemetry.entity.EventType;

import java.util.List;

public class Classload extends Event {
    private final List<ClassInfo> loadedClasses;

    public Classload(String clientId, String authToken,
                     boolean isEnabled, List<ClassInfo> loadedClasses) {
        super(clientId, authToken, isEnabled);
        this.loadedClasses = loadedClasses;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = "";
        try {
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(this);

            JsonNode json = mapper.readTree(jsonResult);

            ((ObjectNode) json).put("@type", String.valueOf(EventType.CLASSLOAD));

            String loadedClassesJson = loadedClasses.toString();
            JsonNode tempJson = mapper.readTree(loadedClassesJson);
            ((ObjectNode) json).put("loadedClasses", tempJson);

            jsonResult = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
