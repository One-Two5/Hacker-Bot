package org.example.hakerbot.tgclient;

import jakarta.transaction.Transactional;
import org.example.hakerbot.dto.TgBotResultDto;
import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.service.BirthdayGreetingService;
import org.example.hakerbot.service.imp.GameChangerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TgGameService {

    private final UserSessionRepository userRepository;
    private final GameChangerService gameChangerService;
    private final BirthdayGreetingService birthdayGreetingService;

    public TgGameService(UserSessionRepository userRepository,
                         GameChangerService gameChangerService, BirthdayGreetingService birthdayGreetingService) {
        this.userRepository = userRepository;
        this.gameChangerService = gameChangerService;
        this.birthdayGreetingService = birthdayGreetingService;
    }

    @Transactional
    public TgBotResultDto processTgMessage(Long chatId, String messageText) {
        UserState state = userRepository.findById(chatId)
                .orElseGet(() -> userRepository.save(new UserState(chatId, State.START)));

        boolean wasAlreadyRegistered = state.getBirthDate() != null;

        String gameResponse = gameChangerService.handleMessage(state, messageText);

        String birthdayGreeting = null;

        if (wasAlreadyRegistered) {

            boolean isEvenMinute = LocalDateTime.now().getMinute() % 2 == 0;
            String greeting = birthdayGreetingService.getTgGreeting(state, isEvenMinute);

            if (greeting != null && !greeting.isBlank()) {
                birthdayGreeting = greeting;
            }
        }

        userRepository.saveAndFlush(state);

        return new TgBotResultDto(birthdayGreeting, gameResponse);
    }
}
