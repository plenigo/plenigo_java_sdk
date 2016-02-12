package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.RestClient;
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
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * Tests for {@link UserManagementService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class UserManagementServiceTest {

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
        Constructor constructor = UserManagementService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }


    @Test
    public void testSuccessfulRegisterUser() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.CUST_ID, "customerId");
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.post(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        String customerId = UserManagementService.registerUser("email@email.com", "en");
        assertNotNull(customerId);
    }

    @Test
    public void testSuccessfulChangeEmail() throws Exception {
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.put(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap(), Mockito.anyMap()))
                .thenReturn(new HashMap<String, Object>());
        HttpConfig.get().setClient(client);
        boolean wasEmailChanged = UserManagementService.changeEmail("customerId", "email@email.com");
        assertTrue(wasEmailChanged);
    }

    @Test
    public void testSuccessfulLoginToken() throws Exception {
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
                .thenReturn(new HashMap<String, Object>());
        HttpConfig.get().setClient(client);
        String appTokenData = UserManagementService.createLoginToken("customerId");
        assertNotNull(appTokenData);
    }

}
