package com.plenigo.sdk.models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link AppAccessData}.
 * </p>
 */
public class AppAccessDataTest {
    public static final String CUSTOMER_ID = "customerId";
    public static final String DESCRIPTION = "description";
    public static final String CUSTOMER_APP_ID = "customerAppId";
    public static final String PRODUCT_ID = "productId";
    private AppAccessData appAccessData;


    @Before
    public void setup(){
        appAccessData = new AppAccessData(CUSTOMER_ID, DESCRIPTION, CUSTOMER_APP_ID, PRODUCT_ID);
    }

    @Test
    public void testGetCustomerId(){
        assertEquals("Customer id is not equal", CUSTOMER_ID, appAccessData.getCustomerId());
    }

    @Test
    public void testGetDescription(){
        assertEquals("Description is not equal", DESCRIPTION, appAccessData.getDescription());
    }

    @Test
    public void testGetCustomerAppId(){
        assertEquals("Customer app id is not equal", CUSTOMER_APP_ID, appAccessData.getCustomerAppId());
    }

    @Test
    public void testProductId(){
        assertEquals("Product id is not equal", PRODUCT_ID, appAccessData.getProductId());
    }
}
