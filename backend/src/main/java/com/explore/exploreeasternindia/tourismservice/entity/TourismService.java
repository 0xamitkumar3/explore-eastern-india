package com.explore.exploreeasternindia.tourismservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tourism_services")
@Getter
@Setter
@NoArgsConstructor
public class TourismService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // HOTEL, RESTAURANT, TRANSPORT, TOUR_OPERATOR
    private String category;

    private String state;

    private String district;

    @Column(length = 500)
    private String address;

    private String contactNumber;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Boolean verified = false;
}
