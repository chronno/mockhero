package com.chronno.mockhero.controller;

import com.chronno.mockhero.persistence.model.Mock;
import com.chronno.mockhero.service.MockConfigurationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    private final MockConfigurationService mockConfigurationService;

    public ConfigurationController(MockConfigurationService mockConfigurationService) {
        this.mockConfigurationService = mockConfigurationService;
    }

    @PostMapping
    public void configureSomething(@RequestBody Mock mock) {
        mockConfigurationService.addMock(mock);

    }

    @GetMapping("/{mockId}")
    public Mock findMock(@PathVariable("mockId") Long mockId) {
        return mockConfigurationService.findMockById(mockId).orElse(null);
    }
}
