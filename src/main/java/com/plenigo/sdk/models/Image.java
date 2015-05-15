package com.plenigo.sdk.models;


import java.io.Serializable;

/**
 * <p>
 * This object contains image information related to a product
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class Image implements Serializable {
    private String url;
    private String description;
    private String altText;


    /**
     * Image constructor.
     *
     * @param url         The URL of the image
     * @param description The description of the image
     * @param altText     The alt text of the image
     */
    public Image(String url, String description, String altText) {
        this.url = url;
        this.description = description;
        this.altText = altText;
    }

    /**
     * The URL of the image.
     *
     * @return The URL of the image
     */
    public String getUrl() {
        return url;
    }

    /**
     * The description of the image.
     *
     * @return The description of the image
     */
    public String getDescription() {
        return description;
    }

    /**
     * The alt text of the image.
     *
     * @return The alt text of the image
     */
    public String getAltText() {
        return altText;
    }

    @Override
    public String toString() {
        return "Image{" + "url='" + url + '\'' + ", description='" + description + '\'' + ", altText='" + altText + '\'' + '}';
    }
}
