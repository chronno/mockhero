package com.chronno.mockhero.configuration.threading;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;

@Configuration
public class AsyncConfig {

    public static final String HTTP_MOCK = "http-mock-";

    @Bean(name = "httpThreadPoolFactory")
    public ThreadFactory httpThreadPoolFactory() {
        return new MockingThreadFactory(HTTP_MOCK, true, Thread.NORM_PRIORITY);
    }


    @Bean(name = "httpThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(ThreadFactory httpThreadPoolFactory) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadFactory(httpThreadPoolFactory);
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix(HTTP_MOCK);
        executor.initialize();
        return executor;
    }
}
