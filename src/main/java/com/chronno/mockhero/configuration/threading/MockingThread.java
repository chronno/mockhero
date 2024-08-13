package com.chronno.mockhero.configuration.threading;

import lombok.Getter;
import org.apache.tomcat.util.threads.TaskThread;

import java.util.Map;

public class MockingThread extends TaskThread {

    @Getter
    private final ThreadLocal<Map<String, Object>> threadLocal;

    public MockingThread(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
        this.threadLocal = new ThreadLocal<>();
    }

    public MockingThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
        this.threadLocal = new ThreadLocal<>();
    }
}
