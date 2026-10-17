package org.example.hakerbot.service.imp.hacker;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class HackerStartHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.HACKER_START;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("сканировать порты")) {
            state.setState(State.HACKER_AWAITING_SCAN);
            return "Запущено сканирование портов... Введите команду <получить результаты>, чтобы проверить уязвимости.";
        } else {
            return "Введите команду <сканировать порты>.";
        }
    }
}
