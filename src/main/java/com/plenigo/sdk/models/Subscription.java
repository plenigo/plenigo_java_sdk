package com.plenigo.sdk.models;

import java.io.Serializable;

/**
 * <p>
 * This class contains general attributes regarding a product's subscription.
 * A product with a subscription is something that is regularly paid for.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class Subscription implements Serializable {
    private boolean subscribable;
    private int term;
    private int cancellationPeriod;
    private boolean autoRenewed;

    /**
     * Subscription constructor.
     *
     * @param subscribable       Flag indicating if a product represents a subscription
     * @param term               Subscription terms
     * @param cancellationPeriod cancellation period
     * @param autoRenewed        Is the subscription autorenewed
     */
    public Subscription(boolean subscribable, int term, int cancellationPeriod, boolean autoRenewed) {
        this.subscribable = subscribable;
        this.term = term;
        this.cancellationPeriod = cancellationPeriod;
        this.autoRenewed = autoRenewed;
    }

    /**
     * Flag indicating if the product represents a subscription.
     *
     * @return A boolean indicating if the product represents a subscription
     */
    public boolean isSubscribable() {
        return subscribable;
    }

    /**
     * The subscription term.
     *
     * @return The subscription term
     */
    public int getTerm() {
        return term;
    }

    /**
     * The cancellation period.
     *
     * @return The cancellation period
     */
    public int getCancellationPeriod() {
        return cancellationPeriod;
    }

    /**
     * Flag indicating if the product subscription is auto renewed.
     *
     * @return boolean indicating if the product subscription is auto renewed
     */
    public boolean isAutoRenewed() {
        return autoRenewed;
    }

    @Override
    public String toString() {
        return "Subscription{" + "subscribable=" + subscribable + ", term='" + term + '\'' + ", cancellationPeriod='" + cancellationPeriod + '\''
                + ", autoRenewed=" + autoRenewed + '}';
    }
}
