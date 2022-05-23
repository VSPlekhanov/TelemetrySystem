package org.azul.telemetry.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class VMInfo {

    private final String vmName;
    private final String vmVendor;
    private final String vmVersion;

    public VMInfo(String vmName, String vmVendor, String vmVersion) {
        this.vmName = vmName;
        this.vmVendor = vmVendor;
        this.vmVersion = vmVersion;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> classInfo = new HashMap<>();
        classInfo.put("vmName", vmName);
        classInfo.put("vmVendor", vmVendor);
        classInfo.put("vmVersion", vmVersion);

        String jsonResult = "";
        try {
            jsonResult = mapper.writeValueAsString(classInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonResult;
    }
}
