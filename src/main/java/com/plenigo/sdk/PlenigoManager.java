package com.plenigo.sdk;


import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.models.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p>
 * This class centralizes plenigo's Configuration so that it can be used through
 * the complete SDK.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class PlenigoManager {

    private static final Logger LOGGER = Logger.getLogger(PlenigoManager.class.getName());

    /**
     * Singleton instance.
     */
    private static final PlenigoManager INSTANCE = new PlenigoManager();
    /**
     * Configuration Object containing environment specific data.
     */
    private Configuration config;

    /**
     * Default constructor.
     */
    private PlenigoManager() {
        config = new Configuration();
        config.setUrl(ApiURLs.DEFAULT_PLENIGO_URL);
    }

    /**
     * Singleton instance retrieval method.
     *
     * @return Singleton instance of @{link {@link PlenigoManager}}
     */
    public static PlenigoManager get() {
        return INSTANCE;
    }

    /**
     * Returns the company id.
     *
     * @return the company id
     */
    public String getCompanyId() {
        return config.getCompanyId();
    }

    /**
     * Returns the secret key.
     *
     * @return The secret key
     */
    public String getSecret() {
        return config.getSecret();
    }

    /**
     * This returns the URL used by all the API communications within Plenigo.
     *
     * @return The API base URL
     */
    public String getUrl() {
        return config.getUrl();
    }


    /**
     * This returns the URL used by the OAuth communications within Plenigo.
     *
     * @return The OAuth base URL
     */
    public String getOauthUrl() {
        return config.getOauthUrl();
    }

    /**
     * Returns the company id.
     *
     * @return the company id
     */
    public Boolean isTestMode() {
        if (config.isTestMode() == null) {
            return false;
        } else {
            return config.isTestMode();
        }
    }


    /**
     * Configures the data to be used by the SDK.
     *
     * @param secret    The secret to  be used
     * @param companyId Unique id of the company
     */
    public void configure(final String secret, final String companyId) {
        config.setSecret(secret);
        config.setCompanyId(companyId);
        LOGGER.log(Level.INFO, "plenigo Manager Configured with the following data: {0}!", config);
    }

    /**
     * Configures the data to be used by the SDK.
     *
     * @param secret    The secret to  be used
     * @param companyId Unique id of the company
     * @param testMode  Are API transactions on test mode
     */
    public void configure(final String secret, final String companyId, final boolean testMode) {
        configure(secret, companyId);
        config.setTestMode(testMode);
        if (testMode) {
            LOGGER.log(Level.INFO, "Test mode is turned on for plenigo SDK transactions.", companyId);
        }
    }

    /**
     * Configures the data to be used by the SDK.
     *  @param url       The url where the plenigo API is
     * @param secret    The secret to  be used
     * @param companyId Unique id of the company
     * @param testMode  Are API transactions on test mode
     * @param oauthUrl The OAUTH base url
     */
    public void configure(final String url, final String secret, final String companyId, final Boolean testMode, String oauthUrl) {
        config.setUrl(url);
        config.setOauthUrl(oauthUrl);
        configure(secret, companyId, testMode);
    }
}
