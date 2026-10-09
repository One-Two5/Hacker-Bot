package org.example.hakerbot.service;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;

public interface GameScript {
    boolean supports(State state);
    String handle(UserState state, String messageText);
}
