package com.plenigo.sdk.builders;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.models.Product;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.internal.util.EncryptionUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This class builds a plenigo's Javascript API checkout
 * snippet based on a {@link Product} object that is
 * compliant.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is <b>not</b> thread safe.
 * </p>
 */
public class CheckoutSnippetBuilder {

    private static final Logger LOGGER = Logger.getLogger(CheckoutSnippetBuilder.class.getName());

    public static final String DECIMAL_FORMAT = "%.2f";
    /**
     * The Product object used to build the link.
     */
    private Product product;
    /**
     * This flag is used to indicate that you want a confirmation check before
     * generating the link.
     */
    private Boolean alreadyPayedConfirmation;

    /**
     * This flag is used to indicate that you want a confirmation check before
     * generating the link.
     */
    private Boolean paymentConfirmation;

    /**
     * This is used to indicate that want to  use cross-site request forgery
     * (<a href="http://en.wikipedia.org/wiki/Cross-site_request_forgery">CSRF</a>)
     * tokens.
     */
    private String csrfToken;

    /**
     * The redirect URL used for the single sign on process.
     */
    private String redirectUrl;

    /**
     * This flag is used to indicate that you want to show the failed payments of the current user.
     */
    private Boolean failedPayments;
    /**
     * The checkout event template, this can be interpreted as a javascript
     * snippet.
     */
    private static final String CHECKOUT_SNIPPET_TPL = "plenigo.checkout('%s');";

    /**
     * This constructor takes a {@link Product} object as a parameter.
     *
     * @param productToChkOut The product object to build the link from
     */
    public CheckoutSnippetBuilder(final Product productToChkOut) {
        this.product = productToChkOut;
    }

    /**
     * Builds a checkout snippet builder with the failed payment flag set.
     */
    public CheckoutSnippetBuilder() {
        this.failedPayments = true;
    }

    /**
     * This method is used to build the link once all the information and
     * options have been selected, this will produce a Javascript snippet of
     * code that can be used as an event on a webpage.
     *
     * @return A Javascript snippet that is compliant with plenigo's Javascript SDK.
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    public String build() throws PlenigoException {
        String encodedData = buildEncodedData();
        String snippet = String
                .format(CHECKOUT_SNIPPET_TPL, encodedData);
        LOGGER.log(Level.FINEST, "Built checkout snippet: {0}.", snippet);
        return snippet;
    }

    /**
     * This method builds the encoded data from the Checkout Object.
     *
     * @return The encoded data
     *
     * @throws com.plenigo.sdk.PlenigoException whenever an error happens
     */
    private String buildEncodedData() throws PlenigoException {
        String dataString = SdkUtils.getStringFromMap(convertToMap());
        return EncryptionUtils.get().encryptWithAES(
                PlenigoManager.get().getSecret(), dataString);
    }

    /**
     * This method puts the non null parameters
     * of the {@link Product} object into a {@link Map}.
     *
     * @return A map with the non null values of a {@link Product} object
     */
    private Map<String, Object> convertToMap() {
        LOGGER.log(Level.FINEST, "Data: {0} is being added to a map.", this);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if(this.failedPayments != null) {
            map.put(ApiParams.FAILED_PAYMENT, failedPayments);
        } else {
            // LinkedHashMap is used to maintain order
            String price = formatPriceIfNotNull(product.getPrice());

            LOGGER.log(Level.FINEST, "Formatted price: {0}", price);

            String taxType = null;
            if (product.getTaxType() != null) {
                taxType = product.getTaxType().name();
            }
            LOGGER.log(Level.FINEST, "Tax type: {0}", taxType);

            SdkUtils.addIfNotNull(map, ApiParams.PROD_PRICE, price);
            SdkUtils.addIfNotNull(map, ApiParams.CURRENCY, product.getCurrency());
            SdkUtils.addIfNotNull(map, ApiParams.PROD_TAX_TYPE, taxType);
            SdkUtils.addIfNotNull(map, ApiParams.PROD_TITLE, product.getTitle());
            SdkUtils.addIfNotNull(map, ApiParams.PROD_ID, product.getId());
            SdkUtils.addIfNotNull(map, ApiParams.CUSTOM_AMOUNT, product.isCustomPrice());
            SdkUtils.addIfNotNull(map, ApiParams.INFO_SCRN_SHOWN_ON_RETRY, alreadyPayedConfirmation);
            SdkUtils.addIfNotNull(map, ApiParams.INFO_SCRN_SHOWN_AT_END_OF_PAYMENT, paymentConfirmation);
            SdkUtils.addIfNotNull(map, ApiParams.TEST_TRANSACTION, PlenigoManager.get().isTestMode());
            SdkUtils.addIfNotNull(map, ApiParams.SINGLE_SIGN_ON, redirectUrl);
            SdkUtils.addIfNotNull(map, ApiParams.CATEGORY_ID, product.getCategoryId());
            SdkUtils.addIfNotNull(map, ApiParams.SUBSCRIPTION_RENEWAL, product.getSubscriptionRenewal());
            if (csrfToken != null) {
                LOGGER.log(Level.FINEST, "The used CSRF Token: {0}", csrfToken);
                map.put(ApiParams.CSRF_TOKEN, csrfToken);
            }
            LOGGER.log(Level.FINEST, "Map result: {0}", map);
        }
        return map;
    }

    /**
     * Fornats the given price to the appropriate decimal format.
     *
     * @param price The price to convert
     *
     * @return A String with the price using the correct format
     */
    private String formatPriceIfNotNull(Double price) {
        if (price != null) {
            return String.format(DECIMAL_FORMAT, price);
        }
        return null;
    }

    /**
     * When this method is called before the {@link CheckoutSnippetBuilder#build()}
     * method, when doing a checkout there will be a verification
     * to see if a product has been already paid.
     *
     * @return The same {@link CheckoutSnippetBuilder} instance
     */
    public CheckoutSnippetBuilder withAlreadyPayedConfirmation() {
        alreadyPayedConfirmation = true;
        return this;
    }


    /**
     * When this method is called before the {@link CheckoutSnippetBuilder#build()}
     * method, when doing a checkout there will be Single Sign On support.
     *
     * @param pageRedirectUrl The URI to redirect to when the login is done.
     *
     * @return The same {@link CheckoutSnippetBuilder} instance
     */
    public CheckoutSnippetBuilder withSSO(String pageRedirectUrl) {
        redirectUrl = pageRedirectUrl;
        return this;
    }

    /**
     * When this method is called before the {@link CheckoutSnippetBuilder#build()}
     * method, when doing a checkout an info screen should
     * be shown to users who try to buy the same product again,
     * otherwise the user will be redirected to the product directly.
     *
     * @return The same {@link CheckoutSnippetBuilder} instance
     */
    public CheckoutSnippetBuilder withPaymentConfirmation() {
        paymentConfirmation = true;
        return this;
    }

    /**
     * When this method is called before the {@link CheckoutSnippetBuilder#build()}
     * method, when doing a checkout the generated snippet
     * will be using a cross-site request forgery (CSRF) token.
     *
     * @param token The provided CSRF token
     *
     * @return The same {@link CheckoutSnippetBuilder} instance
     */
    public CheckoutSnippetBuilder withCSRFToken(String token) {
        csrfToken = token;
        return this;
    }

    @Override
    public String toString() {
        return "CheckoutSnippetBuilder{" + "product=" + product + ", alreadyPayedConfirmation=" + alreadyPayedConfirmation
                + ", paymentConfirmation=" + paymentConfirmation + ", csrfToken='" + csrfToken + '\'' + ", redirectUrl='" + redirectUrl + '\'' + '}';
    }
}
