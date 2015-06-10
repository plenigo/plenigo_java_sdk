package com.plenigo.sdk.internal.models;

import com.plenigo.sdk.models.SubscriptionProduct;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.models.SubscriptionProduct}.
 * </p>
 */
public class SubscriptionProductTest {
    public static final String PRODUCT_ID = "productId";
    public static final String TITLE = "title";
    public static final Date BUY_DATE = new Date();
    public static final Date END_DATE = new Date();
    SubscriptionProduct product;


    @Before
    public void setup(){
        product = new SubscriptionProduct(PRODUCT_ID, TITLE, BUY_DATE, END_DATE);
    }

    @Test
    public void testGetEndDate() {
        assertEquals("End Date does not match",END_DATE, product.getEndDate());
    }
}
