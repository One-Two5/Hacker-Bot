package org.example.hakerbot.service;

import jakarta.transaction.Transactional;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.springframework.stereotype.Service;


@Service
public class HackerService {

    private final UserSessionRepository userRepository;

    public HackerService(UserSessionRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public String handleMessage(Long userId, String message) {
        String messageText = message.trim().toLowerCase();

        if (messageText.equals("/start")) {
            updateState(userId, "AWAITING_SCAN");
            return "Соединение установлено. Ты подключился к защищенному шлюзу. Напиши команду <сканировать порты>.";
        }

        String currentState = userRepository.findById(userId)
                .map(UserState::getState)
                .orElse("START");

        switch (currentState) {
            case "AWAITING_SCAN":
                if (messageText.equals("сканировать порты")) {
                    updateState(userId, "AWAITING_CONNECT");
                    return "🔍 Найдена уязвимость: порт 8080. Напиши: <подключить порт 8080>";
                } else {
                    return "Введи команду: <сканировать порты>";
                }
            case "AWAITING_CONNECT":
                if (messageText.equals("подключить порт 8080")) {
                    updateState(userId,"CHOOSE_TARGET");
                    return "Доступ получен. Выбери действие: <скачать архив* или *майнить крипту>";
                } else {
                   return "Введи команду: <подключить порт 8080>";
                }
            case "CHOOSE_TARGET":
                if (messageText.equals("майнить крипту")) {
                    updateState(userId,"START");
                    return "Сервер перегрелся, миссия провалена! Напиши `/start` для сброса.";
                } else if (messageText.equals("скачать архив")) {
                    updateState(userId,"AWAITING_MATH_ANSWER");
                    return  "Скачивание 90%... Тревога! Введи ответ уравнения: <(5 + 5) * 5>";
                } else {
                    return "Выбери: <скачать архив> или <майнить крипту>";
                }
            case "AWAITING_MATH_ANSWER":
                updateState(userId, "START");
                if (messageText.equals("50")) {
                    return "Победа! Архив скачан. Напиши `/start` для новой игры.";
                } else {
                    return "Ошибка вычислений! Данные удалены. Напиши `/start` для перезапуска.";
                }

            default:
                return "Напиши `/start`, чтобы начать игру.";
        }
    }

    private void updateState(Long userId, String state) {
        UserState session = userRepository.findById(userId)
                .orElse(new UserState(userId, state));
        session.setState(state);
        userRepository.save(session);
    }
}
