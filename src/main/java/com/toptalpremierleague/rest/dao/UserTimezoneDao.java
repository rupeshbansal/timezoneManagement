package com.toptalpremierleague.rest.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Set;

public interface UserTimezoneDao {
    @SqlUpdate("create table user_timezones (email varchar(100), timezone_id integer, PRIMARY KEY(email, timezone_id))")
    void createTable();

    @SqlUpdate("insert into user_timezones (email, timezone_id) values (:email, :timezone_id)")
    void insert(@Bind("email") String email, @Bind("timezone_id") int timezone_id);

    @SqlQuery("select timezone_id from user_timezones where email = :email")
    Set<Integer> getUserTimezones(@Bind("email") String email);

    @SqlUpdate("delete from user_timezones where email = :email and timezone_id = :timezone_id")
    void delete(@Bind("email") String email, @Bind("timezone_id") int timezone_id);
}
