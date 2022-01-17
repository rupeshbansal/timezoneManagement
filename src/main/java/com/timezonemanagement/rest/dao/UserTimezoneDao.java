package com.timezonemanagement.rest.dao;

import com.timezonemanagement.rest.representations.Timezone;
import com.timezonemanagement.rest.representations.UserTimezone;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.sql.Timestamp;
import java.util.Set;

public interface UserTimezoneDao {
    @SqlUpdate("create table user_timezones (id serial primary key, email varchar(100), name varchar(100), timezone_id integer, created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(), updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW())")
    void createUserTimezonesTable();

    @SqlUpdate("insert into user_timezones (email, name, timezone_id) values (:email, :name, :timezone_id)")
    @GetGeneratedKeys
    int insert(@Bind("email") String email, @Bind("name") String name, @Bind("timezone_id") int timezone_id);

    @SqlQuery("select * from user_timezones where email = :email")
    @UseRowMapper(UserTimezone.UserTimezoneMapper.class)
    Set<UserTimezone> getUserTimezones(@Bind("email") String email);

    @SqlQuery("select email from user_timezones where id = :id")
    String getEmailIdFromUserTimezoneId(@Bind("id") int id);

    @SqlUpdate("delete from user_timezones where id = :id")
    void delete(@Bind("id") int id);

    @SqlUpdate("update user_timezones set name = :name, timezone_id = :timezone_id, updated_at = :updated_at where id = :id")
    void update(@Bind("id") int id, @Bind("timezone_id") int timezone_id, @Bind("name") String name,  @Bind("updated_at") Timestamp updated_at);

}
