package org.example.hakerbot.service.imp.hacker;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class HackerAwaitingMathAnswerHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.HACKER_AWAITING_MATH_ANSWER;
    }

    @Override
    public String handle(UserState state, String messageText) {
        state.setState(State.START);

        if (messageText.trim().equals("50")) {
            return "Победа! Архив скачан. Напиши `/start` для новой игры.";
        }

        return "Ошибка в симуляции Хакер. Сессия сброшена. Напиши `/start`.";
    }
}
