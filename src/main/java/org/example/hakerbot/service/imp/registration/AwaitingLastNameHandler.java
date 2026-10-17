package org.example.hakerbot.service.imp.registration;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.example.hakerbot.util.InputValidator;
import org.springframework.stereotype.Component;

@Component
public class AwaitingLastNameHandler implements GameScript {


    @Override
    public State getTargetState() {
        return State.AWAITING_LASTNAME;
    }

    @Override
    public String handle(UserState state, String messageText) {
        String input = messageText.trim();

        if (!InputValidator.isValidName(input)) {
            return "Некорректная фамилия! Используй только буквы: ";
        }

        state.setLastName(input);
        state.setState(State.AWAITING_MIDDLENAME);
        return "Введите отчество: ";
    }
}
