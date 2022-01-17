package com.toptalpremierleague.rest.service;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.toptalpremierleague.rest.dao.UserDao;
import com.toptalpremierleague.rest.representations.User;
import org.eclipse.jetty.util.security.Credential;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Set<String> getAllUserEmails() {
        return userDao.getAllUserEmailIds();
    }

    public void createUser(User user) {
        userDao.insert(user.getEmail(), user.getFirstName(), user.getLastName(), Credential.MD5.digest(user.getSalt()));
    }

    public Optional<User> getUserByEmailId(String emailId) {
        Set<User> users =  userDao.findUserByEmail(emailId);
        if(users.isEmpty()) return Optional.empty();
        return Optional.of(Iterables.getOnlyElement(users));
    }
}
