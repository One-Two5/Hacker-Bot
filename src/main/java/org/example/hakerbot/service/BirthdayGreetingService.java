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

        boolean alreadyGreetingThisYear = state.getLastBirthdayGreeting() != null
                && state.getLastBirthdayGreeting() == today.getYear();

        if (isBirthdayToday && !alreadyGreetingThisYear) {
            state.setLastBirthdayGreeting(today.getYear());

            return "С ДНЕМ РОЖДЕНИЯ!!!";
        }
        return "";
    }
}
