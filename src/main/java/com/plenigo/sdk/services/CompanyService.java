package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.models.Customer;
import com.plenigo.sdk.internal.services.InternalUserApiService;
import com.plenigo.sdk.internal.util.CookieParser;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.models.CompanyUser;
import com.plenigo.sdk.models.ElementList;
import com.plenigo.sdk.models.ProductsBought;
import com.plenigo.sdk.models.SinglePaymentProduct;
import com.plenigo.sdk.models.SubscriptionProduct;
import com.plenigo.sdk.models.UserData;

import java.net.HttpCookie;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p>
 * This contains the services related to user managemet with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class CompanyService {
    private static final Logger LOGGER = Logger.getLogger(CompanyService.class.getName());
    private static final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";


    /**
     * Default constructor.
     */
    private CompanyService() {

    }

    /**
     * This method retrieves user data with the provided access token.
     *
     * @param accessToken The provided access token.
     *
     * @return the user data related to the access token
     *
     * @throws PlenigoException whenever an error happens
     */
    public static ElementList<CompanyUser> getUserList() throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> objectMap = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.COMPANY_USERS, SdkUtils.buildUrlQueryString(params)
                , JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return new ElementList<CompanyUser>(0, 0, null);
    }

//    /**
//     * Queries the paywall service to check if its enabled, if disabled all product paywall should be disabled.
//     *
//     * @return a boolean, true if its enabled an false otherwise
//     *
//     * @throws PlenigoException if any error happens
//     */
//    public static boolean isPaywallEnabled() throws PlenigoException {
//        Map<String, Object> params = new HashMap<String, Object>();
//        Map<String, Object> objectMap = client.get(PlenigoManager.get().getUrl(), ApiURLs.PAYWALL_STATE, SdkUtils.buildUrlQueryString(params)
//                , JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
//        Object paywallState = objectMap.get(ApiResults.PAYWALL_STATE);
//        boolean isEnabled = false;
//        if (paywallState != null) {
//            isEnabled = Boolean.valueOf(paywallState.toString());
//        }
//        return isEnabled;
//    }
//
//    /**
//     * Returns a flag indicating if the user is logged in or not.
//     *
//     * @param cookieHeader the cookie information
//     *
//     * @return an indicator saying if the user is logged in or not
//     *
//     * @throws PlenigoException if any parsing error occurs
//     */
//    public static boolean isLoggedIn(String cookieHeader) throws PlenigoException {
//        Customer customer = getCustomerInfo(cookieHeader);
//        if (customer == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * Returns the products the user has bought with the configured company.
//     *
//     * @param cookieHeader the cookie information
//     *
//     * @return the amount of products the user has bought
//     *
//     * @throws PlenigoException if any error occurs
//     */
//    public ProductsBought getProductsBought(String cookieHeader) throws PlenigoException {
//        List<SinglePaymentProduct> singlePaymentProducts = new LinkedList<SinglePaymentProduct>();
//        List<SubscriptionProduct> subscriptionProducts = new LinkedList<SubscriptionProduct>();
//
//        Customer customer = getCustomerInfo(cookieHeader);
//        if (customer == null) {
//            return new ProductsBought(subscriptionProducts, singlePaymentProducts);
//        }
//
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode());
//        Map<String, Object> stringObjectMap = client.get(PlenigoManager.get().getUrl(), String.format(ApiURLs.USER_PRODUCTS, customer.getCustomerId()),
//                SdkUtils.buildUrlQueryString(params), JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
//        fillProductsBoughtObject(stringObjectMap, singlePaymentProducts, subscriptionProducts);
//        return new ProductsBought(subscriptionProducts, singlePaymentProducts);
//    }
//
//    /**
//     * Fills the products bought object with the given object map.
//     *
//     * @param stringObjectMap           the object map
//     * @param subscriptionProductsList  the subscrption products list
//     * @param singlePaymentProductsList the payment products list
//     */
//    private void fillProductsBoughtObject(Map<String, Object> stringObjectMap,
//                                          List<SinglePaymentProduct> singlePaymentProductsList,
//                                          List<SubscriptionProduct> subscriptionProductsList) {
//        Object subscriptionsObj = stringObjectMap.get(ApiResults.SUBSCRIPTIONS_LIST);
//        Object singlePaymentProductObj = stringObjectMap.get(ApiResults.SINGLE_PAYMENT_PRODUCT_LIST);
//        DateFormat dateFormat = new SimpleDateFormat(EXPECTED_DATE_FORMAT);
//        if (subscriptionsObj != null && subscriptionsObj instanceof List) {
//            List<Map<String, String>> subscriptions = (List<Map<String, String>>) subscriptionsObj;
//            for (Map<String, String> subscription : subscriptions) {
//                String productId = subscription.get(ApiResults.PROD_ID);
//                String title = subscription.get(ApiResults.TITLE);
//                Date buyDate = parseDate(dateFormat, subscription.get(ApiResults.BUY_DATE));
//                Date endDate = parseDate(dateFormat, subscription.get(ApiResults.END_DATE));
//                subscriptionProductsList.add(new SubscriptionProduct(productId, title, buyDate, endDate));
//            }
//        }
//
//        if (singlePaymentProductObj != null && singlePaymentProductObj instanceof List) {
//            List<Map<String, String>> singlePaymentProducts = (List<Map<String, String>>) singlePaymentProductObj;
//            for (Map<String, String> subscription : singlePaymentProducts) {
//                String productId = subscription.get(ApiResults.PROD_ID);
//                String title = subscription.get(ApiResults.TITLE);
//                Date buyDate = parseDate(dateFormat, subscription.get(ApiResults.BUY_DATE));
//                singlePaymentProductsList.add(new SinglePaymentProduct(productId, title, buyDate));
//            }
//        }
//    }
//
//    /**
//     * Parses the given date with the date format, if it can't be parsed it returns null.
//     *
//     * @param dateFormat the date format object
//     * @param dateStr    the date to format
//     *
//     * @return the date
//     */
//    private Date parseDate(DateFormat dateFormat, String dateStr) {
//        Date buyDate = null;
//        if (dateStr != null && !dateStr.isEmpty()) {
//            try {
//                buyDate = dateFormat.parse(dateStr);
//            } catch (ParseException e) {
//                LOGGER.warning("The buy date could not be parsed, given string= " + dateStr);
//            }
//        }
//        return buyDate;
//    }
}
