package com.toptalpremierleague.rest.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.toptalpremierleague.rest.dao.TimezoneDao;
import com.toptalpremierleague.rest.dao.UserTimezoneDao;
import com.toptalpremierleague.rest.representations.Timezone;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public Map<String, Set<Timezone>> getAllTimezonesForAllUsers(Set<String> userEmails) {
        return userEmails.stream().collect(Collectors.toMap(Function.identity(), this::getAllTimezones));
    }

    public Set<Timezone> getAllTimezones(String userEmail) {
        Set<Integer> timezoneIds = userTimezoneDao.getUserTimezones(userEmail);

        return timezoneDao.getTimezones(timezoneIds);
    }

    public void createTimezone(Timezone timezone, String userEmail) {
        int timezoneId = timezoneDao.insert(timezone.getName(), timezone.getCity(), timezone.getGmt_difference());
        userTimezoneDao.insert(userEmail, timezoneId);
    }

    public void updateTimezone(Timezone timezone, String userEmail) {
        Set<Integer> userTimezoneIds = userTimezoneDao.getUserTimezones(userEmail);
        if(!userTimezoneIds.contains(timezone.getId())) {
            throw new RuntimeException("Timezone not found for this user");
        }
        timezoneDao.update(timezone.getId(), timezone.getName(), timezone.getCity(), timezone.getGmt_difference());
    }

    public void deleteTimezone(int timezoneId, String userEmail) {
        Set<Integer> userTimezoneIds = userTimezoneDao.getUserTimezones(userEmail);
        if(!userTimezoneIds.contains(timezoneId)) {
            throw new RuntimeException("Timezone not found for this user");
        }
        timezoneDao.delete(timezoneId);
        userTimezoneDao.delete(userEmail, timezoneId);
    }
}
