package com.toptalpremierleague.rest.representations;

import org.hibernate.validator.constraints.NotBlank;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Timezone {
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGmt_difference() {
        return gmt_difference;
    }

    public void setGmt_difference(int gmt_difference) {
        this.gmt_difference = gmt_difference;
    }

    private String city;

    private int gmt_difference;

    public Timezone() {

    }

    public Timezone(int id, String name, String city, int gmt_difference) {
        this.id = id;
        this.city = city;
        this.name = name;
        this.gmt_difference = gmt_difference;
    }

    public Timezone(String name, String city, int gmt_difference) {
        this.city = city;
        this.name = name;
        this.gmt_difference = gmt_difference;
    }


    public static class TimezoneMapper implements RowMapper<Timezone> {
        @Override
        public Timezone map(ResultSet resultSet, StatementContext ctx) throws SQLException {
            return new Timezone(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("city"), resultSet.getInt("gmt_difference"));
        }
    }

}