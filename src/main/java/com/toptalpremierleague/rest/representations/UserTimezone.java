package com.toptalpremierleague.rest.representations;

import java.time.ZoneId;
import java.util.Set;

public class UserTimezone {
    public static Set<String> getAllTimezoneIds() {
        return ZoneId.getAvailableZoneIds();
    }
}
