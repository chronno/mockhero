package com.chronno.mockhero.persistence.model;

import com.chronno.mockhero.configuration.application.RawJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "requests")
public class MockRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonRawValue
    private String url;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    @ElementCollection
    @JsonRawValue
    @CollectionTable(name = "request_headers", joinColumns = @JoinColumn(name = "request_id"))
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    private Map<String, String> headers;

    @JsonDeserialize(using = RawJsonDeserializer.class)
    @JsonRawValue
    @Lob
    private String body;
}
