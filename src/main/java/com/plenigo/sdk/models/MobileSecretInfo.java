package com.plenigo.sdk.models;


/**
 * <p>
 * This object represents the mobile secret info returned due to a request.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class MobileSecretInfo {
    private String email;
    private String mobileAppSecret;

    /**
     * Required constructor.
     *
     * @param email           email address
     * @param mobileAppSecret mobile app secret
     */
    public MobileSecretInfo(String email, String mobileAppSecret) {
        this.email = email;
        this.mobileAppSecret = mobileAppSecret;
    }

    /**
     * Returns the email address.
     *
     * @return email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the mobile app secret.
     *
     * @return mobile app secret
     */
    public String getMobileAppSecret() {
        return mobileAppSecret;
    }
}
