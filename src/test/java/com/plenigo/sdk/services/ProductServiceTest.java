package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ErrorCode;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.models.CategoryData;
import com.plenigo.sdk.models.CategoryInfo;
import com.plenigo.sdk.models.PagedList;
import com.plenigo.sdk.models.ProductData;
import com.plenigo.sdk.models.ProductInfo;
import com.plenigo.sdk.models.ValidityTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * Tests for {@link ProductService}.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionUtils.class, PlenigoManager.class, RestClient.class})
public class ProductServiceTest {

    @Test
    public void testConstructorIsPrivate() throws Exception {
        Constructor constructor = ProductService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }


    @Test
    public void testSuccessfulGetProductData() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = getSampleProductMap();
        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        ProductData productData = ProductService.getProductData("1");
        validateSampleProductMap(productData);
    }

    private void validateSampleProductMap(ProductData productData) {
        assertNotNull(productData);
        assertNotNull(productData.getActionPeriodName());
        assertFalse(productData.getActionPeriodName().isEmpty());
        assertNotNull(productData.getActionPeriodPrice());
        assertNotNull(productData.getActionPeriodTerm());
        assertEquals(5, productData.getActionPeriodTerm());
        assertFalse(productData.isPriceChosen());
        assertTrue(productData.getPrice() == 20.99);
        assertTrue(productData.getTaxes() == 19.00);
        assertTrue(productData.isSubscribable());
        assertEquals(5, productData.getSubscriptionTerm());
        assertTrue(productData.isAutoRenewed());
        assertNotNull(productData.getCancellationPeriod());
        assertEquals(5, productData.getCancellationPeriod());
        assertNotNull(productData.getCurrency());
        assertFalse(productData.getCurrency().isEmpty());
        assertNotNull(productData.getDescription());
        assertFalse(productData.getDescription().isEmpty());
        assertNotNull(productData.getId());
        assertFalse(productData.getId().isEmpty());
        assertNotNull(productData.getDescription());
        assertTrue(productData.isCollectible());
        assertFalse(productData.getDescription().isEmpty());
        assertNotNull(productData.getImages());
        assertFalse(productData.getImages().isEmpty());
        assertFalse(productData.getTitle().isEmpty());
        assertFalse(productData.getDescription().isEmpty());
        assertFalse(productData.getImages().get(0).getDescription().isEmpty());
        assertFalse(productData.getImages().get(0).getAltText().isEmpty());
        assertFalse(productData.getImages().get(0).getUrl().isEmpty());
    }

    private Map<String, Object> getSampleProductMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.ID, "prod1");
        map.put(ApiResults.SUBSCRIPTION, "true");
        map.put(ApiResults.TITLE, "title");
        map.put(ApiResults.DESCRIPTION, "desc");
        map.put(ApiResults.COLLECTIBLE, "true");
        map.put(ApiResults.URL, "url");
        map.put(ApiResults.CAN_CHOOSE_PRICE, false);
        map.put(ApiResults.PRICE, "20.99");
        map.put(ApiResults.TAXES, "19");
        map.put(ApiResults.CURRENCY, "EUR");
        map.put(ApiResults.TERM, "5");
        map.put(ApiResults.CANCELLATION_PERIOD, "5");
        map.put(ApiResults.AUTO_RENEWAL, "true");
        map.put(ApiResults.ACTION_PERIOD_NAME, "periodName");
        map.put(ApiResults.ACTION_PERIOD_TERM, "5");
        map.put(ApiResults.ACTION_PERIOD_PRICE, "20.00");
        Map<String, Object> img = new HashMap<String, Object>();
        img.put(ApiResults.URL, "http://img.url");
        img.put(ApiResults.DESCRIPTION, "img dsc");
        img.put(ApiResults.ALT_TEXT, "alt txt");
        map.put(ApiResults.IMAGES, Collections.singletonList(img));
        return map;
    }

    private void mockPlenigoManager() {
        suppressConstructor(PlenigoManager.class);
        mockStatic(PlenigoManager.class);
        PlenigoManager mockedMgr = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockedMgr);
        PowerMockito.when(mockedMgr.getCompanyId()).thenReturn("CP_ID");
    }


    @Test
    public void testSuccessfulGetProductDataWithNullValues() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ApiResults.ID, null);
        map.put(ApiResults.SUBSCRIPTION, null);
        map.put(ApiResults.TITLE, null);
        map.put(ApiResults.DESCRIPTION, null);
        map.put(ApiResults.URL, null);
        map.put(ApiResults.CAN_CHOOSE_PRICE, null);
        map.put(ApiResults.PRICE, null);
        map.put(ApiResults.TAXES, null);
        map.put(ApiResults.CURRENCY, null);
        map.put(ApiResults.TERM, null);
        map.put(ApiResults.CANCELLATION_PERIOD, null);
        map.put(ApiResults.AUTO_RENEWAL, null);
        map.put(ApiResults.ACTION_PERIOD_NAME, null);
        map.put(ApiResults.ACTION_PERIOD_TERM, null);
        map.put(ApiResults.ACTION_PERIOD_PRICE, null);
        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), Mockito.anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        assertNotNull(ProductService.getProductData("1"));
    }

    @Test
    public void testGetProductDataWithInvalidParameters() throws Exception {
        mockPlenigoManager();
        RestClient client = PowerMockito.spy(new RestClient());
        HttpURLConnection connection = Mockito
                .mock(HttpURLConnection.class);
        Mockito.when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);
        PowerMockito.when(client, method(RestClient.class, "getHttpConnection", String.class, String.class, String.class, Map.class))
                .withArguments(anyString(), anyString(), anyString(), anyMap()).thenReturn(connection);
        String invalidParamsJson = "{\"userId\":{\"Error\":\"cannot be null\",\"Rejected Value\":\"null\"}}";
        Mockito.when(connection.getErrorStream()).thenReturn(new ByteArrayInputStream(invalidParamsJson.getBytes()));
        try {
            PowerMockito.doCallRealMethod().
                    when(client,
                            method(RestClient.class, "handleResponse", HttpURLConnection.class, String.class, String.class))
                    .withArguments(any(HttpURLConnection.class), anyString(), anyString());
            ProductService instance = Whitebox.invokeConstructor(ProductService.class);
            ReflectionTestUtils.setField(instance, "client", client);
            ProductService.getProductData("sampleProd");
        } catch (PlenigoException pe) {
            assertEquals(ErrorCode.INVALID_PARAMETERS.getCode(), pe.getResponseCode());
            assertNotNull(pe.getErrors());
        }
    }

    @Test
    public void testGetProductListSuccessfully() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        //paging information
        int totalElements = 3;
        int pageSize = 3;
        map.put(ApiResults.TOTAL_ELEMENTS, totalElements);
        map.put(ApiResults.PAGE_SIZE, pageSize);
        map.put(ApiResults.LAST_ID, Collections.singletonMap(ApiResults.PROD_ID, "lastId"));
        List<Map<String, String>> elements = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> productInformation = new HashMap<String, String>();
            productInformation.put(ApiResults.PROD_ID, "prodId" + i);
            productInformation.put(ApiResults.TITLE, "title" + i);
            productInformation.put(ApiResults.DESCRIPTION, "desc" + i);
            elements.add(productInformation);
        }
        map.put(ApiResults.ELEMENTS, elements);

        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        PagedList<ProductInfo> productList = ProductService.getProductList(3, 0);
        assertNotNull(productList);
        assertEquals(productList.getTotalElements(), totalElements);
        assertEquals(productList.getPageSize(), pageSize);
    }

    @Test
    public void testGetProductListSuccessfullyWithLastId() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        //paging information
        int totalElements = 3;
        int pageSize = 3;
        map.put(ApiResults.TOTAL_ELEMENTS, totalElements);
        map.put(ApiResults.PAGE_SIZE, pageSize);
        map.put(ApiResults.LAST_ID, Collections.singletonMap(ApiResults.PROD_ID, "lastId"));
        List<Map<String, String>> elements = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> productInformation = new HashMap<String, String>();
            productInformation.put(ApiResults.PROD_ID, "prodId" + i);
            productInformation.put(ApiResults.TITLE, "title" + i);
            productInformation.put(ApiResults.DESCRIPTION, "desc" + i);
            elements.add(productInformation);
        }
        map.put(ApiResults.ELEMENTS, elements);

        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        PagedList<ProductInfo> productList = ProductService.getProductList(3, 0);
        assertNotNull(productList);
        assertEquals(productList.getTotalElements(), totalElements);
        assertEquals(productList.getPageSize(), pageSize);
    }


    @Test
    public void testGetCategoryListSuccessfully() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        //paging information
        int totalElements = 3;
        int pageSize = 3;
        map.put(ApiResults.TOTAL_ELEMENTS, totalElements);
        map.put(ApiResults.PAGE_SIZE, pageSize);
        map.put(ApiResults.LAST_ID, Collections.singletonMap(ApiResults.CATEGORY_ID, "lastId"));
        List<Map<String, String>> elements = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> categoryInformation = new HashMap<String, String>();
            categoryInformation.put(ApiResults.CATEGORY_ID, "catId" + i);
            categoryInformation.put(ApiResults.TITLE, "title" + i);
            elements.add(categoryInformation);
        }
        map.put(ApiResults.ELEMENTS, elements);

        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        PagedList<CategoryInfo> categoryList = ProductService.getCategoryList(3, 0);
        assertNotNull(categoryList);
        assertEquals(categoryList.getTotalElements(), totalElements);
        assertEquals(categoryList.getPageSize(), pageSize);
    }


    @Test
    public void testGetCategoryListSuccessfullyWithLastId() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        //paging information
        int totalElements = 3;
        int pageSize = 3;
        map.put(ApiResults.TOTAL_ELEMENTS, totalElements);
        map.put(ApiResults.PAGE_SIZE, pageSize);
        map.put(ApiResults.LAST_ID, Collections.singletonMap(ApiResults.CATEGORY_ID, "lastId"));
        List<Map<String, String>> elements = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 3; i++) {
            Map<String, String> categoryInformation = new HashMap<String, String>();
            categoryInformation.put(ApiResults.CATEGORY_ID, "catId" + i);
            categoryInformation.put(ApiResults.TITLE, "title" + i);
            elements.add(categoryInformation);
        }
        map.put(ApiResults.ELEMENTS, elements);

        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        PagedList<CategoryInfo> categoryList = ProductService.getCategoryList(3, 0);
        assertNotNull(categoryList);
        assertEquals(categoryList.getTotalElements(), totalElements);
        assertEquals(categoryList.getPageSize(), pageSize);
    }


    @Test
    public void testGetCategoryDataSuccessfully() throws Exception {
        mockPlenigoManager();
        RestClient client = Mockito.mock(RestClient.class);
        Map<String, Object> map = new HashMap<String, Object>();
        //paging information
        map.put(ApiResults.ID, "id");
        map.put(ApiResults.VALIDITY_TIME, ValidityTime.DAY.getValue());


        Mockito.when(client.get(anyString(), anyString(), anyString(), anyString(), anyMap()))
                .thenReturn(map);
        ProductService instance = Whitebox.invokeConstructor(ProductService.class);
        ReflectionTestUtils.setField(instance, "client", client);
        CategoryData data = ProductService.getCategoryData("id");
        assertNotNull(data);
        assertEquals(data.getId(), "id");
        assertEquals(data.getValidityTime(), ValidityTime.DAY);
    }
}
