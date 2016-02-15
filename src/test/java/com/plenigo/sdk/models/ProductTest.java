package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
        prod.setSubscriptionRenewal(false);
        assertNotNull(prod.toString());
    }

    @Test
    public void testProductWithPrice() {
        Double price = 12.00;
        String id = "id";
        Product prod = new Product(id, price);
        assertEquals("Prices are not the same", price, prod.getPrice());
        assertEquals("Id is not the same", id, prod.getId());
    }
}
