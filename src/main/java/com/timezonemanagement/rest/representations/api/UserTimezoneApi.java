package com.timezonemanagement.rest.representations.api;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull
    private Integer timezoneId;

    @NotEmpty
    @Length(min = 2, max = 255)
    private String name;

    public Optional<String> getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(Optional<String> userEmailId) {
        this.userEmailId = userEmailId;
    }

    private Optional<String> userEmailId = Optional.empty();
}