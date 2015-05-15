package com.plenigo.sdk.services;


import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.models.MeteredUserData;
import com.plenigo.sdk.models.TimePeriod;
import com.plenigo.sdk.internal.services.InternalMeterService;
import com.plenigo.sdk.internal.util.CookieParser;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HexUtils;
import com.plenigo.sdk.internal.util.SdkUtils;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This contains the services related to metering user views with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class MeterService {
    /**
    * Metered data initialization vector.
    */
    static final byte[] METERED_INIT_VECTOR = HexUtils.decodeHex("7a134cc376d05cf6bc116e1e53c8801e");

    /**
     * Metered view array index position.
     */
    public static final int METERED_VIEW_ACTIVATED_IDX_POS = 1;

    /**
     * Min expected size for metered view data.
     */
    public static final int MIN_EXPECTED_SIZE = 2;
    /**
     * Free views allowed array index position.
     */
    public static final int FREE_VIEWS_ALLOWED_IDX_POS = 2;
    /**
     * Free views taken array index position.
     */
    public static final int FREE_VIEWS_TAKEN_IDX_POS = 3;
    /**
     * Limit reached array index position.
     */
    public static final int LIMIT_REACHED_IDX_POS = 4;

    /**
     * Unique visited sites array index position.
     */
    public static final int UNIQUELY_VISITED_SITES_IDX_POS = 5;
    /**
     * Free views allowed (after login) array index position.
     */
    public static final int LOGIN_FREE_VIEWS_ALLOWED_IDX_POS = 9;
    /**
     * Free views taken (after login) array index position.
     */
    public static final int LOGIN_FREE_VIEWS_TAKEN_IDX_POS = 10;
    /**
     * Limit reached (after login) array index position.
     */
    public static final int LOGIN_FREE_VIEWS_LIMIT_REACHED_IDX_POS = 11;
    /**
     * Time of the first page hit.
     */
    public static final int START_TIME_IDX_POS = 12;
    /**
     * Time period metered view counter is running. Possible values (DAY|WEEK|MONTH|YEAR)
     */
    public static final int METERED_PERIOD_IDX_POS = 13;
    /**
     * Flag indicating if metered period starts with first visit or at first day / 0 o'clock, etc.
     */
    public static final int START_WITH_FIRST_DAY_IDX_POS = 14;
    /**
     * Time as long indicating representing cookie creating time.
     */
    public static final int COOKIE_CREATION_TIME_IDX_POS = 15;

    /**
     * A day in milliseconds.
     * (24*60*60*100)
     */
    public static final long TS_DAY_IN_MILLIS = 86400000L; //24hours in millis

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    public static final String METERED_LIMIT_REACHED = "meteredLimitReached";
    public static final long WEEK_IN_DAYS = 7L;
    public static final long MONTH_IN_DAYS = 31L;
    public static final long YEAR_IN_DAYS = 365L;

    private static InternalMeterService internalMeterService = new InternalMeterService();

    /**
     * Default constructor.
     */
    private MeterService() {

    }

    /**
     * This method parses the metered view data from the user in the cookie.
     *
     * @param cookieHeader The cookie header
     *
     * @return The metered user data
     *
     * @throws com.plenigo.sdk.PlenigoException if there was an error decrypting the cookie
     */
    private static MeteredUserData getMeteredUserData(String cookieHeader) throws PlenigoException {
        HttpCookie userMeteredDataCookie = CookieParser.getUserMeteredDataCookie(cookieHeader);
        if (userMeteredDataCookie.getValue() == null || userMeteredDataCookie.getValue().trim().isEmpty()) {
            return null;
        }
        String data = EncryptionUtils.get().decryptWithAES(PlenigoManager.get().getCompanyId() //companyhash
                , userMeteredDataCookie.getValue(), //cookie
                METERED_INIT_VECTOR); //init vector
        LOGGER.log(Level.FINEST, "Resulting data from decryption of meter cookie: {0}", data);
        String[] userData = data.split("\\|");
        if (userData.length < MIN_EXPECTED_SIZE) {
            //invalid data that couldnt be parsed
            return null;
        }
        return parseMeteredUserData(userData);
    }

    /**
     * Returns a flag indicating if the user still has free views left.
     *
     * @param cookieHeader       The header that contains the metered data
     * @param requestQueryString The url query string
     *
     * @return True if the user still has free views left, false otherwise
     *
     * @throws PlenigoException If an error happens during cookie parsing
     */
    public static boolean hasFreeViews(String cookieHeader, String requestQueryString) throws PlenigoException {
        MeteredUserData meteredUserData = getMeteredUserData(cookieHeader);
        if (meteredUserData == null) {
            if (isLimitReachedByUrlParam(requestQueryString)) {
                return false;
            }
            return true;
        }
        boolean isLoggedIn = UserService.isLoggedIn(cookieHeader);
        boolean isExpiredData = checkCookieValidity(meteredUserData);
        return internalMeterService.hasFreeViews(meteredUserData, isLoggedIn, isExpiredData);
    }

    /**
     * This method validates if the query string contains a metered limit reached parameter that indicates that the user
     * should not have free views.
     *
     * @param requestQueryString the request query string
     *
     * @return the boolean that indicates if the limit has been reached by url parameter
     *
     * @throws PlenigoException if an encoding error occurs
     */
    private static boolean isLimitReachedByUrlParam(String requestQueryString) throws PlenigoException {
        if (requestQueryString != null && requestQueryString.contains(METERED_LIMIT_REACHED)) {
            try {
                Map<String, String> stringStringMap = SdkUtils.parseQueryStringToMap(requestQueryString);
                String meteredLimitReached = stringStringMap.get(METERED_LIMIT_REACHED);
                if (meteredLimitReached != null && !meteredLimitReached.isEmpty() && Boolean.parseBoolean(meteredLimitReached)) {
                    LOGGER.info("Limit reached by URL parameter");
                    return true;
                }
            } catch (UnsupportedEncodingException e) {
                String message = "Error while parsing the query string";
                LOGGER.log(Level.SEVERE, message, e);
                throw new PlenigoException(message, e);
            }
        }
        return false;
    }

    /**
     * Checks to see if the metered cookie is valid.
     *
     * @param meteredUserData the metered user data
     *
     * @return a boolean value indicating if the cookie is valid
     */
    private static boolean checkCookieValidity(MeteredUserData meteredUserData) {
        boolean isCookieValid = true;
        TimePeriod timePeriod = meteredUserData.getMeteredPeriod();
        long currentTime = System.currentTimeMillis();
        long timeLapse = currentTime - meteredUserData.getCookieCreationTime();

        if (timeLapse > 0) {
            long timeToCompareWith;
            switch (timePeriod) {
                case WEEK:
                    timeToCompareWith = TS_DAY_IN_MILLIS * WEEK_IN_DAYS;
                    break;
                case MONTH:
                    timeToCompareWith = TS_DAY_IN_MILLIS * MONTH_IN_DAYS;
                    break;
                case YEAR:
                    timeToCompareWith = TS_DAY_IN_MILLIS * YEAR_IN_DAYS;
                    break;
                //By default we assume is day
                default:
                    timeToCompareWith = TS_DAY_IN_MILLIS;
            }
            if (timeLapse > timeToCompareWith) {
                LOGGER.info("Cookie time period: " + timePeriod + ", timeLapse: " + timeLapse + ", is cookie valid: " + isCookieValid);
                isCookieValid = false;
            }

        }
        return isCookieValid;
    }

    /**
     * Parses the data from the input array.
     *
     * @param userData The input array with the metered user data
     *
     * @return The metered user data object
     *
     * @throws com.plenigo.sdk.PlenigoException when an error during parsing occurs
     */
    private static MeteredUserData parseMeteredUserData(String[] userData) throws PlenigoException {
        Boolean isMeteredViewActivated = SdkUtils.getArrayValueIfExistsOrNull(METERED_VIEW_ACTIVATED_IDX_POS, userData, Boolean.class);
        Long freeViewsAllowed = SdkUtils.getArrayValueIfExistsOrNull(FREE_VIEWS_ALLOWED_IDX_POS, userData, Long.class);
        Long viewsTaken = SdkUtils.getArrayValueIfExistsOrNull(FREE_VIEWS_TAKEN_IDX_POS, userData, Long.class);
        Boolean isLimitReached = SdkUtils.getArrayValueIfExistsOrNull(LIMIT_REACHED_IDX_POS, userData, Boolean.class);
        Long loginFreeViewsAllowed = SdkUtils.getArrayValueIfExistsOrNull(LOGIN_FREE_VIEWS_ALLOWED_IDX_POS, userData, Long.class);
        Long loginFreeViewsTaken = SdkUtils.getArrayValueIfExistsOrNull(LOGIN_FREE_VIEWS_TAKEN_IDX_POS, userData, Long.class);
        Boolean isLoginLimitReached = SdkUtils.getArrayValueIfExistsOrNull(LOGIN_FREE_VIEWS_LIMIT_REACHED_IDX_POS, userData, Boolean.class);
        String uniqueVisitedSites = SdkUtils.getArrayValueIfExistsOrNull(UNIQUELY_VISITED_SITES_IDX_POS, userData, String.class);
        MeteredUserData meteredUserData = new MeteredUserData(isMeteredViewActivated, freeViewsAllowed, viewsTaken, isLimitReached, loginFreeViewsAllowed,
                loginFreeViewsTaken, isLoginLimitReached);
        if (uniqueVisitedSites != null && !uniqueVisitedSites.trim().isEmpty()) {
            String[] uniqueVisitedSitesArr = uniqueVisitedSites.split(",");
            meteredUserData.getUniqueVisitedSites().addAll(Arrays.asList(uniqueVisitedSitesArr));
        }
        Long startTime = SdkUtils.getArrayValueIfExistsOrNull(START_TIME_IDX_POS, userData, Long.class);
        if (startTime != null) {
            meteredUserData.setStartTime(startTime);
        }

        String meteredPeriodVal = SdkUtils.getArrayValueIfExistsOrNull(METERED_PERIOD_IDX_POS, userData, String.class);
        if (meteredPeriodVal != null && !meteredPeriodVal.trim().isEmpty()) {
            meteredUserData.setMeteredPeriod(meteredPeriodVal.trim());
        }

        Boolean startWithFirstDay = SdkUtils.getArrayValueIfExistsOrNull(START_WITH_FIRST_DAY_IDX_POS, userData, Boolean.class);
        if (startWithFirstDay != null) {
            meteredUserData.setStartWithFirstDay(startWithFirstDay);
        }

        Long cookieCreationTime = SdkUtils.getArrayValueIfExistsOrNull(COOKIE_CREATION_TIME_IDX_POS, userData, Long.class);
        if (cookieCreationTime != null) {
            meteredUserData.setCookieCreationTime(cookieCreationTime);
        }
        return meteredUserData;
    }
}
