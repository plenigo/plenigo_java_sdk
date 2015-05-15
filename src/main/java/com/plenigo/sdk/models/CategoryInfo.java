package com.plenigo.sdk.models;


/**
 * <p>
 * This object represents the category information retrieved from a paged list.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class CategoryInfo {
    private String categoryId;
    private String title;

    /**
     * Required constructor.
     *
     * @param categoryId The id of the category
     * @param title      the title of the category
     */
    public CategoryInfo(String categoryId, String title) {
        this.categoryId = categoryId;
        this.title = title;
    }


    /**
     * Retrieves the category id.
     *
     * @return The category id
     */
    public String getCategoryId() {
        return categoryId;
    }


    /**
     * Retrieves the title id.
     *
     * @return The title id
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "CategoryInfo{" + "categoryId='" + categoryId + '\'' + ", title='" + title + '\'' + '}';
    }
}
