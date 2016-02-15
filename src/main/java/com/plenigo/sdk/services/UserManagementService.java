package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.SdkUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * This contains the services related touser management handling with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class UserManagementService {


    /**
     * Default constructor.
     */
    private UserManagementService() {

    }

    /**
     * Registers a new user bound to the company that registers the user. This functionality is only available for companies with closed user groups.
     *
     * @param email    email address of the user to register
     * @param language Language of the user as two digit ISO code(e.g. en), if left null, en(english) will be used.
     *
     * @return customer id
     *
     * @throws PlenigoException if any error occurs
     */
    public static String registerUser(String email, String language) throws PlenigoException {
        Map<String, String> body = new LinkedHashMap<String, String>();
        body.put(ApiParams.EMAIL, email);
        if (language == null) {
            language = "en";
        }
        body.put(ApiParams.LANGUAGE, language);
        Map<String, Object> response = HttpConfig.get().getClient().post(PlenigoManager.get().getUrl(), ApiURLs.REGISTER_EXTERNAL_USER_URL,
                ApiURLs.REGISTER_EXTERNAL_USER_URL, null, body, JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(),
                        PlenigoManager.get().getSecret()));
        return SdkUtils.getValueIfNotNull(response, ApiResults.CUST_ID);
    }

    /**
     * Change email address of an existing user. This functionality is only available for companies with closed user groups.
     *
     * @param customerId customer id of the user to change email address for
     * @param email      new email address of user
     *
     * @return TRUE if the email address has changed
     *
     * @throws PlenigoException if any error occurs
     */
    public static boolean changeEmail(String customerId, String email) throws PlenigoException {
        Map<String, String> body = new LinkedHashMap<String, String>();
        body.put(ApiParams.EMAIL, email);
        HttpConfig.get().getClient().put(PlenigoManager.get().getUrl(), ApiURLs.EXTERNAL_USER_EMAIL_CHANGE_URL,
                String.format(ApiURLs.EXTERNAL_USER_EMAIL_CHANGE_URL, customerId), null, body,
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return true;
    }

    /**
     * Create a login token for an existing user. This functionality is only available for companies with closed user groups.
     *
     * @param customerId the customer id
     *
     * @return login token
     *
     * @throws PlenigoException if any error occurs
     */
    public static String createLoginToken(String customerId) throws PlenigoException {
        Map<String, Object> response = HttpConfig.get().getClient().post(PlenigoManager.get().getUrl(), ApiURLs.EXTERNAL_USER_CREATE_LOGIN_TOKEN_URL,
                String.format(ApiURLs.EXTERNAL_USER_CREATE_LOGIN_TOKEN_URL, customerId), null, null,
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return SdkUtils.getValueIfNotNull(response, ApiResults.LOGIN_TOKEN);
    }
}
