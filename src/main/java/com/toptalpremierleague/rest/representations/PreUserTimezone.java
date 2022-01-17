package com.toptalpremierleague.rest.representations;

public final class PreUserTimezone {
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    private String emailId;
    private Timezone timezone;
}
