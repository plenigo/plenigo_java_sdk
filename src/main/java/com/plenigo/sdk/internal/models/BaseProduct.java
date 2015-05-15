package com.plenigo.sdk.internal.models;

import com.plenigo.sdk.internal.util.DateUtils;

import java.util.Date;


/**
 * <p>
 * Represents a basic product.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class BaseProduct {
    private String productId;
    private String title;
    private Date buyDate;

    /**
     * Required constructor.
     *
     * @param productId the id
     * @param title     the title
     * @param buyDate   the date the product was bought
     */
    public BaseProduct(String productId, String title, Date buyDate) {
        this.productId = productId;
        this.title = title;
        this.buyDate = DateUtils.copy(buyDate);
    }

    /**
     * Returns a product id.
     *
     * @return the id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Returns a product title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the buy date.
     *
     * @return the buy date
     */
    public Date getBuyDate() {
        return DateUtils.copy(buyDate);
    }
}
