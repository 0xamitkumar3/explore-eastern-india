
package com.explore.exploreeasternindia.destination.repository;

import com.explore.exploreeasternindia.destination.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    boolean existsByName(String name);
    List<Destination> findByState(String state);
    List<Destination> findByCategory(String category);
    List<Destination> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
