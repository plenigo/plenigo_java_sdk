package com.plenigo.sdk.models;

/**
 * <p>
 * Contains all the data access scopes available.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public enum DataAccessScope {
    PROFILE("profile");
    private String scopeName;

    /**
     * Constructor with the required scopeName name.
     *
     * @param name The name of the scopeName
     */
    DataAccessScope(String name) {
        scopeName = name;
    }

    /**
     * Returns the name of the login scopeName.
     *
     * @return the name of the login scopeName
     */
    public String getName() {
        return scopeName;
    }
}
