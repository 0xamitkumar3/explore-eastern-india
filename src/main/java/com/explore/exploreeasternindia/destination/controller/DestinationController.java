
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
}
