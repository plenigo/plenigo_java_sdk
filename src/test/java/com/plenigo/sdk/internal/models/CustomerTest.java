package com.plenigo.sdk.internal.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Tests for {@link Customer}.
 * </p>
 */
public class CustomerTest {

    @Test
    public void testToString() {
        assertNotNull(new Customer("sample", 1L).toString());
    }
}
