package com.plenigo.sdk.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link CompanyUser}.
 * </p>
 */
public class TransactionTest {
    public static final String TRANSACTION_ID = "id";
    public static final String CUSTOMER_ID = "custId";
    public static final String PRODUCT_ID = "p";
    public static final String TITLE = "t";
    public static final double PRICE = 1.00;
    public static final double TAXES_PERCENTAGE = 2.00;
    public static final double TAXES_AMOUNT = 3.00;
    public static final String TAXES_COUNTRY = "DE";
    public static final String CURRENCY = "USD";
    public static final PaymentMethod PAYMENT_METHOD = PaymentMethod.BANK_ACCOUNT;
    public static final Date TRANSACTION_DATE = new Date();
    public static final TransactionStatus TRANSACTION_STATUS = TransactionStatus.BOOKED;
    public static final long BILLING_ID = 123L;
    public static final String CANCELLATION_TRANSACTION_ID = "1235D";
    public static final String CANCELLED_TRANSACTION_ID = "12354A";
    Transaction transaction;

    @Before
    public void setup() {
        transaction = new Transaction(TRANSACTION_ID, CUSTOMER_ID, PRODUCT_ID, TITLE, PRICE, TAXES_PERCENTAGE, TAXES_AMOUNT, TAXES_COUNTRY,
                CURRENCY, PAYMENT_METHOD, TRANSACTION_DATE, TRANSACTION_STATUS, BILLING_ID, CANCELLATION_TRANSACTION_ID, CANCELLED_TRANSACTION_ID);
    }

    @Test
    public void testGetTransactionId() {
        assertEquals("Transaction id is not equals", TRANSACTION_ID, transaction.getTransactionId());
    }

    @Test
    public void testGetCustomerId() {
        assertEquals("Customer id is not equals", CUSTOMER_ID, transaction.getCustomerId());
    }

    @Test
    public void testGetProductId() {
        assertEquals("Product id is not equals", PRODUCT_ID, transaction.getProductId());
    }

    @Test
    public void testGetTitle() {
        assertEquals("Title is not equals", TITLE, transaction.getTitle());
    }

    @Test
    public void testGetPrice() {
        assertEquals("Price is not equals", PRICE, transaction.getPrice(), 0.0);
    }


    @Test
    public void testGetTaxesPercentage() {
        assertEquals("Taxes percentage is not equals", TAXES_PERCENTAGE, transaction.getTaxesPercentage(), 0.0);
    }

    @Test
    public void testGetTaxesAmount() {
        assertEquals("Taxes amount is not equals", TAXES_AMOUNT, transaction.getTaxesAmount(), 0.0);
    }

    @Test
    public void testGetTaxesCountry() {
        assertEquals("Taxes country is not equals", TAXES_COUNTRY, transaction.getTaxesCountry());
    }

    @Test
    public void testGetCurrency() {
        assertEquals("Currency is not equals", CURRENCY, transaction.getCurrency());
    }

    @Test
    public void testGetPaymentMethod() {
        assertEquals("Payment method is not equals", PAYMENT_METHOD, transaction.getPaymentMethod());
    }

    @Test
    public void testGetTransactionDate() {
        assertEquals("Transaction date is not equals", TRANSACTION_DATE, transaction.getTransactionDate());
    }

    @Test
    public void testGetTransactionStatus() {
        assertEquals("Transaction status is not equals", TRANSACTION_STATUS, transaction.getStatus());
    }

    @Test
    public void testGetBillingid() {
        assertEquals("Billing id is not equal", BILLING_ID, transaction.getBillingId());
    }

    @Test
    public void testGetCancellationTransactionId() {
        assertEquals("Cancellation transaction id is not equal", CANCELLATION_TRANSACTION_ID, transaction.getCancellationTransactionId());
    }

    @Test
    public void testGetCancelledTransactionId() {
        assertEquals("Cancelled transaction id is not equal", CANCELLED_TRANSACTION_ID, transaction.getCancelledTransactionId());
    }
}
