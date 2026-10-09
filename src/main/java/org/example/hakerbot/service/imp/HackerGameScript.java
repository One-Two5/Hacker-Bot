package org.example.hakerbot.service.imp;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Service;

@Service
public class HackerGameScript implements GameScript {

    @Override
    public boolean supports(State state) {
        return state != null && state.name().startsWith("HACKER_");
    }

    @Override
    public String handle(UserState state, String messageText) {
        switch (state.getState()) {
            case HACKER_START:
                if (messageText.equals("сканировать порты")) {
                    state.setState(State.HACKER_AWAITING_CONNECT);
                    return " Найдена уязвимость: порт 8080. Напиши: <подключить порт 8080>";
                }
                return "Введи команду: <сканировать порты>";

            case HACKER_AWAITING_CONNECT:
                if (messageText.equals("подключить порт 8080")) {
                    state.setState(State.HACKER_CHOOSE_TARGET);
                    return " Доступ получен. Выбери действие: <скачать архив> или <майнить крипту>";
                }
                return "Введи команду: <подключить порт 8080>";

            case HACKER_CHOOSE_TARGET:
                if (messageText.equals("майнить крипту")) {
                    state.setState(State.START);
                    return " Сервер перегрелся, миссия провалена! Напиши `/start` для сброса.";
                } else if (messageText.equals("скачать архив")) {
                    state.setState(State.HACKER_AWAITING_MATH_ANSWER);
                    return " Скачивание 90%... Тревога! Введи ответ уравнения: <(5 + 5) * 5>";
                }
                return " Выбери: <скачать архив> или <майнить крипту>";

            case HACKER_AWAITING_MATH_ANSWER:
                state.setState(State.START);
                if (messageText.equals("50")) {
                    return "Победа! Архив скачан. Напиши `/start` для новой игры.";
                }
                return " Ошибка вычислений! Данные удалены. Напиши `/start` для перезапуска.";

            default:
                state.setState(State.START);
                return " Ошибка в симуляции Хакер. Сессия сброшена. Напиши `/start`.";
        }
    }
}
