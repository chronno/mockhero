package com.chronno.mockhero.configuration.application;

import com.chronno.mockhero.interceptors.MockInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Interceptors implements WebMvcConfigurer {

    private final MockInterceptor mockInterceptor;

    public Interceptors(MockInterceptor mockInterceptor) {
        this.mockInterceptor = mockInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mockInterceptor).excludePathPatterns("/configuration/**");
    }
}
