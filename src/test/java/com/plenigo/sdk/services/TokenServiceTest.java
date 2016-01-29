package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.AccessTokenRequest;
import com.plenigo.sdk.models.RefreshTokenRequest;
import com.plenigo.sdk.models.TokenData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * Tests for {@link TokenService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptionUtils.class)
public class TokenServiceTest {

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = TokenService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testSuccessfulRefreshAccessToken() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.REFRESH_TOKEN, "123");
        map.put(ApiResults.STATE, "12345");
        map.put(ApiResults.ACCESS_TOKEN, "1");
        map.put(ApiResults.EXPIRES_IN, 3600L);
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        TokenService instance = Whitebox.invokeConstructor(TokenService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        RefreshTokenRequest request = new RefreshTokenRequest("1234");
        TokenData tokenData = TokenService.getNewAccessToken(request);
        assertEquals(map.get(ApiResults.REFRESH_TOKEN), tokenData.getRefreshToken());
        assertEquals(map.get(ApiResults.ACCESS_TOKEN), tokenData.getAccessToken());
        assertEquals(map.get(ApiResults.EXPIRES_IN), tokenData.getExpiresIn());
        assertEquals(map.get(ApiResults.STATE), tokenData.getState());
    }


    @Test(expected = PlenigoException.class)
    public void testUnsuccessfulRefreshAccessToken() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.ERROR, "1233");
        map.put(ApiResults.ERROR_DESCRIPTION, "ERROR MSG");
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);

        TokenService instance = Whitebox.invokeConstructor(TokenService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        RefreshTokenRequest request = new RefreshTokenRequest("1234", "12391823");
        TokenService.getNewAccessToken(request);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUnsuccessfulRefreshAccessTokenBecauseOfWrongState() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.REFRESH_TOKEN, "123");
        map.put(ApiResults.STATE, "123456");
        map.put(ApiResults.ACCESS_TOKEN, "1");
        map.put(ApiResults.EXPIRES_IN, 3600L);
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        TokenService instance = Whitebox.invokeConstructor(TokenService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        RefreshTokenRequest request = new RefreshTokenRequest("1234", "123");
        TokenData tokenData = TokenService.getNewAccessToken(request);
        assertEquals(map.get(ApiResults.REFRESH_TOKEN), tokenData.getRefreshToken());
        assertEquals(map.get(ApiResults.ACCESS_TOKEN), tokenData.getAccessToken());
        assertEquals(map.get(ApiResults.EXPIRES_IN), tokenData.getExpiresIn());
        assertEquals(map.get(ApiResults.STATE), tokenData.getState());
    }


    @Test
    public void testSuccessfulGetAccessToken() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.REFRESH_TOKEN, "123");
        map.put(ApiResults.STATE, "12345");
        map.put(ApiResults.ACCESS_TOKEN, "1");
        map.put(ApiResults.EXPIRES_IN, 3600L);
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        TokenService instance = Whitebox.invokeConstructor(TokenService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        AccessTokenRequest request = new AccessTokenRequest("1234", "http://example.com");
        TokenData tokenData = TokenService.getAccessToken(request);
        assertEquals(map.get(ApiResults.REFRESH_TOKEN), tokenData.getRefreshToken());
        assertEquals(map.get(ApiResults.ACCESS_TOKEN), tokenData.getAccessToken());
        assertEquals(map.get(ApiResults.EXPIRES_IN), tokenData.getExpiresIn());
        assertEquals(map.get(ApiResults.STATE), tokenData.getState());
    }
}
