package com.explore.exploreeasternindia.tourismservice.repository;

import com.explore.exploreeasternindia.tourismservice.entity.TourismService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourismServiceRepository
        extends JpaRepository<TourismService, Long> {

    List<TourismService> findByCategoryIgnoreCase(String category);

    List<TourismService> findByDistrictIgnoreCase(String district);

    List<TourismService> findByVerifiedTrue();

    List<TourismService> findByCategoryIgnoreCaseAndDistrictIgnoreCase(
            String category,
            String district
    );
}