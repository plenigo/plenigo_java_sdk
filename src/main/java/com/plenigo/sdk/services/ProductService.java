package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.models.PagingInfo;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.RestClient;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.models.ActionPeriod;
import com.plenigo.sdk.models.CategoryData;
import com.plenigo.sdk.models.CategoryInfo;
import com.plenigo.sdk.models.Image;
import com.plenigo.sdk.models.PagedList;
import com.plenigo.sdk.models.PricingData;
import com.plenigo.sdk.models.ProductData;
import com.plenigo.sdk.models.ProductInfo;
import com.plenigo.sdk.models.Subscription;
import com.plenigo.sdk.models.ValidityTime;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This contains the services required  for Product management with plenigo.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class ProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());

    /**
     * Rest client used internally.
     */
    private static RestClient client = new RestClient();

    /**
     * Default constructor.
     */
    private ProductService() {

    }

    /**
     * This method retrieves the product data of a provided product id.
     * This can only be used for plenigo managed products.
     *
     * @param productId The product id to use.
     *
     * @return the user data related to the access token
     *
     * @throws PlenigoException whenever an error happens
     */
    public static ProductData getProductData(String productId) throws PlenigoException {
        LOGGER.log(Level.FINEST, "Getting the product data for the product id: {0} using the following company id: {1}"
                , new Object[]{productId, PlenigoManager.get().getCompanyId()});
        Map<String, Object> response = client.get(PlenigoManager.get().getUrl(), ApiURLs.GET_PRODUCT, ApiURLs.GET_PRODUCT + "/" + productId, null
                , JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        LOGGER.log(Level.FINEST, "JSON Data response from product id {0} : {1}", new Object[]{productId, response});
        ProductData productData = buildProductData(response);
        LOGGER.log(Level.FINEST, "Built Product Data from product id {0} : {1}", new Object[]{productId, productData});
        return productData;
    }

    /**
     * Obtain a list of product in a paginated way.
     *
     * @param pageSize The size of the page, it will be trimmed to 10...100
     * @param lastId   the last id of the page that will set the page number to be requested
     *
     * @return A list as a ResultSet with totalElements, page size, last id and the list of products
     *
     * @throws PlenigoException If any error happens
     */
    public static PagedList<ProductInfo> getProductList(int pageSize, String lastId) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.PAGE_SIZE, pageSize);
        if (lastId != null && !lastId.trim().isEmpty()) {
            params.put(ApiParams.LAST_ID, lastId);
        }
        Map<String, Object> objectMap = client.get(PlenigoManager.get().getUrl(), ApiURLs.LIST_PRODUCTS, SdkUtils.buildUrlQueryString(params),
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return buildProductList(objectMap);
    }

    /**
     * Obtain a list of product in a paginated way.
     *
     * @param pageSize The size of the page, it will be trimmed to 10...100
     * @param lastId   the last id of the page that will set the page number to be requested
     *
     * @return A list as a ResultSet with totalElements, page size, last id and the list of products
     *
     * @throws PlenigoException If any error happens
     */
    public static PagedList<CategoryInfo> getCategoryList(int pageSize, String lastId) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.PAGE_SIZE, pageSize);
        if (lastId != null && !lastId.trim().isEmpty()) {
            params.put(ApiParams.LAST_ID, lastId);
        }
        Map<String, Object> objectMap = client.get(PlenigoManager.get().getUrl(), ApiURLs.LIST_CATEGORIES, SdkUtils.buildUrlQueryString(params),
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));

        return buildCategoryList(objectMap);
    }

    /**
     * Retrieves the  product list data values provided in the map,.
     *
     * @param response The response from the API call
     *
     * @return A {@link PagedList} object built from the map
     */
    @SuppressWarnings("unchecked")
    private static PagedList<CategoryInfo> buildCategoryList(Map<String, Object> response) {
        PagingInfo pagingInfo = buildPagingInfo(response, ApiResults.CATEGORY_ID);
        Object elementsObj = response.get(ApiResults.ELEMENTS);
        List<CategoryInfo> categories = new LinkedList<CategoryInfo>();
        //https://code.google.com/p/json-simple/ Json simple library maps arrays as Lists
        if (elementsObj instanceof List) {
            List elements = (List) elementsObj;
            for (Object elementObj : elements) {
                //and objects as maps
                if (elementObj instanceof Map) {
                    Map<String, Object> element = (Map<String, Object>) elementObj;
                    String catId = SdkUtils.getValueIfNotNull(element, ApiResults.CATEGORY_ID);
                    String title = SdkUtils.getValueIfNotNull(element, ApiResults.TITLE);
                    categories.add(new CategoryInfo(catId, title));
                }
            }
        }
        return new PagedList<CategoryInfo>(categories, pagingInfo);
    }

    /**
     * Retrieves the  product list data values provided in the map,.
     *
     * @param response The response from the API call
     *
     * @return A {@link PagedList} object built from the map
     */

    @SuppressWarnings("unchecked")
    private static PagedList<ProductInfo> buildProductList(Map<String, Object> response) {
        PagingInfo pagingInfo = buildPagingInfo(response, ApiResults.PROD_ID);
        Object elementsObj = response.get(ApiResults.ELEMENTS);
        List<ProductInfo> products = new LinkedList<ProductInfo>();
        //https://code.google.com/p/json-simple/ Json simple library maps arrays as Lists
        if (elementsObj instanceof List) {
            List elements = (List) elementsObj;
            for (Object elementObj : elements) {
                //and objects as maps
                if (elementObj instanceof Map) {
                    Map<String, Object> element = (Map<String, Object>) elementObj;
                    String prodId = SdkUtils.getValueIfNotNull(element, ApiResults.PROD_ID);
                    String title = SdkUtils.getValueIfNotNull(element, ApiResults.TITLE);
                    String description = SdkUtils.getValueIfNotNull(element, ApiResults.DESCRIPTION);
                    products.add(new ProductInfo(prodId, title, description));
                }
            }
        }
        return new PagedList<ProductInfo>(products, pagingInfo);
    }

    /**
     * Builds an object containing the required paging information.
     *
     * @param response    The response data from the API
     * @param lastIdParam The parameter that contains the last id in the map
     *
     * @return a paging info object
     */
    @SuppressWarnings("unchecked")
    private static PagingInfo buildPagingInfo(Map<String, Object> response, String lastIdParam) {
        String totalElementsStr = SdkUtils.getValueIfNotNull(response, ApiResults.TOTAL_ELEMENTS);
        int totalElements = 0;
        if (!totalElementsStr.isEmpty()) {
            totalElements = Integer.parseInt(totalElementsStr);
        }
        String pageSizeStr = SdkUtils.getValueIfNotNull(response, ApiResults.PAGE_SIZE);
        int pageSize = 0;
        if (!pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Object lastIdProd = response.get(ApiResults.LAST_ID);
        String lastId = null;
        if (lastIdProd instanceof Map) {
            Map<String, Object> lastIdMap = (Map<String, Object>) lastIdProd;
            lastId = lastIdMap.get(lastIdParam).toString();
        }
        return new PagingInfo(lastId, pageSize, totalElements);
    }

    /**
     * Retrieves the  product data values provided in the map,.
     *
     * @param response The map that contains the data to retrieve
     *
     * @return A {@link ProductData} object built from the map
     */
    private static ProductData buildProductData(Map<String, Object> response) {
        String id = SdkUtils.getValueIfNotNull(response, ApiResults.ID);
        String title = SdkUtils.getValueIfNotNull(response, ApiResults.TITLE);
        String description = SdkUtils.getValueIfNotNull(response, ApiResults.DESCRIPTION);
        Boolean collectible = false;
        String isCollectible = SdkUtils.getValueIfNotNull(response, ApiResults.COLLECTIBLE);
        if (!isCollectible.isEmpty()) {
            collectible = Boolean.parseBoolean(isCollectible);
        }
        PricingData pricingData = buildPricingData(response);
        Subscription subscriptionData = buildSubscription(response);
        ActionPeriod actionPeriod = buildActionPeriod(response);
        List<Image> imageData = buildImageList(response);
        String maxParallelAccessStr = SdkUtils.getValueIfNotNull(response, ApiResults.MAX_PARALLEL_APP_ACCESS);
        int maxParallelAccess = 0;
        if (!maxParallelAccessStr.isEmpty()) {
            maxParallelAccess = Integer.parseInt(maxParallelAccessStr);
        }

        String customInfo = SdkUtils.getValueIfNotNull(response, ApiResults.CUSTOM_INFO);
        return new ProductData(id, subscriptionData, title, description, collectible, pricingData, actionPeriod, imageData, maxParallelAccess, customInfo);
    }

    /**
     * Retrieves the image list values  provided in the map.
     *
     * @param response The map that contains the data to retrieve
     *
     * @return a list of {@link Image} objects built from the map
     */
    @SuppressWarnings("unchecked")
    private static List<Image> buildImageList(Map<String, Object> response) {
        Object images = response.get(ApiResults.IMAGES);
        List<Image> imageData = new LinkedList<Image>();
        if (images instanceof List) {
            List<Map<String, Object>> imageArray = (List<Map<String, Object>>) images;
            for (Map<String, Object> imgMap : imageArray) {
                String imgUrl = SdkUtils.getValueIfNotNull(imgMap, ApiResults.URL);
                String imgDescription = SdkUtils.getValueIfNotNull(imgMap, ApiResults.DESCRIPTION);
                String altText = SdkUtils.getValueIfNotNull(imgMap, ApiResults.ALT_TEXT);
                Image img = new Image(imgUrl, imgDescription, altText);
                imageData.add(img);
            }
        }
        return imageData;
    }

    /**
     * Retrieves the action period values provided in the map.
     *
     * @param response The map that contains the data to retrieve
     *
     * @return An {@link ActionPeriod} object built from the map
     */
    private static ActionPeriod buildActionPeriod(Map<String, Object> response) {
        String actionPeriodName = SdkUtils.getValueIfNotNull(response, ApiResults.ACTION_PERIOD_NAME);
        String actionPeriodTermStr = SdkUtils.getValueIfNotNull(response, ApiResults.ACTION_PERIOD_TERM);
        String actionPeriodPriceStr = SdkUtils.getValueIfNotNull(response, ApiResults.ACTION_PERIOD_PRICE);
        double actionPeriodPrice = 0.0;
        if (!actionPeriodPriceStr.isEmpty()) {
            actionPeriodPrice = Double.parseDouble(actionPeriodPriceStr);
        }
        int actionPeriodTerm = 0;
        if (!actionPeriodTermStr.isEmpty()) {
            actionPeriodTerm = Integer.parseInt(actionPeriodTermStr);
        }
        return new ActionPeriod(actionPeriodName, actionPeriodTerm, actionPeriodPrice);
    }

    /**
     * Retrieves the pricing data values provided in the map.
     *
     * @param response The map that contains the data to retrieve
     *
     * @return An {@link PricingData} object built from the map
     */
    private static PricingData buildPricingData(Map<String, Object> response) {
        String choosePriceStr = SdkUtils.getValueIfNotNull(response, ApiResults.CAN_CHOOSE_PRICE);
        boolean choosePrice = false;
        if (choosePriceStr.isEmpty()) {
            choosePrice = Boolean.parseBoolean(choosePriceStr);
        }
        String priceStr = SdkUtils.getValueIfNotNull(response, ApiResults.PRICE);
        double price = 0.0;
        if (!priceStr.isEmpty()) {
            price = Double.parseDouble(priceStr);
        }
        String taxesStr = SdkUtils.getValueIfNotNull(response, ApiResults.TAXES);
        int taxes = 0;
        if (!taxesStr.isEmpty()) {
            taxes = Integer.parseInt(taxesStr);
        }
        String currency = SdkUtils.getValueIfNotNull(response, ApiResults.CURRENCY);

        return new PricingData(choosePrice, price, taxes, currency);
    }

    /**
     * Retrieves the subscription data values provided in the map.
     *
     * @param response The map that contains the data to retrieve
     *
     * @return An {@link Subscription} object built from the map
     */
    private static Subscription buildSubscription(Map<String, Object> response) {
        String subscriptionStr = SdkUtils.getValueIfNotNull(response, ApiResults.SUBSCRIPTION);
        boolean subscription = false;
        if (!subscriptionStr.isEmpty()) {
            subscription = Boolean.parseBoolean(subscriptionStr);
        }
        String termStr = SdkUtils.getValueIfNotNull(response, ApiResults.TERM);
        int term = 0;
        if (!termStr.isEmpty()) {
            term = Integer.parseInt(termStr);
        }

        String cancellationPeriodStr = SdkUtils.getValueIfNotNull(response, ApiResults.CANCELLATION_PERIOD);

        int cancellationPeriod = 0;
        if (!cancellationPeriodStr.isEmpty()) {
            cancellationPeriod = Integer.parseInt(cancellationPeriodStr);
        }
        String autoRenewalStr = SdkUtils.getValueIfNotNull(response, ApiResults.AUTO_RENEWAL);
        boolean autoRenewal = false;
        if (!autoRenewalStr.isEmpty()) {
            autoRenewal = Boolean.parseBoolean(autoRenewalStr);
        }
        return new Subscription(subscription, term, cancellationPeriod, autoRenewal);
    }

    /**
     * Retrieves the pricing data values provided in the map.
     *
     * @param response The map that contains the data to retrieve
     *
     * @return An {@link CategoryData} object built from the map
     */
    private static CategoryData buildCategoryData(Map<String, Object> response) {
        String id = SdkUtils.getValueIfNotNull(response, ApiResults.ID);
        String validityTime = SdkUtils.getValueIfNotNull(response, ApiResults.VALIDITY_TIME);
        ValidityTime valTime = ValidityTime.get(validityTime);
        PricingData pricingData = buildPricingData(response);
        return new CategoryData(id, pricingData, valTime);
    }

    /**
     * <p>
     * This method retrieves the category data of a provided category id.
     * This can only be used for plenigo managed categories.
     * </p>
     *
     * @param categoryId The category id to use.
     *
     * @return the category data related to the access token
     *
     * @throws PlenigoException whenever an error happens
     */
    public static CategoryData getCategoryData(String categoryId) throws PlenigoException {
        LOGGER.log(Level.FINEST, "Getting the category data for the category id: {0} using the following company id: {1}",
                new Object[]{categoryId, PlenigoManager.get().getCompanyId()});
        Map<String, Object> response = client.get(PlenigoManager.get().getUrl(), ApiURLs.GET_CATEGORY + "/" + categoryId, null,
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        LOGGER.log(Level.FINEST, "JSON Data response from category {0} : {1}", new Object[]{categoryId, response});
        CategoryData categoryData = buildCategoryData(response);
        LOGGER.log(Level.FINEST, "Built Category Data from category id {0} : {1}", new Object[]{categoryId, categoryData});
        return categoryData;
    }
}
