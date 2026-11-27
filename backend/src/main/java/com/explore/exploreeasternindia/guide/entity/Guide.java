package com.explore.exploreeasternindia.guide.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guides")
@Getter
@Setter
@NoArgsConstructor
public class Guide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String state;

    private String district;

    private String languages;

    private String specialization;

    private String contactNumber;

    @Column(length = 1000)
    private String description;

    private Boolean verified = false;
}
