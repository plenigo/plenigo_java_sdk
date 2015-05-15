package com.plenigo.sdk.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <p>
 * This class contains information regarding the products that a user has bought.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class ProductsBought {
    private List<SubscriptionProduct> subscriptionProducts;
    private List<SinglePaymentProduct> singlePaymentProducts;

    /**
     * Required constructor.
     *
     * @param subscriptionProducts  subscription products
     * @param singlePaymentProducts single payment products
     */
    public ProductsBought(List<SubscriptionProduct> subscriptionProducts, List<SinglePaymentProduct> singlePaymentProducts) {
        this.subscriptionProducts = new ArrayList<SubscriptionProduct>();
        if (subscriptionProducts != null) {
            this.subscriptionProducts.addAll(subscriptionProducts);
        }
        this.singlePaymentProducts = new ArrayList<SinglePaymentProduct>();
        if (singlePaymentProducts != null) {
            this.singlePaymentProducts.addAll(singlePaymentProducts);
        }
    }

    /**
     * Returns the subscriptions an user has.
     *
     * @return the subscription products a user has
     */
    public List<SubscriptionProduct> getSubscriptionProducts() {
        return Collections.unmodifiableList(subscriptionProducts);
    }

    /**
     * Returns the single payment products an user has.
     *
     * @return the single payment products an user has
     */
    public List<SinglePaymentProduct> getSinglePaymentProducts() {
        return Collections.unmodifiableList(singlePaymentProducts);
    }
}
