package com.timezonemanagement.rest.controller;

import com.google.common.collect.ImmutableSet;
import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.representations.api.UserApi;
import com.timezonemanagement.rest.service.UserService;
import org.eclipse.jetty.server.Authentication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest {
    private static final String EMAIL_ID = "abc@c.com";
    private static final UserApi USER_API = new UserApi("firstName", "lastName", "ab@c.d", "dcwefe");
    private static final User USER = new User("firstName", "lastName", "ab@c.d", "dcwefe");

    @Mock
    UserService userService;

    UserRestController userRestController;

    @Before
    public void beforeEach() {
        userRestController = new UserRestController(userService);
    }

    @Test
    public void getUser() {
        Optional<User> user = Optional.of(mock(User.class));
        when(userService.getUserByEmailId(EMAIL_ID)).thenReturn(user);
        assertThat(userRestController.getUser(EMAIL_ID)).usingRecursiveComparison().isEqualTo(Response.ok(user.get()).build());
    }

    @Test
    public void createUser() {
        assertThat(userRestController.createUser(USER_API)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }

    @Test
    public void deleteUser() {
        assertThat(userRestController.deleteUser(EMAIL_ID, USER)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }

    @Test
    public void createUserAsAdmin() {
        assertThat(userRestController.createUserAsAdmin(USER_API, USER)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }
}