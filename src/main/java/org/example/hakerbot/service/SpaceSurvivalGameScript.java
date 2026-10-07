package org.example.hakerbot.service;

import org.example.hakerbot.entity.UserState;
import org.example.hakerbot.repository.UserSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class SpaceSurvivalGameScript implements GameScript {

    private final BirthdayGreetingService birthdayGreetingService;
    private final UserSessionRepository userRepository;

    public SpaceSurvivalGameScript(BirthdayGreetingService birthdayGreetingService, UserSessionRepository userRepository) {
        this.birthdayGreetingService = birthdayGreetingService;
        this.userRepository = userRepository;
    }

    @Override
    public String handle(Long userId, String messageText) {

        UserState state = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(new UserState(userId, "SPACE_START")));

        String birthdayGreeting =  birthdayGreetingService.getGreetingIfBirthday(state);

        switch (state.getState()) {
            case "SPACE_START":
                if (messageText.equals("старт")) {
                    state.setState("SPACE_AWAITING_RADAR");
                    return birthdayGreeting + " Бортовой компьютер активирован. Приветствую, капитан! " +
                            "Обнаружен сбой в работе навигационного оборудования." +
                            "Главнокомандующий на связи. Введи команду: <включить радар>, чтобы просканировать пространство.";
                }
                return birthdayGreeting + " Введи команду: <старт>";

            case "SPACE_AWAITING_RADAR":
                if (messageText.equals("включить радар")) {
                    state.setState("SPACE_AWAITING_SHIELD");
                    return birthdayGreeting + " Сканирование... Обнаружен пояс метеоритов! " +
                            "Прямая угроза обшивке корабля через 3 минуты." +
                            "Защитные поля отключены. Напиши команду: <запустить щит>, чтобы активировать генератор энергии.";
                }
                return birthdayGreeting + " Неизвестная команда. Введи: <включить радар>";

            case "SPACE_AWAITING_SHIELD":
                if (messageText.equals("запустить щит")) {
                    state.setState("SPACE_CHOOSE_PATH");
                    return birthdayGreeting + " Генератор запущен. Компьютер запрашивает, " +
                            "куда направить энергию для обеспечения безопасности." +
                            "Выбери один из вариантов:" +
                            "- <силовой барьер> (оптимальная защита ядра корабля)" +
                            "- <гиперпрыжок> (попытка уйти из опасного сектора)";
                }
                return birthdayGreeting + " Корабль в опасности! Напиши: <запустить щит>";

            case "SPACE_CHOOSE_PATH":
                if (messageText.equals("гиперпрыжок")) {
                    state.setState("START");
                    return birthdayGreeting + " Ошибка! Энергосеть корабля перегружена из-за неисправных двигателей. " +
                            "Гипердвигатель взорвался. Корабль уничтожен." +
                            "Напиши `/start`, чтобы вернуться в главное меню.";
                } else if (messageText.equals("силовой барьер")) {
                    state.setState("SPACE_AWAITING_CALCULATION");
                    return birthdayGreeting + " Удар! Силовой барьер сдерживает метеоритный поток. " +
                            "Чтобы рассчитать безопасную траекторию выхода из пояса, реши уравнение:" +
                            "Введи ответ для компьютера: <(140 / 2) + 5>";
                }
                return "Неверный выбор. Напиши: <силовой барьер> или <гиперпрыжок>";

            case "SPACE_AWAITING_CALCULATION":
                state.setState("START");
                if (messageText.equals("75")) {
                    return birthdayGreeting + " Стабилизация! Траектория посчитана, системы пришли в норму. " +
                            "Космический корабль успешно вышел из зоны метеоритов." +
                            "Капитан, вы спасли экипаж! Миссия выполнена. Напиши `/start` для новой игры.";
                }
                return birthdayGreeting + " Неверные расчеты! Вы направили корабль прямо по курсу крупного астероида. " +
                        "Реактор разрушен, корабль потерян." +
                        "Миссия провалена. Напиши `/start` для перезапуска.";

            default:
                state.setState("START");
                return birthdayGreeting +" Ошибка космического симулятора. Напиши `/start` для возврата в меню.";
        }
    }
}
