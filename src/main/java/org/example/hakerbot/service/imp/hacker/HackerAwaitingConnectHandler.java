package org.example.hakerbot.service.imp.hacker;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class HackerAwaitingConnectHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.HACKER_AWAITING_CONNECT;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if ("подключить порт 8080".trim().equalsIgnoreCase(messageText.trim())) {
            state.setState(State.HACKER_CHOOSE_TARGET);
            return "Доступ получен! Выбери действие: <скачать архив> или <майнить крипту>";
        }
        return "Введите команду: <подключить порт 8080>";
    }
}
