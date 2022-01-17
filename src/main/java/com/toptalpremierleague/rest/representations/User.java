package com.toptalpremierleague.rest.representations;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class User implements Principal{
    @NotBlank
    @Length(min = 2, max = 255)
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank
    @Length(min = 2, max = 255)
    @JsonProperty("last_name")
    private String lastName;
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    private String email;

    @NotBlank
    @Length(min = 2, max = 255)
    private String salt;

    @JsonProperty("is_admin")
    private Optional<Boolean> isAdmin;

    public User() {

    }

    public User(String firstName, String lastName, String email, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.isAdmin = Optional.empty();
    }

    public User(String firstName, String lastName, String email, String salt, Optional<Boolean> isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salt = salt;
        this.isAdmin = isAdmin;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin.orElse(Boolean.FALSE);
    }

    public void setIsAdmin(Boolean isAdminStatus) {
        this.isAdmin = Optional.of(isAdminStatus);
    }

    @Override
    public String getName() {
        return email;
    }

    public static class UserMapper implements RowMapper<User> {
        @Override
        public User map(ResultSet resultSet, StatementContext ctx) throws SQLException {
            return new User( resultSet.getString("first_name"),  resultSet.getString("last_name"),  resultSet.getString("email"),  resultSet.getString("salt"), Optional.ofNullable(resultSet.getBoolean("is_admin")));
        }
    }

}