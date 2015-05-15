package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * <p>
 * Tests for {@link CategoryData}.
 * </p>
 */
public class CategoryDataTest {
    @Test
    public void testToString() {
        assertNotNull(new CategoryData("catId", null, ValidityTime.DAY).toString());
    }

    @Test
    public void testGetters() {
        CategoryData data = new CategoryData("catId", new PricingData(false, 0.0, 0.0, ""), ValidityTime.DAY);
        assertNotNull(data.getId());
        assertNotNull(data.getPricingData());
        assertNotNull(data.getValidityTime());
    }
}
