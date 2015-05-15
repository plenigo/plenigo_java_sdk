package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.models.BaseProduct;

import java.util.Date;


/**
 * <p>
 * Represents a single payment product.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class SinglePaymentProduct extends BaseProduct {

    /**
     * Required constructor.
     *
     * @param productId the id
     * @param title     the title
     * @param buyDate   the date the product was bought
     */
    public SinglePaymentProduct(String productId, String title, Date buyDate) {
        super(productId, title, buyDate);
    }
}
