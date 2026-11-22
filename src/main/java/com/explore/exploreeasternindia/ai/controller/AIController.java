package com.explore.exploreeasternindia.ai.controller;

import com.explore.exploreeasternindia.ai.service.AIService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/ask")
    public String askAI(@RequestParam String prompt) {
        return aiService.generateResponse(prompt);
    }
}
