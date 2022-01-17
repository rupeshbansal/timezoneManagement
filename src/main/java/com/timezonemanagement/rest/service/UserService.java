package com.timezonemanagement.rest.service;

import com.google.common.collect.Iterables;
import com.timezonemanagement.rest.dao.UserTimezoneDao;
import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.dao.UserDao;
import com.timezonemanagement.rest.validator.UserValidator;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.Optional;
import java.util.Set;

public class UserService {
    private final UserDao userDao;
    private final UserTimezoneDao userTimezoneDao;
    private final UserValidator userValidator;

    public UserService(UserDao userDao, UserTimezoneDao userTimezoneDao, UserValidator userValidator) {
        this.userDao = userDao;
        this.userTimezoneDao = userTimezoneDao;
        this.userValidator = userValidator;
    }

    public Set<String> getAllUserEmails() {
        return userDao.getAllUserEmailIds();
    }

    public void createUser(User user) {
        userValidator.validateUserNonExistence(user.getEmail());
        userDao.insert(user.getEmail(), user.getFirstName(), user.getLastName(), user.getSalt());
    }

    public void createUserAsAdmin(User user) {
        userValidator.validateUserNonExistence(user.getEmail());
        userDao.insertUserAsAdmin(user.getEmail(), user.getFirstName(), user.getLastName(), user.getSalt(), true);
    }

    @Transaction
    public void deleteUser(String userEmailId) {
        userValidator.validateUserExistence(userEmailId);
        userDao.deleteUser(userEmailId);
        userTimezoneDao.deleteUserEntries(userEmailId);
    }

    public Optional<User> getUserByEmailId(String emailId) {
        Set<User> users =  userDao.findUserByEmail(emailId);
        if(users.isEmpty()) return Optional.empty();
        return Optional.of(Iterables.getOnlyElement(users));
    }
}
