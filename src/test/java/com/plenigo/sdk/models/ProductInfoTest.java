package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * <p>
 * Tests for {@link com.plenigo.sdk.models.CategoryInfo}.
 * </p>
 */
public class ProductInfoTest {
    @Test
    public void testToString() {
        assertNotNull(new ProductInfo("prodId", "title", "desc").toString());
    }


    @Test
    public void testGetters() {
        ProductInfo productInfo = new ProductInfo("prodId", "title", "desc");
        assertNotNull(productInfo.getProductId());
        assertNotNull(productInfo.getTitle());
        assertNotNull(productInfo.getDescription());
    }
}
