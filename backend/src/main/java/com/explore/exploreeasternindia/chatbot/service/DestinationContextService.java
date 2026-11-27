package com.explore.exploreeasternindia.chatbot.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.service.DestinationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DestinationContextService {

    private final DestinationService destinationService;

    public DestinationContextService(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    // Retrieve and format destination records from MySQL.
    public String getRelevantDestinationContext() {

        List<Destination> destinations =
                destinationService.getAllDestinations();

        if (destinations.isEmpty()) {
            return "No destination records are currently available "
                    + "in the database.";
        }

        StringBuilder context = new StringBuilder();

        for (Destination destination : destinations) {

            String bestTimeStatus;

            if (Boolean.TRUE.equals(destination.getBestTimeVerified())
                    && destination.getBestTimeToVisit() != null
                    && !destination.getBestTimeToVisit().isBlank()) {

                bestTimeStatus = "VERIFIED: "
                        + destination.getBestTimeToVisit();

            } else {
                bestTimeStatus = "NOT VERIFIED / NOT AVAILABLE";
            }

            String entryFeeStatus;

            if (Boolean.TRUE.equals(destination.getEntryFeeVerified())
                    && destination.getEntryFee() != null) {

                BigDecimal fee = destination.getEntryFee();

                entryFeeStatus = fee.compareTo(BigDecimal.ZERO) == 0
                        ? "VERIFIED: Free"
                        : "VERIFIED: ₹"
                        + fee.stripTrailingZeros().toPlainString();

            } else {
                entryFeeStatus = "NOT VERIFIED / NOT AVAILABLE";
            }

            context.append("""
                    SOURCE: MYSQL DATABASE
                    Destination: %s
                    State: %s
                    District: %s
                    Category: %s
                    Description: %s
                    Best Time to Visit: %s
                    Entry Fee: %s

                    """.formatted(

                    destination.getName() != null
                            ? destination.getName()
                            : "NOT AVAILABLE IN DATABASE",

                    destination.getState() != null
                            ? destination.getState()
                            : "NOT AVAILABLE IN DATABASE",

                    destination.getDistrict() != null
                            ? destination.getDistrict()
                            : "NOT AVAILABLE IN DATABASE",

                    destination.getCategory() != null
                            ? destination.getCategory()
                            : "NOT AVAILABLE IN DATABASE",

                    destination.getDescription() != null
                            ? destination.getDescription()
                            : "NOT AVAILABLE IN DATABASE",

                    bestTimeStatus,
                    entryFeeStatus
            ));
        }

        return context.toString();
    }
}
