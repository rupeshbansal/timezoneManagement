package com.toptalpremierleague.rest.representations;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public final class UserTimezoneId {
    @NotBlank
    @NotNull
    @Length(min=2, max=255)
    private Integer employeeId;
    @NotBlank
    private String timezoneId;


    public UserTimezoneId(){
    }

    public UserTimezoneId(Integer employeeId, String timezoneId) {
        this.employeeId = employeeId;
        this.timezoneId = timezoneId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    @Override
    public String toString() {
        return "Emplyee]";
    }

}
