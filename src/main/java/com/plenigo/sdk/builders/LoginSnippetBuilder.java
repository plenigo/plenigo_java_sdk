package com.plenigo.sdk.builders;


import com.plenigo.sdk.models.LoginConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This class builds a plenigo's Javascript API login that is
 * compliant.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is <b>not</b> thread safe.
 * </p>
 */
public class LoginSnippetBuilder {

    private static final Logger LOGGER = Logger.getLogger(LoginSnippetBuilder.class.getName());
    /**
     * The data required to build the snippet.
     */
    private LoginConfig loginConfig;

    /**
     * The cross-site request forgery (CSRF) token.
     */
    private String csrfToken;
    /**
     * The login event template, this can be interpreted as a javascript
     * snippet.
     */
    private static final String LOGIN_SNIPPET_TPL = "plenigo.login('%s','%s'%s);";

    /**
     * The login event template, this can be interpreted as a javascript
     * snippet.
     */
    private static final String NO_ARG_LOGIN_SNIPPET = "plenigo.login();";

    /**
     * Constructor of the snippet builder, this requires a {@link LoginConfig} object.
     *
     * @param data The login data used.
     */
    public LoginSnippetBuilder(LoginConfig data) {
        loginConfig = data;
    }

    /**
     * Constructor of the snippet builder, this is used when the user wants to
     * use the login functionality without single sign on.
     */
    public LoginSnippetBuilder() {
        this(new LoginConfig("", null));
    }

    /**
     * This method is used to build the link once all the information and
     * options have been selected, this will produce a Javascript snippet of
     * code that can be used as an event on a webpage.
     *
     * @return A Javascript snippet that is compliant with plenigo's Javascript
     * SDK.
     */
    public String build() {
        LOGGER.log(Level.FINEST, "Creating snippet with login data {0}", this);
        String stateForSnippet = "";

        String accessScope = "";
        if (loginConfig.getAccessScope() != null) {
            accessScope = loginConfig.getAccessScope().getName();
        }

        if (csrfToken != null) {
            stateForSnippet = ",'" + csrfToken + "'";
        }
        String loginSnippet;
        if (!loginConfig.getRedirectUri().isEmpty()) {
            loginSnippet = String.format(LOGIN_SNIPPET_TPL, loginConfig.getRedirectUri(), accessScope, stateForSnippet);
        } else {
            LOGGER.log(Level.FINEST, "No redirect uri or token specified, using no arg login snippet");
            loginSnippet = NO_ARG_LOGIN_SNIPPET;
        }
        LOGGER.log(Level.FINEST, "Built login snippet: {0}.", loginSnippet);
        return loginSnippet;
    }


    /**
     * When this method is called before the {@link LoginSnippetBuilder#build()}
     * method, when the login snippet is built, it will fill out the state parameter of the Javascript SDK
     * login function with a cross-site request forgery (CSRF) token.
     *
     * @param token The provided CSRF token
     *
     * @return The same {@link LoginSnippetBuilder} instance
     */
    public LoginSnippetBuilder withCSRFToken(String token) {
        csrfToken = token;
        return this;
    }

    @Override
    public String toString() {
        return "LoginSnippetBuilder{" + "loginData=" + loginConfig + ", csrfToken='" + csrfToken + '\'' + '}';
    }
}
