package com.chronno.mockhero.service;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JsonMatcherService {

    private static final String PLACEHOLDER_REGEX  = "\\{\\w+}";
    private final ObjectMapper defaultObjectMapper;
    private final MockContextService mockContextService;

    public JsonMatcherService(ObjectMapper defaultObjectMapper, MockContextService mockContextService) {
        this.defaultObjectMapper = defaultObjectMapper;
        this.mockContextService = mockContextService;
    }

    public Boolean hasPlaceHolder(String json) {
        Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(json);
        return matcher.find();
    }


    public Boolean validateRequest(String mockRequest, String endpointRequest) {
        try {
            JsonNode originalJson = defaultObjectMapper.readTree(mockRequest);
            JsonNode receivedJson = defaultObjectMapper.readTree(endpointRequest);
            Iterator<Map.Entry<String, JsonNode>> fields = originalJson.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = field.getKey();
                String originalValue = field.getValue().asText();
                JsonNode receivedValueNode = receivedJson.get(key);
                if (originalValue.matches(PLACEHOLDER_REGEX)) {
                    if (receivedValueNode == null || receivedValueNode.asText().matches(PLACEHOLDER_REGEX)) {
                        return false;
                    }
                }
                mockContextService.addContextVariable(originalValue, receivedValueNode.textValue());
            }
            return true;
        } catch (JacksonException e) {
            return false;
        }
    }

}
