package org.example.hakerbot.service.imp.hacker;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class HackerChooseTargetHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.HACKER_CHOOSE_TARGET;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("майнить крипту")) {
            state.setState(State.START);
            return " Сервер перегрелся, миссия провалена! Напиши `/start` для сброса.";
        } else if (messageText.equals("скачать архив")) {
            state.setState(State.HACKER_AWAITING_MATH_ANSWER);
            return " Скачивание 90%... Тревога! Введи ответ уравнения: <(5 + 5) * 5>";
        }
        return " Введите: <скачать архив> или <майнить крипту>";
    }
}
