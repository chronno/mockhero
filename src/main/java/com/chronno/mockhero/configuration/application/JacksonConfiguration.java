package com.chronno.mockhero.configuration.application;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper defaultObjectMapper() {
        SimpleModule jsonAsStringDeserializer = new SimpleModule();
        jsonAsStringDeserializer.addDeserializer(String.class, new RawJsonDeserializer());
        return Jackson2ObjectMapperBuilder.json()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .failOnEmptyBeans(false)
                .failOnUnknownProperties(false)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                .timeZone(TimeZone.getDefault())
                .build().registerModules(new JavaTimeModule(), jsonAsStringDeserializer);
    }
}
