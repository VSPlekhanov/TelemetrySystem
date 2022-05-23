package org.azul.telemetry.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Map<String, String> parseCmdLineArgs(String cmdLineArgs) {
        Map<String, String> arguments = new HashMap<>();
        String[] splittedArguments = cmdLineArgs.split("&");

        for (String argument : splittedArguments) {
            String[] keyValue = argument.split("=");
            arguments.put(keyValue[0], keyValue[1]);
        }
        return arguments;
    }

    public static String addAttributeToJson(String json, Map.Entry<String, String> attribute) {
        ObjectMapper mapper = new ObjectMapper();
        String updatedJson = "";

        try {
            JsonNode jsonNode = mapper.readTree(json);
            ((ObjectNode) jsonNode).put(attribute.getKey(), attribute.getValue());
            updatedJson = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(jsonNode);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Error e) {
        }
        return updatedJson;
    }
}
