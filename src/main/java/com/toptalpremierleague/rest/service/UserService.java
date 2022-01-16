package com.toptalpremierleague.rest.service;

import com.google.common.collect.ImmutableSet;
import com.toptalpremierleague.rest.dao.UserDao;

import java.util.Set;

public final class UserService {
    private final UserDao userDao;


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Set<String> getAllUserEmails() {
        return ImmutableSet.of();
    }
}
