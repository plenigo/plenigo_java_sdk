package com.plenigo.sdk.util;

import com.plenigo.sdk.internal.util.CookieParser;
import org.junit.Test;

import java.net.HttpCookie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.internal.util.CookieParser}.
 * </p>
 */
public class CookieParserTest {

    /**
     * This method tests an {@link com.plenigo.sdk.internal.util.CookieParser#getCustomerCookie(String)}
     * call with a null argument
     */
    @Test
    public void testGetCustomerCookieWithEmptyValues() {
        HttpCookie customerCookie = CookieParser.getCustomerCookie(null);
        assertNotNull(customerCookie);
        assertNull(customerCookie.getValue());
    }

    @Test
    public void testGetPlenigoCookieSuccessfully() {
        String expectedCookieValue = "094e30d2c2d8f7242a82cf9bb8040bd33431b50f9bc4609c0c9589aa7c3b37ba7756be63568de63bf2776c6dd70040bea9";
        String bigCookieHeader = "Cookies =>JSESSIONID=D7E0A06D30AC326AFD32841DF975226A; " +
                "__gads=ID=715df506a90d4e06:T=1398990226:S=ALNI_Mbv0m8KH4HK8b0kn850r_Ie7jbuJw; POPUPCHECK=1399076648180; plenigo_user=" +
                expectedCookieValue + "|giberish";
        String cookieValue = CookieParser.extractCookieValue(CookieParser.PLENIGO_USER_COOKIE_NAME, bigCookieHeader);
        assertNotNull(cookieValue);
        assertFalse(cookieValue.isEmpty());
        assertEquals(expectedCookieValue, cookieValue);
    }


    @Test
    public void testReturnNullWhenCookieNotFound() {
        String bigCookieHeader = "Cookies =>JSESSIONID=D7E0A06D30AC326AFD32841DF975226A; " +
                "__gads=ID=715df506a90d4e06:T=1398990226:S=ALNI_Mbv0m8KH4HK8b0kn850r_Ie7jbuJw; POPUPCHECK=1399076648180";
        String cookieValue = CookieParser.extractCookieValue(CookieParser.PLENIGO_USER_COOKIE_NAME, bigCookieHeader);
        assertNull(cookieValue);
    }


    @Test
    public void testReturnNullWhenCookieIsNull() {
        String cookieValue = CookieParser.extractCookieValue(CookieParser.PLENIGO_USER_COOKIE_NAME, null);
        assertNull(cookieValue);
    }
}
