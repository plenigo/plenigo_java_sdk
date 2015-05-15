package com.plenigo.sdk.internal.models;

import com.plenigo.sdk.internal.ApiURLs;

/**
 * <p>
 * This class contains general attributes regarding plenigo's general
 * configuration.
 * <p>
 * <b>IMPORTANT:</b> This class is part of the internal API, please do not use it, because it can
 * be removed in future versions of the SDK or access to such elements could
 * be changed from 'public' to 'default' or less.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is <b>not</b> thread safe.
 * </p>
 */
public class Configuration {
    /**
     * The URL where plenigos API is located.
     */
    private String url;


    /**
     * The URL where plenigos OAUTH methods are located.
     */
    private String oauthUrl;
    /**
     * The users secret that will be used with the SDK.
     */
    private String secret;

    /**
     * The company ID used by the API user.
     */
    private String companyId;

    /**
     * This indicates if all the transactions are being used in test mode.
     */
    private Boolean testMode;

    /**
     * Default constructor.
     */
    public Configuration() {
        setUrl(ApiURLs.DEFAULT_PLENIGO_URL);
        setOauthUrl(ApiURLs.OAUTH_PLENIGO_URL);
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * @param configKey the configKey to set
     */
    public void setSecret(final String configKey) {
        this.secret = configKey;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param newUrl the url to set
     */
    public void setUrl(final String newUrl) {
        this.url = newUrl;
    }

    /**
     * @return the companyId
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @param compId the compId to set
     */
    public void setCompanyId(final String compId) {
        this.companyId = compId;
    }

    /**
     * @return is in test mode
     */
    public Boolean isTestMode() {
        return testMode;
    }

    /**
     * @param isTestMode the testMode to set
     */
    public void setTestMode(Boolean isTestMode) {
        this.testMode = isTestMode;
    }

    /**
     * Returns an oauth url.
     *
     * @return the oauth url
     */
    public String getOauthUrl() {
        return oauthUrl;
    }

    /**
     * Sets the oauth url.
     *
     * @param oauthUrl the oauth url
     */
    public void setOauthUrl(String oauthUrl) {
        this.oauthUrl = oauthUrl;
    }

    @Override
    public String toString() {
        String secretVar = null;
        if (secret != null) {
            secretVar = secret.replaceAll("(?s).", "*");
        }
        return "Configuration{" + "url='" + url + "', oauthUrl=" + oauthUrl + "', companyId='" + companyId + '\'' + ", secret=" + secretVar + '}';
    }
}
