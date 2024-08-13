package com.chronno.mockhero.persistence.model;

import com.chronno.mockhero.configuration.application.RawJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "responses")
public class MockResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer code;

    @ElementCollection
    @CollectionTable(name = "response_headers", joinColumns = @JoinColumn(name = "response_id"))
    @MapKeyColumn(name = "header_key")
    @Column(name = "header_value")
    @JsonRawValue
    private Map<String, String> headers;

    @JsonRawValue
    private String contentType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "latency_id", referencedColumnName = "id")
    private Latency latency;

    @JsonDeserialize(using = RawJsonDeserializer.class)
    @JsonRawValue
    @Lob
    private String body;
}
