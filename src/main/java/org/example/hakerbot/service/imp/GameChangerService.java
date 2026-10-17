package org.example.hakerbot.service.imp;
import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameChangerService {

    private final Map<State, GameScript> gameScripts;

    public GameChangerService(List<GameScript> gameScripts) {
        this.gameScripts = gameScripts.stream()
                .collect(Collectors.toMap(
                        GameScript::getTargetState, Function.identity()
                ));
    }

    public String handleMessage(UserState state, String message) {

        GameScript script = gameScripts.get(state.getState());

        if (script == null) {
            return "Ошибка: Сценарий для состояния " + state.getState() + " не зарегистрирован!";
        }
        return script.handle(state, message);
    }
}
