package com.chronno.mockhero.controller;

import com.chronno.mockhero.persistence.model.Mock;
import com.chronno.mockhero.service.RequestMatchingService;
import com.chronno.mockhero.service.ResponseBuilderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/mockhero")
public class MockheroController {

    private final RequestMatchingService requestMatchingService;
    private final ResponseBuilderService responseBuilderService;

    public MockheroController(RequestMatchingService requestMatchingService, ResponseBuilderService responseBuilderService) {
        this.requestMatchingService = requestMatchingService;
        this.responseBuilderService = responseBuilderService;
    }


    @RequestMapping("/**" )
    public ResponseEntity<?> processMock(HttpServletRequest request) {
        Optional<Mock> matchingMock = requestMatchingService.findMatchingMock(request);
        if (matchingMock.isPresent()) {
            return responseBuilderService.buildResponse(matchingMock.get());
        }
        return ResponseEntity.of(matchingMock);
    }
}
