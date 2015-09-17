package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used to represent application access tokens.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class AppAccessToken {
    private String customerId;
    private String token;

    /**
     * Constructor with the required fields.
     *
     * @param customerId customer id
     * @param token      token
     */
    public AppAccessToken(String customerId, String token) {
        this.customerId = customerId;
        this.token = token;
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
     * Returns the token.
     *
     * @return token
     */
    public String getToken() {
        return token;
    }

}
