package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Tests for {@link LoginConfig}.
 * </p>
 */
public class LoginConfigTest {
    @Test
    public void testToString() {
        assertNotNull(new LoginConfig("redUri", DataAccessScope.PROFILE).toString());
    }
}
