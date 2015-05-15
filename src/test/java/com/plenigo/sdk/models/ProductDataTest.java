package com.plenigo.sdk.models;


import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.internal.util.EncryptionUtils}.
 * </p>
 */
public class ProductDataTest {
    @Test
    public void testToString() {
        Subscription subscription = new Subscription(true, 1, 2, false);
        PricingData pricingData = new PricingData(true, 10.00, 34, "USD");
        ActionPeriod actionPeriod = new ActionPeriod("name", 4, 12.00);
        List<Image> images = Collections.singletonList(new Image("url", "desc", "alt"));
        ProductData data = new ProductData("id", subscription, "title", "desc", false, pricingData, actionPeriod, images);
        assertNotNull(data.toString());
    }

    @Test
    public void testToStringWithNullObjects() {
        ProductData data = new ProductData("id", null, "title", "desc", false, null, null, null);
        assertNotNull(data.toString());
    }
}
