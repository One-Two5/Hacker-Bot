package org.example.hakerbot.tgclient;

import jakarta.transaction.Transactional;
import org.example.hakerbot.dto.TgBotResultDto;
import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.service.BirthdayGreetingService;
import org.example.hakerbot.service.imp.GameChangerService;
import org.example.hakerbot.service.imp.RegistrationScript;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TgGameService {

    private final UserSessionRepository userRepository;
    private final GameChangerService gameChangerService;
    private final BirthdayGreetingService birthdayGreetingService;
    private final RegistrationScript registrationScript;

    public TgGameService(UserSessionRepository userRepository,
                         GameChangerService gameChangerService, BirthdayGreetingService birthdayGreetingService, RegistrationScript registrationScript) {
        this.userRepository = userRepository;
        this.gameChangerService = gameChangerService;
        this.birthdayGreetingService = birthdayGreetingService;
        this.registrationScript = registrationScript;
    }

    @Transactional
    public TgBotResultDto processTgMessage(Long chatId, String messageText) {
        UserState state = userRepository.findById(chatId)
                .orElseGet(() -> userRepository.save(new UserState(chatId, State.START)));

        boolean wasAlreadyRegistered = state.getBirthDate() != null;

        String gameResponse = gameChangerService.handleMessage(state.getUserId(), messageText);

        String birthdayGreeting = null;
        boolean isRegistering = state.getState() == State.START || registrationScript.supports(state.getState());
        boolean justRegistered = !wasAlreadyRegistered && state.getBirthDate() != null;

        if ((wasAlreadyRegistered || justRegistered) && !isRegistering) {
            LocalDateTime now = LocalDateTime.now();
            boolean isEvenMinute = (now.getMinute() % 2 == 0);

            String greeting = birthdayGreetingService.getGreetingIfBirthday(state, isEvenMinute);
            if (greeting != null && !greeting.isBlank()) {
                birthdayGreeting = greeting;
            }
        }

        userRepository.saveAndFlush(state);

        return new TgBotResultDto(birthdayGreeting, gameResponse);
    }
}
