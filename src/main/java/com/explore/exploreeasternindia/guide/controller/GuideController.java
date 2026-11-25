package com.explore.exploreeasternindia.guide.controller;

import com.explore.exploreeasternindia.guide.entity.Guide;
import com.explore.exploreeasternindia.guide.service.GuideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    // Create guide
    @PostMapping
    public Guide createGuide(@RequestBody Guide guide) {
        return guideService.createGuide(guide);
    }

    // Get all guides
    @GetMapping
    public List<Guide> getAllGuides() {
        return guideService.getAllGuides();
    }

    // Get guide by ID
    @GetMapping("/{id}")
    public Guide getGuideById(@PathVariable Long id) {
        return guideService.getGuideById(id);
    }

    // Update guide
    @PutMapping("/{id}")
    public Guide updateGuide(
            @PathVariable Long id,
            @RequestBody Guide guide) {
        return guideService.updateGuide(id, guide);
    }

    // Delete guide
    @DeleteMapping("/{id}")
    public void deleteGuide(@PathVariable Long id) {
        guideService.deleteGuide(id);
    }

    // Search guides by district
    @GetMapping("/district/{district}")
    public List<Guide> getGuidesByDistrict(
            @PathVariable String district) {
        return guideService.getGuidesByDistrict(district);
    }

    // Search guides by language
    @GetMapping("/language/{language}")
    public List<Guide> getGuidesByLanguage(
            @PathVariable String language) {
        return guideService.getGuidesByLanguage(language);
    }

    // Search guides by specialization
    @GetMapping("/specialization/{specialization}")
    public List<Guide> getGuidesBySpecialization(
            @PathVariable String specialization) {
        return guideService.getGuidesBySpecialization(specialization);
    }

    // Get all verified guides
    @GetMapping("/verified")
    public List<Guide> getVerifiedGuides() {
        return guideService.getVerifiedGuides();
    }

    // Get verified guides by district
    @GetMapping("/verified/district/{district}")
    public List<Guide> getVerifiedGuidesByDistrict(
            @PathVariable String district) {
        return guideService.getVerifiedGuidesByDistrict(district);
    }
}
