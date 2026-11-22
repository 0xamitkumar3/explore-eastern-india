
package com.explore.exploreeasternindia.destination.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.repository.DestinationRepository;
import com.explore.exploreeasternindia.exception.DestinationNotFoundException;
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
                .orElseThrow(() -> new DestinationNotFoundException(
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

    // FIND DESTINATIONS BY STATE
    public List<Destination> getDestinationsByState(String state) {
        return destinationRepository.findByState(state);
    }

    // FIND DESTINATIONS BY CATEGORY
    public List<Destination> getDestinationsByCategory(String category) {
        return destinationRepository.findByCategoryIgnoreCase(category);
    }

    // SEARCH DESTINATIONS BY KEYWORD
    public List<Destination> searchDestinations(String keyword) {
        return destinationRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword, keyword);
    }

    // Recommend destinations based on category
    public List<Destination> recommendDestinations(String interest) {
        return destinationRepository.findByCategoryIgnoreCase(interest);
    }

    // Recommend destinations based on keyword
    public List<Destination> recommendByKeyword(String keyword) {
        return destinationRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        keyword, keyword);
    }

    // Recommend destinations based on state
    public List<Destination> recommendByState(String state) {
        return destinationRepository.findByState(state);
    }

    // Recommend destinations by category and state
    public List<Destination> recommendByCategoryAndState(
            String category, String state) {

        return destinationRepository
                .findByCategoryIgnoreCaseAndStateIgnoreCase(category, state);
    }
}
