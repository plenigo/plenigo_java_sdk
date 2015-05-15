package com.plenigo.sdk.internal.models;

import com.plenigo.sdk.models.TimePeriod;

/**
 * <p>
 * Represents metered user data.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class MeteredUserData extends BaseUserMeteredData {
    private long startTime;
    private TimePeriod meteredPeriod = TimePeriod.DAY;
    private boolean startWithFirstDay = true;
    private long cookieCreationTime;

    /**
     * Required constructor.
     *
     * @param isMeteredViewActivated indicates if the metered view is activated
     * @param freeViewsAllowed       indicates how many free views are allowed
     * @param viewsTaken             indicates the amount of views that the user has taken
     * @param isLimitReached         indicates if the limit has been reached
     * @param loginFreeViewsAllowed  indicates how many free views are allowed after login
     * @param loginFreeViewsTaken    indicates the amount of views that the user has taken after login
     * @param loginLimitReached      indicates if the limit has been reached after login
     */
    public MeteredUserData(Boolean isMeteredViewActivated, Long freeViewsAllowed, Long viewsTaken, Boolean isLimitReached, Long loginFreeViewsAllowed,
                           Long loginFreeViewsTaken, Boolean loginLimitReached) {
        super(isMeteredViewActivated, freeViewsAllowed, viewsTaken, isLimitReached, loginFreeViewsAllowed, loginFreeViewsTaken, loginLimitReached);
    }

    /**
     * Returns the metered period.
     *
     * @return the metered period
     */
    public TimePeriod getMeteredPeriod() {
        return meteredPeriod;
    }

    /**
     * Sets the metered period.
     *
     * @param meteredPeriod metered period
     *
     * @return the current instance
     */
    public MeteredUserData setMeteredPeriod(String meteredPeriod) {
        this.meteredPeriod = TimePeriod.get(meteredPeriod);
        return this;
    }

    /**
     * Returns an indicator saying if metering should start with the first day.
     *
     * @return the start with the first day indicator
     */
    public boolean getStartWithFirstDay() {
        return startWithFirstDay;
    }

    /**
     * Sets the indicator saying if metering should start with the first day.
     *
     * @param startWithFirstDay start with first day
     *
     * @return the current instance
     */
    public MeteredUserData setStartWithFirstDay(Boolean startWithFirstDay) {
        this.startWithFirstDay = startWithFirstDay;
        return this;
    }

    /**
     * Returns the cookie creation time in milliseconds.
     *
     * @return the cookie creation time
     */
    public long getCookieCreationTime() {
        return cookieCreationTime;
    }

    /**
     * Sets the cookie creation time in milliseconds.
     *
     * @param cookieCreationTime the cookie creation time
     *
     * @return the current instance
     */
    public MeteredUserData setCookieCreationTime(Long cookieCreationTime) {
        this.cookieCreationTime = cookieCreationTime;
        return this;
    }

    /**
     * Returns the start time.
     *
     * @return the start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     *
     * @param startTime the start time
     *
     * @return the current instance
     */
    public MeteredUserData setStartTime(long startTime) {
        this.startTime = startTime;
        return this;
    }
}
