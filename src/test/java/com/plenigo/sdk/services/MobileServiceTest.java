package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.MobileSecretInfo;
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
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * Tests for {@link MobileService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class MobileServiceTest {

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
        Constructor constructor = MobileService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }


    @Test
    public void testSuccessfulCreateMobileSecret() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.CUST_ID, "customerId");
        map.put(ApiResults.MOBILE_APP_SECRET, "mobileAppSecret");
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.post(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        MobileSecretInfo mobileSecretInfo = MobileService.createMobileSecret("customerId", 6);
        assertNotNull(mobileSecretInfo);
    }

    @Test
    public void testSuccessfulGetMobileSecret() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.CUST_ID, "customerId");
        map.put(ApiResults.MOBILE_APP_SECRET, "mobileAppSecret");
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        MobileSecretInfo mobileSecretInfo = MobileService.getMobileSecret("customerId");
        assertNotNull(mobileSecretInfo);
    }

    @Test
    public void testSuccessfulVerifyMobileSecret() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.CUST_ID, "customerId");
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        String customerId = MobileService.verifyMobileSecret("email", "mobileSecret");
        assertNotNull(customerId);
    }

    @Test
    public void testSuccessfulDeleteMobileSecret() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.delete(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        boolean customerId = MobileService.deleteMobileSecret("customerId");
        assertTrue(customerId);
    }
}
