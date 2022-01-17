package com.timezonemanagement.rest.service;

import com.google.common.collect.Iterables;
import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.dao.UserDao;
import org.eclipse.jetty.util.security.Credential;

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
