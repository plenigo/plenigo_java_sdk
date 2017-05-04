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
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.internal.util.SdkUtils;
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
public final class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    /**
     * Cookie expiration time lapse in milliseconds.
     */
    private static final long TS_EXP_TIME_LAPSE_IN_MILLIS = 60/*secs*/ * 60/*mins*/ * 24/*hours*/ * 1000/*millis*/;

    /**
     * Rest client used internally.
     */
    private static RestClient client = new RestClient();

    private static InternalUserApiService internalUserApiService = new InternalUserApiService();

    private static final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";


    /**
     * Default constructor.
     */
    private UserService() {

    }

    /**
     * This method retrieves user data with the provided access token.
     *
     * @param accessToken The provided access token.
     *
     * @return the user data related to the access token
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static UserData getUserData(String accessToken) throws PlenigoException {
        return internalUserApiService.getUserData(PlenigoManager.get().getUrl(), PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret(),
                accessToken);
    }

    /**
     * Checks if the user can access a product. If there is an error response from the API this will throw a {@link PlenigoException},
     * in the case of BAD_REQUEST types, the exception will contain a list of {@link com.plenigo.sdk.models.ErrorDetail}.
     *
     * @param productId    The id of the product to be queried against the user
     * @param cookieHeader The cookie header of the user
     *
     * @return True if the user in the cookie has bought the product and the session is not expired, false otherwise
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static boolean hasUserBought(String productId, String cookieHeader) throws PlenigoException {
        return hasUserBought(Collections.singletonList(productId), cookieHeader);
    }

    /**
     * Checks if the user can access a product. If there is an error response from the API this will throw a {@link PlenigoException},
     * in the case of BAD_REQUEST types, the exception will contain a list of {@link com.plenigo.sdk.models.ErrorDetail}.
     *
     * @param productId          The id of the product to be queried against the user
     * @param customerId         The customer id
     * @param withExternalUserId Flag indicating if the customer id parameter is an internal plenigo id or an external customer id
     *
     * @return True if the external customer has bought the product, otherwise false
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static boolean hasUserBoughtByCustomerId(String productId, String customerId, boolean withExternalUserId) throws PlenigoException {
        return hasUserBoughtByCustomerId(Collections.singletonList(productId), customerId, withExternalUserId);
    }

    /**
     * Checks if the user can access a product. If there is an error response from the API this will throw a {@link PlenigoException},
     * in the case of BAD_REQUEST types, the exception will contain a list of {@link com.plenigo.sdk.models.ErrorDetail}.
     *
     * @param productIds   The ids of the products to be queried against the user
     * @param cookieHeader The cookie header of the user
     *
     * @return True if the user in the cookie has bought at least one of the product ids , otherwise false
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static boolean hasUserBought(List<String> productIds, String cookieHeader) throws PlenigoException {
        LOGGER.log(Level.FINEST, "Checking if an user has bought a product with the ids: {0} and the cookie header: {1}",
                new Object[]{productIds, cookieHeader});
        Customer customer = getCustomerInfo(cookieHeader);
        if (hasExpired(customer)) {
            return false;
        }
        return internalUserApiService.hasUserBought(PlenigoManager.get().getUrl(), customer.getCustomerId(), PlenigoManager.get().getSecret(),
                PlenigoManager.get().getCompanyId(), PlenigoManager.get().isTestMode(), productIds, false);
    }

    /**
     * Checks if a customer can access a product. If there is an error response from the API this will throw a {@link PlenigoException},
     * in the case of BAD_REQUEST types, the exception will contain a list of {@link com.plenigo.sdk.models.ErrorDetail}.
     *
     * @param productIds         The ids of the products to be queried against the user.
     * @param customerId         The customer id of the user.
     * @param withExternalUserId Flag indicating if the customer id parameter is an internal plenigo id or an external customer id
     *
     * @return True if the user has bought at least one of the product id, otherwise false.
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static boolean hasUserBoughtByCustomerId(List<String> productIds, String customerId, boolean withExternalUserId) throws PlenigoException {
        LOGGER.log(Level.FINEST, "Checking if an user has bought a product with the ids: {0} and the customer id: {1}",
                new Object[]{productIds, customerId});
        return internalUserApiService.hasUserBought(PlenigoManager.get().getUrl(), customerId, PlenigoManager.get().getSecret(),
                PlenigoManager.get().getCompanyId(), PlenigoManager.get().isTestMode(), productIds, withExternalUserId);
    }

    /**
     * Retrieves the user info from the cookie.
     *
     * @param userCookie The user information cookie.
     *
     * @return The Customer information from the cookie.
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    private static Customer getCustomerInfo(HttpCookie userCookie) throws PlenigoException {
        if (userCookie.getValue() == null) {
            return null;
        }
        String data = EncryptionUtils.get().decryptWithAES(PlenigoManager.get().getSecret(), userCookie.getValue());
        Map<String, String> userData = SdkUtils.getMapFromString(data);
        String timestamp = userData.get(ApiResults.TIMESTAMP);
        String customerId = userData.get(ApiResults.CUSTOMER_ID);
        if (timestamp == null || timestamp.isEmpty()
                || customerId == null || customerId.isEmpty()) {
            LOGGER.log(Level.FINEST, "Timestamp: {0} or customerId: {1} were null or empty", new Object[]{timestamp, customerId});
            return null;
        }
        long timestampInMillis = Long.parseLong(timestamp);
        return new Customer(customerId, timestampInMillis);
    }

    /**
     * Retrieves the user info from the cookie.
     *
     * @param cookieHeader The raw cookie header
     *
     * @return The Customer Information from the cookie
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static Customer getCustomerInfo(String cookieHeader) throws PlenigoException {
        HttpCookie customerCookie = CookieParser.getCustomerCookie(cookieHeader);
        return getCustomerInfo(customerCookie);
    }

    /**
     * Checks if the customer timestamp has expired.
     *
     * @param customer The customer entity to be used in order to examine if the cookie has expired.
     *
     * @return A boolean if the cookie has not expired.
     */
    private static boolean hasExpired(Customer customer) {
        if (customer == null) {
            return true;
        }
        long timeLapse = System.currentTimeMillis() - customer.getTimestamp();
        boolean hasExpired = timeLapse > TS_EXP_TIME_LAPSE_IN_MILLIS;
        LOGGER.log(Level.FINEST, "Has the customer {0} expired: {1}", new Object[]{customer, hasExpired});
        return hasExpired;
    }

    /**
     * Queries the paywall service to check if its enabled, if disabled all product paywall should be disabled.
     *
     * @return a boolean, true if its enabled an false otherwise
     *
     * @throws PlenigoException if any error happens
     */
    public static boolean isPaywallEnabled() throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> objectMap = client.get(PlenigoManager.get().getUrl(), ApiURLs.PAYWALL_STATE, ApiURLs.PAYWALL_STATE,
                SdkUtils.buildUrlQueryString(params), JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        Object paywallState = objectMap.get(ApiResults.PAYWALL_STATE);
        boolean isEnabled = false;
        if (paywallState != null) {
            isEnabled = Boolean.valueOf(paywallState.toString());
        }
        return isEnabled;
    }

    /**
     * Returns a flag indicating if the user is logged in or not.
     *
     * @param cookieHeader the cookie information
     *
     * @return an indicator saying if the user is logged in or not
     *
     * @throws PlenigoException if any parsing error occurs
     */
    public static boolean isLoggedIn(String cookieHeader) throws PlenigoException {
        Customer customer = getCustomerInfo(cookieHeader);
        if (customer == null) {
            return false;
        }
        return true;
    }

    /**
     * Returns the products the user has bought with the configured company.
     *
     * @param cookieHeader the cookie information
     *
     * @return the amount of products the user has bought
     *
     * @throws PlenigoException if any error occurs
     */
    public ProductsBought getProductsBought(String cookieHeader) throws PlenigoException {
        List<SinglePaymentProduct> singlePaymentProducts = new LinkedList<SinglePaymentProduct>();
        List<SubscriptionProduct> subscriptionProducts = new LinkedList<SubscriptionProduct>();

        Customer customer = getCustomerInfo(cookieHeader);
        if (customer == null) {
            return new ProductsBought(subscriptionProducts, singlePaymentProducts);
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode());
        Map<String, Object> stringObjectMap = client.get(PlenigoManager.get().getUrl(), ApiURLs.USER_PRODUCTS, String.format(ApiURLs.USER_PRODUCTS,
                customer.getCustomerId()), SdkUtils.buildUrlQueryString(params), JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(),
                PlenigoManager.get().getSecret()));
        fillProductsBoughtObject(stringObjectMap, singlePaymentProducts, subscriptionProducts);
        return new ProductsBought(subscriptionProducts, singlePaymentProducts);
    }

    /**
     * Fills the products bought object with the given object map.
     *
     * @param stringObjectMap           the object map
     * @param subscriptionProductsList  the subscrption products list
     * @param singlePaymentProductsList the payment products list
     */
    private void fillProductsBoughtObject(Map<String, Object> stringObjectMap,
                                          List<SinglePaymentProduct> singlePaymentProductsList,
                                          List<SubscriptionProduct> subscriptionProductsList) {
        Object subscriptionsObj = stringObjectMap.get(ApiResults.SUBSCRIPTIONS_LIST);
        Object singlePaymentProductObj = stringObjectMap.get(ApiResults.SINGLE_PAYMENT_PRODUCT_LIST);
        DateFormat dateFormat = new SimpleDateFormat(EXPECTED_DATE_FORMAT);
        if (subscriptionsObj != null && subscriptionsObj instanceof List) {
            List<Map<String, String>> subscriptions = (List<Map<String, String>>) subscriptionsObj;
            for (Map<String, String> subscription : subscriptions) {
                String productId = subscription.get(ApiResults.PROD_ID);
                String title = subscription.get(ApiResults.TITLE);
                Date buyDate = parseDate(dateFormat, subscription.get(ApiResults.BUY_DATE));
                Date endDate = parseDate(dateFormat, subscription.get(ApiResults.END_DATE));
                subscriptionProductsList.add(new SubscriptionProduct(productId, title, buyDate, endDate));
            }
        }

        if (singlePaymentProductObj != null && singlePaymentProductObj instanceof List) {
            List<Map<String, String>> singlePaymentProducts = (List<Map<String, String>>) singlePaymentProductObj;
            for (Map<String, String> subscription : singlePaymentProducts) {
                String productId = subscription.get(ApiResults.PROD_ID);
                String title = subscription.get(ApiResults.TITLE);
                Date buyDate = parseDate(dateFormat, subscription.get(ApiResults.BUY_DATE));
                singlePaymentProductsList.add(new SinglePaymentProduct(productId, title, buyDate));
            }
        }
    }

    /**
     * Parses the given date with the date format, if it can't be parsed it returns null.
     *
     * @param dateFormat the date format object
     * @param dateStr    the date to format
     *
     * @return the date
     */
    private Date parseDate(DateFormat dateFormat, String dateStr) {
        Date buyDate = null;
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                buyDate = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                LOGGER.warning("The buy date could not be parsed, given string= " + dateStr);
            }
        }
        return buyDate;
    }
}