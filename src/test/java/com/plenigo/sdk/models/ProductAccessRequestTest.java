package com.plenigo.sdk.models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.models.ProductAccessRequest}.
 * </p>
 */
public class ProductAccessRequestTest {
    public static final String CUSTOMER_ID = "customerId";
    public static final String PRODUCT_ID = "productId";
    public static final String CUSTOMER_APP_ID = "appId";
    private ProductAccessRequest accessRequest;


    @Before
    public void setup(){
        accessRequest = new ProductAccessRequest(CUSTOMER_ID, PRODUCT_ID, CUSTOMER_APP_ID);
    }

    @Test
    public void testGetCustomerId(){
        assertEquals("Customer id is not equal", CUSTOMER_ID, accessRequest.getCustomerId());
    }

    @Test
    public void testGetProductId(){
        assertEquals("Product id is not equal", PRODUCT_ID, accessRequest.getProductId());
    }

    @Test
    public void testGetCustomerAppId(){
        assertEquals("Customer app id is not equal", CUSTOMER_APP_ID, accessRequest.getCustomerAppId());
    }
}
