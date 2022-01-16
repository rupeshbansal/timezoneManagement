package com.toptalpremierleague.rest.dao;

import com.toptalpremierleague.rest.representations.Timezone;
import com.toptalpremierleague.rest.representations.User;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

public interface TimezoneDao {

    @SqlUpdate("create table timezones (id serial primary key, name varchar(100), city varchar(100), gmt_difference int)")
    void createTable();

    @SqlUpdate("insert into timezones (name, city, gmt_difference) values (:name, :city, :gmt_difference)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name, @Bind("city") String city, @Bind("gmt_difference") int gmt_difference);

    @SqlUpdate("update timezones set name = :name, city = :city, gmt_difference = :gmt_difference where id = :id")
    void update(@Bind("id") int id, @Bind("name") String name, @Bind("city") String city, @Bind("gmt_difference") int gmt_difference);

    @SqlUpdate("delete from timezones where id = :id")
    void delete(@Bind("id") int id);

    @SqlQuery("select * from timezones where id = :email")
    @UseRowMapper(User.UserMapper.class)
    List<User> findUserByEmail(@Bind("email") String email);
}
