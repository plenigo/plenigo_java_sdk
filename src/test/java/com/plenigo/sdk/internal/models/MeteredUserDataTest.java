package com.plenigo.sdk.internal.models;

import com.plenigo.sdk.models.TimePeriod;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link MeteredUserData}.
 * </p>
 */
public class MeteredUserDataTest {

    public static final long COOKIE_CREATION_TIME = 123L;
    public static final boolean START_WITH_FIRST_DAY = false;
    public static final long START_TIME = 1235L;
    public static final String METERED_PERIOD = "MONTH";
    private MeteredUserData meteredUserData;

    @Before
    public void setup(){
        meteredUserData = new MeteredUserData(true, 1L, 2L, false, 3L, 4L, false);
        meteredUserData.setCookieCreationTime(COOKIE_CREATION_TIME);
        meteredUserData.setStartWithFirstDay(START_WITH_FIRST_DAY);
        meteredUserData.setStartTime(START_TIME);
        meteredUserData.setMeteredPeriod(METERED_PERIOD);
    }

    @Test
    public void testGetCookieCreationTime(){
        assertEquals("Cookie creation time is not correct", COOKIE_CREATION_TIME, meteredUserData.getCookieCreationTime());
    }


    @Test
    public void testGetStartWithFirstDay(){
        assertEquals("Start with first day is not correct", START_WITH_FIRST_DAY, meteredUserData.getStartWithFirstDay());
    }

    @Test
    public void testGetStartTime(){
        assertEquals("Start time is not correct", START_TIME, meteredUserData.getStartTime());
    }

    @Test
    public void testGetMeteredPeriod(){
        assertEquals("Metered period is not correct", TimePeriod.MONTH, meteredUserData.getMeteredPeriod());
    }

}
