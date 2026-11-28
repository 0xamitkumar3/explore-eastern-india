
package com.explore.exploreeasternindia.destination.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "destinations")
@Getter
@Setter
@NoArgsConstructor
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state;

    private String district;

    private String category;

    @Column(length = 2000)
    private String description;

    private String bestTimeToVisit;

    private BigDecimal entryFee;

    @Column(nullable = false)
    private Boolean bestTimeVerified = false;

    @Column(nullable = false)
    private Boolean entryFeeVerified = false;

    // Destination hero/gallery image path or URL
    @Column(length = 1000)
    private String imageUrl;
}
