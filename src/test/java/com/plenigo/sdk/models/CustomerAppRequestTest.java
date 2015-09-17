package com.plenigo.sdk.models;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link com.plenigo.sdk.models.CustomerAppRequest}.
 * </p>
 */
public class CustomerAppRequestTest {
    public static final String CUSTOMER_ID = "customerId";
    private CustomerAppRequest appAccessData;


    @Before
    public void setup(){
        appAccessData = new CustomerAppRequest(CUSTOMER_ID);
    }

    @Test
    public void testGetCustomerId(){
        assertEquals("Customer id is not equal", CUSTOMER_ID, appAccessData.getCustomerId());
    }
}
