package org.example.hakerbot.service.imp;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.example.hakerbot.util.InputValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class RegistrationScript implements GameScript {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public boolean supports(State state) {
        return state == State.AWAITING_NAME ||
                state == State.AWAITING_LASTNAME ||
                state == State.AWAITING_MIDDLENAME ||
                state == State.AWAITING_BIRTHDATE;
    }

    @Override
    public String handle(UserState state, String messageText) {
        String inputMessage = messageText.trim();
        if (state.getBirthDate() == null) {

            switch (state.getState()) {
                case AWAITING_NAME:
                    if (!InputValidator.isValidName(inputMessage)) {
                        return "Некорректное имя! Используй только буквы: ";
                    }

                    state.setFirstName(inputMessage);
                    state.setState(State.AWAITING_LASTNAME);
                    return "Введите фамилию: ";

                case AWAITING_LASTNAME:
                    if (!InputValidator.isValidName(inputMessage)) {
                        return "Некорректная фамилия! Используй только буквы: ";
                    }

                    state.setLastName(inputMessage);
                    state.setState(State.AWAITING_MIDDLENAME);
                    return "Введите отчество: ";

                case AWAITING_MIDDLENAME:
                    if (!InputValidator.isValidName(inputMessage)) {
                        return "Некорректное отчество! Используй только буквы: ";
                    }

                    state.setMiddleName(inputMessage);
                    state.setState(State.AWAITING_BIRTHDATE);
                    return "Введите дату рождения в формате (01.01.2000):";

                case AWAITING_BIRTHDATE:
                    if (!InputValidator.isValidBirthDate(inputMessage)) {
                        return "Неверный формат даты! Введи дату в формате ДД.ММ.ГГГГ: ";
                    }

                    state.setBirthDate(LocalDate.parse(inputMessage, dateFormatter));
                    state.setState(State.CHOOSE_GAME);
                    return "Регистрация успешна! Данные сохранены. Выберите игру: 1.Хакер 2.Космос";

                default:
                    return "Ошибка регистрации.";
            }
        }
        return "";
    }
}
