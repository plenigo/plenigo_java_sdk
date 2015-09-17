package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used for requesting product access verification of a product.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class ProductAccessRequest {
    private String customerId;
    private String productId;
    private String customerAppId;

    /**
     * Constructor with the required fields.
     *
     * @param customerId    customer id
     * @param productId     product id
     * @param customerAppId customer app id
     */
    public ProductAccessRequest(String customerId, String productId, String customerAppId) {
        this.customerId = customerId;
        this.productId = productId;
        this.customerAppId = customerAppId;
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
     * Returns the customer app id.
     *
     * @return customer app id
     */
    public String getCustomerAppId() {
        return customerAppId;
    }
}
