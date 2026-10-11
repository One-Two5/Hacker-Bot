package org.example.hakerbot.dto;

public class TgBotResultDto {

    private String birthdayGreeting;
    private String gameResponse;

    public TgBotResultDto() {}

    public TgBotResultDto(String birthdayGreeting, String gameResponse) {
        this.birthdayGreeting = birthdayGreeting;
        this.gameResponse = gameResponse;
    }

    public String getBirthdayGreeting() {
        return birthdayGreeting;
    }

    public void setBirthdayGreeting(String birthdayGreeting) {
        this.birthdayGreeting = birthdayGreeting;
    }

    public String getGameResponse() {
        return gameResponse;
    }

    public void setGameResponse(String gameResponse) {
        this.gameResponse = gameResponse;
    }
}
