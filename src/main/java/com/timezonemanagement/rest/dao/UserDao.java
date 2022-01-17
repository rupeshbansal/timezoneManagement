package com.timezonemanagement.rest.dao;

import com.timezonemanagement.rest.representations.User;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.Set;

public interface UserDao {
    @SqlUpdate("create table users (email varchar(100) primary key, first_name varchar(100), last_name varchar(100), salt varchar(100), is_admin boolean)")
    void createUserTable();

    @SqlUpdate("insert into users (email, first_name, last_name, salt) values (:email, :first_name, :last_name, :salt)")
    void insert(@Bind("email") String email, @Bind("first_name") String first_name, @Bind("last_name") String last_name, @Bind("salt") String salt);

    @SqlUpdate("insert into users (email, first_name, last_name, salt, is_admin) values (:email, :first_name, :last_name, :salt, :is_admin)")
    void insertUserAsAdmin(@Bind("email") String email, @Bind("first_name") String first_name, @Bind("last_name") String last_name, @Bind("salt") String salt, @Bind("is_admin") boolean is_admin);

    @SqlQuery("select * from users where email = :email")
    @UseRowMapper(User.UserMapper.class)
    Set<User> findUserByEmail(@Bind("email") String email);

    @SqlQuery("select email from users")
    Set<String> getAllUserEmailIds();
}
