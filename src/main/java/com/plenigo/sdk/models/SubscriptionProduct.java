package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.models.BaseProduct;
import com.plenigo.sdk.internal.util.DateUtils;

import java.util.Date;


/**
 * <p>
 * Represents a subscription product.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class SubscriptionProduct extends BaseProduct {

    private Date endDate;

    /**
     * Required constructor.
     *
     * @param productId the id
     * @param title     the title
     * @param buyDate   the date the product was bought
     * @param endDate   the date the subscription expires
     */
    public SubscriptionProduct(String productId, String title, Date buyDate, Date endDate) {
        super(productId, title, buyDate);
        this.endDate = DateUtils.copy(endDate);
    }

    /**
     * Returns the date where the subscription expires.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return DateUtils.copy(endDate);
    }
}
