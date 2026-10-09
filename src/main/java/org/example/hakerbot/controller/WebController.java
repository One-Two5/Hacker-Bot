package org.example.hakerbot.controller;

import org.example.hakerbot.dto.GameHttpResponseDto;
import org.example.hakerbot.service.GameWebService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    private final GameWebService gameWebService;

    public WebController(GameWebService gameWebService) {
        this.gameWebService = gameWebService;
    }

    @PostMapping("/start")
    public ResponseEntity<GameHttpResponseDto> handleWebMessage(@RequestParam Long userId,
                                                                @RequestParam String message) {
        GameHttpResponseDto responseDto = gameWebService.processWebMessage(userId, message);

        return ResponseEntity.ok(responseDto);
    }
}
