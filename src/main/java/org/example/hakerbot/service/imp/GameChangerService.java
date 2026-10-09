package org.example.hakerbot.service.imp;
import jakarta.transaction.Transactional;
import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameChangerService {

    private final UserSessionRepository userRepository;
    private final List<GameScript> gameScripts;

    public GameChangerService(UserSessionRepository userRepository, List<GameScript> gameScripts) {
        this.userRepository = userRepository;
        this.gameScripts = gameScripts;
    }

    @Transactional
    public String handleMessage(Long userId, String message) {
        UserState state = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new UserState(userId, State.START)));

        String response = gameScripts.stream()
                .filter(script -> script.supports(state.getState()))
                .findFirst()
                .map(script -> script.handle(state, message))
                .orElse("Ошибка!");

        userRepository.saveAndFlush(state);
        return response;
    }
}
