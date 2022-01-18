package com.timezonemanagement.rest.validator;

import com.google.common.collect.ImmutableSet;
import com.timezonemanagement.rest.dao.TimezoneDao;
import com.timezonemanagement.rest.dao.UserTimezoneDao;
import com.timezonemanagement.rest.representations.Timezone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TimezoneValidatorTest {
    private static final String EMAIL = "abc@d.com";
    private static final int TIMEZONE_ID = 1;

    @Mock
    TimezoneDao timezoneDao;

    @Mock
    UserTimezoneDao userTimezoneDao;

    TimezoneValidator timezoneValidator;

    @Before
    public void before() {
        timezoneValidator = new TimezoneValidator(timezoneDao, userTimezoneDao);
    }

    @Test
    public void validateTimezoneExists() {
        when(timezoneDao.getTimezones(ImmutableSet.of(TIMEZONE_ID))).thenReturn(ImmutableSet.of(mock(Timezone.class)));
        timezoneValidator.validateTimezoneExists(TIMEZONE_ID);
    }

    @Test
    public void validateUserAssociatedWithTimezone() {
        when(userTimezoneDao.getEmailIdFromUserTimezoneId(TIMEZONE_ID)).thenReturn(EMAIL);
        timezoneValidator.validateUserAssociatedWithTimezone(TIMEZONE_ID, EMAIL);
    }
}