package com.plenigo.sdk.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * <p>
 * Tests for {@link ProductsBought}.
 * </p>
 */
public class ProductsBoughtTest {
    public static final List<SubscriptionProduct> SUBSCRIPTION_PRODUCTS = Collections.singletonList(new SubscriptionProduct("prodId", "title", new Date(), null));
    public static final List<SinglePaymentProduct> SINGLE_PAYMENT_PRODUCTS = Collections.singletonList(new SinglePaymentProduct("prodId", "title", new Date()));
    ProductsBought productsBought;

    @Before
    public void setup() {
        productsBought = new ProductsBought(SUBSCRIPTION_PRODUCTS, SINGLE_PAYMENT_PRODUCTS);
    }

    @Test
    public void create() {
        assertNotNull("Error occured while creating a products bought object", new ProductsBought(SUBSCRIPTION_PRODUCTS, SINGLE_PAYMENT_PRODUCTS));
    }

    @Test
    public void getSubscriptions() {
        assertEquals("The subscription product list is not the same", SUBSCRIPTION_PRODUCTS, productsBought.getSubscriptionProducts());
    }

    @Test
    public void getSinglePaymentProducts() {
        assertEquals("The single payment product list is not the same", SINGLE_PAYMENT_PRODUCTS, productsBought.getSinglePaymentProducts());
    }
}
