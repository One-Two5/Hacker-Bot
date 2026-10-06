package org.example.hakerbot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_states")
public class UserState {

    @Id
    private Long userId;
    private String state;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private Integer lastBirthdayGreeting;

    public UserState() {
    }

    public UserState(Long userId, String state) {
        this.userId = userId;
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public Integer getLastBirthdayGreeting() {
        return lastBirthdayGreeting;
    }

    public void setLastBirthdayGreeting(Integer lastBirthdayGreeting) {
        this.lastBirthdayGreeting = lastBirthdayGreeting;
    }
}
