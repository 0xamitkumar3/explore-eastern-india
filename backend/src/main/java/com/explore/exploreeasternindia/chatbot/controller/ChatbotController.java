
package com.explore.exploreeasternindia.chatbot.controller;

import com.explore.exploreeasternindia.chatbot.dto.ChatbotRequest;
import com.explore.exploreeasternindia.chatbot.dto.ChatbotResponse;
import com.explore.exploreeasternindia.chatbot.service.ChatbotService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ChatbotResponse chat(@RequestBody ChatbotRequest request) {

        String reply = chatbotService.sendMessage(
                request.getConversationId(),
                request.getMessage()
        );

        return new ChatbotResponse(
                request.getConversationId(),
                reply
        );
    }
}
