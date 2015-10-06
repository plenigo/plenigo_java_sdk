package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.models.MobileSecretInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * This contains the services related to mobile secret handling with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class MobileService {
    public static final int MIN_MOBILE_SECRET_SIZE = 6;
    public static final int MAX_MOBILE_SECRET_SIZE = 40;

    /**
     * Default constructor.
     */
    private MobileService() {

    }

    /**
     * Returns the customer id given the mobile secret.
     *
     * @param email        the email address
     * @param mobileSecret the mobile secret
     *
     * @return the mobile secret
     *
     * @throws PlenigoException if any error occurs
     */
    public static String verifyMobileSecret(String email, String mobileSecret) throws PlenigoException {
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put(ApiParams.COMPANY_ID, PlenigoManager.get().getCompanyId());
        body.put(ApiParams.SECRET, PlenigoManager.get().getSecret());
        body.put(ApiParams.EMAIL, email);
        body.put(ApiParams.MOBILE_SECRET, mobileSecret);
        Map<String, Object> response = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.MOBILE_SECRET_VERIFY
                , SdkUtils.buildUrlQueryString(body));
        return SdkUtils.getValueIfNotNull(response, ApiResults.CUST_ID);
    }

    /**
     * Returns the mobile secret.
     *
     * @param customerId customer id
     *
     * @return the mobile secret info
     *
     * @throws PlenigoException if any error occurs
     */
    public static MobileSecretInfo getMobileSecret(String customerId) throws PlenigoException {
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put(ApiParams.COMPANY_ID, PlenigoManager.get().getCompanyId());
        body.put(ApiParams.SECRET, PlenigoManager.get().getSecret());
        Map<String, Object> response = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), String.format(ApiURLs.MOBILE_SECRET_URL, customerId)
                , SdkUtils.buildUrlQueryString(body));
        return buildMobileSecretInfo(response);
    }

    /**
     * Creates a mobile secret for a specific customer.
     *
     * @param customerId       customer id
     * @param mobileSecretSize mobile secret size
     *
     * @return the mobile secret info
     *
     * @throws PlenigoException if any error occurs
     */
    public static MobileSecretInfo createMobileSecret(String customerId, int mobileSecretSize) throws PlenigoException {
        if (mobileSecretSize < MIN_MOBILE_SECRET_SIZE) {
            mobileSecretSize = MIN_MOBILE_SECRET_SIZE;
        }
        if (mobileSecretSize > MAX_MOBILE_SECRET_SIZE) {
            mobileSecretSize = MAX_MOBILE_SECRET_SIZE;
        }
        Map<String, String> body = new LinkedHashMap<String, String>();
        body.put(ApiParams.COMPANY_ID, PlenigoManager.get().getCompanyId());
        body.put(ApiParams.SECRET, PlenigoManager.get().getSecret());
        body.put(ApiParams.MOBILE_SECRET_SIZE, String.valueOf(mobileSecretSize));
        Map<String, Object> response = HttpConfig.get().getClient().post(PlenigoManager.get().getUrl(), String.format(ApiURLs.MOBILE_SECRET_URL, customerId)
                , null, body);
        return buildMobileSecretInfo(response);
    }

    /**
     * Builds the mobile secret info object.
     *
     * @param response the json response as a map
     *
     * @return mobile secret info object
     */
    private static MobileSecretInfo buildMobileSecretInfo(Map<String, Object> response) {
        String customerId = SdkUtils.getValueIfNotNull(response, ApiResults.EMAIL);
        String mobileAppSecret = SdkUtils.getValueIfNotNull(response, ApiResults.MOBILE_APP_SECRET);
        return new MobileSecretInfo(customerId, mobileAppSecret);
    }

    /**
     * Deletes a mobile secret.
     *
     * @param customerId customer id
     *
     * @return true if it the request was successful, false otherwise
     *
     * @throws PlenigoException if any error occurs
     */
    public static boolean deleteMobileSecret(String customerId) throws PlenigoException {
        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put(ApiParams.COMPANY_ID, PlenigoManager.get().getCompanyId());
        body.put(ApiParams.SECRET, PlenigoManager.get().getSecret());
        HttpConfig.get().getClient().delete(PlenigoManager.get().getUrl(), String.format(ApiURLs.MOBILE_SECRET_URL, customerId)
                , SdkUtils.buildUrlQueryString(body));
        return true;
    }
}
