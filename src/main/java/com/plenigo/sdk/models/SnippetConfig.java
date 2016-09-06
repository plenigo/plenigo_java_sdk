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

    public SnippetConfig(String elementId, SnippetType snippetType, String loggedOutRedirectUrl, String loginToken) {
        this.elementId = elementId;
        this.snippetType = snippetType;
        this.loggedOutRedirectUrl = loggedOutRedirectUrl;
        this.loginToken = loginToken;
    }

    public SnippetConfig() {
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public SnippetType getSnippetType() {
        return snippetType;
    }

    public void setSnippetType(SnippetType snippetType) {
        this.snippetType = snippetType;
    }

    public String getLoggedOutRedirectUrl() {
        return loggedOutRedirectUrl;
    }

    public void setLoggedOutRedirectUrl(String loggedOutRedirectUrl) {
        this.loggedOutRedirectUrl = loggedOutRedirectUrl;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
