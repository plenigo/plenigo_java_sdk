package com.plenigo.sdk.models;


/**
 * <p>
 * This object represents the snippet config data.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is <b>not</b> thread safe.
 * </p>
 */
public class SnippetConfig {
    private String elementId;
    private SnippetType snippetType;
    private String loggedOutRedirectUrl;
    private String loginToken;

    /**
     * This constructor builds a snippet config with the provided parameters.
     *
     * @param elementId            element id
     * @param snippetType          snippet type
     * @param loggedOutRedirectUrl logged out redirection url
     * @param loginToken           login token
     */
    public SnippetConfig(String elementId, SnippetType snippetType, String loggedOutRedirectUrl, String loginToken) {
        this.elementId = elementId;
        this.snippetType = snippetType;
        this.loggedOutRedirectUrl = loggedOutRedirectUrl;
        this.loginToken = loginToken;
    }

    /**
     * Default constructor.
     */
    public SnippetConfig() {
    }

    /**
     * Get the element id.
     *
     * @return element id
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Set the element id.
     *
     * @param elementId element id
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Get the snippet type.
     *
     * @return snippet type
     */
    public SnippetType getSnippetType() {
        return snippetType;
    }

    /**
     * Set the snippet type.
     *
     * @param snippetType snippet type
     */
    public void setSnippetType(SnippetType snippetType) {
        this.snippetType = snippetType;
    }

    /**
     * Get the logged out redirect url.
     *
     * @return redirect url
     */
    public String getLoggedOutRedirectUrl() {
        return loggedOutRedirectUrl;
    }

    /**
     * Set logged out redirect url.
     *
     * @param loggedOutRedirectUrl logged out redirect url
     */
    public void setLoggedOutRedirectUrl(String loggedOutRedirectUrl) {
        this.loggedOutRedirectUrl = loggedOutRedirectUrl;
    }

    /**
     * Get the login token.
     *
     * @return login token
     */
    public String getLoginToken() {
        return loginToken;
    }

    /**
     * Set the login token.
     *
     * @param loginToken login token
     */
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
