package org.example.hakerbot.service;

import org.example.hakerbot.dto.GameHttpResponseDto;
import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.service.imp.GameChangerService;
import org.springframework.stereotype.Service;

@Service
public class GameWebService {

    private final UserSessionRepository userRepository;
    private final GameChangerService gameChangerService;
    private final BirthdayGreetingService birthdayGreetingService;

    public GameWebService(UserSessionRepository userRepository, GameChangerService gameChangerService, BirthdayGreetingService birthdayGreetingService) {
        this.userRepository = userRepository;
        this.gameChangerService = gameChangerService;
        this.birthdayGreetingService = birthdayGreetingService;
    }

    public GameHttpResponseDto processWebMessage(Long userId, String message) {
        UserState state = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new UserState(userId, State.START)));

        String gameText = gameChangerService.handleMessage(state.getUserId(), message);

        if (state.getBirthDate() != null) {
            String birthdayGreeting = birthdayGreetingService.getGreetingIfBirthday(state);

            if (birthdayGreeting != null && !birthdayGreeting.isBlank()) {
                gameText = birthdayGreeting + gameText;
            }
        }
        userRepository.saveAndFlush(state);
        return new GameHttpResponseDto(null, gameText);
    }
}
