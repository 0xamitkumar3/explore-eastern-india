package com.explore.exploreeasternindia.admin.controller;

import com.explore.exploreeasternindia.destination.repository.DestinationRepository;
import com.explore.exploreeasternindia.guide.repository.GuideRepository;
import com.explore.exploreeasternindia.tourismservice.repository.TourismServiceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final DestinationRepository destinationRepository;
    private final GuideRepository guideRepository;
    private final TourismServiceRepository tourismServiceRepository;

    public AdminDashboardController(
            DestinationRepository destinationRepository,
            GuideRepository guideRepository,
            TourismServiceRepository tourismServiceRepository) {
        this.destinationRepository = destinationRepository;
        this.guideRepository = guideRepository;
        this.tourismServiceRepository = tourismServiceRepository;
    }

    @GetMapping("/stats")
    public Map<String, Long> getDashboardStats() {
        return Map.of(
                "totalDestinations", destinationRepository.count(),
                "totalGuides", guideRepository.count(),
                "totalTourismServices", tourismServiceRepository.count()
        );
    }
}
