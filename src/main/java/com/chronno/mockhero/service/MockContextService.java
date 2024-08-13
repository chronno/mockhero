package com.chronno.mockhero.service;

import com.chronno.mockhero.configuration.threading.MockingThread;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MockContextService {


    public void addContextVariable(String name, Object value) {
        getLocalContext().put(name, value);
    }

    private Map<String, Object> getLocalContext() {
        Map<String, Object> result;
        MockingThread mockingThread = (MockingThread) Thread.currentThread();
        ThreadLocal<Map<String, Object>> local = mockingThread.getThreadLocal();
        result = local.get();
        if (result == null) {
            result = new HashMap<>();
            local.set(result);
        }
        return result;
    }

    public Map<String, Object> getContext() {
        return getLocalContext();
    }

    public void addAll(Map<String, ?> context) {
        getContext().putAll(context);
    }
}
