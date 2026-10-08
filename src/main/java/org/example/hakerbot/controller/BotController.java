package org.example.hakerbot.controller;

import org.example.hakerbot.dto.GameHttpResponse;
import org.example.hakerbot.service.GameChangerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    private final GameChangerService gameChangerService;

    public BotController(GameChangerService gameChangerService) {
        this.gameChangerService = gameChangerService;
    }

    @PostMapping("/start")
    public GameHttpResponse sendText(@RequestParam Long userId, @RequestParam String message) {
        String response = gameChangerService.handleMessage(userId, message);
        return new GameHttpResponse(response);
    }
}
