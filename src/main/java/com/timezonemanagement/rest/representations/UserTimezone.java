package com.timezonemanagement.rest.representations;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class UserTimezone {
    private final int id;
    private final int timezoneId;
    private final String name;

    public int getId() {
        return id;
    }

    public int getTimezoneId() {
        return timezoneId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    private final String email;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public UserTimezone(int id, int timezoneId, String name, String emailId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.timezoneId = timezoneId;
        this.name = name;
        this.email = emailId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static class UserTimezoneMapper implements RowMapper<UserTimezone> {
        @Override
        public UserTimezone map(ResultSet resultSet, StatementContext ctx) throws SQLException {
            return new UserTimezone(resultSet.getInt("id"), resultSet.getInt("timezone_id"), resultSet.getString("name"), resultSet.getString("email"), resultSet.getTimestamp("created_at"), resultSet.getTimestamp("updated_at"));
        }
    }
}
