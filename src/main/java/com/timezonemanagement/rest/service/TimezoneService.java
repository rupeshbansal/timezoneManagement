package com.timezonemanagement.rest.service;

import com.timezonemanagement.rest.validator.TimezoneValidator;
import com.timezonemanagement.rest.validator.UserValidator;
import com.timezonemanagement.rest.dao.TimezoneDao;
import com.timezonemanagement.rest.dao.UserTimezoneDao;
import com.timezonemanagement.rest.representations.Timezone;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TimezoneService {
    private final TimezoneDao timezoneDao;
    private final UserTimezoneDao userTimezoneDao;
    private final UserValidator userValidator;
    private final TimezoneValidator timezoneValidator;

    public TimezoneService(TimezoneDao timezoneDao, UserTimezoneDao userTimezoneDao, UserValidator userValidator, TimezoneValidator timezoneValidator) {
        this.timezoneDao = timezoneDao;
        this.userTimezoneDao = userTimezoneDao;
        this.userValidator = userValidator;
        this.timezoneValidator = timezoneValidator;
    }

    public void populateTimezones() {
        ZoneId.getAvailableZoneIds().forEach(timezoneId -> {
            TimeZone timeZone = TimeZone.getTimeZone(timezoneId);
            timezoneDao.insert(timeZone.getDisplayName(), timezoneId, timeZone.getRawOffset());
        });
    }

    public Map<String, Set<Timezone>> getAllTimezonesForAllUsers(Set<String> userEmails) {
        return userEmails.stream().collect(Collectors.toMap(Function.identity(), this::getAllTimezones));
    }

    public Set<Timezone> getAllTimezones() {
        return timezoneDao.getAllTimezones();
    }

    public Set<Timezone> getAllTimezones(String userEmail) {
        Set<Integer> timezoneIds = userTimezoneDao.getUserTimezones(userEmail);
        return timezoneDao.getTimezones(timezoneIds);
    }

    @Transaction
    public void createTimezone(int timezoneId, String name, String userEmailId) {
        userValidator.validateUserExistence(userEmailId);
        timezoneValidator.validateTimezoneExists(timezoneId);
        timezoneValidator.validateUserNotAssociatedWithTimezone(userEmailId, timezoneId);
        userTimezoneDao.insert(userEmailId, name, timezoneId);
    }

    @Transaction
    public void updateTimezone(int timezoneId, String name, String userEmailId) {
        userValidator.validateUserExistence(userEmailId);
        timezoneValidator.validateTimezoneExists(timezoneId);
        timezoneValidator.validateUserAssociatedWithTimezone(userEmailId, timezoneId);
        userTimezoneDao.update(timezoneId, userEmailId, name);
    }

    @Transaction
    public void deleteTimezone(int timezoneId, String userEmailId) {
        userValidator.validateUserExistence(userEmailId);
        timezoneValidator.validateTimezoneExists(timezoneId);
        timezoneValidator.validateUserAssociatedWithTimezone(userEmailId, timezoneId);
        userTimezoneDao.delete(userEmailId, timezoneId);
    }
}
