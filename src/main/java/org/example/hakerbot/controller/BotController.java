package org.example.hakerbot.controller;

import org.example.hakerbot.dto.HackerResponse;
import org.example.hakerbot.service.HackerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotController {

    private final HackerService hackerService;

    public BotController(HackerService hackerService) {
        this.hackerService = hackerService;
    }

    @PostMapping("/start")
    public HackerResponse sendText(@RequestParam Long userId, @RequestParam String message) {
        String reply = hackerService.handleMessage(userId, message);
        return new  HackerResponse(reply);
    }
}
