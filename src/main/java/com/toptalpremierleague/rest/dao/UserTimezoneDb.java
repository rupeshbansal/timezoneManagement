package com.toptalpremierleague.rest.dao;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public final class UserTimezoneDb {
    public static Set<String> getAllTimezones(Integer employeeId) {
        return ImmutableSet.of();
    }

    public static void setAllTimezones(Integer employeeId, Set<String> timezoneIds) {
        return;
    }

    public static void removeTimezones(Integer employeeId, String timezoneId) {
        return;
    }
}
