package com.timezonemanagement.rest.validator;

import com.google.common.collect.ImmutableSet;
import com.timezonemanagement.rest.dao.TimezoneDao;
import com.timezonemanagement.rest.dao.UserTimezoneDao;
import com.timezonemanagement.rest.representations.Timezone;

import java.util.Set;

public final class TimezoneValidator {

    private TimezoneDao timezoneDao;
    private UserTimezoneDao userTimezoneDao;

    public TimezoneValidator(TimezoneDao timezoneDao, UserTimezoneDao userTimezoneDao) {
        this.timezoneDao = timezoneDao;
        this.userTimezoneDao = userTimezoneDao;
    }

    public void validateTimezoneExists(int timezoneId) {
        Set<Timezone> timezones = timezoneDao.getTimezones(ImmutableSet.of(timezoneId));
        if (timezones.isEmpty()) {
            throw new RuntimeException("Invalid timezoneId");
        }
    }

    public void validateUserAssociatedWithTimezone(int userTimezoneId, String userEmailId) {
        String dbUserEmailId = userTimezoneDao.getEmailIdFromUserTimezoneId(userTimezoneId);
        if (dbUserEmailId == null || !dbUserEmailId.equals(userEmailId)) {
            throw new RuntimeException("User is not associated with this timezone");
        }
    }
}
