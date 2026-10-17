package org.example.hakerbot.service;

import org.example.hakerbot.entity.UserState;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BirthdayGreetingService {


   public String getWebGreeting(UserState state) {
       if (!getGreetingIfBirthday(state)) {
           return "";
       }
       markAsCongratulated(state);
       return "С ДНЕМ РОЖДЕНИЯ!!!";
   }

   public String getTgGreeting(UserState state, boolean isEvenMinute) {
       if (!getGreetingIfBirthday(state)) {
           return "";
       }
       markAsCongratulated(state);
       return isEvenMinute ? "С Днём Рождения!!! Ты стал на год ближе к пенсии, держись там!" : "С ДНЕМ РОЖДЕНИЯ!!!";

   }

    public boolean getGreetingIfBirthday(UserState state) {
        if (state == null || state.getBirthDate() == null) {
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate birthDate = state.getBirthDate();

        boolean isBirthdayToday = today.getDayOfMonth() == birthDate.getDayOfMonth()
                && today.getMonth() == birthDate.getMonth();

        LocalDate lastCongratulation = state.getLastBirthdayGreeting();

        boolean alreadyGreetingThisYear = lastCongratulation != null
                && lastCongratulation.getYear() == today.getYear();

        return isBirthdayToday && !alreadyGreetingThisYear;
    }

    private void markAsCongratulated(UserState state) {
        state.setLastBirthdayGreeting(LocalDate.now());
    }
}
