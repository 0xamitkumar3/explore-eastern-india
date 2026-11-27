
package com.explore.exploreeasternindia.chatbot.service;

import org.springframework.ai.session.SessionService;
import org.springframework.ai.session.CreateSessionRequest;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.stereotype.Service;

@Service
public class ChatMemoryService {

    private final SessionService sessionService;

    public ChatMemoryService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    // Ensure a conversation session exists.
    public void createSessionIfNotExists(String conversationId) {

        sessionService.findOrCreate(
                CreateSessionRequest.builder()
                        .id(conversationId)
                        .build()
        );
    }

    // Save the user's message.
    public void saveUserMessage(
            String conversationId,
            String message) {

        sessionService.appendMessage(
                conversationId,
                new UserMessage(message)
        );
    }

    // Save the assistant's response.
    public void saveAssistantMessage(
            String conversationId,
            String response) {

        sessionService.appendMessage(
                conversationId,
                new AssistantMessage(response)
        );
    }

    // Save both sides of a conversation.
    public void saveConversation(
            String conversationId,
            String userMessage,
            String assistantResponse) {

        createSessionIfNotExists(conversationId);

        saveUserMessage(conversationId, userMessage);

        saveAssistantMessage(conversationId, assistantResponse);
    }
}
