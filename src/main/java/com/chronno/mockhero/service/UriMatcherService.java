package com.chronno.mockhero.service;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

import java.util.Map;

@Service
public class UriMatcherService {

    private final MockContextService mockContextService;

    public UriMatcherService(MockContextService mockContextService) {
        this.mockContextService = mockContextService;
    }

    public boolean matches(String pattern, String uri) {
        String cleanPattern = pattern.replace("\"", "");
        String cleanUri = uri.replace("/mockhero/", "/");
        UriTemplate template = new UriTemplate(cleanPattern);
        boolean result = template.matches(cleanUri);
        if (result) {
            Map<String, String> context = extractParameters(pattern, uri);
            context.forEach((key, value) -> {
                String formmatedKey = String.format("{%s}", key);
                mockContextService.addContextVariable(formmatedKey, value);
            });
        }
        return template.matches(cleanUri);
    }

    public Map<String, String> extractParameters(String pattern, String uri) {
        String cleanPattern = pattern.replace("\"", "");
        String cleanUri = uri.replace("/mockhero/", "/");
        UriTemplate template = new UriTemplate(cleanPattern);
        return template.match(cleanUri);
    }
}