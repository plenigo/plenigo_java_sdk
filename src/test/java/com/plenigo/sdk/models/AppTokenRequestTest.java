package com.plenigo.sdk.models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.models.AppTokenRequest}.
 * </p>
 */
public class AppTokenRequestTest {
    public static final String CUSTOMER_ID = "customerId";
    public static final String PRODUCT_ID = "productId";
    public static final String DESCRIPTION = "description";
    private AppTokenRequest appTokenRequest;


    @Before
    public void setup(){
        appTokenRequest = new AppTokenRequest(CUSTOMER_ID, PRODUCT_ID, DESCRIPTION);
    }

    @Test
    public void testGetCustomerId(){
        assertEquals("Customer id is not equal", CUSTOMER_ID, appTokenRequest.getCustomerId());
    }

    @Test
    public void testGetProductId(){
        assertEquals("Product id is not equal", PRODUCT_ID, appTokenRequest.getProductId());
    }

    @Test
    public void testGetDescription(){
        assertEquals("Description is not equal", DESCRIPTION, appTokenRequest.getDescription());
    }
}
