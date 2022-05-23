package org.azul.telemetry.entity.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.azul.telemetry.entity.ClassInfo;

import java.util.Arrays;
import java.util.List;

public class ClassloadEvent extends Event {
    private final Class[] loadedClasses;

    public ClassloadEvent(String clientId, String authToken,
                          boolean isEnabled, Class[] loadedClasses) {
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

            List<ClassInfo> classInfo = Arrays.stream(loadedClasses)
                    .map(clazz -> new ClassInfo(clazz.getCanonicalName())).toList();

            String loadedClassesJson = classInfo.toString();
            JsonNode tempJson = mapper.readTree(loadedClassesJson);
            ((ObjectNode) json).put("loadedClasses", tempJson);

            jsonResult = mapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
