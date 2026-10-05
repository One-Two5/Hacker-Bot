package org.example.hakerbot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_states")
public class UserState {

    @Id
    private Long userId;
    private String state;

    public UserState() {
    }

    public UserState(Long userId, String state) {
        this.userId = userId;
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
