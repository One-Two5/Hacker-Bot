package org.example.hakerbot.service.imp;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.service.GameScript;
import org.springframework.stereotype.Service;

import static org.example.hakerbot.entity.State.*;

@Service
public class SpaceSurvivalGameScript implements GameScript {

    @Override
    public boolean supports(State state) {
        return state != null && state.name().startsWith("SPACE_");
    }

    @Override
    public String handle(UserState state, String messageText) {
        switch (state.getState()) {
            case SPACE_START:
                if (messageText.equals("старт")) {
                    state.setState(State.SPACE_AWAITING_RADAR);
                    return " Бортовой компьютер активирован. Приветствую, капитан! " +
                            "Обнаружен сбой в работе навигационного оборудования." +
                            "Главнокомандующий на связи. Введи команду: <включить радар>, чтобы просканировать пространство.";
                }
                return " Введи команду: <старт>";

            case SPACE_AWAITING_RADAR:
                if (messageText.equals("включить радар")) {
                    state.setState(State.SPACE_AWAITING_SHIELD);
                    return " Сканирование... Обнаружен пояс метеоритов! " +
                            "Прямая угроза обшивке корабля через 3 минуты." +
                            "Защитные поля отключены. Напиши команду: <запустить щит>, чтобы активировать генератор энергии.";
                }
                return " Неизвестная команда. Введи: <включить радар>";

            case SPACE_AWAITING_SHIELD:
                if (messageText.equals("запустить щит")) {
                    state.setState(State.SPACE_CHOOSE_PATH);
                    return " Генератор запущен. Компьютер запрашивает, " +
                            "куда направить энергию для обеспечения безопасности." +
                            "Выбери один из вариантов:" +
                            "- <силовой барьер> (оптимальная защита ядра корабля)" +
                            "- <гиперпрыжок> (попытка уйти из опасного сектора)";
                }
                return " Корабль в опасности! Напиши: <запустить щит>";

            case SPACE_CHOOSE_PATH:
                if (messageText.equals("гиперпрыжок")) {
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

            case SPACE_AWAITING_CALCULATION:
                state.setState(State.START);
                if (messageText.equals("75")) {
                    return " Стабилизация! Траектория посчитана, системы пришли в норму. " +
                            "Космический корабль успешно вышел из зоны метеоритов." +
                            "Капитан, вы спасли экипаж! Миссия выполнена. Напиши `/start` для новой игры.";
                }
                return " Неверные расчеты! Вы направили корабль прямо по курсу крупного астероида. " +
                        "Реактор разрушен, корабль потерян." +
                        "Миссия провалена. Напиши `/start` для перезапуска.";

            default:
                state.setState(State.START);
                return " Ошибка космического симулятора. Напиши `/start` для возврата в меню.";
        }
    }
}
