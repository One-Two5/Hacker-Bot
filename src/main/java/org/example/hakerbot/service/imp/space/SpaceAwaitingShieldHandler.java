package org.example.hakerbot.service.imp.space;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
class SpaceAwaitingShieldHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.SPACE_AWAITING_SHIELD;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("запустить щит")) {
            state.setState(State.SPACE_CHOOSE_PATH);
            return " Генератор запущен. Компьютер запрашивает, " +
                    "куда направить энергию для обеспечения безопасности." +
                    "Выбери один из вариантов:" +
                    "- <силовой барьер> (оптимальная защита ядра корабля)" +
                    "- <гиперпрыжок> (попытка уйти из опасного сектора)";
        }
        return " Корабль в опасности! Напиши: <запустить щит>";

    }
}
