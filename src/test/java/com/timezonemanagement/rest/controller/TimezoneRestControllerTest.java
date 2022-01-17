package com.timezonemanagement.rest.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.timezonemanagement.rest.representations.Timezone;
import com.timezonemanagement.rest.representations.User;
import com.timezonemanagement.rest.representations.UserTimezone;
import com.timezonemanagement.rest.representations.api.UserTimezoneApi;
import com.timezonemanagement.rest.service.TimezoneService;
import com.timezonemanagement.rest.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class TimezoneRestControllerTest {
    private static final User USER = new User("firstName", "lastName", "ab@c.d", "dcwefe");
    private static final UserTimezoneApi USER_TIMEZONE_API_AS_ADMIN = new UserTimezoneApi(null, 2, "Display Name", Optional.of("abc@d.com"));
    private static final UserTimezoneApi USER_TIMEZONE_API_UPDATE_AS_ADMIN = new UserTimezoneApi(2, 2, "Display Name", Optional.of("abc@d.com"));
    private static final UserTimezoneApi USER_TIMEZONE_API_AS_USER = new UserTimezoneApi(null, 2, "Display Name", Optional.empty());
    private static final UserTimezoneApi USER_TIMEZONE_API_UPDATE_AS_USER = new UserTimezoneApi(2, 2, "Display Name", Optional.empty());

    @Mock
    private TimezoneService timezoneService;

    @Mock
    private UserService userService;

    private TimezoneRestController timezoneRestController;

    @Before
    public void init() {
        timezoneRestController = new TimezoneRestController(timezoneService, userService);
    }

    @Test
    public void getAllTimezones() {
        timezoneRestController.getAllTimezones();
        verify(timezoneService).getAllTimezones();
    }

    @Test
    public void getAllUserTimezones() {
        Set<String> emails = ImmutableSet.of("abc@d.com");
        Map<String, Set<UserTimezone>> userTimeZonesByUserEmailIds = ImmutableMap.of("abc@d.com", ImmutableSet.of(mock(UserTimezone.class)));
        when(userService.getAllUserEmails()).thenReturn(emails);
        when(timezoneService.getAllTimezonesForAllUsers(emails)).thenReturn(userTimeZonesByUserEmailIds);
        assertThat(timezoneRestController.getAllUserTimezones(USER)).usingRecursiveComparison().isEqualTo(Response.ok(userTimeZonesByUserEmailIds).build());
    }

    @Test
    public void createUserTimezoneAsAdmin() {
        assertThat(timezoneRestController.createUserTimezoneAsAdmin(USER_TIMEZONE_API_AS_ADMIN, USER)).usingRecursiveComparison().isEqualTo(Response.ok(0).build());
    }

    @Test
    public void updateUserTimezoneAsAdmin() {
        assertThat(timezoneRestController.updateUserTimezoneAsAdmin(USER_TIMEZONE_API_UPDATE_AS_ADMIN, USER)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }

    @Test
    public void deleteUserTimezoneAsAdmin() {
        assertThat(timezoneRestController.deleteUserTimezoneAsAdmin(USER_TIMEZONE_API_UPDATE_AS_ADMIN, USER)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }

    @Test
    public void testGetAllTimezones() {
        Set<Timezone> timezones = ImmutableSet.of(mock(Timezone.class));
        when(timezoneService.getAllTimezones()).thenReturn(timezones);
        assertThat(timezoneRestController.getAllTimezones()).usingRecursiveComparison().isEqualTo(Response.ok(timezones).build());
    }

    @Test
    public void createTimezone() {
        when(timezoneService.createTimezone(USER_TIMEZONE_API_AS_USER.getTimezoneId(), USER_TIMEZONE_API_AS_USER.getName(), USER.getEmail())).thenReturn(1);
        assertThat(timezoneRestController.createTimezone(USER_TIMEZONE_API_AS_USER, USER)).usingRecursiveComparison().isEqualTo(Response.ok(1).build());
    }

    @Test
    public void updateTimezone() {
        assertThat(timezoneRestController.updateTimezone(USER_TIMEZONE_API_UPDATE_AS_USER, USER)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }

    @Test
    public void deleteTimezone() {
        assertThat(timezoneRestController.deleteTimezone(USER_TIMEZONE_API_UPDATE_AS_USER, USER)).usingRecursiveComparison().isEqualTo(Response.ok().build());
    }
}