package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.util.CookieParser;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.HashUtils;
import com.plenigo.sdk.util.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * Tests for {@link MeterService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, HashUtils.class})
@PowerMockIgnore({"javax.crypto.*"})
public class MeterServiceTest {
    //This sample cookie contains data with the following format: 4bb615165daf02f6d5d88abcfb4b1aca|true|4|167|true

    @Test
    public void testSuccessfulGetMeteredViewData() throws Exception {
        String cookieValue = "browserId|true|2|2|true|etc";
        String encodedData = EncryptionUtils.get().encryptWithAES(TestUtil.COMPANY_ID, cookieValue, MeterService.METERED_INIT_VECTOR);
        String cookie = String.format(TestUtil.COOKIE_TPL, CookieParser.PLENIGO_METERED_VIEW_COOKIE_NAME, encodedData);
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn(TestUtil.COMPANY_ID);
        PowerMockito.when(mockedMgr.getSecret()).thenReturn(TestUtil.SECRET);
        assertFalse(MeterService.hasFreeViews(cookie, ""));
    }

    @Test
    public void testUnsuccessfulGetMeteredViewDataWithEmptyString() throws Exception {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn(TestUtil.COMPANY_ID);
        assertTrue(MeterService.hasFreeViews("", ""));
    }


    @Test
    public void testUnsuccessfulGetMeteredViewDataWithRandomString() throws Exception {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn(TestUtil.COMPANY_ID);
        assertTrue(MeterService.hasFreeViews("asdasdasd", ""));
    }


    @Test
    public void testUnsuccessfulGetMeteredViewDataWithEmptyCookie() throws Exception {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn(TestUtil.COMPANY_ID);
        assertTrue(MeterService.hasFreeViews("plenigo_view=aefaef", ""));
    }
}
