package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link ValidityTime}.
 * </p>
 */
public class ValidityTimeTest {
    @Test
    public void testValidValidityTime() {
        assertEquals("Validity times are not equal", ValidityTime.DAY, ValidityTime.get(ValidityTime.DAY.getValue()));
    }

    @Test
    public void testDefaultValidityTime() {
        assertEquals("Default validity time is not what expected", ValidityTime.UNLIMITED, ValidityTime.get(null));
    }
}
