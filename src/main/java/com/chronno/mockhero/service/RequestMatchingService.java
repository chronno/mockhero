package com.chronno.mockhero.service;

import com.chronno.mockhero.persistence.model.Mock;
import com.chronno.mockhero.persistence.repository.MockRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class RequestMatchingService {

    private final UriMatcherService uriMatcherService;
    private final JsonMatcherService jsonMatcherService;
    private final MockRepository mockRepository;


    public RequestMatchingService(UriMatcherService uriMatcherService, JsonMatcherService jsonMatcherService, MockRepository mockRepository) {
        this.uriMatcherService = uriMatcherService;
        this.jsonMatcherService = jsonMatcherService;
        this.mockRepository = mockRepository;
    }

    public Optional<Mock> findMatchingMock(HttpServletRequest request) {
        return findMocksByUrl(request.getRequestURI())
                .stream()
                .filter(mock -> checkHttpMethod(mock, request))
                .filter(mock -> checkAllHeadersExist(mock, request))
                .filter(mock -> checkBodyMatches(mock, request))
                .findAny();
    }

    private Boolean checkBodyMatches(Mock mock, HttpServletRequest request) {
        try {
            String body = new String(request.getInputStream().readAllBytes(), Charset.defaultCharset());
            return jsonMatcherService.validateRequest(mock.getRequest().getBody(), body);
        } catch (IOException e) {
            return false;
        }
    }

    private Boolean checkHttpMethod(Mock mock, HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(mock.getRequest().getMethod().name());
    }

    public Boolean checkAllHeadersExist(Mock mock, HttpServletRequest request) {
        Map<String, String> map = mock.getRequest().getHeaders();
        Enumeration<String> enumeration = request.getHeaderNames();
        List<String> enumerationSet = Collections.list(enumeration);
        for (String key : map.keySet()) {
            if (!enumerationSet.contains(key) || !request.getHeader(key).equalsIgnoreCase(map.get(key))) {
                return false;
            }
        }
        return true;
    }

    public List<Mock> findMocksByUrl(String url) {
        List<Mock> mocks = mockRepository.findAll();
        return mocks
                .stream()
                .filter(mock -> uriMatcherService.matches(mock.getRequest().getUrl(), url))
                .toList();
    }

}
