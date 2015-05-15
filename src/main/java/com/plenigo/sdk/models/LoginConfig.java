package com.plenigo.sdk.models;

import java.io.Serializable;

/**
 * <p>
 * This object represents the used login data for the
 * plenigo Javascript API's login method.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class LoginConfig implements Serializable {

    /**
     * The URI to redirect to when the login is done.
     */
    private String redirectUri;
    /**
     * The data access accessScope.
     */
    private DataAccessScope accessScope;

    /**
     * The constructor with the required parameters, this object is meant to be
     * used with at least these parameters.
     *
     * @param redUri          The redirect URL
     * @param dataAccessScope The data access scope
     */
    public LoginConfig(String redUri, DataAccessScope dataAccessScope) {
        this.redirectUri = redUri;
        this.accessScope = dataAccessScope;
    }

    /**
     * The URL to be redirected to after successful login to finish up the server side workflow.
     * The given URL (or at least the starting part) must be registered in the plenigo backend. Otherwise an error is returned.
     *
     * @return The redirect URL
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * The data access accessScope.
     *
     * @return The data access scope
     */
    public DataAccessScope getAccessScope() {
        return accessScope;
    }

    @Override
    public String toString() {
        return "LoginData{" + "redirectUri='" + redirectUri + '\'' + ", accessScope=" + accessScope + '}';
    }
}
