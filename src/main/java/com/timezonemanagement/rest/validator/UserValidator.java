package com.timezonemanagement.rest.validator;

import com.timezonemanagement.rest.dao.UserDao;

public class UserValidator {
    private UserDao userDao;

    public UserValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    public void validateUserExistence(String userEmailId) {
        if(userDao.findUserByEmail(userEmailId).isEmpty()) {
            throw new RuntimeException("No such user found");
        }
    }

    public void validateUserNonExistence(String userEmailId) {
        if(!userDao.findUserByEmail(userEmailId).isEmpty()) {
            throw new RuntimeException("User already exists");
        }
    }
}
