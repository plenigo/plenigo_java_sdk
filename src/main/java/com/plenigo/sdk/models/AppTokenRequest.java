package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used for requesting an app token for customer app management purposes.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class AppTokenRequest {
    private String customerId;
    private String productId;
    private String description;

    /**
     * Constructor with the required fields.
     *
     * @param customerId customer id
     * @param productId product id
     * @param description description
     */
    public AppTokenRequest(String customerId, String productId, String description) {
        this.customerId = customerId;
        this.productId = productId;
        this.description = description;
    }

    /**
     * Returns the customer id.
     *
     * @return customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the product id.
     *
     * @return product id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Returns the description.
     *
     * @return description object
     */
    public String getDescription() {
        return description;
    }
}
