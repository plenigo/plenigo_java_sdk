package com.plenigo.sdk.models;


/**
 * <p>
 * Contains all the the produc types.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public enum ProductType {
    EBOOK(false),
    DIGITALNEWSPAPER(false),
    DOWNLOAD(false),
    VIDEO(false),
    MUSIC(false),
    BOOK(true),
    NEWSPAPER(true);

    private boolean isShippingAllowed;

    /**
     * Constructs a product type.
     *
     * @param isShippingAllowed if the shipping is allowed
     */
    private ProductType(boolean isShippingAllowed) {
        this.isShippingAllowed = isShippingAllowed;
    }

    /**
     * Returns a flag that indicates if shipping is allowed.
     *
     * @return true if shipping is allowed, false otherwise
     */
    public boolean isShippingAllowed() {
        return isShippingAllowed;
    }
}
