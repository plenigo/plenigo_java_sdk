package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.ErrorCode;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.models.AppAccessData;
import com.plenigo.sdk.models.AppAccessToken;
import com.plenigo.sdk.models.AppTokenRequest;
import com.plenigo.sdk.models.CustomerAppRequest;
import com.plenigo.sdk.models.DeleteAppIdRequest;
import com.plenigo.sdk.models.ProductAccessRequest;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This contains the services related to app management services with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class AppManagementService {
    /**
     * Default constructor.
     */
    private AppManagementService() {

    }

    /**
     * Requests an application token.
     *
     * @param request the request information
     *
     * @return application access token
     *
     * @throws PlenigoException if any error occured
     */
    public static AppAccessToken requestAppToken(AppTokenRequest request) throws PlenigoException {
        Map<String, String> body = new LinkedHashMap<String, String>();
        body.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode().toString());
        body.put(ApiParams.PRODUCT_ID, request.getProductId());
        body.put(ApiParams.DESCRIPTION, request.getDescription());
        Map<String, Object> response = HttpConfig.get().getClient().post(PlenigoManager.get().getUrl(), ApiURLs.ACCESS_APP_TOKEN,
                String.format(ApiURLs.ACCESS_APP_TOKEN, request.getCustomerId()), null, body, JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(),
                        PlenigoManager.get().getSecret()));
        return buildAppAccessToken(response);
    }

    /**
     * Builds the application access token.
     *
     * @param response the response object
     *
     * @return application access token
     */
    private static AppAccessToken buildAppAccessToken(Map<String, Object> response) {
        String customerId = response.get(ApiResults.CUST_ID).toString();
        String token = response.get(ApiResults.APP_TOKEN).toString();
        return new AppAccessToken(customerId, token);
    }

    /**
     * Returns the customer applications.
     *
     * @param request the request information
     *
     * @return a list of application accesses
     *
     * @throws PlenigoException if any error occurs
     */
    public static List<AppAccessData> getCustomerApps(CustomerAppRequest request) throws PlenigoException {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(ApiParams.CUSTOMER_ID, request.getCustomerId());
        params.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode().toString());
        Map<String, Object> response = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.ACCESS_APP_CUSTOMER,
                String.format(ApiURLs.ACCESS_APP_CUSTOMER, request.getCustomerId()), SdkUtils.buildUrlQueryString(params),
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return buildAppAccessList(response);
    }

    /**
     * Requests a customer application id.
     *
     * @param request the application access token
     *
     * @return the app access data
     *
     * @throws PlenigoException if any error occurs
     */
    public static AppAccessData requestAppId(AppAccessToken request) throws PlenigoException {
        Map<String, String> body = new LinkedHashMap<String, String>();
        body.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode().toString());
        body.put(ApiParams.APP_ACCESS_TOKEN, request.getToken());
        Map<String, Object> response = HttpConfig.get().getClient().post(PlenigoManager.get().getUrl(), ApiURLs.ACCESS_APP_CUSTOMER,
                String.format(ApiURLs.ACCESS_APP_CUSTOMER, request.getCustomerId()), null, body,
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return buildAppIdData(response);
    }

    /**
     * Checks if an user has bought a product.
     *
     * @param request the request information
     *
     * @return if the flag is true, then the user bought the product, false otherwise
     *
     * @throws PlenigoException if any error occurs
     */
    public static boolean hasUserBought(ProductAccessRequest request) throws PlenigoException {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        boolean hasAccess = true;
        params.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode().toString());
        try {
            HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.VERIFY_CUSTOMER_APP_PRODUCT,
                    String.format(ApiURLs.VERIFY_CUSTOMER_APP_PRODUCT, request.getCustomerId(), request.getProductId(),
                            request.getCustomerAppId()), SdkUtils.buildUrlQueryString(params), JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(),
                            PlenigoManager.get().getSecret()));
        } catch (PlenigoException pe) {
            //Forbidden means that the user has not bought the product.
            if (ErrorCode.get(pe.getResponseCode()) == ErrorCode.CANNOT_ACCESS_PRODUCT
                    || pe.getResponseCode().equals(String.valueOf(HttpURLConnection.HTTP_FORBIDDEN))) {
                hasAccess = false;
            } else {
                throw pe;
            }
        }
        return hasAccess;
    }

    /**
     * Deletes the customer application.
     *
     * @param request request information
     *
     * @throws PlenigoException if any error occurs
     */
    public static void deleteCustomerApp(DeleteAppIdRequest request) throws PlenigoException {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(ApiParams.TEST_MODE, PlenigoManager.get().isTestMode().toString());
        try {
            HttpConfig.get().getClient().delete(PlenigoManager.get().getUrl(), ApiURLs.DELETE_CUSTOMER_APP, String.format(ApiURLs.DELETE_CUSTOMER_APP,
                    request.getCustomerId(), request.getCustomerAppId()), SdkUtils.buildUrlQueryString(params),
                    JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        } catch (PlenigoException pe) {
            if (!(pe.getResponseCode().equals(String.valueOf(HttpURLConnection.HTTP_NO_CONTENT))
                    || ErrorCode.get(pe.getResponseCode()) == ErrorCode.APP_ID_DELETED)) {
                throw pe;
            }
        }
    }

    /**
     * Builds the application access data object.
     *
     * @param response the response
     *
     * @return application access data object
     */
    private static AppAccessData buildAppIdData(Map<String, Object> response) {
        String customerId = response.get(ApiResults.CUST_ID).toString();
        String appId = response.get(ApiResults.CUSTOMER_APP_ID).toString();
        String description = response.get(ApiResults.DESCRIPTION).toString();
        String productId = response.get(ApiResults.PROD_ID).toString();
        return new AppAccessData(customerId, description, appId, productId);
    }

    /**
     * Builds a list of application access data objects.
     *
     * @param response the response
     *
     * @return ta list of application access data objects
     */
    private static List<AppAccessData> buildAppAccessList(Map<String, Object> response) {
        Object elementsObj = response.get(ApiResults.APP_LIST);
        List<AppAccessData> appAccessDatas = new LinkedList<AppAccessData>();
        //https://code.google.com/p/json-simple/ Json simple library maps arrays as Lists
        if (elementsObj instanceof List) {
            List elements = (List) elementsObj;
            for (Object elementObj : elements) {
                //and objects as maps
                if (elementObj instanceof Map) {
                    Map<String, Object> element = (Map<String, Object>) elementObj;
                    String customerId = SdkUtils.getValueIfNotNull(element, ApiResults.CUST_ID);
                    String description = SdkUtils.getValueIfNotNull(element, ApiResults.DESCRIPTION);
                    String customerAppId = SdkUtils.getValueIfNotNull(element, ApiResults.CUSTOMER_APP_ID);
                    String prodId = SdkUtils.getValueIfNotNull(element, ApiResults.PROD_ID);
                    appAccessDatas.add(new AppAccessData(customerId, description, customerAppId, prodId));
                }
            }
        }
        return appAccessDatas;
    }

}
