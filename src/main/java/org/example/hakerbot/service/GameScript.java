package org.example.hakerbot.service;

import org.example.hakerbot.entity.State;
import org.example.hakerbot.entity.UserState;

public interface GameScript {

    State getTargetState();
    String handle(UserState state, String messageText);
}
