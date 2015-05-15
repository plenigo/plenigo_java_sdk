package com.plenigo.sdk.models;


import java.io.Serializable;

/**
 * <p>
 * This class contains general attributes regarding a product's price.
 * A product's price is conformed by many variables such as currency, taxes and untaxed price.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class PricingData implements Serializable {
    private boolean choosePrice;
    private double amount;
    private double taxes;
    private String currency;

    /**
     * Returns if the price amount can be changed by the user.
     *
     * @return A boolean indicating if the price amount can be changed by the user
     */
    public boolean isChoosePrice() {
        return choosePrice;
    }

    /**
     * The price amount.
     *
     * @return The price amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * The amount of taxes used as a whole number. e.g. 19.
     *
     * @return The taxes
     */
    public double getTaxes() {
        return taxes;
    }

    /**
     * The currency as an ISO Code, e.g, EUR.
     *
     * @return The iso code
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Pricing Data constructor.
     *
     * @param choosePrice The choose price flag
     * @param amount      The price amount
     * @param taxes       The taxes percent
     * @param currency    The currency iso code
     */
    public PricingData(boolean choosePrice, double amount, double taxes, String currency) {
        this.choosePrice = choosePrice;
        this.amount = amount;
        this.taxes = taxes;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Price{" + "choosePrice=" + choosePrice + ", amount=" + amount + ", taxes=" + taxes + ", currency='" + currency + '\'' + '}';
    }
}
