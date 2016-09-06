package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.internal.util.ValidationUtils;
import com.plenigo.sdk.models.PaymentMethod;
import com.plenigo.sdk.models.Transaction;
import com.plenigo.sdk.models.TransactionList;
import com.plenigo.sdk.models.TransactionSearchRequest;
import com.plenigo.sdk.models.TransactionStatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.plenigo.sdk.internal.util.SdkUtils.buildUrlQueryString;
import static com.plenigo.sdk.internal.util.SdkUtils.getValueIfNotNull;
import static com.plenigo.sdk.internal.util.SdkUtils.isNotBlank;


/**
 * <p>
 * This contains the services related to transactions with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());
    private static final String REQUEST_DATE_FORMAT = "yyyy-MM-dd";
    public static final String RESPONSE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Default constructor.
     */
    private TransactionService() {

    }

    /**
     * Searches transactions with the provided criteria.
     *
     * @param request search criteria
     *
     * @return transaction list
     *
     * @throws PlenigoException if any error happens
     */
    public static TransactionList searchTransactions(TransactionSearchRequest request) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        ValidationUtils.validate(request);
        ValidationUtils.validateDateRange(request);
        DateFormat dateFormat = new SimpleDateFormat(REQUEST_DATE_FORMAT);
        params.put(ApiParams.START_DATE, dateFormat.format(request.getStartDate()));
        params.put(ApiParams.END_DATE, dateFormat.format(request.getEndDate()));
        params.put(ApiParams.PAGE_NUMBER, request.getPageNumber());
        params.put(ApiParams.PAGE_SIZE, request.getPageSize());
        SdkUtils.addIfNotNull(params, ApiParams.TRANSACTION_STATUS, request.getTransactionStatus());
        SdkUtils.addIfNotNull(params, ApiParams.PAYMENT_METHOD, request.getPaymentMethod());
        params.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode());
        Map<String, Object> objectMap = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.TX_SEARCH, ApiURLs.TX_SEARCH,
                buildUrlQueryString(params), JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return buildTransactionList(objectMap, request);
    }

    /**
     * Builds a transaction list from the json representation.
     *
     * @param objectMap json representation
     * @param request   search request
     *
     * @return transaction list
     */
    private static TransactionList buildTransactionList(Map<String, Object> objectMap, TransactionSearchRequest request) {
        String totalElementsStr = SdkUtils.getValueIfNotNull(objectMap, ApiResults.TOTAL_ELEMENTS);
        long totalElements = 0;
        if (!totalElementsStr.isEmpty()) {
            totalElements = Long.parseLong(totalElementsStr);
        }
        String pageSizeStr = SdkUtils.getValueIfNotNull(objectMap, ApiResults.PAGE_SIZE);
        int pageSize = 0;
        if (!pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Object companyUsersObj = objectMap.get(ApiResults.ELEMENTS);
        List<Transaction> transactionList = buildTransactionList(companyUsersObj);
        return new TransactionList(request.getPageNumber(), pageSize, totalElements, transactionList, request.getStartDate(), request.getEndDate());
    }

    /**
     * Builds a transaction list from the json representation.
     *
     * @param companyUsersObj json representation
     *
     * @return transaction list
     */
    private static List<Transaction> buildTransactionList(Object companyUsersObj) {
        List<Transaction> transactionList = new LinkedList<Transaction>();
        if (companyUsersObj != null && companyUsersObj instanceof List) {
            List<Map<String, Object>> companyUsers = (List<Map<String, Object>>) companyUsersObj;
            for (Map<String, Object> companyUser : companyUsers) {
                String transactionId = getValueIfNotNull(companyUser, ApiResults.TRANSACTION_ID);
                String customerId = getValueIfNotNull(companyUser, ApiResults.CUST_ID);
                String productId = getValueIfNotNull(companyUser, ApiResults.PROD_ID);
                String title = getValueIfNotNull(companyUser, ApiResults.TITLE);
                String priceStr = getValueIfNotNull(companyUser, ApiResults.PRICE);
                double price = 0.0;
                if (isNotBlank(priceStr)) {
                    price = Double.parseDouble(priceStr);
                }

                String taxesPercentageStr = getValueIfNotNull(companyUser, ApiResults.TAXES_PERCENTAGE);
                double taxesPercentage = 0.0;
                if (isNotBlank(taxesPercentageStr)) {
                    taxesPercentage = Double.parseDouble(taxesPercentageStr);
                }
                String taxesAmountStr = getValueIfNotNull(companyUser, ApiResults.TAXES_AMOUNT);
                double taxesAmount = 0.0;
                if (isNotBlank(taxesAmountStr)) {
                    taxesAmount = Double.parseDouble(taxesAmountStr);
                }
                String taxesCountry = getValueIfNotNull(companyUser, ApiResults.TAXES_COUNTRY);

                String currency = getValueIfNotNull(companyUser, ApiResults.CURRENCY);
                String paymentMethodStr = getValueIfNotNull(companyUser, ApiResults.PAYMENT_METHOD);
                PaymentMethod paymentMethod = PaymentMethod.get(paymentMethodStr);
                String transactionDateStr = getValueIfNotNull(companyUser, ApiResults.TRANSACTION_DATE);
                Date transactionDate = null;
                if (isNotBlank(transactionDateStr)) {
                    try {
                        transactionDate = new SimpleDateFormat(RESPONSE_DATE_FORMAT).parse(transactionDateStr);
                    } catch (ParseException e) {
                        LOGGER.log(Level.SEVERE, "Could not parse the transaction date string: " + transactionDateStr, e);
                    }
                }
                String transactionStatusStr = getValueIfNotNull(companyUser, ApiResults.TRANSACTION_STATUS);
                TransactionStatus status = TransactionStatus.get(transactionStatusStr);


                String billingIdStr = getValueIfNotNull(companyUser, ApiResults.BILLING_ID);
                long billingId = 0;
                if (isNotBlank(billingIdStr)) {
                    billingId = Long.parseLong(billingIdStr);
                }
                String cancellationTransactionId = getValueIfNotNull(companyUser, ApiResults.CANCELLATION_TRANSACTION_ID);
                String cancelledTransactionId = getValueIfNotNull(companyUser, ApiResults.CANCELLED_TRANSACTION_ID);
                transactionList.add(new Transaction(transactionId, customerId, productId, title, price, taxesPercentage, taxesAmount, taxesCountry, currency,
                        paymentMethod, transactionDate, status, billingId, cancellationTransactionId, cancelledTransactionId));
            }
        }
        return transactionList;
    }
}
