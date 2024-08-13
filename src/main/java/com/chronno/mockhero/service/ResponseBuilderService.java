package com.chronno.mockhero.service;

import com.chronno.mockhero.persistence.model.Mock;
import com.chronno.mockhero.persistence.model.MockResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

@Service
public class ResponseBuilderService {

    private final MockContextService mockContextService;

    public ResponseBuilderService(MockContextService mockContextService) {
        this.mockContextService = mockContextService;
    }

    public ResponseEntity<?> buildResponse(Mock mock) {
        MockResponse response = mock.getResponse();
        return ResponseEntity.status(response.getCode())
                .contentType(MediaType.valueOf(response.getContentType().replace("\"", "")))
                .headers(buildHeaders(response.getHeaders()))
                .body(formattedBody(response.getBody()));

    }

    private HttpHeaders buildHeaders(Map<String, String> headers) {
        HttpHeaders result = new HttpHeaders();
        headers.forEach(result::add);
        return result;
    }

    private String formattedBody(String rawBody) {
        Map<String, Object> context = mockContextService.getContext();
        for (Map.Entry<String, Object> entry : context.entrySet()) {
            String key = Pattern.quote(entry.getKey());
            rawBody = rawBody.replaceAll(key, entry.getValue().toString());
        }
        return rawBody;
    }

}
