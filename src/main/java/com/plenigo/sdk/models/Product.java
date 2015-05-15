package com.plenigo.sdk.models;

import java.io.Serializable;

/**
 * <p>
 * This class represents a product in the plenigo platform. A product can be any
 * digital content.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is <b>not</b> thread safe.
 * </p>
 */
public class Product implements Serializable {
    /**
     * The price of the product.
     */
    private Double price;

    /**
     * The product title.
     */
    private String title;

    /**
     * The product id.
     */
    private String id;

    /**
     * The currency of the price.
     */
    private String currency;


    /**
     * Flag indicating if it is a pay what you want payment process.
     */
    private Boolean customPrice;

    /**
     * Category id of the product.
     */
    private String categoryId;

    /**
     * Type of tax.
     */
    private TaxType taxType;

    /**
     * Subscription renewal.
     */
    private Boolean subscriptionRenewal;

    /**
     * This constructor receives price, title, id and currency as parameters, it
     * is recommended for instantiating products that are not managed by
     * plenigo.
     *
     * @param prodPrice the product price
     * @param prodTitle the product title
     * @param prodId    product id
     * @param curr      the currency
     * @param taxType   tax type
     */
    public Product(final Double prodPrice, final String prodTitle,
                   final String prodId, final String curr, final TaxType taxType) {
        super();
        this.price = prodPrice;
        this.title = prodTitle;
        this.id = prodId;
        this.currency = curr;
        this.taxType = taxType;
    }

    /**
     * This constructor receives only an id parameter, it is recommended to use
     * this one for plenigo managed products.
     *
     * @param prodId     product id
     * @param title      product title
     * @param categoryId product category
     */
    public Product(final String prodId, final String title, final String categoryId) {
        super();
        this.id = prodId;
        this.title = title;
        this.categoryId = categoryId;
    }


    /**
     * This constructor receives an id parameter,  it is recommended to use
     * this one for plenigo managed products.
     *
     * @param prodId product id
     */
    public Product(final String prodId) {
        super();
        this.id = prodId;
    }

    /**
     * Returns the currency of the product's price.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Returns the product id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the price of the product.
     *
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Returns the product's title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the currency of the product's price.
     *
     * @param curr the currency to set
     */
    public void setCurrency(final String curr) {
        this.currency = curr;
    }

    /**
     * Sets the product id.
     *
     * @param prodId the id to set
     */
    public void setId(final String prodId) {
        this.id = prodId;
    }

    /**
     * Sets the product price.
     *
     * @param prodPrice the price to set
     */
    public void setPrice(final Double prodPrice) {
        this.price = prodPrice;
    }

    /**
     * Sets the title of the product.
     *
     * @param newTitle the title to set
     */
    public void setTitle(final String newTitle) {
        this.title = newTitle;
    }

    /**
     * Returns a flag indicating if the user is setting a custom amount.
     *
     * @return true if the price to set is a custom amount
     */
    public Boolean isCustomPrice() {
        return customPrice;
    }

    /**
     * Sets the flag to indicate that the price is custom.
     *
     * @param condition the condition to set
     */
    public void setCustomPrice(final Boolean condition) {
        this.customPrice = condition;
    }

    /**
     * Returns the category id of the product.
     *
     * @return the category id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category id.
     *
     * @param categoryId The id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }


    /**
     * Returns the tax type.
     *
     * @return the tax type
     */
    public TaxType getTaxType() {
        return taxType;
    }

    /**
     * Returns the flag indicating if the subscription is going to be renewed.
     *
     * @return flag indicating if the subscription is going to be renewed
     */
    public Boolean getSubscriptionRenewal() {
        return subscriptionRenewal;
    }

    /**
     * Sets the flag indicating if the subscription is going to be renewed.
     *
     * @param subscriptionRenewal flag indicating if the subscription is going to be renewed
     */
    public void setSubscriptionRenewal(Boolean subscriptionRenewal) {
        this.subscriptionRenewal = subscriptionRenewal;
    }

    @Override
    public String toString() {
        return "Product{" + "price=" + price + ", title='" + title + '\'' + ", id='" + id + '\'' + ", currency='" + currency + '\'' + ", customPrice="
                + customPrice + ", categoryId='" + categoryId + '\'' + ", taxType=" + taxType + ", subscriptionRenewal=" + subscriptionRenewal + '}';
    }

}
