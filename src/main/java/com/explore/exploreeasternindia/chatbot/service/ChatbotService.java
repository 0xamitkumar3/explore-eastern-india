package com.explore.exploreeasternindia.chatbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.session.SessionService;
import org.springframework.ai.session.advisor.SessionAdvisors;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    private final ChatClient chatClient;

    public ChatbotService(
            ChatClient.Builder builder,
            SessionService sessionService) {

        this.chatClient = builder
                .defaultAdvisors(
                        SessionAdvisors.builder(sessionService).build()
                )
                .build();
    }

    public String sendMessage(String conversationId, String message) {

        return chatClient.prompt()
                .user(message)
                .advisors(SessionAdvisors.session(conversationId))
                .call()
                .content();
    }
}
