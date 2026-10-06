package org.example.hakerbot.service;

import jakarta.transaction.Transactional;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.example.hakerbot.util.InputValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class HackerService {

    private final UserSessionRepository userRepository;
    private final BirthdayGreetingService birthdayGreetingService;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public HackerService(UserSessionRepository userRepository, BirthdayGreetingService birthdayGreetingService) {
        this.userRepository = userRepository;
        this.birthdayGreetingService = birthdayGreetingService;
    }

    @Transactional
    public String handleMessage(Long userId, String message) {
        String registerRowMessage = message.trim();
        String messageText = registerRowMessage.trim().toLowerCase();

        UserState state = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new UserState(userId, "START")));

        String birthdayGreeting = birthdayGreetingService.getGreetingIfBirthday(state);

        if (messageText.equals("/start")) {
            if (state.getBirthDate() != null) {
                state.setState("AWAITING_SCAN");
                return birthdayGreeting + " С возвращением! Соединение установлено. Напиши команду <сканировать порты>.";
            } else {
                state.setState("AWAITING_NAME");
                return "Добро пожаловать в шлюз. Перед началом авторизации, пожалуйста, введи твое Имя: ";
            }
        }

        switch (state.getState()) {
            case "AWAITING_NAME":
                if (!InputValidator.isValidName(registerRowMessage)) {
                    return "Некорректное имя! Используй только буквы. Пожалуйста, введи твое имя снова: ";
                }

                state.setFirstName(registerRowMessage);
                state.setState("AWAITING_LASTNAME");
                return "Введите фамилию: ";

            case "AWAITING_LASTNAME":
                if (!InputValidator.isValidName(registerRowMessage)) {
                    return "Некорректная фамилия! Используй только буквы. Пожалуйста, введи вашу фамилию снова: ";
                }

                state.setLastName(registerRowMessage);
                state.setState("AWAITING_MIDDLENAME");
                return "Введите отчество: ";

            case "AWAITING_MIDDLENAME":
                if (!InputValidator.isValidName(registerRowMessage)) {
                    return "Некорректное отчество! Используй только буквы. Пожалуйста, введи твое отчество снова: ";
                }

                state.setMiddleName(registerRowMessage);
                state.setState("AWAITING_BIRTHDATE");
                return "Введите дату рождения в формате (01.01.2000):";

            case "AWAITING_BIRTHDATE":
                if (!InputValidator.isValidBirthDate(registerRowMessage)) {
                    return "Неверный формат даты! Введи дату в формате ДД.ММ.ГГГГ цифрами через точку (например, 15.08.1998): ";
                }

                state.setBirthDate(LocalDate.parse(registerRowMessage,  dateFormatter));
                state.setState("AWAITING_SCAN");
                return "Регистрация успешна! Данные сохранены." +
                        "Соединение установлено. " +
                        "Ты подключился к защищенному шлюзу. Напиши команду <сканировать порты>.";

            case "AWAITING_SCAN":
                if (messageText.equals("сканировать порты")) {
                    state.setState("AWAITING_CONNECT");
                    return birthdayGreeting + " Найдена уязвимость: порт 8080. Напиши: <подключить порт 8080>";
                } else {
                    return birthdayGreeting + " Введи команду: <сканировать порты>";
                }
            case "AWAITING_CONNECT":
                if (messageText.equals("подключить порт 8080")) {
                    state.setState("CHOOSE_TARGET");
                    return birthdayGreeting + " Доступ получен. Выбери действие: <скачать архив> или <майнить крипту>";
                } else {
                   return birthdayGreeting + " Введи команду: <подключить порт 8080>";
                }
            case "CHOOSE_TARGET":
                if (messageText.equals("майнить крипту")) {
                    state.setState("START");
                    return birthdayGreeting + " Сервер перегрелся, миссия провалена! Напиши `/start` для сброса.";
                } else if (messageText.equals("скачать архив")) {
                    state.setState("AWAITING_MATH_ANSWER");
                    return birthdayGreeting + " Скачивание 90%... Тревога! Введи ответ уравнения: <(5 + 5) * 5>";
                } else {
                    return birthdayGreeting + " Выбери: <скачать архив> или <майнить крипту>";
                }
            case "AWAITING_MATH_ANSWER":
                state.setState("START");
                if (messageText.equals("50")) {
                    return birthdayGreeting + " Победа! Архив скачан. Напиши `/start` для новой игры.";
                } else {
                    return birthdayGreeting + " Ошибка вычислений! Данные удалены. Напиши `/start` для перезапуска.";
                }

            default:
                return "Напиши `/start`, чтобы начать игру.";
        }
    }
}
