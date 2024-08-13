package com.chronno.mockhero.service;

import com.chronno.mockhero.persistence.model.Mock;
import com.chronno.mockhero.persistence.repository.MockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MockConfigurationService {

    private final MockRepository mockRepository;

    public MockConfigurationService(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public Mock addMock(Mock mock) {
        return mockRepository.save(mock);
    }

    public Optional<Mock> findMockById(Long mockId) {
        return mockRepository.findById(mockId);
    }


}
