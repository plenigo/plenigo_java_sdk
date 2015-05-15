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
    private BaseProduct baseProduct;

    @Before
    public void setup(){
        baseProduct = new BaseProduct(PRODUCT_ID, TITLE, BUY_DATE);
    }

    @Test
    public void testGetProductId(){
        assertEquals("Product id is not correct", PRODUCT_ID, baseProduct.getProductId());
    }


    @Test
    public void testGetTitle(){
        assertEquals("Title is not correct", TITLE, baseProduct.getTitle());
    }

    @Test
    public void testGetBuyDate(){
        assertEquals("Buy date is not correct", BUY_DATE, baseProduct.getBuyDate());
    }
}
