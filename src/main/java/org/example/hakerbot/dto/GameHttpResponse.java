package org.example.hakerbot.dto;

public class GameHttpResponse {
    private String text;

    public GameHttpResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
