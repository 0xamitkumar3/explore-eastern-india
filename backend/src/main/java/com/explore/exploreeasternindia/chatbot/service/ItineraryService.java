package com.explore.exploreeasternindia.chatbot.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.service.DestinationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ItineraryService {

    private final DestinationService destinationService;
    private final ChatMemoryService chatMemoryService;

    public ItineraryService(
            DestinationService destinationService,
            ChatMemoryService chatMemoryService) {

        this.destinationService = destinationService;
        this.chatMemoryService = chatMemoryService;
    }

    // Identify whether the user is requesting an itinerary.
    public boolean isItineraryRequest(String message) {

        String keyword = message.toLowerCase(Locale.ROOT);

        boolean hasDayCount = Pattern
                .compile("\\b\\d+\\s*[- ]?\\s*days?\\b")
                .matcher(keyword)
                .find();

        boolean mentionsTrip = keyword.contains("trip")
                || keyword.contains("itinerary")
                || keyword.contains("travel plan");

        return keyword.contains("itinerary")
                || (hasDayCount && mentionsTrip)
                || keyword.contains("plan a trip")
                || keyword.contains("plan my trip")
                || keyword.contains("trip plan");
    }

    // Generate an itinerary using Java-controlled selection.
    public String generateItinerary(
            String message,
            String conversationId) {

        // Step 1: Extract the number of days.
        Matcher matcher = Pattern.compile(
                "\\b(\\d+)\\s*[- ]?\\s*days?\\b",
                Pattern.CASE_INSENSITIVE
        ).matcher(message);

        if (!matcher.find()) {
            return "How many days are you planning to travel? "
                    + "For example, 3 days or 5 days.";
        }

        int numberOfDays = Integer.parseInt(matcher.group(1));

        if (numberOfDays <= 0 || numberOfDays > 14) {
            return "Please choose a trip duration between 1 and 14 days.";
        }

        // Step 2: Identify the requested category.
        String keyword = message.toLowerCase(Locale.ROOT);

        String category;

        if (keyword.contains("historical")
                || keyword.contains("history")) {

            category = "Historical";

        } else if (keyword.contains("religious")
                || keyword.contains("religion")
                || keyword.contains("spiritual")) {

            category = "Religious";

        } else {
            return "Would you prefer historical or religious "
                    + "destinations for your Bihar trip?";
        }

        // Step 3: Fetch matching destinations from MySQL.
        List<Destination> destinations =
                getItineraryDestinations(category);

        if (destinations.isEmpty()) {
            return "Sorry, I couldn't find matching "
                    + category.toLowerCase(Locale.ROOT)
                    + " destinations in our Bihar database.";
        }

        // Step 4: Distribute destinations using Java.
        String itinerary = distributeDestinations(
                destinations,
                numberOfDays
        );

        // Step 5: Construct the final response.
        String response = """
                Your %d-day %s itinerary for Bihar!

                The following destinations have been selected
                from our MySQL database:

                %s

                Important information:
                - Destination details are taken from our database.
                - Entry fees are currently unverified.
                - Best visiting times are currently unverified.
                - Distances, travel times, accommodation and
                  transport costs are not available in our database.

                Please check official tourism sources for
                current travel information.

                Would you like to explore any of these destinations?
                """.formatted(
                numberOfDays,
                category,
                itinerary
        );

        chatMemoryService.saveConversation(
                conversationId,
                message,
                response
        );

        return response;
    }

    // Fetch destinations matching the requested category from Bihar.
    private List<Destination> getItineraryDestinations(String category) {

        return destinationService.recommendByCategoryAndState(
                category,
                "Bihar"
        );
    }

    // Distribute destinations evenly across the requested days.
    private String distributeDestinations(
            List<Destination> destinations,
            int numberOfDays) {

        if (destinations == null || destinations.isEmpty()) {
            return "No matching destinations found in our database.";
        }

        if (numberOfDays <= 0) {
            return "Please provide a valid number of days.";
        }

        StringBuilder itinerary = new StringBuilder();

        int totalDestinations = destinations.size();
        int index = 0;

        for (int day = 1; day <= numberOfDays; day++) {

            itinerary.append("Day ").append(day).append(":\n");

            int remainingDestinations = totalDestinations - index;
            int remainingDays = numberOfDays - day + 1;

            int destinationsForDay =
                    (int) Math.ceil(
                            (double) remainingDestinations / remainingDays
                    );

            for (int i = 0; i < destinationsForDay; i++) {

                if (index < totalDestinations) {

                    Destination destination = destinations.get(index);

                    itinerary.append("- ")
                            .append(destination.getName())
                            .append(" | District: ")
                            .append(destination.getDistrict())
                            .append(" | Description: ")
                            .append(destination.getDescription())
                            .append("\n");

                    index++;
                }
            }

            itinerary.append("\n");
        }

        return itinerary.toString();
    }
}
