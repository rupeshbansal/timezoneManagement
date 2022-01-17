package com.toptalpremierleague.rest.service;

import com.google.common.collect.ImmutableSet;
import com.toptalpremierleague.rest.dao.TimezoneDao;
import com.toptalpremierleague.rest.dao.UserTimezoneDao;
import com.toptalpremierleague.rest.representations.Timezone;

import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TimezoneService {
    private final TimezoneDao timezoneDao;
    private final UserTimezoneDao userTimezoneDao;

    public TimezoneService(TimezoneDao timezoneDao, UserTimezoneDao userTimezoneDao) {
        this.timezoneDao = timezoneDao;
        this.userTimezoneDao = userTimezoneDao;

//        timezoneDao.createTable();
//        userTimezoneDao.createTable();
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

    public void createTimezone(int timezoneId, String name, String userEmailId) {
        Set<Timezone> timezones = timezoneDao.getTimezones(ImmutableSet.of(timezoneId));
        if(timezones.isEmpty()) {
            throw new RuntimeException("Timezone not found");
        }
        userTimezoneDao.insert(userEmailId, name, timezoneId);
    }

    public void updateTimezone(int timezoneId, String name, String userEmailId) {
        Set<Integer> userTimezoneIds = userTimezoneDao.getUserTimezones(userEmailId);
        if(!userTimezoneIds.contains(timezoneId)) {
            throw new RuntimeException("Timezone not found for this user");
        }
        userTimezoneDao.update(timezoneId, userEmailId, name);
    }

    public void deleteTimezone(int timezoneId, String userEmail) {
        Set<Integer> userTimezoneIds = userTimezoneDao.getUserTimezones(userEmail);
        if(!userTimezoneIds.contains(timezoneId)) {
            throw new RuntimeException("Timezone not found for this user");
        }
        userTimezoneDao.delete(userEmail, timezoneId);
    }
}
