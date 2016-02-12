package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.util.DateUtils;

import java.util.Date;


/**
 * <p>
 * This class is used to represent a company transaction.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class Transaction {
    private String transactionId;
    private String customerId;
    private String productId;
    private String title;
    private double price;
    private double taxesPercentage;
    private double taxesAmount;
    private String taxesCountry;
    private String currency;
    private PaymentMethod paymentMethod;
    private Date transactionDate;
    private TransactionStatus status;
    private double shippingCosts;
    private double shippingCostsTaxesPercentage;
    private double shippingCostsTaxesAmount;
    private long billingId;
    private String cancellationTransactionId;
    private String cancelledTransactionId;

    /**
     * Constructor that builds an object with all the parameters.
     *
     * @param transactionId                transaction id
     * @param customerId                   customer id
     * @param productId                    product id
     * @param title                        product title
     * @param price                        price
     * @param taxesPercentage              taxes percentage
     * @param taxesAmount                  taxes amount
     * @param taxesCountry                 taxes country
     * @param currency                     currency ISO code
     * @param paymentMethod                payment method
     * @param transactionDate              transaction date
     * @param status                       transaction status
     * @param shippingCosts                shipping costs
     * @param shippingCostsTaxesPercentage shipping costs taxes percentage
     * @param shippingCostsTaxesAmount     shipping costs taxes amount
     * @param billingId                    billing id
     * @param cancellationTransactionId    cancellation transaction id
     * @param cancelledTransactionId       cancelled transaction id
     */
    public Transaction(String transactionId, String customerId, String productId, String title, double price, double taxesPercentage, double taxesAmount,
                       String taxesCountry, String currency, PaymentMethod paymentMethod, Date transactionDate, TransactionStatus status, double shippingCosts,
                       double shippingCostsTaxesPercentage, double shippingCostsTaxesAmount, long billingId, String cancellationTransactionId,
                       String cancelledTransactionId) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.taxesPercentage = taxesPercentage;
        this.taxesAmount = taxesAmount;
        this.taxesCountry = taxesCountry;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.transactionDate = DateUtils.copy(transactionDate);
        this.status = status;
        this.shippingCosts = shippingCosts;
        this.shippingCostsTaxesPercentage = shippingCostsTaxesPercentage;
        this.shippingCostsTaxesAmount = shippingCostsTaxesAmount;
        this.billingId = billingId;
        this.cancellationTransactionId = cancellationTransactionId;
        this.cancelledTransactionId = cancelledTransactionId;
    }

    /**
     * Returns the transaction id.
     *
     * @return transaction id
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the customer id.
     *
     * @return customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the product id.
     *
     * @return product id
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Returns the title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the price.
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the taxes percentage.
     *
     * @return taxes percentage
     */
    public double getTaxesPercentage() {
        return taxesPercentage;
    }

    /**
     * Returns the taxes amount.
     *
     * @return taxes amount
     */
    public double getTaxesAmount() {
        return taxesAmount;
    }

    /**
     * Returns the taxes country.
     *
     * @return taxes country
     */
    public String getTaxesCountry() {
        return taxesCountry;
    }

    /**
     * Returns the currency ISO code.
     *
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Returns the payment method.
     *
     * @return payment method
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Returns the transaction date.
     *
     * @return transaction date
     */
    public Date getTransactionDate() {
        return DateUtils.copy(transactionDate);
    }

    /**
     * Returns the transaction status.
     *
     * @return transaction status
     */
    public TransactionStatus getStatus() {
        return status;
    }

    /**
     * Returns the shipping costs.
     *
     * @return shipping costs
     */
    public double getShippingCosts() {
        return shippingCosts;
    }

    /**
     * Returns the shipping costs taxes percentage.
     *
     * @return shipping costs taxes percentage
     */
    public double getShippingCostsTaxesPercentage() {
        return shippingCostsTaxesPercentage;
    }

    /**
     * Returns the shipping costs taxes amount.
     *
     * @return shipping costs taxes amount
     */
    public double getShippingCostsTaxesAmount() {
        return shippingCostsTaxesAmount;
    }

    /**
     * Returns the billing id.
     *
     * @return billing id
     */
    public long getBillingId() {
        return billingId;
    }

    /**
     * Returns the cancellation transaction id.
     *
     * @return cancellation transaction id
     */
    public String getCancellationTransactionId() {
        return cancellationTransactionId;
    }

    /**
     * Returns the cancelled transaction id.
     *
     * @return cancelled transaction id
     */
    public String getCancelledTransactionId() {
        return cancelledTransactionId;
    }
}
