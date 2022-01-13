package com.toptalpremierleague.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HelloWorldConfiguration extends Configuration {

    @NotNull
    private String host;

    @NotNull
    private String port;

    @JsonProperty
    public String getHost() {
        return host;
    }

    @JsonProperty
    public String getPort() {
        return port;
    }
}