package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.TransactionList;
import com.plenigo.sdk.models.TransactionSearchRequest;
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
 * Tests for {@link TransactionService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class TransactionServiceTest {

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
        Constructor constructor = TransactionService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testSuccessfulSearchTransactions() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        RestClient client = Mockito.mock(RestClient.class);
        Mockito.when(client.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyMap()))
                .thenReturn(map);
        HttpConfig.get().setClient(client);
        TransactionList list = TransactionService.searchTransactions(new TransactionSearchRequest(0, 10));
        assertNotNull(list);
    }
}
