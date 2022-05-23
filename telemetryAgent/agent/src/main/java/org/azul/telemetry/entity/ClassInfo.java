package org.azul.telemetry.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ClassInfo {

    private final String className;

    public ClassInfo(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> classInfo = new HashMap<>();
        classInfo.put("className", className);

        String jsonResult = "";
        try {
            jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(classInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
