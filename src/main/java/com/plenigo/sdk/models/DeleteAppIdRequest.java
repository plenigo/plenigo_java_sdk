package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used for deleting an application id registered to a customer.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class DeleteAppIdRequest {
    private String customerId;
    private String customerAppId;

    /**
     * Constructor with the required fields.
     *
     * @param customerId    customer id
     * @param customerAppId customer app id
     */
    public DeleteAppIdRequest(String customerId, String customerAppId) {
        this.customerId = customerId;
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
     * Returns the customer app id.
     *
     * @return customer app id
     */
    public String getCustomerAppId() {
        return customerAppId;
    }
}
