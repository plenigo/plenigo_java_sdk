package com.plenigo.sdk.models;

/**
 * Snippet type for js snippets.
 */
public enum SnippetType {

    /**
     * Personal profile snippet.
     */
    PERSONAL_DATA("plenigo.Snippet.PERSONAL_DATA"),

    /**
     * Order list snippet.
     */
    ORDER("plenigo.Snippet.ORDER"),

    /**
     * Subscription status snippet.
     */
    SUBSCRIPTION("plenigo.Snippet.SUBSCRIPTION"),

    /**
     * Payment methods screen snippet.
     */
    PAYMENT_METHODS("plenigo.Snippet.PAYMENT_METHODS"),

    /**
     * Address information snippet.
     */
    ADDRESS_DATA("plenigo.Snippet.ADDRESS_DATA");

    private String type;

    /**
     * Constructor with snippet type.
     *
     * @param type snippet type
     */
    SnippetType(String type) {
        this.type = type;
    }

    /**
     * Returns the snippet type.
     *
     * @return snippet type
     */
    public String getType() {
        return type;
    }
}
