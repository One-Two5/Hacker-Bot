package org.example.hakerbot.service;

import java.time.LocalDate;

public interface GameScript {
    String handle(Long userId, String messageText);
}
