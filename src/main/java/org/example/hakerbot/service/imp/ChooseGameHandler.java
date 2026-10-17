package org.example.hakerbot.service.imp;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
class ChooseGameHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.CHOOSE_GAME;
    }

    @Override
    public String handle(UserState state, String messageText) {

        String text = messageText.trim().toLowerCase();

        if (text.contains("1") || text.contains("хакер")) {
            state.setState(State.HACKER_START);
            return "Вы запустили симуляцию 'Хакер'. Напиши команду <сканировать порты>.";
        }
        else if (text.contains("2") || text.contains("космос")) {
            state.setState(State.SPACE_START);
            return "Вы запустили сценарий 'Выжить в космосе'. Напиши команду <старт>.";
        }

        return "Пожалуйста, выберите игру: введите 1 (Хакер) или 2 (Космос).";
    }
}
