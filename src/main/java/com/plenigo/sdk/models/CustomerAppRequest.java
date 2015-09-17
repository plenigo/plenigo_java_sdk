package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used for requesting an app token for customer app management purposes.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class CustomerAppRequest {
    private String customerId;

    /**
     * Constructor with the required fields.
     *
     * @param customerId customer id
     */
    public CustomerAppRequest(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the customer id.
     *
     * @return customer id
     */
    public String getCustomerId() {
        return customerId;
    }
}
