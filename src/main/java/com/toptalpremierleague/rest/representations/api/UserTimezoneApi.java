package com.toptalpremierleague.rest.representations.api;

import java.util.Optional;

public final class UserTimezoneApi {

    public int getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(int timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int timezoneId;
    private String name;

    public Optional<String> getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(Optional<String> userEmailId) {
        this.userEmailId = userEmailId;
    }

    private Optional<String> userEmailId = Optional.empty();
}
