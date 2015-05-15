package com.plenigo.sdk.models;

/**
 * <p>
 * This class contains category information from a plenigo defined category. A category can be any
 * digital content.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class CategoryData {

    private String id;
    private PricingData pricingData;
    private ValidityTime validityTime;

    /**
     * Required constructor.
     *
     * @param id Category id
     * @param pricingData Pricing information
     * @param validityTime validity time of the category
     */
    public CategoryData(String id, PricingData pricingData, ValidityTime validityTime) {
        this.id = id;
        if (pricingData == null) {
            pricingData = new PricingData(false, 0.0, 0.0, "");
        }
        this.pricingData = pricingData;
        this.validityTime = validityTime;
    }

    /**
     * Returns the category id.
     * @return The category id
     */
    public String getId() {
        return id;
    }

    /**
     * Pricing information of the category.
     * @return pricing information
     */
    public PricingData getPricingData() {
        return pricingData;
    }

    /**
     * The validity time of the category.
     * @return The validity time.
     */
    public ValidityTime getValidityTime() {
        return validityTime;
    }
}
