package com.plenigo.sdk.builders;

import com.plenigo.sdk.models.DataAccessScope;
import com.plenigo.sdk.models.LoginConfig;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.services.TokenService;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * <p>
 * Tests for {@link LoginSnippetBuilder}.
 * </p>
 */
public class LoginSnippetBuilderTest {

    /**
     * The base link that should be formed after
     * the user calls the {@link LoginSnippetBuilder#build()}
     * method.
     */
    private static final String PLENIGO_LOGIN_REGEX_BASIC_SNIPPET = "^plenigo\\.login\\s*\\(\\s*'(.*?)\\'\\s*\\)\\s*;$";
    private static final String PLENIGO_LOGIN_NO_ARGS = "plenigo.login();";
    private static final String SAMPLE_URI = "http://sampleuri/resource";

    /**
     * Returns a mocked builder since the Encryption is not necessary for these unit tests.
     *
     * @param loginConfig The login data to use for creating the builder.
     *
     * @return The builder to test
     */
    private LoginSnippetBuilder getBuilder(LoginConfig loginConfig) {
        suppressConstructor(EncryptionUtils.class);
        return new LoginSnippetBuilder(loginConfig);
    }

    /**
     * Test a successful case for {@link LoginSnippetBuilder#build()}
     */
    @Test
    public final void testBuildSuccessfulCase() {
        LoginConfig loginConfig = new LoginConfig(SAMPLE_URI, DataAccessScope.PROFILE);
        LoginSnippetBuilder linkBuilder = getBuilder(loginConfig);
        String builtSnippet = linkBuilder.build();
        assertNotNull("The generated object is null", builtSnippet);
        assertFalse("The snippet is blank", builtSnippet.trim().isEmpty());
        assertTrue("The snippet does not match the expected regex -> " + builtSnippet, builtSnippet.matches(PLENIGO_LOGIN_REGEX_BASIC_SNIPPET));
    }

    /**
     * Test a successful case for {@link LoginSnippetBuilder#build()}
     * with csrf token
     */
    @Test
    public final void testBuildSuccessfulCaseWithCSRFToken() {
        LoginConfig loginConfig = new LoginConfig(SAMPLE_URI, DataAccessScope.PROFILE);
        LoginSnippetBuilder linkBuilder = getBuilder(loginConfig);
        String builtSnippet = linkBuilder.withCSRFToken(TokenService.createCsrfToken()).build();
        assertNotNull("The generated object is null", builtSnippet);
        assertFalse("The snippet is blank", builtSnippet.trim().isEmpty());
        assertTrue("The snippet does not match the expected regex -> " + builtSnippet, builtSnippet.matches(PLENIGO_LOGIN_REGEX_BASIC_SNIPPET));
    }

    /**
     * Test a successful case for {@link LoginSnippetBuilder#build()}
     * with csrf token
     */
    @Test
    public final void testBuildSuccessfulCaseWithElementId() {
        LoginConfig loginConfig = new LoginConfig(SAMPLE_URI, DataAccessScope.PROFILE);
        LoginSnippetBuilder linkBuilder = getBuilder(loginConfig);
        String builtSnippet = linkBuilder.withElementId("elementId").build();
        assertNotNull("The generated object is null", builtSnippet);
        assertFalse("The snippet is blank", builtSnippet.trim().isEmpty());
        assertTrue("The snippet does not match the expected regex -> " + builtSnippet, builtSnippet.matches(PLENIGO_LOGIN_REGEX_BASIC_SNIPPET));
    }

    /**
     * Test a successful case for {@link LoginSnippetBuilder#build()}
     * without SSO and CSRF Token.
     */
    @Test
    public final void testBuildSuccessfulCaseWithNoSSOAndCSRFToken() {
        suppressConstructor(EncryptionUtils.class);
        LoginSnippetBuilder linkBuilder = new LoginSnippetBuilder();
        String builtSnippet = linkBuilder.withCSRFToken(TokenService.createCsrfToken()).build();
        assertNotNull("The generated object is null", builtSnippet);
        assertFalse("The snippet is blank", builtSnippet.trim().isEmpty());
        assertEquals("The snippet does not match the expected regex -> " + builtSnippet, builtSnippet, PLENIGO_LOGIN_NO_ARGS);
    }

    /**
     * Test a successful case for {@link LoginSnippetBuilder#build()}
     * without SSO nor CSRF token.
     */
    @Test
    public final void testBuildSuccessfulCaseWithNoSSONorLoginData() {
        suppressConstructor(EncryptionUtils.class);
        LoginSnippetBuilder linkBuilder = new LoginSnippetBuilder();
        String builtSnippet = linkBuilder.build();
        assertNotNull("The generated object is null", builtSnippet);
        assertFalse("The snippet is blank", builtSnippet.trim().isEmpty());
        assertEquals("The snippet is not equal to the expected one -> " + builtSnippet, PLENIGO_LOGIN_NO_ARGS, builtSnippet);
    }

    /**
     * Test toString method
     */
    @Test
    public final void testToString() {
        suppressConstructor(EncryptionUtils.class);
        LoginSnippetBuilder linkBuilder = new LoginSnippetBuilder();
        assertNotNull(linkBuilder.toString());
    }
}
