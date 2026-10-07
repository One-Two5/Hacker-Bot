package org.example.hakerbot.service;

import jakarta.transaction.Transactional;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.util.InputValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class GameChangerService {

    private final UserSessionRepository userRepository;
    private final BirthdayGreetingService birthdayGreetingService;
    private final HackerGameScript hackerGameScript;
    private final SpaceSurvivalGameScript spaceSurvivalGameScript;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public GameChangerService(UserSessionRepository userRepository,
                              BirthdayGreetingService birthdayGreetingService,
                              HackerGameScript hackerGameScript,
                              SpaceSurvivalGameScript spaceSurvivalGameScript) {
        this.userRepository = userRepository;
        this.birthdayGreetingService = birthdayGreetingService;
        this.hackerGameScript = hackerGameScript;
        this.spaceSurvivalGameScript = spaceSurvivalGameScript;
    }

    @Transactional
    public String handleMessage(Long userId, String message) {
        String inputMessage = message.trim();
        String messageText = inputMessage.toLowerCase().trim();

        UserState state = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new UserState(userId, "START")));

        String birthdayGreeting = birthdayGreetingService.getGreetingIfBirthday(state);

        if (messageText.equals("/start")) {
            if (state.getBirthDate() == null) {
                state.setState("AWAITING_NAME");
                return birthdayGreeting + " Добро пожаловать в систему." +
                        " Перед началом авторизации, пожалуйста, введи твое Имя: ";
            } else {
                state.setState("CHOOSE_GAME");
                return birthdayGreeting + " Главное меню выбора симуляций:" +
                        "1.Хакер (Хакерский квест)" +
                        "2.Космос (Выжить в космосе)" +
                        "Напиши цифру или название игры, чтобы начать.";
            }
        }

        switch (state.getState()) {
            case "AWAITING_NAME":
                if (!InputValidator.isValidName(inputMessage)) {
                    return "Некорректное имя! Используй только буквы: ";
                }
                state.setFirstName(inputMessage);
                state.setState("AWAITING_LASTNAME");
                return "Введите фамилию: ";

            case "AWAITING_LASTNAME":
                if (!InputValidator.isValidName(inputMessage)) {
                    return "Некорректная фамилия! Используй только буквы: ";
                }

                state.setLastName(inputMessage);
                state.setState("AWAITING_MIDDLENAME");
                return "Введите отчество: ";

            case "AWAITING_MIDDLENAME":
                if (!InputValidator.isValidName(inputMessage)) {
                    return "Некорректное отчество! Используй только буквы: ";
                }

                state.setMiddleName(inputMessage);
                state.setState("AWAITING_BIRTHDATE");
                return "Введите дату рождения в формате (01.01.2000):";

            case "AWAITING_BIRTHDATE":
                if (!InputValidator.isValidBirthDate(inputMessage)) {
                    return "Неверный формат даты! Введи дату в формате ДД.ММ.ГГГГ: ";
                }

                state.setBirthDate(LocalDate.parse(inputMessage, dateFormatter));
                state.setState("CHOOSE_GAME");
                return "Регистрация успешна! Данные сохранены. Выберите игру: 1.Хакер 2.Космос";

            case "CHOOSE_GAME":
                if (messageText.contains("1") || messageText.contains("хакер")) {
                    state.setState("AWAITING_SCAN");
                    return birthdayGreeting + " Вы запустили симуляцию 'Хакер'. Соединение установлено. Напиши команду <сканировать порты>.";
                }
                else if (messageText.contains("2") || messageText.contains("космос")) {
                    state.setState("SPACE_START");
                    return birthdayGreeting + " Вы запустили сценарий 'Выжить в космосе'. Напиши команду <старт> для запуска бортового компьютера.";
                }
                return birthdayGreeting + " Неверный выбор. Пожалуйста, введите цифру 1 (Хакер) или 2 (Космос).";
        }

        String response;

        if (state.getState().startsWith("SPACE_")) {
            response = spaceSurvivalGameScript.handle(userId, messageText);
        } else {
            response = hackerGameScript.handle(userId, messageText);
        }
        return response;
    }
}
