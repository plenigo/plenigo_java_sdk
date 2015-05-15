package com.plenigo.sdk.models;


/**
 * <p>
 * Represents Categories validity time.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public enum ValidityTime {
    UNLIMITED("0"), DAY("1"), TWO_DAYS("2"), WEEK("7"), TWO_WEEKS("14"), MONTH("31");

    private String value;


    /**
     * Constructor with the required validity time value.
     *
     * @param value The name of the grant type
     */
    ValidityTime(String value) {
        this.value = value;
    }

    /**
     * Returns the value of the validity time.
     *
     * @return the value of the validity time
     */
    public String getValue() {
        return value;
    }

    /**
     * Retrieves the validity time that corresponds to the value, otherwise it assumes its unlimited.
     *
     * @param value the value to compare with
     *
     * @return The validity time
     */
    public static ValidityTime get(String value) {
        for (ValidityTime valTime : values()) {
            if (valTime.value.equals(value)) {
                return valTime;
            }
        }
        return ValidityTime.UNLIMITED;
    }
}
