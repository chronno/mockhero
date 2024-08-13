package com.chronno.mockhero.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mocks")
public class Mock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    private MockRequest request;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "response_id", referencedColumnName = "id")
    private MockResponse response;
}
