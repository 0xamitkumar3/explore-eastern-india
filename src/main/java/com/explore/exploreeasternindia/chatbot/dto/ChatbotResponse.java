package com.explore.exploreeasternindia.chatbot.dto;

public class ChatbotResponse {

    private String conversationId;
    private String reply;

    public ChatbotResponse(String conversationId, String reply) {
        this.conversationId = conversationId;
        this.reply = reply;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getReply() {
        return reply;
    }
}
