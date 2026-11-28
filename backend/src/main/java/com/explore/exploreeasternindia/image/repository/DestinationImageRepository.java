
package com.explore.exploreeasternindia.image.repository;

import com.explore.exploreeasternindia.image.entity.DestinationImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationImageRepository
        extends JpaRepository<DestinationImage, Long> {

    List<DestinationImage> findByDestination_Id(Long destinationId);

    Optional<DestinationImage> findFirstByDestination_IdOrderByIdAsc(
            Long destinationId
    );
}
