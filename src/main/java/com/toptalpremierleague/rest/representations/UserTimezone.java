package com.toptalpremierleague.rest.representations;

import java.time.ZoneId;
import java.util.Set;

public class UserTimezone {
    private int timezoneId;
    private String name;

    public static Set<String> getAllTimezoneIds() {
        return ZoneId.getAvailableZoneIds();
    }
}
