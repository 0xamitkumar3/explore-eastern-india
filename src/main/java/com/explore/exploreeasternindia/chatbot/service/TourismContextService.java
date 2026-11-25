package com.explore.exploreeasternindia.chatbot.service;

import com.explore.exploreeasternindia.tourismservice.entity.TourismService;
import com.explore.exploreeasternindia.tourismservice.service.TourismServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourismContextService {

    private final TourismServiceService tourismServiceService;

    public TourismContextService(
            TourismServiceService tourismServiceService) {

        this.tourismServiceService = tourismServiceService;
    }

    // Retrieve and format tourism service records from MySQL.
    public String getRelevantTourismContext() {

        List<TourismService> services =
                tourismServiceService.getAllServices();

        if (services.isEmpty()) {
            return "No tourism service records are currently "
                    + "available in the database.";
        }

        StringBuilder context = new StringBuilder();

        for (TourismService service : services) {

            String verificationStatus =
                    Boolean.TRUE.equals(service.getVerified())
                            ? "VERIFIED"
                            : "NOT VERIFIED";

            context.append("""
                    SOURCE: MYSQL TOURISM SERVICES DATABASE
                    Name: %s
                    Category: %s
                    State: %s
                    District: %s
                    Address: %s
                    Contact Number: %s
                    Description: %s
                    Verification Status: %s

                    """.formatted(

                    service.getName() != null
                            ? service.getName()
                            : "NOT AVAILABLE",

                    service.getCategory() != null
                            ? service.getCategory()
                            : "NOT AVAILABLE",

                    service.getState() != null
                            ? service.getState()
                            : "NOT AVAILABLE",

                    service.getDistrict() != null
                            ? service.getDistrict()
                            : "NOT AVAILABLE",

                    service.getAddress() != null
                            ? service.getAddress()
                            : "NOT AVAILABLE",

                    service.getContactNumber() != null
                            ? service.getContactNumber()
                            : "NOT AVAILABLE",

                    service.getDescription() != null
                            ? service.getDescription()
                            : "NOT AVAILABLE",

                    verificationStatus
            ));
        }

        return context.toString();
    }
}
