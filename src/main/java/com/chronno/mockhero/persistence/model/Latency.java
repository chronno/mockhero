package com.chronno.mockhero.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "latencies")
public class Latency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double min;

    private Double max;
}
