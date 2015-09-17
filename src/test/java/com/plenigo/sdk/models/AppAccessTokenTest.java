package com.plenigo.sdk.models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.models.AppAccessToken}.
 * </p>
 */
public class AppAccessTokenTest {
    public static final String CUSTOMER_ID = "customerId";
    public static final String TOKEN = "token";
    private AppAccessToken appAccessToken;


    @Before
    public void setup(){
        appAccessToken = new AppAccessToken(CUSTOMER_ID, TOKEN);
    }

    @Test
    public void testGetCustomerId(){
        assertEquals("Customer id is not equal", CUSTOMER_ID, appAccessToken.getCustomerId());
    }

    @Test
    public void testGetToken(){
        assertEquals("Token is not equal", TOKEN, appAccessToken.getToken());
    }
}
