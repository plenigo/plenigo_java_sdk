package com.plenigo.sdk.internal.util;

import java.net.HttpCookie;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * This class contains cookie parsing functions in order to
 * offer an easy way convert information.
 * </p>
 * <p>
 * <b>IMPORTANT:</b> This class is part of the internal API, please do not use it, because it can
 * be removed in future versions of the SDK or access to such elements could
 * be changed from 'public' to 'default' or less.
 * </p>
 */
public final class CookieParser {

    private static final Logger LOGGER = Logger.getLogger(CookieParser.class.getName());
    /**
     * plenigo's Customer cookie name.
     */
    public static final String PLENIGO_USER_COOKIE_NAME = "plenigo_user";
    /**
     * plenigo's metered view cookie name.
     */
    public static final String PLENIGO_METERED_VIEW_COOKIE_NAME = "plenigo_view";

    /**
     * Regular expression used to match a key-value pair inside a cookie string.
     */
    private static final Pattern COOKIE_PARSER = Pattern.compile("([^=\\s]+)=([^=;]*)(;?|$)");

    /**
     * Default constructor.
     */
    private CookieParser() {
    }

    /**
     * Finds the plenigo user cookie within the header.
     *
     * @param cookieHeader The raw cookie header string
     *
     * @return The plenigo User HTTP Cookie
     */
    public static HttpCookie getCustomerCookie(String cookieHeader) {
        return getHttpCookie(cookieHeader, PLENIGO_USER_COOKIE_NAME);
    }

    /**
     * Finds the plenigo metered data cookie within the header.
     *
     * @param cookieHeader The raw cookie header string
     *
     * @return The plenigo metered data HTTP Cookie
     */
    public static HttpCookie getUserMeteredDataCookie(String cookieHeader) {
        return getHttpCookie(cookieHeader, PLENIGO_METERED_VIEW_COOKIE_NAME);
    }

    /**
     * Retrieves a cookie key-value pair from the header.
     *
     * @param cookieHeader The complete cookie header
     * @param cookieKey    The key to get
     *
     * @return The Http cookie with the key and the value
     */
    private static HttpCookie getHttpCookie(String cookieHeader, String cookieKey) {
        if (cookieHeader == null || cookieHeader.isEmpty()) {
            LOGGER.log(Level.FINEST, "Cookie Header: {0} is null or empty", cookieHeader);
            return new HttpCookie(cookieKey, null);
        }
        String cookieValue = CookieParser.extractCookieValue(cookieKey, cookieHeader);
        return new HttpCookie(cookieKey, cookieValue);
    }

    /**
     * Extracts a given cookie value with the specified name.
     *
     * @param cookieName      The name of the cookie to extract
     * @param rawCookieString The complete cookie header
     *
     * @return The cookie value of the given cookie name, or null if it was not found
     */
    public static String extractCookieValue(String cookieName, String rawCookieString) {
        if (rawCookieString == null || rawCookieString.isEmpty()) {
            return null;
        }
        Matcher m = COOKIE_PARSER.matcher(rawCookieString);
        while (m.find()) {
            String key = m.group(1);
            String val = m.group(2);
            if (key.contains(cookieName)) {
                LOGGER.log(Level.FINEST, "Found the key {0} in the cookie header, extracting the value from {1}", new Object[]{key, val});
                String[] split = val.split("\\|");
                return split[0];
            }
        }
        return null;
    }
}
