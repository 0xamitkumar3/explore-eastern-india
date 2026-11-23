package com.explore.exploreeasternindia.chatbot.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.service.DestinationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.session.SessionService;
import org.springframework.ai.session.advisor.SessionAdvisors;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ChatbotService {

    private final ChatClient chatClient;
    private final DestinationService destinationService;

    public ChatbotService(
            ChatClient.Builder builder,
            SessionService sessionService,
            DestinationService destinationService) {

        this.destinationService = destinationService;

        this.chatClient = builder
                .defaultAdvisors(
                        SessionAdvisors.builder(sessionService).build()
                )
                .build();
    }

    public String sendMessage(String conversationId, String message) {

        // Handle factual entry-fee questions using Java.
        if (isEntryFeeQuestion(message)) {
            return getEntryFeeResponse(message);
        }

        // Handle factual best-time questions using Java.
        if (isBestTimeQuestion(message)) {
            return getBestTimeResponse(message);
        }

        // Handle itinerary requests using Java-controlled selection.
        if (isItineraryRequest(message)) {
            return generateItinerary(message, conversationId);
        }

        // Retrieve destination information from MySQL.
        String destinationContext = getRelevantDestinationContext();

        return chatClient.prompt()

                .system("""
                        You are Explore Eastern India's virtual travel guide.

                        Your current focus is Bihar, India.

                        PERSONALITY:
                        - Be friendly, helpful and conversational.
                        - Explain things in simple language.
                        - Help users discover destinations and plan trips.
                        - Ask relevant follow-up questions when needed.

                        DATABASE RULES:
                        - Destination records provided in the user message
                          come from our MySQL database.
                        - Use database information when discussing
                          destinations stored in the database.
                        - Never invent or modify database fields.
                        - Only use fields explicitly marked VERIFIED
                          as verified information.
                        - Never use unverified entry fees or
                          best-time information as factual data.
                        - If a field is marked NOT VERIFIED,
                          say that it has not been verified.
                        - If a field is unavailable, state that
                          the information is currently unavailable.
                        - Do not claim that general knowledge came
                          from our database.

                        CONVERSATION MEMORY:
                        - Use conversation history to understand
                          the user's interests and follow-up questions.
                        - Remember the user's previously stated
                          travel preferences.
                        - Recommend database destinations when
                          they match the user's interests.
                        - Never claim that the database has no
                          matching destination without checking
                          the supplied context.

                        SOURCE RULES:
                        - Only destinations explicitly listed in
                          the supplied MySQL context are database-backed.
                        - Never label general knowledge destinations
                          as coming from MySQL.
                        - Clearly distinguish database information
                          from general tourism suggestions.
                        - Do not copy information from one destination
                          to another.

                        GENERAL TOURISM KNOWLEDGE:
                        - You may answer general tourism questions using
                          your general knowledge.
                        - Clearly distinguish general knowledge from
                          database-backed information.
                        - Do not present uncertain information as fact.
                        - Never estimate or generalize entry fees,
                          ticket prices or other monetary amounts.
                        - Never claim that destinations are free or
                          paid without verified information.
                        - Never say that historical sites generally
                          have free or modest entry fees.
                        - Do not estimate prices based on typical
                          tourism practices or other destinations.
                        - For current prices, opening hours, travel
                          schedules and changing information,
                          recommend checking official sources.
                        - General seasonal suggestions must be clearly
                          labelled as general climate guidance.
                        - Do not present general climate guidance
                          as a verified destination-specific
                          best time to visit.
                        - Do not assign the same visiting season
                          to every destination without evidence.

                        RESPONSE RULES:
                        - Keep answers relevant to the user's question.
                        - Avoid unnecessary claims and details.
                        - Never fabricate facts, prices or travel information.
                        - If you do not know something, say so honestly.
                        - Do not add generic fee or price estimates
                          when the user has not requested them.
                        """)

                .user("""
                        User question:
                        %s

                        AVAILABLE DESTINATION RECORDS FROM MYSQL:

                        %s

                        IMPORTANT:
                        Use conversation history to understand the user's
                        interests and preferences.

                        Use the supplied destination records to identify
                        database-backed recommendations.

                        Clearly label any additional general tourism
                        suggestions.

                        Never present unverified database fields
                        as verified facts.

                        Never estimate or generalize entry fees.
                        If entry-fee information is unverified,
                        explicitly say that it is unknown.

                        Do not present general seasonal guidance
                        as verified visiting-time information.
                        """.formatted(message, destinationContext))

                .advisors(SessionAdvisors.session(conversationId))
                .call()
                .content();
    }

    // Detect entry-fee-related questions.
    private boolean isEntryFeeQuestion(String message) {

        String keyword = message.toLowerCase(Locale.ROOT);

        return keyword.contains("entry fee")
                || keyword.contains("ticket price")
                || keyword.contains("admission fee")
                || keyword.contains("entrance fee")
                || keyword.contains("entry charge")
                || keyword.contains("ticket cost")
                || keyword.contains("how much")
                || keyword.contains("is it free")
                || keyword.contains("are they free")
                || keyword.contains("cost to enter");
    }

    // Detect best-time-to-visit questions.
    private boolean isBestTimeQuestion(String message) {

        String keyword = message.toLowerCase(Locale.ROOT);

        return keyword.contains("best time")
                || keyword.contains("when to visit")
                || keyword.contains("when should i visit")
                || keyword.contains("best season")
                || keyword.contains("which season")
                || keyword.contains("which month")
                || keyword.contains("months to visit")
                || keyword.contains("ideal time")
                || keyword.contains("time to visit");
    }

    // Find destinations explicitly mentioned by the user.
    private List<Destination> findMentionedDestinations(String message) {

        String keyword = message.toLowerCase(Locale.ROOT);

        return destinationService.getAllDestinations()
                .stream()
                .filter(destination ->
                        destination.getName() != null
                                && keyword.contains(
                                destination.getName().toLowerCase(Locale.ROOT)
                        )
                )
                .toList();
    }

    // Return controlled entry-fee information.
    private String getEntryFeeResponse(String message) {

        List<Destination> destinations =
                findMentionedDestinations(message);

        if (destinations.isEmpty()) {
            return "I don't currently have verified entry-fee information. "
                    + "Please specify the destination you want to know about.";
        }

        return destinations.stream()
                .map(destination -> {

                    if (!Boolean.TRUE.equals(
                            destination.getEntryFeeVerified())) {

                        return destination.getName()
                                + ": Entry-fee information is currently "
                                + "unverified. I cannot confirm whether "
                                + "the destination is free or paid.";
                    }

                    BigDecimal fee = destination.getEntryFee();

                    if (fee == null) {
                        return destination.getName()
                                + ": Verified entry-fee information "
                                + "is currently unavailable.";
                    }

                    if (fee.compareTo(BigDecimal.ZERO) == 0) {
                        return destination.getName()
                                + ": Verified as having no entry fee.";
                    }

                    return destination.getName()
                            + ": Verified entry fee is ₹"
                            + fee.stripTrailingZeros().toPlainString()
                            + ".";
                })
                .collect(Collectors.joining("\n"));
    }

    // Return controlled best-time-to-visit information.
    private String getBestTimeResponse(String message) {

        List<Destination> destinations =
                findMentionedDestinations(message);

        if (destinations.isEmpty()) {
            return "Which destination are you planning to visit? "
                    + "Please specify its name so I can check "
                    + "the available visiting-time information.";
        }

        return destinations.stream()
                .map(destination -> {

                    if (!Boolean.TRUE.equals(
                            destination.getBestTimeVerified())) {

                        return destination.getName()
                                + ": Best time to visit is currently "
                                + "unverified or unavailable in our database. "
                                + "Please check official tourism sources.";
                    }

                    String bestTime = destination.getBestTimeToVisit();

                    if (bestTime == null || bestTime.isBlank()) {
                        return destination.getName()
                                + ": Verified visiting-time information "
                                + "is currently unavailable.";
                    }

                    return destination.getName()
                            + ": Verified best time to visit is "
                            + bestTime + ".";
                })
                .collect(Collectors.joining("\n"));
    }

    // Retrieve and format destination records from MySQL.
    private String getRelevantDestinationContext() {

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

    // Identify whether the user is requesting an itinerary.
    private boolean isItineraryRequest(String message) {

        String keyword = message.toLowerCase(Locale.ROOT);

        return keyword.contains("itinerary")
                || keyword.contains("plan a trip")
                || keyword.contains("plan my trip")
                || keyword.contains("trip plan")
                || keyword.contains("travel plan")
                || keyword.contains("days trip");
    }



    private String generateItinerary(String message, String conversationId) {

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

        // Step 5: Construct the final response using Java.
        return """
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
    }
}