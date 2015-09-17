package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.models.AppAccessData;
import com.plenigo.sdk.models.AppAccessToken;
import com.plenigo.sdk.models.AppTokenRequest;
import com.plenigo.sdk.models.CustomerAppRequest;
import com.plenigo.sdk.models.DeleteAppIdRequest;
import com.plenigo.sdk.models.ProductAccessRequest;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link com.plenigo.sdk.services.ProductService}.
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class AppManagementServiceTest {

    @Before
    public void setup() throws PlenigoException {
        PlenigoManager.get().configure("https://api.s-devops.com", "AMXzF7qJ9y0uuz2IawRIk6ZMLVeYKq9yXh7lURXQ", "h7evZBaXvhaLVHYRue7X", false
                , "https://api.s-devops.com");
        //delete all app ids
        try {
            List<AppAccessData> appTokenData = AppManagementService.getCustomerApps(new CustomerAppRequest("XBH05SNGJY8F"));
            for (AppAccessData appAccessData : appTokenData) {
                AppManagementService.deleteCustomerApp(new DeleteAppIdRequest(appAccessData.getCustomerId(), appAccessData.getCustomerAppId()));
            }
        }catch(PlenigoException e){
            if(!e.getResponseCode().equals(String.valueOf(HttpURLConnection.HTTP_FORBIDDEN))){
                throw e;
            }
        }
    }

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = ProductService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }


    @Test
    public void testSuccessfulRequestAccessToken() throws Exception {
        AppAccessToken appAccessToken = AppManagementService.requestAppToken(new AppTokenRequest("XBH05SNGJY8F", "2jmZXbt9229990636341", "test"));
        assertNotNull(appAccessToken);
    }

    @Test
    public void testSuccessfulGetCustomerApps() throws Exception {
        List<AppAccessData> appTokenData = AppManagementService.getCustomerApps(new CustomerAppRequest("XBH05SNGJY8F"));
        assertNotNull(appTokenData);
    }


    @Test
    public void testSuccessfulRequestAppId() throws Exception {
        AppTokenRequest tokenRequest = new AppTokenRequest("XBH05SNGJY8F", "2jmZXbt9229990636341", "test");
        AppAccessToken appAccessToken = AppManagementService.requestAppToken(tokenRequest);
        AppAccessData appAccessData = AppManagementService.requestAppId(appAccessToken);
        assertNotNull(appAccessData);
    }


    @Test
    public void testHasUserBought() throws Exception {
        AppTokenRequest tokenRequest = new AppTokenRequest("XBH05SNGJY8F", "w1eqJhA7793680636341", "test");
        AppAccessToken appAccessToken = AppManagementService.requestAppToken(tokenRequest);
        AppAccessData appAccessData = AppManagementService.requestAppId(appAccessToken);
        Boolean hasUserBought = AppManagementService.hasUserBought(new ProductAccessRequest(tokenRequest.getCustomerId(), tokenRequest.getProductId()
                , appAccessData.getCustomerAppId()));
        assertTrue("The product shouldve been bought",hasUserBought);
    }
}
