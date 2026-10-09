package org.example.hakerbot.service.imp;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class ChooseGameScript implements GameScript {

    @Override
    public boolean supports(State state) {
        return state == State.CHOOSE_GAME || state == State.START;
    }

    @Override
    public String handle(UserState state, String messageText) {
        String text = messageText.toLowerCase().trim();

        if (text.equals("/start")) {
            if (state.getBirthDate() == null) {
                state.setState(State.AWAITING_NAME);
                return "Добро пожаловать в систему." +
                " Перед началомввввв, пожалуйста, введи твое Имя: ";
            }
            else {
                state.setState(State.CHOOSE_GAME);
               return getMenuText();
            }
        }

        if (text.contains("1") || text.contains("хакер")) {
            state.setState(State.HACKER_START);
            return "Вы запустили симуляцию 'Хакер'. Напиши команду <сканировать порты>.";
        }
        else if (text.contains("2") || text.contains("космос")) {
            state.setState(State.SPACE_START);
            return "Вы запустили сценарий 'Выжить в космосе'. Напиши команду <старт>.";
        }

        return getMenuText();
    }

    private String getMenuText() {
        return "Пожалуйста, введите цифру 1 (Хакер) или 2 (Космос).";
    }
}
