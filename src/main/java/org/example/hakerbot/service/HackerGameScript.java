package org.example.hakerbot.service;

import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class HackerGameScript implements GameScript {

    private final UserSessionRepository userRepository;
    private final BirthdayGreetingService birthdayGreetingService;

    public HackerGameScript(UserSessionRepository userRepository, BirthdayGreetingService birthdayGreetingService) {
        this.userRepository = userRepository;
        this.birthdayGreetingService = birthdayGreetingService;
    }

    @Override
    public String handle(Long userId, String messageText) {
        UserState state = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new UserState(userId, "SPACE_START")));

        String birthdayGreeting =  birthdayGreetingService.getGreetingIfBirthday(state);

        switch (state.getState()) {
            case "AWAITING_SCAN":
                if (messageText.equals("сканировать порты")) {
                    state.setState("AWAITING_CONNECT");
                    return birthdayGreeting + " Найдена уязвимость: порт 8080. Напиши: <подключить порт 8080>";
                }
                return "Введи команду: <сканировать порты>";

            case "AWAITING_CONNECT":
                if (messageText.equals("подключить порт 8080")) {
                    state.setState("CHOOSE_TARGET");
                    return birthdayGreeting + " Доступ получен. Выбери действие: <скачать архив> или <майнить крипту>";
                }
                return "Введи команду: <подключить порт 8080>";

            case "CHOOSE_TARGET":
                if (messageText.equals("майнить крипту")) {
                    state.setState("START");
                    return birthdayGreeting + " Сервер перегрелся, миссия провалена! Напиши `/start` для сброса.";
                } else if (messageText.equals("скачать архив")) {
                    state.setState("AWAITING_MATH_ANSWER");
                    return birthdayGreeting + " Скачивание 90%... Тревога! Введи ответ уравнения: <(5 + 5) * 5>";
                }
                return birthdayGreeting + " Выбери: <скачать архив> или <майнить крипту>";

            case "AWAITING_MATH_ANSWER":
                state.setState("START");
                if (messageText.equals("50")) {
                    return birthdayGreeting + "Победа! Архив скачан. Напиши `/start` для новой игры.";
                }
                return birthdayGreeting + " Ошибка вычислений! Данные удалены. Напиши `/start` для перезапуска.";

            default:
                state.setState("START");
                return birthdayGreeting + " Ошибка в симуляции Хакер. Сессия сброшена. Напиши `/start`.";
        }
    }
}
