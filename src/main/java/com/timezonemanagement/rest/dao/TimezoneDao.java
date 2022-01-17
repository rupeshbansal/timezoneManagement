package com.timezonemanagement.rest.dao;

import com.timezonemanagement.rest.representations.Timezone;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.Set;


public interface TimezoneDao {

    @SqlUpdate("create table timezones (id serial primary key, name varchar(100), city varchar(100), gmt_difference int)")
    void createTable();

    @SqlUpdate("insert into timezones (name, city, gmt_difference) values (:name, :city, :gmt_difference)")
    @GetGeneratedKeys
    int insert(@Bind("name") String name, @Bind("city") String city, @Bind("gmt_difference") int gmt_difference);

    @SqlQuery("select * from timezones where id = any(:timezoneIds)")
    @UseRowMapper(Timezone.TimezoneMapper.class)
    Set<Timezone> getTimezones(@Bind("timezoneIds") Set<Integer> timezoneIds);

    @SqlQuery("select * from timezones")
    @UseRowMapper(Timezone.TimezoneMapper.class)
    Set<Timezone> getAllTimezones();
}
