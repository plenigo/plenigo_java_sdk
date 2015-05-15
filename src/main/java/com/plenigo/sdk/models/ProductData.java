package com.plenigo.sdk.models;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * This class contains product information from a plenigo defined product. A product can be any
 * digital content.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class ProductData implements Serializable {
    private String id;
    private Subscription subscription;
    private String title;
    private String description;
    private PricingData pricingData;
    private ActionPeriod actionPeriod;
    private List<Image> images;

    private boolean collectible;

    /**
     * Product Data constructor, must be filled with the required data.
     *
     * @param id           The product id
     * @param subscription The product subscription
     * @param title        The title
     * @param description  The description
     * @param collectible  Flag indicating if product is part of the collectible model
     * @param pricingData  The pricing information
     * @param actionPeriod The action period information
     * @param images       The images information related to the product
     */
    public ProductData(String id, Subscription subscription, String title, String description, Boolean collectible, PricingData pricingData,
                       ActionPeriod actionPeriod, List<Image> images) {
        this.id = id;
        if (subscription == null) {
            subscription = new Subscription(false, 0, 0, false);
        }
        this.subscription = subscription;
        this.title = title;
        this.description = description;
        this.collectible = collectible;
        if (pricingData == null) {
            pricingData = new PricingData(false, 0.0, 0.0, "");
        }
        this.pricingData = pricingData;
        if (actionPeriod == null) {
            actionPeriod = new ActionPeriod("", 0, 0.0);
        }
        this.actionPeriod = actionPeriod;
        this.images = images;
    }

    /**
     * Id of the product.
     *
     * @return The id of the product
     */
    public String getId() {
        return id;
    }

    /**
     * Flag indicating if product represents a subscription.
     *
     * @return a boolean indicating if the product represents a subscription
     */
    public boolean isSubscribable() {
        return subscription.isSubscribable();
    }

    /**
     * Title of the product.
     *
     * @return The title of the product
     */
    public String getTitle() {
        return title;
    }

    /**
     * Description of the product.
     *
     * @return The description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Flag indicating if the product price can be freely selected by the user.
     *
     * @return A boolean indicating if the user can select the price or not
     */
    public boolean isPriceChosen() {
        return pricingData.isChoosePrice();
    }

    /**
     * The price of the product.
     *
     * @return the price of the product
     */
    public double getPrice() {
        return pricingData.getAmount();
    }

    /**
     * Tax in percent as simple number, e.g. 19 .
     *
     * @return the tax percent
     */
    public double getTaxes() {
        return pricingData.getTaxes();
    }

    /**
     * Currency as ISO 4217 code, e.g. EUR .
     *
     * @return the currency iso code
     */
    public String getCurrency() {
        return pricingData.getCurrency();
    }

    /**
     * Subscription term.
     *
     * @return the subscription term
     */
    public int getSubscriptionTerm() {
        return subscription.getTerm();
    }

    /**
     * Cancellation period for the subscription.
     *
     * @return the cancellation period for the subscription
     */
    public int getCancellationPeriod() {
        return subscription.getCancellationPeriod();
    }

    /**
     * Flag indicating if the subscription is auto renewed.
     *
     * @return a boolean indicating if the subscription is auto-renewed
     */
    public boolean isAutoRenewed() {
        return subscription.isAutoRenewed();
    }

    /**
     * Name of the action period if one is defined.
     *
     * @return The name of the action period
     */
    public String getActionPeriodName() {
        return actionPeriod.getName();
    }

    /**
     * Term of the action period if one is defined.
     *
     * @return The term of the action period
     */
    public int getActionPeriodTerm() {
        return actionPeriod.getTerm();
    }

    /**
     * Price of the action period if one is defined.
     *
     * @return The action period if one is defined
     */
    public Double getActionPeriodPrice() {
        return actionPeriod.getPrice();
    }

    /**
     * An array of images that refer to information images of the product.
     *
     * @return The images array
     */
    public List<Image> getImages() {
        return images;
    }


    /**
     * Flag indicating if product is part of the collectible model.
     *
     * @return a boolean indicating if the product is collectible
     */
    public boolean isCollectible() {
        return collectible;
    }

    @Override
    public String toString() {
        return "ProductData{" + "id='" + id + '\'' + ", subscription=" + subscription + ", title='" + title + '\'' + ", description='" + description + '\''
                + ", collectible='" + collectible + '\'' + ", pricingData=" + pricingData + ", actionPeriod=" + actionPeriod + ", images=" + images + '}';
    }
}
