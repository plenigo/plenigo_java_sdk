package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.ErrorCode;
import com.plenigo.sdk.internal.models.Address;
import com.plenigo.sdk.internal.services.InternalUserApiService;
import com.plenigo.sdk.internal.util.CookieParser;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HashUtils;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.ProductsBought;
import com.plenigo.sdk.models.UserData;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * Tests for {@link UserService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, HashUtils.class, RestClient.class})
@PowerMockIgnore({"javax.crypto.*"})
public class UserServiceTest {
    private static final String PLENIGO_USER_SAMPLE_COOKIE = "plenigo_user=sample";
    public static final String VALID_CUSTOMER = "ci=>1234&ts=>" + System.currentTimeMillis();


    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = UserService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testSuccessfulHasUserBought() throws Exception {
        configurePlenigoManager();

        UserService instance = Whitebox.invokeConstructor(UserService.class);
        InternalUserApiService internalUserApiService = Mockito.mock(InternalUserApiService.class);
        Mockito.when(internalUserApiService.hasUserBought(anyString(), anyString(), anyString(), anyString(),
                Mockito.anyBoolean(), Mockito.anyList(), Mockito.anyBoolean())).thenReturn(true);
        ReflectionTestUtils.setField(instance, "internalUserApiService", internalUserApiService);
        Assert.assertTrue(UserService.hasUserBought("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE));
        Assert.assertTrue(UserService.hasUserBought(Collections.singletonList("SAMPLE_PROD"), PLENIGO_USER_SAMPLE_COOKIE));
    }

    @Test
    public void testSuccessfulHasUserBoughtByCustomerId() throws Exception {
        configurePlenigoManager();

        UserService instance = Whitebox.invokeConstructor(UserService.class);
        InternalUserApiService internalUserApiService = Mockito.mock(InternalUserApiService.class);
        Mockito.when(internalUserApiService.hasUserBought(anyString(), anyString(), anyString(), anyString(),
                Mockito.anyBoolean(), Mockito.anyList(), Mockito.anyBoolean())).thenReturn(true);
        ReflectionTestUtils.setField(instance, "internalUserApiService", internalUserApiService);
        Assert.assertTrue(UserService.hasUserBoughtByCustomerId("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE, true));
        Assert.assertTrue(UserService.hasUserBoughtByCustomerId(Collections.singletonList("SAMPLE_PROD"), PLENIGO_USER_SAMPLE_COOKIE, true));

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
        Mockito.when(client.get(anyString(), anyString(), eq(ApiURLs.PAYWALL_STATE), anyString(), Mockito.anyMap()))
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
        configurePlenigoManager();
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        InternalUserApiService internalUserApiService = Mockito.mock(InternalUserApiService.class);
        Mockito.when(internalUserApiService.hasUserBought(anyString(), anyString(), anyString(), anyString(),
                Mockito.anyBoolean(), Mockito.anyList(), Mockito.anyBoolean())).thenThrow(new PlenigoException(ErrorCode.SERVER, "", null));
        ReflectionTestUtils.setField(instance, "internalUserApiService", internalUserApiService);
        Assert.assertTrue(UserService.hasUserBought("SAMPLE_PROD", PLENIGO_USER_SAMPLE_COOKIE));
    }


    @Test
    public void testIsPaywallEnabled() throws Exception {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn(VALID_CUSTOMER);
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
        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
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
        PowerMockito.when(client, method(RestClient.class, "getHttpConnection", String.class, String.class, String.class, Map.class))
                .withArguments(anyString(), anyString(), anyString(), anyMap()).thenReturn(connection);
        String invalidParamsJson = "{\"subscriptions\":[{\"productId\":\"bbftsqC3787224694141\",\"title\":\"Test Subscription\"," +
                "\"buyDate\":\"2014-12-10 14:08:07 +0100\",\"endDate\":\"2014-12-10 14:08:07 +0100\"}]}";
        Mockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(invalidParamsJson.getBytes()));
        PowerMockito.doCallRealMethod().
                when(client,
                        method(RestClient.class, "handleResponse", HttpURLConnection.class, String.class, String.class))
                .withArguments(any(HttpURLConnection.class), anyString(), anyString());
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        ProductsBought productsBought = instance.getProductsBought(PLENIGO_USER_SAMPLE_COOKIE);
        assertNotNull(productsBought);
        assertNotNull(productsBought.getSubscriptionProducts());
        assertEquals(1, productsBought.getSubscriptionProducts().size());
    }


    @Test
    public void testSuccessfulGetProductsBoughtWithSingleProducts() throws Exception {
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
        PowerMockito.when(client, method(RestClient.class, "getHttpConnection", String.class, String.class, String.class, Map.class))
                .withArguments(anyString(), anyString(), anyString(), anyMap()).thenReturn(connection);
        String invalidParamsJson = "{\"singleProducts\":[{\"productId\":\"bbftsqC3787224694141\",\"title\":\"Test Subscription\"," +
                "\"buyDate\":\"2014-12-10 14:08:07 +0100\",\"endDate\":\"2014-12-10 14:08:07 +0100\"}]}";
        Mockito.when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(invalidParamsJson.getBytes()));
        PowerMockito.doCallRealMethod().
                when(client,
                        method(RestClient.class, "handleResponse", HttpURLConnection.class, String.class, String.class))
                .withArguments(any(HttpURLConnection.class), anyString(), anyString());
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        ProductsBought productsBought = instance.getProductsBought(PLENIGO_USER_SAMPLE_COOKIE);
        assertNotNull(productsBought);
        assertNotNull(productsBought.getSinglePaymentProducts());
        assertEquals(1, productsBought.getSinglePaymentProducts().size());
    }


    @Test
    public void testUnsuccessfulGetProductsBoughtWithSingleProducts() throws Exception {
        UserService instance = Whitebox.invokeConstructor(UserService.class);
        ProductsBought productsBought = instance.getProductsBought(null);
        assertNotNull(productsBought);
        assertNotNull(productsBought.getSinglePaymentProducts());
        assertTrue(productsBought.getSinglePaymentProducts().isEmpty());
        assertTrue(productsBought.getSubscriptionProducts().isEmpty());
    }

    @Test
    public void testSuccessfulIsLoggedIn() throws Exception {
        configurePlenigoManager();
        Assert.assertEquals("Expected logged in is different", true, UserService.isLoggedIn(CookieParser.PLENIGO_USER_COOKIE_NAME + "=" + VALID_CUSTOMER));
    }

    @Test
    public void testSuccessfulGetUserData() throws Exception {
        configurePlenigoManager();

        UserService instance = Whitebox.invokeConstructor(UserService.class);
        InternalUserApiService internalUserApiService = Mockito.mock(InternalUserApiService.class);
        Address addressInfo = new Address("Calle", "Adicional", "00000", "Sto Dgo", "Dom Rep");
        UserData userData = new UserData("id", "email@sample.com", "MALE", "Torres", "Ricardo", addressInfo, "ricardo");
        Mockito.when(internalUserApiService.getUserData(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(userData);
        ReflectionTestUtils.setField(instance, "internalUserApiService", internalUserApiService);
        Assert.assertEquals("User data is different", userData, UserService.getUserData("123"));
    }

    private void configurePlenigoManager() throws PlenigoException {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.decryptWithAES(anyString(), anyString())).thenReturn("ci=>1234&ts=>" + System.currentTimeMillis());


        mockPlenigoManager();

        suppressConstructor(HashUtils.class);
        mockStatic(HashUtils.class);
    }


    private void mockPlenigoManager() {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");
    }
}
