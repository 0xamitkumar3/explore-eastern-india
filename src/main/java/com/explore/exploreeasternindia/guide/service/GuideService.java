
package com.explore.exploreeasternindia.guide.service;

import com.explore.exploreeasternindia.guide.entity.Guide;
import com.explore.exploreeasternindia.guide.repository.GuideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideService {

    private final GuideRepository guideRepository;

    public GuideService(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    // Add a new guide
    public Guide createGuide(Guide guide) {
        return guideRepository.save(guide);
    }

    // Get all guides
    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }

    // Get guide by ID
    public Guide getGuideById(Long id) {
        return guideRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Guide not found with ID: " + id));
    }

    // Update guide
    public Guide updateGuide(Long id, Guide updatedGuide) {
        Guide existingGuide = getGuideById(id);

        existingGuide.setName(updatedGuide.getName());
        existingGuide.setState(updatedGuide.getState());
        existingGuide.setDistrict(updatedGuide.getDistrict());
        existingGuide.setLanguages(updatedGuide.getLanguages());
        existingGuide.setSpecialization(updatedGuide.getSpecialization());
        existingGuide.setContactNumber(updatedGuide.getContactNumber());
        existingGuide.setDescription(updatedGuide.getDescription());
        existingGuide.setVerified(updatedGuide.getVerified());

        return guideRepository.save(existingGuide);
    }

    // Delete guide
    public void deleteGuide(Long id) {
        Guide guide = getGuideById(id);
        guideRepository.delete(guide);
    }

    // Search guides by district
    public List<Guide> getGuidesByDistrict(String district) {
        return guideRepository.findByDistrictIgnoreCase(district);
    }

    // Search guides by language
    public List<Guide> getGuidesByLanguage(String language) {
        return guideRepository.findByLanguagesContainingIgnoreCase(language);
    }
}
