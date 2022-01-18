package com.timezonemanagement.rest.validator;

import com.google.common.collect.ImmutableSet;
import com.timezonemanagement.rest.controller.UserRestController;
import com.timezonemanagement.rest.dao.UserDao;
import com.timezonemanagement.rest.representations.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserValidatorTest {
    private static final String EMAIL_ID = "abc@c.com";

    @Mock
    UserDao userDao;

    private UserValidator userValidator;

    @Before
    public void beforeEach() {
        userValidator = new UserValidator(userDao);
    }

    @Test
    public void validateUserExistence() {
        when(userDao.findUserByEmail(EMAIL_ID)).thenReturn(ImmutableSet.of(mock(User.class)));
        userValidator.validateUserExistence(EMAIL_ID);
    }

    @Test
    public void validateUserNonExistence() {
        when(userDao.findUserByEmail(EMAIL_ID)).thenReturn(ImmutableSet.of());
        userValidator.validateUserNonExistence(EMAIL_ID);
    }
}