package org.example.hakerbot.service.imp.registration;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.example.hakerbot.util.InputValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class AwaitingBirthdayHandler implements GameScript {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public State getTargetState() {
        return State.AWAITING_BIRTHDATE;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (!InputValidator.isValidBirthDate(messageText)) {
            return "Неверный формат даты! Введи дату в формате ДД.ММ.ГГГГ: ";
        }

        state.setBirthDate(LocalDate.parse(messageText, dateFormatter));

        state.setState(State.CHOOSE_GAME);
        return "Регистрация успешна! Данные сохранены. Выберите игру: 1. Хакер 2. Космос";
    }
}
