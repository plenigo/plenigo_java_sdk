package com.plenigo.sdk.internal.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link BaseProduct}.
 * </p>
 */
public class BaseProductTest {
    public static final String PRODUCT_ID = "productId";
    public static final String TITLE = "title";
    public static final Date BUY_DATE = new Date();
    BaseProduct product;


    @Before
    public void setup(){
        product = new BaseProduct(PRODUCT_ID, TITLE, BUY_DATE);
    }

    @Test
    public void testGetProductId() {
        assertEquals("Product id does not match",PRODUCT_ID, product.getProductId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Title does not match",TITLE, product.getTitle());
    }

    @Test
    public void testGetBuyDate() {
        assertEquals("Buy Date does not match",BUY_DATE, product.getBuyDate());
    }
}
