package org.example.hakerbot.service.imp.registration;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.example.hakerbot.util.InputValidator;
import org.springframework.stereotype.Component;

@Component
public class AwaitingMiddleNameHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.AWAITING_MIDDLENAME;
    }

    @Override
    public String handle(UserState state, String messageText) {
        String input = messageText.trim();

        if (!InputValidator.isValidName(input)) {
            return "Некорректное отчество! Используй только буквы: ";
        }

        state.setMiddleName(input);
        state.setState(State.AWAITING_BIRTHDATE);
        return "Введите дату рождения в формате (01.01.2000):";
    }
}
