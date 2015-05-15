package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.ErrorCode;
import com.plenigo.sdk.internal.services.InternalUserApiService;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HashUtils;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.ProductsBought;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * Tests for {@link UserService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, HashUtils.class, UserService.class, RestClient.class})
@PowerMockIgnore({"javax.crypto.*"})
public class UserServiceTest {
    private static final String PLENIGO_USER_SAMPLE_COOKIE = "plenigo_user=sample";

    @Test
    public void testSuccessfulHasUserBought() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>1234&ts=>" + System.currentTimeMillis());


        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");

        suppressConstructor(HashUtils.class);
        mockStatic(HashUtils.class);

        UserService instance = Whitebox.invokeConstructor(UserService.class);
        InternalUserApiService internalUserApiService = Mockito.mock(InternalUserApiService.class);
        Mockito.when(internalUserApiService.hasUserBought(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyBoolean(), Mockito.anyList())).thenReturn(true);
        ReflectionTestUtils.setField(instance, "internalUserApiService", internalUserApiService);
        Assert.assertTrue(UserService.hasUserBought("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE));
    }

    @Test
    public void testUnsuccessfulHasUserBoughtWithEmptyData() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>&ts=>");


        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");


        suppressConstructor(HashUtils.class);
        mockStatic(HashUtils.class);


        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(Mockito.anyString(), eq(ApiURLs.PAYWALL_STATE), Mockito.anyString()))
                .thenReturn(Collections.singletonMap("enabled", (Object) "true"));
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        Assert.assertFalse(UserService.hasUserBought("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE));
    }

    @Test
    public void testHasUserBoughtWithExpiredCookie() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>1234&ts=>" + 1);

        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");

        RestClient client = Mockito.mock(RestClient.class);
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        assertFalse(UserService.hasUserBought("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE));
    }


    @Test(expected = PlenigoException.class)
    public void testHasUserBoughtWithNotFoundException() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>1234&ts=>" + System.currentTimeMillis());
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");
        suppressConstructor(HashUtils.class);
        mockStatic(HashUtils.class);
        RestClient client = Mockito.mock(RestClient.class);
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        InternalUserApiService internalUserApiService = Mockito.mock(InternalUserApiService.class);
        Mockito.when(internalUserApiService.hasUserBought(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyBoolean(), Mockito.anyList())).thenThrow(new PlenigoException(ErrorCode.SERVER, "", null));
        ReflectionTestUtils.setField(instance, "internalUserApiService", internalUserApiService);
        Assert.assertTrue(UserService.hasUserBought("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE));
    }


    @Test
    public void testIsPaywallEnabled() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>1234&ts=>" + System.currentTimeMillis());
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");
        PowerMockito.when(mockedMgr.getSecret()).thenReturn("SECRET");
        suppressConstructor(HashUtils.class);
        mockStatic(HashUtils.class);
        RestClient client = Mockito.mock(RestClient.class);
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(ApiResults.PAYWALL_STATE, Boolean.TRUE.toString());
        Mockito.when(client.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(result);
        assertTrue(UserService.isPaywallEnabled());
    }


    @Test
    public void testSuccessfulGetProductsBought() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>1234&ts=>" + System.currentTimeMillis());

        mockPlenigoManager();
        suppressConstructor(HashUtils.class);
        mockStatic(HashUtils.class);

        RestClient client = PowerMockito.spy(new RestClient());
        HttpURLConnection connection = Mockito
                .mock(HttpURLConnection.class);
        Mockito.when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        PowerMockito.when(client, method(RestClient.class, "getHttpConnection", String.class, String.class, String.class))
                .withArguments(anyString(), anyString(), anyString()).thenReturn(connection);
        String invalidParamsJson = "{\"subscriptions\":[{\"productId\":\"bbftsqC3787224694141\",\"title\":\"Test Subscription\"," +
                "\"buyDate\":\"2014-12-10 14:08:07 +0100\",\"endDate\":\"2014-12-10 14:08:07 +0100\"}]}";
        Mockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(invalidParamsJson.getBytes()));
        PowerMockito.doCallRealMethod().
                when(client,
                        method(RestClient.class, "handleResponse", HttpURLConnection.class, String.class))
                .withArguments(any(HttpURLConnection.class), anyString());
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        ProductsBought productsBought = instance.getProductsBought(PLENIGO_USER_SAMPLE_COOKIE);
        assertNotNull(productsBought);
        assertNotNull(productsBought.getSubscriptionProducts());
        assertEquals(1, productsBought.getSubscriptionProducts().size());
    }


    private void mockPlenigoManager() {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");
    }
}
