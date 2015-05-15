package com.plenigo.sdk.models;


/**
 * Time periods.
 */
public enum TimePeriod {
    DAY, WEEK, MONTH, YEAR;

    /**
     * Searches for the given period and if it does not exist, it returns DAY by default.
     *
     * @param period the period to search for
     *
     * @return the period if it exists, DAY otherwise
     */
    public static TimePeriod get(String period) {
        TimePeriod selectedPeriod = DAY;
        for (TimePeriod timePeriod : values()) {
            if (timePeriod.name().equals(period.toUpperCase())) {
                selectedPeriod = timePeriod;
                break;
            }
        }
        return selectedPeriod;
    }
}
