package com.explore.exploreeasternindia.chatbot.service;

import com.explore.exploreeasternindia.destination.entity.Destination;
import com.explore.exploreeasternindia.destination.service.DestinationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DestinationInfoService {

    private final DestinationService destinationService;

    public DestinationInfoService(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    // Detect entry-fee-related questions.
    public boolean isEntryFeeQuestion(String message) {

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
    public boolean isBestTimeQuestion(String message) {

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
    public String getEntryFeeResponse(String message) {

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
    public String getBestTimeResponse(String message) {

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
}
