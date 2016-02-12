package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.CompanyUser;
import com.plenigo.sdk.models.ElementList;
import com.plenigo.sdk.models.MobileSecretInfo;
import com.plenigo.sdk.models.PageRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * Tests for {@link CompanyService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class CompanyServiceTest {

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
        Constructor constructor = CompanyService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testSuccessfulGetUserList() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        ElementList<CompanyUser> list = CompanyService.getUserList(new PageRequest(0,10));
        assertNotNull(list);
    }

    @Test
    public void testSuccessfulGetUserListWithUserIds() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        List<CompanyUser> list = CompanyService.getUserList(Collections.singletonList("userId"));
        assertNotNull(list);
    }
}
