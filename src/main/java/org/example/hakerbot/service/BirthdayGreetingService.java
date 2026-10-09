package org.example.hakerbot.service;

import org.example.hakerbot.entity.UserState;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BirthdayGreetingService {

    public String getGreetingIfBirthday(UserState state) {
        if (state == null || state.getBirthDate() == null) {
            return "";
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = state.getBirthDate();

        boolean isBirthdayToday = today.getDayOfMonth() == birthDate.getDayOfMonth()
                && today.getMonth() == birthDate.getMonth();

        LocalDate lastCongratulation = state.getLastBirthdayGreeting();

        boolean alreadyGreetingThisYear = lastCongratulation != null
                && lastCongratulation.getYear() == today.getYear();

        if (isBirthdayToday && !alreadyGreetingThisYear) {
            state.setLastBirthdayGreeting(today);
            return "С ДНЕМ РОЖДЕНИЯ!!!";
        }
        return "";
    }

    public String getGreetingIfBirthday(UserState state, boolean isEvenMinute) {
        if (state == null || state.getBirthDate() == null) {
            return "";
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = state.getBirthDate();

        boolean isBirthdayToday = today.getDayOfMonth() == birthDate.getDayOfMonth()
                && today.getMonth() == birthDate.getMonth();

        LocalDate lastCongratulation = state.getLastBirthdayGreeting();

        boolean alreadyGreetingThisYear = lastCongratulation != null
                && lastCongratulation.getYear() == today.getYear();

        if (isBirthdayToday && !alreadyGreetingThisYear) {
            state.setLastBirthdayGreeting(today);

            if (isEvenMinute) {
                return "С Днём Рождения!!! Ты стал на год ближе к пенсии, держись там!";
            }
            else {
                return "С ДНЕМ РОЖДЕНИЯ!!!";
            }
        }
        return "";
    }
}
