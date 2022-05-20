package org.azul.telemetry.utils;

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
}
