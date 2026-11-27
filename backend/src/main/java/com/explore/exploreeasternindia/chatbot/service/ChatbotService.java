package com.explore.exploreeasternindia.chatbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.session.SessionService;
import org.springframework.ai.session.advisor.SessionAdvisors;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    private final ChatClient chatClient;

    private final DestinationInfoService destinationInfoService;
    private final DestinationContextService destinationContextService;
    private final TourismContextService tourismContextService;
    private final ItineraryService itineraryService;

    public ChatbotService(
            ChatClient.Builder builder,
            SessionService sessionService,
            ItineraryService itineraryService,
            DestinationInfoService destinationInfoService,
            DestinationContextService destinationContextService,
            TourismContextService tourismContextService) {

        this.itineraryService = itineraryService;
        this.destinationInfoService = destinationInfoService;
        this.destinationContextService = destinationContextService;
        this.tourismContextService = tourismContextService;

        this.chatClient = builder
                .defaultAdvisors(
                        SessionAdvisors.builder(sessionService).build()
                )
                .build();
    }

    public String sendMessage(String conversationId, String message) {

        // Handle factual entry-fee questions using Java.
        if (destinationInfoService.isEntryFeeQuestion(message)) {
            return destinationInfoService.getEntryFeeResponse(message);
        }

        // Handle factual best-time questions using Java.
        if (destinationInfoService.isBestTimeQuestion(message)) {
            return destinationInfoService.getBestTimeResponse(message);
        }

        // Handle itinerary requests using Java-controlled selection.
        if (itineraryService.isItineraryRequest(message)) {
            return itineraryService.generateItinerary(
                    message,
                    conversationId
            );
        }

        // Handle hospital and medical facility queries safely.
        String normalizedMessage = message.toLowerCase();

        if (normalizedMessage.contains("hospital")
                || normalizedMessage.contains("clinic")
                || normalizedMessage.contains("medical centre")
                || normalizedMessage.contains("medical center")) {

            return """
            I currently don't have hospital or medical facility
            listings in our Explore Eastern India database.

            I don't want to provide unverified hospital names,
            addresses or contact numbers.

            For reliable information, please check official
            government health directories or trusted medical
            directories.

            If you are facing a medical emergency in India,
            call 112.
            """;
        }

        // Retrieve destination information from MySQL.
        String destinationContext =
                destinationContextService.getRelevantDestinationContext();

        // Retrieve tourism service information from MySQL.
        String tourismContext =
                tourismContextService.getRelevantTourismContext();

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

                        TOURISM SERVICE RULES:
                        - Tourism service records are provided from
                          our MySQL database.
                        - Use them when discussing hotels, restaurants,
                          transport and tour operators.
                        - Only explicitly listed services are
                          database-backed.
                        - Respect the verification status of each service.
                        - Never claim an unverified service is verified.
                        - Never invent contact numbers, addresses
                          or service details.
                        - Clearly distinguish database information
                          from general suggestions.
                        - If information is unavailable, say so honestly.

                        LOCAL BUSINESS AND INSTITUTION RULES:
                        - Never invent or guess the names of hospitals,
                          clinics, hotels, restaurants, transport providers,
                          tour operators or other local businesses.
                        - Never recommend a specific local business unless
                          its details are explicitly available in the
                          supplied database context or a trusted
                          external source.
                        - If no matching database records exist,
                          clearly state that no matching listings are
                          currently available in our database.
                        - You may provide general advice about how users
                          can find the requested service, but do not
                          invent specific business names.
                        - Never assume that a business exists just because
                          its name sounds familiar or plausible.
                        - Never assume that a database record represents
                          a real, currently operating business.
                        - Database verification status is an internal
                          record status. Do not describe it as independently
                          verified or officially certified unless
                          independent evidence is provided.
                        - Never refer users to sample hotels, restaurants
                          or other demo records as real-world businesses.
                        - Never invent relationships, recommendations
                          or affiliations between different database records.
                        - For hospitals and emergency medical services,
                          do not invent facility names, addresses,
                          contact numbers or availability.
                        - If reliable information is unavailable,
                          say so honestly.

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
                          the supplied MySQL destination context
                          are database-backed destinations.
                        - Only tourism services explicitly listed
                          in the supplied MySQL tourism context
                          are database-backed services.
                        - Never label general knowledge destinations
                          or services as coming from MySQL.
                        - Clearly distinguish database information
                          from general tourism suggestions.
                        - Do not copy information from one destination
                          or tourism service to another.

                        STRICT DATABASE DATA PRESERVATION RULES:
                        
                        - When presenting database records, reproduce names,
                          addresses, phone numbers and descriptions exactly as stored.
                        - Never reformat, modify, shorten or infer database values.
                        - Never assume a hotel has a restaurant or other facilities
                          unless explicitly mentioned in the database.
                        - Never make unsupported claims about local businesses,
                          food availability, opening hours, facilities or services.
                        - Clearly separate database-backed information from
                          general travel advice.
                        - Never describe sample or demo records as real businesses.

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

                        AVAILABLE TOURISM SERVICE RECORDS FROM MYSQL:

                        %s

                        IMPORTANT:
                        Use conversation history to understand the user's
                        interests and preferences.

                        Use the supplied destination records to identify
                        database-backed recommendations.

                        Use the supplied tourism service records when
                        answering questions about tourism services.

                        Clearly label any additional general tourism
                        suggestions.

                        Never present unverified database fields
                        as verified facts.

                        Never estimate or generalize entry fees.
                        If entry-fee information is unverified,
                        explicitly say that it is unknown.

                        Do not present general seasonal guidance
                        as verified visiting-time information.

                        Never invent tourism service contact details,
                        addresses or verification status.

                        Never recommend specific local businesses
                        or institutions without reliable supporting data.

                        Never treat sample or demo database records
                        as proof of real-world business existence.
                        """.formatted(
                        message,
                        destinationContext,
                        tourismContext
                ))

                .advisors(SessionAdvisors.session(conversationId))
                .call()
                .content();
    }
}