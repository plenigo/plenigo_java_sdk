package com.plenigo.sdk.internal.models;

import com.plenigo.sdk.models.TimePeriod;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.internal.models.MeteredUserData}.
 * </p>
 */
public class MeteredUserDataTest {
    public static final boolean IS_METERED_VIEW_ACTIVATED = true;
    public static final long FREE_VIEWS_ALLOWED = 1L;
    public static final long VIEWS_TAKEN = 2L;
    public static final boolean IS_LIMIT_REACHED = false;
    public static final long LOGIN_FREE_VIEWS_ALLOWED = 3L;
    public static final long LOGIN_FREE_VIEWS_TAKEN = 4L;
    public static final boolean LOGIN_LIMIT_REACHED = true;
    public static final long START_TIME = 5L;
    public static final TimePeriod METERED_PERIOD = TimePeriod.DAY;
    public static final boolean START_WITH_FIRST_DAY = false;
    public static final long COOKIE_CREATION_TIME = 6L;
    MeteredUserData userData;


    @Before
    public void setup() {
        userData = new MeteredUserData(IS_METERED_VIEW_ACTIVATED, FREE_VIEWS_ALLOWED, VIEWS_TAKEN,
                IS_LIMIT_REACHED, LOGIN_FREE_VIEWS_ALLOWED, LOGIN_FREE_VIEWS_TAKEN, LOGIN_LIMIT_REACHED);
        userData.setStartTime(START_TIME);
        userData.setMeteredPeriod(METERED_PERIOD.name());
        userData.setStartWithFirstDay(START_WITH_FIRST_DAY);
        userData.setCookieCreationTime(COOKIE_CREATION_TIME);
    }

    @Test
    public void testGetStartTime() {
        assertEquals("Start time does not match", START_TIME, userData.getStartTime());
    }

    @Test
    public void testGetMeteredPeriod() {
        assertEquals("Metered period does not match", METERED_PERIOD, userData.getMeteredPeriod());
    }

    @Test
    public void testGetStartWithFirstDay() {
        assertEquals("Start with first day does not match", START_WITH_FIRST_DAY, userData.getStartWithFirstDay());
    }


    @Test
    public void testGetCookieCreationTime() {
        assertEquals("Cookie creation time does not match", COOKIE_CREATION_TIME, userData.getCookieCreationTime());
    }
}
