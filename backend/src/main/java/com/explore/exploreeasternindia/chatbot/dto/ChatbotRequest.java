
package com.explore.exploreeasternindia.chatbot.dto;

public class ChatbotRequest {

    private String conversationId;
    private String message;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
