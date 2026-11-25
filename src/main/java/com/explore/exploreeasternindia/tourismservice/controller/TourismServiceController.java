package com.explore.exploreeasternindia.tourismservice.controller;

import com.explore.exploreeasternindia.tourismservice.entity.TourismService;
import com.explore.exploreeasternindia.tourismservice.service.TourismServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tourism-services")
public class TourismServiceController {

    private final TourismServiceService service;

    public TourismServiceController(TourismServiceService service) {
        this.service = service;
    }

    @PostMapping
    public TourismService createService(@RequestBody TourismService tourismService) {
        return service.createService(tourismService);
    }

    @GetMapping
    public List<TourismService> getAllServices() {
        return service.getAllServices();
    }

    @GetMapping("/{id}")
    public TourismService getServiceById(@PathVariable Long id) {
        return service.getServiceById(id);
    }

    @PutMapping("/{id}")
    public TourismService updateService(
            @PathVariable Long id,
            @RequestBody TourismService tourismService) {
        return service.updateService(id, tourismService);
    }

    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable Long id) {
        service.deleteService(id);
        return "Tourism service deleted successfully";
    }

    @GetMapping("/category/{category}")
    public List<TourismService> getServicesByCategory(
            @PathVariable String category) {
        return service.getServicesByCategory(category);
    }

    @GetMapping("/district/{district}")
    public List<TourismService> getServicesByDistrict(
            @PathVariable String district) {
        return service.getServicesByDistrict(district);
    }

    @GetMapping("/verified")
    public List<TourismService> getVerifiedServices() {
        return service.getVerifiedServices();
    }

    @GetMapping("/filter")
    public List<TourismService> getServicesByCategoryAndDistrict(
            @RequestParam String category,
            @RequestParam String district) {
        return service.getServicesByCategoryAndDistrict(category, district);
    }
}