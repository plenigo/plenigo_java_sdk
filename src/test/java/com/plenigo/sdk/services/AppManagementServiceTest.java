package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.AppAccessData;
import com.plenigo.sdk.models.AppAccessToken;
import com.plenigo.sdk.models.AppTokenRequest;
import com.plenigo.sdk.models.CustomerAppRequest;
import com.plenigo.sdk.models.DeleteAppIdRequest;
import com.plenigo.sdk.models.ProductAccessRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * Tests for {@link AppManagementService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class AppManagementServiceTest {

    @Before
    public void setup() throws PlenigoException {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");

    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = AppManagementService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }


    @Test
    public void testSuccessfulRequestAccessToken() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.CUST_ID, "customerId");
        map.put(ApiResults.APP_TOKEN, "appToken");
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        AppAccessToken appAccessToken = AppManagementService.requestAppToken(new AppTokenRequest("XBH05SNGJY8F", "2jmZXbt9229990636341", "test"));
        assertNotNull(appAccessToken);
    }

    @Test
    public void testSuccessfulGetCustomerApps() throws Exception {
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
                .thenReturn(new HashMap<String, Object>());
        HttpConfig.get().setClient(client);
        List<AppAccessData> appTokenData = AppManagementService.getCustomerApps(new CustomerAppRequest("XBH05SNGJY8F"));
        assertNotNull(appTokenData);
    }


    @Test
    public void testSuccessfulRequestAppId() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.CUST_ID, "customerId");
        map.put(ApiResults.APP_TOKEN, "appToken");
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        AppTokenRequest tokenRequest = new AppTokenRequest("XBH05SNGJY8F", "2jmZXbt9229990636341", "test");
        AppAccessToken appAccessToken = AppManagementService.requestAppToken(tokenRequest);

        Map<String, Object> apiDataMap = new HashMap<String, Object>();
        apiDataMap.put(ApiResults.CUST_ID, "customerId");
        apiDataMap.put(ApiResults.CUSTOMER_APP_ID, "customerAppId");
        apiDataMap.put(ApiResults.DESCRIPTION, "description");
        apiDataMap.put(ApiResults.PROD_ID, "productId");

        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(apiDataMap);
        AppAccessData appAccessData = AppManagementService.requestAppId(appAccessToken);
        assertNotNull(appAccessData);
    }


    @Test
    public void testSuccessfulDelete() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.delete(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        AppManagementService.deleteCustomerApp(new DeleteAppIdRequest("customerId", "customerAppid"));
    }

    @Test
    public void testSuccessfulHasUserBought() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        Boolean hasUserBought = AppManagementService.hasUserBought(new ProductAccessRequest("customerId", "productId", "customerAppId"));
        assertTrue(hasUserBought);
    }
}
