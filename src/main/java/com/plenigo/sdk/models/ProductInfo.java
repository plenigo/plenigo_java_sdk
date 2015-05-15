package com.plenigo.sdk.models;


/**
 * <p>
 * This object represents the product information retrieved from a paged list.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class ProductInfo {
    private String productId;
    private String title;
    private String description;

    /**
     * Required constructor.
     *
     * @param productId   id of the product
     * @param title       title of the product
     * @param description description of the product
     */
    public ProductInfo(String productId, String title, String description) {
        this.productId = productId;
        this.title = title;
        this.description = description;
    }

    /**
     * Retrieves the product id.
     *
     * @return the product id
     */
    public String getProductId() {
        return productId;
    }


    /**
     * Retrieves the product title.
     *
     * @return the product title
     */
    public String getTitle() {
        return title;
    }


    /**
     * Retrieves the product description.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }
}
