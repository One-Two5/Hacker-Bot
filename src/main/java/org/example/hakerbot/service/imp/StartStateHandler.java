package org.example.hakerbot.service.imp;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
class StartStateHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.START;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (state.getBirthDate() == null) {
            state.setState(State.AWAITING_NAME);
            return "Добро пожаловать в систему! Перед началом, пожалуйста, введи твое Имя: ";
        }

        state.setState(State.CHOOSE_GAME);
        return "Добро пожаловать обратно! Пожалуйста, выберите игру: 1. Хакер, 2. Космос";
    }
}
