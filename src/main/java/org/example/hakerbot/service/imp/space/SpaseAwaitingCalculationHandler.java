package org.example.hakerbot.service.imp.space;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
class SpaseAwaitingCalculationHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.SPACE_AWAITING_CALCULATION;
    }

    @Override
    public String handle(UserState state, String messageText) {
        state.setState(State.START);
        if (messageText.trim().equals("75")) {
            return " Стабилизация! Траектория посчитана, системы пришли в норму. " +
                    "Космический корабль успешно вышел из зоны метеоритов." +
                    "Капитан, вы спасли экипаж! Миссия выполнена. Напиши `/start` для новой игры.";
        }
        return " Неверные расчеты! Вы направили корабль прямо по курсу крупного астероида. " +
                "Реактор разрушен, корабль потерян." +
                "Миссия провалена. Напиши `/start` для перезапуска.";
    }
}
