package org.example.hakerbot.util;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ\\s\\-]+$");
    private static final Pattern DATE_PATTERn = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return NAME_PATTERN.matcher(name).matches() && name.length() >= 2 && name.length() <= 20;
    }

    public static boolean isValidBirthDate(String date) {
        if (date == null || date.isBlank()) {
            return false;
        }
        return DATE_PATTERn.matcher(date).matches();
    }
}
