package org.example.hakerbot.service.imp.space;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
class SpaseChoosePathHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.SPACE_CHOOSE_PATH;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("гиперпрыжок")) {
            state.setState(State.START);
            return " Ошибка! Энергосеть корабля перегружена из-за неисправных двигателей. " +
                    "Гипердвигатель взорвался. Корабль уничтожен." +
                    "Напиши `/start`, чтобы вернуться в главное меню.";
        } else if (messageText.equals("силовой барьер")) {
            state.setState(State.SPACE_AWAITING_CALCULATION);
            return " Удар! Силовой барьер сдерживает метеоритный поток. " +
                    "Чтобы рассчитать безопасную траекторию выхода из пояса, реши уравнение:" +
                    "Введи ответ для компьютера: <(140 / 2) + 5>";
        }
        return "Неверный выбор. Напиши: <силовой барьер> или <гиперпрыжок>";
    }
}
