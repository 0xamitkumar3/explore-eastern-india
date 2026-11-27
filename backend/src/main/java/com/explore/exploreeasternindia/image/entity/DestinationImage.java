package com.explore.exploreeasternindia.image.entity;

import com.explore.exploreeasternindia.destination.entity.Destination;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "destination_images")
@Getter
@Setter
@NoArgsConstructor
public class DestinationImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageName;

    @Column(nullable = false, length = 1000)
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    private LocalDateTime uploadedAt = LocalDateTime.now();
}
