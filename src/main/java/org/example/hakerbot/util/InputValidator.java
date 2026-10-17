package org.example.hakerbot.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Zа-яА-ЯёЁ\\s\\-]+$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{2}\\.\\d{2}\\.\\d{4}$");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public static boolean isValidName(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        return NAME_PATTERN.matcher(name).matches() && name.length() >= 2 && name.length() <= 20;
    }

    public static boolean isValidBirthDate(String date) {
        if (date == null || date.isBlank() || !DATE_PATTERN.matcher(date).matches()) {
            return false;
        }
        try {

            LocalDate birthDate = LocalDate.parse(date, DATE_FORMATTER);

            return !birthDate.isAfter(LocalDate.now());

        }catch (DateTimeParseException e) {
            return false;
        }
    }
}
