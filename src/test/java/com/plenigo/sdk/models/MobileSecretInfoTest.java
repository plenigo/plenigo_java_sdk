package com.plenigo.sdk.models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link MobileSecretInfo}.
 * </p>
 */
public class MobileSecretInfoTest {
    public static final String EMAIL = "customerId";
    public static final String MOBILE_APP_SECRET = "mobileAppSecret";
    private MobileSecretInfo mobileSecretInfo;


    @Before
    public void setup(){
        mobileSecretInfo = new MobileSecretInfo(EMAIL, MOBILE_APP_SECRET);
    }

    @Test
    public void testGetEmail(){
        assertEquals("email is not equal", EMAIL, mobileSecretInfo.getEmail());
    }

    @Test
    public void testGetMobileAppSecret(){
        assertEquals("mobile app secret is not equal", MOBILE_APP_SECRET, mobileSecretInfo.getMobileAppSecret());
    }
}
