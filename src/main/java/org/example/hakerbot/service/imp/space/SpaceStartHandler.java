package org.example.hakerbot.service.imp.space;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Component;

@Component
public class SpaceStartHandler implements GameScript {

    @Override
    public State getTargetState() {
        return State.SPACE_START;
    }

    @Override
    public String handle(UserState state, String messageText) {
        if (messageText.trim().equalsIgnoreCase("старт")) {
            state.setState(State.SPACE_AWAITING_RADAR);
            return "Бортовой компьютер активирован. Приветствую, капитан! " +
                    "Обнаружен сбой в работе навигационного оборудования." +
                    "Главнокомандующий на связи. Введи команду: <включить радар>, чтобы просканировать пространство.";
        } else {
            return "Введите команду: <старт>";
        }
    }
}
