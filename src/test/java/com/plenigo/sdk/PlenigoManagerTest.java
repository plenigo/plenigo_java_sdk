package com.plenigo.sdk;

import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.util.CookieParser;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.internal.models.Configuration;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link PlenigoManager}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(EncryptionUtils.class)
public class PlenigoManagerTest {
    private static final String COOKIE_TPL = "%s=%s;Skin=new;";
    private static final String SECRET = "THE_KEY";
    private static final String SAMPLE_COMPANY_KEY = "COMPANY-KEY";

    public String createCookieHeader(Long timestamp) throws PlenigoException {
        Map<String, Object> data = Collections.singletonMap("ts", (Object) timestamp.toString());
        String cookieValue = SdkUtils.getStringFromMap(data);
        String encodedData = EncryptionUtils.get().encryptWithAES(SECRET, cookieValue);
        return String.format(COOKIE_TPL, CookieParser.PLENIGO_USER_COOKIE_NAME, encodedData);
    }

    @Test
    public void testConfigureWithUrl() throws Exception {
        PlenigoManager instance = Whitebox.invokeConstructor(PlenigoManager.class);
        instance.configure(ApiURLs.DEFAULT_PLENIGO_URL, SECRET, SAMPLE_COMPANY_KEY, true, "http://plenigo.com");
        Configuration config = (Configuration) ReflectionTestUtils.getField(instance, "config");
        assertTrue("URL was not set correctly", ApiURLs.DEFAULT_PLENIGO_URL.equals(config.getUrl()));
        assertTrue("Secret was not set correctly", SECRET.equals(config.getSecret()));
        assertTrue("Company was not set correctly", SAMPLE_COMPANY_KEY.equals(config.getCompanyId()));
    }

    @Test
    public void testSetTestMode() throws Exception {
        PlenigoManager instance = Whitebox.invokeConstructor(PlenigoManager.class);
        instance.configure("http://url", SECRET, SAMPLE_COMPANY_KEY, true, "http://plenigo.com");
        Assert.assertTrue(instance.isTestMode());
    }
}
