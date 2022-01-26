package com.timezonemanagement.rest.representations.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timezonemanagement.rest.representations.User;
import org.eclipse.jetty.util.security.Credential;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public final class UserApi {
    public UserApi(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserApi() {

    }

    @NotEmpty
    @Valid
    @Length(min = 2, max = 255)
    @JsonProperty("first_name")
    private String firstName;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotEmpty
    @Length(min = 2, max = 255)
    @JsonProperty("last_name")
    private String lastName;

    @NotEmpty
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    private String email;

    @NotEmpty
    @JsonProperty("password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

    public User toUser() {
        return new User(this.firstName, this.lastName, this.email, Credential.MD5.digest(this.password));
    }

}
