package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Tests for {@link Product}.
 * </p>
 */
public class ProductTest {
    @Test
    public void testToString() {
        Product prod = new Product("");
        prod.setId("id");
        prod.setTitle("Title");
        prod.setCurrency("USD");
        prod.setPrice(44.4);
        assertNotNull(prod.toString());
    }
}
