package org.example.hakerbot.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GameHttpResponseDto {

    private String birthdayGreeting;
    private String gameMessage;

    public GameHttpResponseDto(String birthdayGreeting, String gameMessage) {
        this.birthdayGreeting = birthdayGreeting;
        this.gameMessage = gameMessage;
    }

    public String getBirthdayGreeting() {
        return birthdayGreeting;
    }

    public void setBirthdayGreeting(String birthdayGreeting) {
        this.birthdayGreeting = birthdayGreeting;
    }

    public String getGameMessage() {
        return gameMessage;
    }

    public void setGameMessage(String gameMessage) {
        this.gameMessage = gameMessage;
    }
}
