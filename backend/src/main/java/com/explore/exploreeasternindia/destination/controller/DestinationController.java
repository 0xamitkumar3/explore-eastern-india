package com.explore.exploreeasternindia.destination.controller;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.service.DestinationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    // CREATE DESTINATION
    @PostMapping
    public Destination createDestination(@RequestBody Destination destination) {
        return destinationService.createDestination(destination);
    }

    // GET ALL DESTINATIONS
    @GetMapping
    public List<Destination> getAllDestinations() {
        return destinationService.getAllDestinations();
    }

    // GET DESTINATION BY ID
    @GetMapping("/{id}")
    public Destination getDestinationById(@PathVariable Long id) {
        return destinationService.getDestinationById(id);
    }

    // UPDATE DESTINATION
    @PutMapping("/{id}")
    public Destination updateDestination(
            @PathVariable Long id,
            @RequestBody Destination destination) {
        return destinationService.updateDestination(id, destination);
    }

    // DELETE DESTINATION
    @DeleteMapping("/{id}")
    public String deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return "Destination deleted successfully!";
    }

    // GET DESTINATIONS BY STATE
    @GetMapping("/state/{state}")
    public List<Destination> getDestinationsByState(@PathVariable String state) {
        return destinationService.getDestinationsByState(state);
    }

    // GET DESTINATIONS BY CATEGORY
    @GetMapping("/category/{category}")
    public List<Destination> getDestinationsByCategory(
            @PathVariable String category) {
        return destinationService.getDestinationsByCategory(category);
    }

    // SEARCH DESTINATIONS BY KEYWORD
    @GetMapping("/search")
    public List<Destination> searchDestinations(
            @RequestParam String keyword) {
        return destinationService.searchDestinations(keyword);
    }

    // Recommend destinations based on interest
    @GetMapping("/recommend")
    public List<Destination> recommendDestinations(
            @RequestParam String interest) {
        return destinationService.recommendDestinations(interest);
    }

    // Recommend destinations based on keyword
    @GetMapping("/recommend/keyword")
    public List<Destination> recommendByKeyword(
            @RequestParam String keyword) {
        return destinationService.recommendByKeyword(keyword);
    }

    // Recommend destinations based on state
    @GetMapping("/recommend/state")
    public List<Destination> recommendByState(
            @RequestParam String state) {
        return destinationService.recommendByState(state);
    }

    // Recommend destinations by category and state
    @GetMapping("/recommend/filter")
    public List<Destination> recommendByCategoryAndState(
            @RequestParam String category,
            @RequestParam String state) {

        return destinationService
                .recommendByCategoryAndState(category, state);
    }
}