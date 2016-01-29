package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.models.AccessTokenRequest;
import com.plenigo.sdk.models.RefreshTokenRequest;
import com.plenigo.sdk.models.TokenData;
import com.plenigo.sdk.models.TokenGrantType;
import com.plenigo.sdk.models.TokenType;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This contains the services required for Single Sign-on authentication with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class TokenService {
    private static final Logger LOGGER = Logger.getLogger(TokenService.class.getName());

    /**
     * Rest client used internally.
     */
    private static RestClient client = new RestClient();

    /**
     * Default constuctor.
     */
    private TokenService() {
    }

    /**
     * Requests an access token with the given info, this method is usually called
     * after the access code has been given. With this token you can request for user
     * information.
     *
     * @param request The request object with all the parameters necessary for an access token
     *
     * @return The token data that is retrieved from the response
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static TokenData getAccessToken(AccessTokenRequest request) throws PlenigoException {
        LOGGER.log(Level.FINEST, "Requesting access token with the following data: {0}", request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.TOKEN_GRANT_TYPE, TokenGrantType.AUTHORIZATION_CODE.getName());
        params.put(ApiParams.OAUTH_ACCESS_CODE, request.getCode());
        params.put(ApiParams.REDIRECT_URI, request.getRedirectUri());
        params.put(ApiParams.CLIENT_ID, PlenigoManager.get().getCompanyId());
        SdkUtils.addIfNotNull(params, ApiParams.STATE, request.getCsrfToken());
        Map<String, Object> result = client.post(PlenigoManager.get().getOauthUrl(), ApiURLs.GET_ACCESS_TOKEN, ApiURLs.GET_ACCESS_TOKEN,
                SdkUtils.buildUrlQueryString(params), null, JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return validateAndBuildResponse(request.getCsrfToken(), result);
    }

    /**
     * Requests another access token with the provided refresh token, this is usually used
     * when the access token is expired, and with it you can request for user information.
     *
     * @param request The request information necessary for a refreshed access token
     *
     * @return The Token data that is retrieved from the response
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public static TokenData getNewAccessToken(RefreshTokenRequest request) throws PlenigoException {
        LOGGER.log(Level.FINEST, "Refreshing access token with the following data: {0}", request);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.TOKEN_GRANT_TYPE, TokenGrantType.REFRESH_TOKEN.getName());
        params.put(ApiParams.REFRESH_TOKEN, request.getRefreshToken());
        params.put(ApiParams.CLIENT_ID, PlenigoManager.get().getCompanyId());
        SdkUtils.addIfNotNull(params, ApiParams.STATE, request.getCsrfToken());
        Map<String, Object> result = client.post(PlenigoManager.get().getOauthUrl(), ApiURLs.REFRESH_ACCESS_TOKEN, ApiURLs.REFRESH_ACCESS_TOKEN
                , SdkUtils.buildUrlQueryString(params) , null, JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId()
                , PlenigoManager.get().getSecret()));

        result.put(ApiResults.REFRESH_TOKEN, request.getRefreshToken());
        return validateAndBuildResponse(request.getCsrfToken(), result);
    }

    /**
     * This method validates a given token response and calls the method
     * that builds the {@link TokenData} object.
     *
     * @param state  The CSRF Token
     * @param result The resulting map of parameters
     *
     * @return The TokenData extracted from the map of parameters
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    private static TokenData validateAndBuildResponse(String state, Map<String, Object> result) throws PlenigoException {
        Object responseState = result.get(ApiResults.STATE);
        if (result.containsKey(ApiResults.ERROR)) {
            throw new PlenigoException(result.get(ApiResults.ERROR).toString(), result.get(ApiResults.ERROR_DESCRIPTION).toString());
        } else if (state != null && (responseState == null || !state.equals(responseState))) {
            LOGGER.log(Level.FINEST, "State used for the request and the response were different!: expected state: {0}, result state: {1}"
                    , new Object[]{state, responseState});
            throw new IllegalArgumentException("The request and response CSRF Token are different! request=" + state + " ; response=" + result
                    .get(ApiResults.STATE));
        } else {
            return buildTokenData(result);
        }
    }

    /**
     * Builds the {@link TokenData} from the mapped JSON request.
     *
     * @param result The {@link TokenData} object with the info from the result map
     *
     * @return A {@link TokenData} object
     */
    private static TokenData buildTokenData(Map<String, Object> result) {
        String accessToken = result.get(ApiResults.ACCESS_TOKEN).toString();
        Long expiresIn = (Long) result.get(ApiResults.EXPIRES_IN);
        String refreshToken = null;
        if (result.containsKey(ApiResults.REFRESH_TOKEN)) {
            refreshToken = result.get(ApiResults.REFRESH_TOKEN).toString();
        }
        String state = null;
        if (result.get(ApiResults.STATE) != null) {
            state = result.get(ApiResults.STATE).toString();
        }
        TokenData tokenData = new TokenData(accessToken, expiresIn, refreshToken, state, TokenType.BEARER);
        LOGGER.log(Level.FINEST, "Token data to return {0}", tokenData);
        return tokenData;
    }

    /**
     * This method generates the cross-site request forgery (CSRF) token.
     *
     * @return a strong cross-site request forgery token
     */
    public static String createCsrfToken() {
        return EncryptionUtils.get().createCsrfToken();
    }
}
