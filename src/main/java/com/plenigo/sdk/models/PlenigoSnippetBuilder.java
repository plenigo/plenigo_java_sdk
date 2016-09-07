package com.plenigo.sdk.models;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.internal.util.HashUtils;

import java.util.UUID;

/**
 * <p>
 * Builder for plenigo snippets.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class PlenigoSnippetBuilder {
    private SnippetConfig snippetConfig;

    /**
     * Builds a plenigo snippet builder with the provided configuration.
     *
     * @param snippetConfig configuration
     */
    public PlenigoSnippetBuilder(SnippetConfig snippetConfig) {
        this.snippetConfig = snippetConfig;
    }

    /**
     * Builds the code snippet with the provided configuration.
     *
     * @return snippet
     *
     * @throws PlenigoException if there are any errors during the build
     */
    public String build() throws PlenigoException {
        SnippetConfig config = snippetConfig;
        if (config == null) {
            config = new SnippetConfig();
        }
        String snippet = "";
        String elementId = config.getElementId();
        SnippetType type = config.getSnippetType();
        String loggedOutRedUrl = config.getLoggedOutRedirectUrl();
        String loginToken = config.getLoginToken();
        if (elementId == null) {
            elementId = "plenigoSnippet" + HashUtils.calculateHash(UUID.randomUUID().toString()).substring(3, 7);
            snippet = "<div id=\"" + elementId + "\"></div>\n";
        }

        if (type == null) {
            type = SnippetType.PERSONAL_DATA;
        }

        String loginAddon = "";

        if (loginToken != null) {
            loginAddon += ",\"" + loginToken + "\"";
        }

        snippet += "<script type=\"application/javascript\">\n";
        snippet += String.format("plenigo.renderSnippet(\"%s\",\"%s\",\"%s\"%s);\n", elementId, type.getType(), loggedOutRedUrl,
                loginAddon);
        snippet += "</script>\n\n";
        return snippet;
    }
}
