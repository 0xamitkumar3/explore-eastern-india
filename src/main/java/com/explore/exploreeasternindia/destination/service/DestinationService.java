
package com.explore.exploreeasternindia.destination.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    // CREATE
    public Destination createDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    // READ ALL
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    // READ BY ID
    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Destination not found with ID: " + id));
    }

    // UPDATE
    public Destination updateDestination(Long id, Destination updatedDestination) {

        Destination existingDestination = getDestinationById(id);

        existingDestination.setName(updatedDestination.getName());
        existingDestination.setState(updatedDestination.getState());
        existingDestination.setDistrict(updatedDestination.getDistrict());
        existingDestination.setCategory(updatedDestination.getCategory());
        existingDestination.setDescription(updatedDestination.getDescription());
        existingDestination.setBestTimeToVisit(updatedDestination.getBestTimeToVisit());
        existingDestination.setEntryFee(updatedDestination.getEntryFee());

        return destinationRepository.save(existingDestination);
    }

    // DELETE
    public void deleteDestination(Long id) {
        Destination existingDestination = getDestinationById(id);
        destinationRepository.delete(existingDestination);
    }
}
