package org.example.hakerbot.service.imp.hacker;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class HackerAwaitingScanHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.HACKER_AWAITING_SCAN;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("получить результаты")) {
            state.setState(State.HACKER_AWAITING_CONNECT);
            return " Найдена уязвимость: порт 8080. Введите: <подключить порт 8080>";
        }
        return "Введите команду: <получить результаты>";
    }
}
