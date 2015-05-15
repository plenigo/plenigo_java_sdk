package com.plenigo.sdk.models;

import java.io.Serializable;

/**
 * <p>
 * This class contains general attributes regarding a product's action period.
 * An action period is a time where a customer can pay a reduced price for the product.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class ActionPeriod implements Serializable {
    private String name;
    private int term;
    private double price;

    /**
     * Action Period constructor.
     *
     * @param name  The name of the action period
     * @param term  The term of the action period
     * @param price The price of the action period
     */
    public ActionPeriod(String name, int term, double price) {
        this.name = name;
        this.term = term;
        this.price = price;
    }

    /**
     * The name of the action period.
     *
     * @return The name of the action period
     */
    public String getName() {
        return name;
    }

    /**
     * The term of the action period.
     *
     * @return The term of the action period
     */
    public int getTerm() {
        return term;
    }

    /**
     * The price of the action period.
     *
     * @return The price of the action period
     */
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ActionPeriod{" + "name='" + name + '\'' + ", term='" + term + '\'' + ", price=" + price + '}';
    }
}
