package org.example.hakerbot.dto;

public class HackerResponse {
    private String text;

    public HackerResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
