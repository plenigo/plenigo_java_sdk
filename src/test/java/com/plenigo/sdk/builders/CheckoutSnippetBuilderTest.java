package com.plenigo.sdk.builders;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.models.Product;
import com.plenigo.sdk.models.ProductType;
import com.plenigo.sdk.models.TaxType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.SuppressCode.suppressConstructor;

/**
 * <p>
 * Tests for {@link CheckoutSnippetBuilder}.
 * </p>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PlenigoManager.class, EncryptionUtils.class})
public class CheckoutSnippetBuilderTest {

    /**
     * The base link that should be formed after
     * the user calls the {@link CheckoutSnippetBuilder#build()}
     * method.
     */
    private static final String PLENIGO_CHECKOUT_REGEX =
            "^plenigo\\.checkout\\('[\\w]+','[\\w]+','[\\w]+','[\\w]+','[\\w]+','[\\w]+'\\);$";

    private static final String PLENIGO_CHECKOUT_REGEX_NULL =
            "^plenigo\\.checkout\\('[\\w]+',[\\w]+,[\\w]+,[\\w]+,[\\w]+,[\\w]+\\);$";

    private static final String PLENIGO_CHECKOUT_REMOTE_REGEX =
            "^plenigo\\.checkoutWithRemoteLogin\\('[\\w]+','[\\w]+','[\\w]+','[\\w]+','[\\w]+','[\\w]+'\\);$";


    /**
     * Returns a mocked builder since the Encryption is not necessary for these unit tests.
     *
     * @param product The product to use for creating the builder.
     *
     * @return The builder to test
     */
    private CheckoutSnippetBuilder getBuilder(Product product) throws PlenigoException {
        mockUtilities();
        return new CheckoutSnippetBuilder(product);
    }

    private void mockUtilities() throws PlenigoException {
        suppressConstructor(EncryptionUtils.class);
        mockStatic(EncryptionUtils.class);
        EncryptionUtils mockSingleton = PowerMockito.mock(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.encryptWithAES(anyString(), anyString())).thenReturn("HASH");
    }

    /**
     * Test a successful case for {@link CheckoutSnippetBuilder#build()}.
     */
    @Test
    public final void testBuildSuccessfulCase() throws PlenigoException {
        Product product = new Product("PRODZ");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }

    /**
     * Test method for
     * {@link CheckoutSnippetBuilder#buildEncodedData()}
     * .
     */
    @Test
    public final void testBuildEncodedData() throws PlenigoException {
        String prodId = "PROD-1";
        Product product = new Product(prodId);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product);
        String encodedData = ReflectionTestUtils.invokeMethod(linkBuilder, "buildEncodedData");
        assertNotNull(encodedData);
    }

    /**
     * Test method for
     * {@link CheckoutSnippetBuilder
     * #toMap(com.plenigo.sdk.Checkout)}
     * .
     */
    @Test
    public final void testToMap() throws PlenigoException {
        Map<String, Object> expectedMap = new LinkedHashMap<String, Object>();
        String prodId = "PROD-1";
        mockStatic(PlenigoManager.class);
        PlenigoManager mockSingleton = PowerMockito.mock(PlenigoManager.class);
        PowerMockito.when(PlenigoManager.get()).thenReturn(mockSingleton);
        PowerMockito.when(mockSingleton.isTestMode()).thenReturn(true);
        expectedMap.put(ApiParams.PROD_ID, prodId);
        expectedMap.put(ApiParams.TEST_TRANSACTION, true);
        Product product = new Product(prodId);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product);
        Map<String, Object> map = ReflectionTestUtils.invokeMethod(linkBuilder, "convertToMap");
        assertEquals(expectedMap, map);
    }

    /**
     * Test method for
     * {@link CheckoutSnippetBuilder
     * #withAlreadyPayedConfirmation()}
     * .
     */
    @Test
    public final void testBuildWithAlreadyPayedConfirmation() throws PlenigoException {
        Product product = new Product("PRODZ");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withAlreadyPayedConfirmation();
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }

    /**
     * Test method for
     * {@link CheckoutSnippetBuilder
     * #withPaymentConfirmation()}
     * .
     */
    @Test
    public final void testBuildWithPaymentConfirmation() throws PlenigoException {
        Product product = new Product("PRODZ");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withPaymentConfirmation();
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));

    }

    /**
     * Test method for
     * {@link CheckoutSnippetBuilder
     * #withSSO()}
     * .
     */
    @Test
    public final void testBuildWithSSOFlag() throws PlenigoException {
        Product product = new Product("PRODZ");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withSSO("http://sample");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }


    /**
     * Test method for
     * {@link CheckoutSnippetBuilder
     * #withCSRF()}.
     */
    @Test
    public final void testBuildWithCSRFlag() throws PlenigoException {
        Product product = new Product("PRODZ");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }

    @Test
    public final void testBuildWithCategory() throws PlenigoException {
        Product product = new Product("PRODZ", "TITLE", "CAT_ID");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }


    @Test
    public final void testBuildWithCategorySetter() throws PlenigoException {
        Product product = new Product("PRODZ");
        product.setTitle("title");
        product.setCategoryId("CAT_ID");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }


    /**
     * Test Product with all the parameters.
     */
    @Test
    public final void testFilledProduct() throws PlenigoException {
        Product product = getProduct();
        product.setCustomPrice(true);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }


    /**
     * Test Product with all the parameters.
     */
    @Test
    public final void testValidShippingCost() throws PlenigoException {
        Product product = getProduct();
        product.setCustomPrice(true);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN").withShipping(BigDecimal.TEN, ProductType.BOOK);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }

    /**
     * Builds a product for test purposes.
     *
     * @return a product
     */
    private Product getProduct() {
        return new Product(12.99, "Sample", "PROD-ID", "USD", TaxType.DOWNLOAD);
    }


    /**
     * Test Product with all the parameters.
     */
    @Test(expected = PlenigoException.class)
    public final void testInvalidShippingCost() throws PlenigoException {
        Product product = getProduct();
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN").withShipping(BigDecimal.TEN, ProductType.VIDEO);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX));
    }

    @Test
    public final void testOverrideMode() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN").withOverrideMode();
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }

    @Test(expected = PlenigoException.class)
    public final void testOverrideModeWithoutPrice() throws PlenigoException {
        Product product = new Product("id");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product).withOverrideMode();
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX));
    }

    /**
     * Test toString method
     */
    @Test
    public final void testToString() throws PlenigoException {
        Product product = getProduct();
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN");
        assertNotNull(linkBuilder.toString());
    }

    @Test
    public final void testLoginToken() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withLoginToken("loginToken");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches("^plenigo\\.checkoutWithRemoteLogin\\('[\\w]+','[\\w]+',[\\w]+,[\\w]+,[\\w]+,[\\w]+\\);$"));
    }

    @Test
    public final void testLoginTokenWithEmptyToken() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withLoginToken("");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_REGEX_NULL));
    }

    @Test
    public final void testSourceUrl() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withSourceUrl("sourceUrl");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches("^plenigo\\.checkout\\('[\\w]+',[\\w]+,'[\\w]+',[\\w]+,[\\w]+,[\\w]+\\);$"));
    }

    @Test
    public final void testTargetUrl() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withTargetUrl("targetUrl");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches("^plenigo\\.checkout\\('[\\w]+',[\\w]+,[\\w]+,'[\\w]+',[\\w]+,[\\w]+\\);$"));
    }

    @Test
    public final void testAffiliateId() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withAffiliateId("affiliateId");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches("^plenigo\\.checkout\\('[\\w]+',[\\w]+,[\\w]+,[\\w]+,'[\\w]+',[\\w]+\\);$"));
    }

    @Test
    public final void testElementId() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withElementId("elementId");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches("^plenigo\\.checkout\\('[\\w]+',[\\w]+,[\\w]+,[\\w]+,[\\w]+,'[\\w]+'\\);$"));
    }

    @Test
    public final void testStartWithRegistration() throws PlenigoException {
        Product product = getProduct();
        product.setPrice(15.00);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withStartWithRegistration(true);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches("^plenigo\\.checkout\\('[\\w]+','[\\w]+',[\\w]+,[\\w]+,[\\w]+,[\\w]+\\);$"));
    }

    /**
     * Tests the default constructor
     */
    @Test
    public final void testNoArgsConstructor() throws PlenigoException {
        mockUtilities();
        assertNotNull(new CheckoutSnippetBuilder().build());
    }
}
