package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used to represent application access information.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class AppAccessData {
    private String customerId;
    private String description;
    private String customerAppId;
    private String productId;

    /**
     * Constructor with the required fields.
     *
     * @param customerId    the customer id
     * @param description   the access description
     * @param customerAppId customer app id
     * @param productId     product id
     */
    public AppAccessData(String customerId, String description, String customerAppId, String productId) {
        this.customerId = customerId;
        this.description = description;
        this.customerAppId = customerAppId;
        this.productId = productId;
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
     * Returns the description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the customer app id.
     *
     * @return customer app id
     */
    public String getCustomerAppId() {
        return customerAppId;
    }

    /**
     * Returns the product id.
     *
     * @return product id
     */
    public String getProductId() {
        return productId;
    }
}
