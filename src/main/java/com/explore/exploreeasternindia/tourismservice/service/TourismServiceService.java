package com.explore.exploreeasternindia.tourismservice.service;

import com.explore.exploreeasternindia.tourismservice.entity.TourismService;
import com.explore.exploreeasternindia.tourismservice.repository.TourismServiceRepository;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TourismServiceService {

    private final TourismServiceRepository repository;

    public TourismServiceService(TourismServiceRepository repository) {
        this.repository = repository;
    }

    public TourismService createService(TourismService service) {
        return repository.save(service);
    }

    public List<TourismService> getAllServices() {
        return repository.findAll();
    }

    public TourismService getServiceById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Tourism service not found"
                ));
    }

    public TourismService updateService(Long id, TourismService updatedService) {
        TourismService existing = getServiceById(id);

        existing.setName(updatedService.getName());
        existing.setCategory(updatedService.getCategory());
        existing.setState(updatedService.getState());
        existing.setDistrict(updatedService.getDistrict());
        existing.setAddress(updatedService.getAddress());
        existing.setContactNumber(updatedService.getContactNumber());
        existing.setDescription(updatedService.getDescription());
        existing.setVerified(updatedService.getVerified());

        return repository.save(existing);
    }

    public void deleteService(Long id) {
        TourismService existing = getServiceById(id);
        repository.delete(existing);
    }

    public List<TourismService> getServicesByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    public List<TourismService> getServicesByDistrict(String district) {
        return repository.findByDistrictIgnoreCase(district);
    }

    public List<TourismService> getVerifiedServices() {
        return repository.findByVerifiedTrue();
    }

    public List<TourismService> getServicesByCategoryAndDistrict(
            String category, String district) {
        return repository.findByCategoryIgnoreCaseAndDistrictIgnoreCase(
                category, district);
    }
}