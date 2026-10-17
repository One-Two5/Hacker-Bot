package org.example.hakerbot.service.imp.space;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class SpaceAwaitingRadarHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.SPACE_AWAITING_RADAR;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("включить радар")) {
            state.setState(State.SPACE_AWAITING_SHIELD);
            return "Сканирование... Обнаружен пояс метеоритов! " +
                    "Прямая угроза обшивке корабля через 3 минуты." +
                    "Защитные поля отключены. Напиши команду: <запустить щит>, чтобы активировать генератор энергии.";
        }
        return "Неизвестная команда. Введите: <включить радар>";
    }
}
