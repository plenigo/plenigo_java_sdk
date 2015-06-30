package com.plenigo.sdk.builders;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.models.Product;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.util.EncryptionUtils;
import com.plenigo.sdk.models.TaxType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
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
    private static final String PLENIGO_CHECKOUT_BASE_REGEX =
            "^plenigo\\.checkout\\('[\\w]+'\\);$";


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
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
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
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
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
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));

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
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
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
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
    }

    @Test
    public final void testBuildWithCategory() throws PlenigoException {
        Product product = new Product("PRODZ","TITLE","CAT_ID");
        CheckoutSnippetBuilder linkBuilder = getBuilder(product);
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
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
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
    }


    /**
     * Test Product with all the parameters.
     */
    @Test
    public final void testFilledProduct() throws PlenigoException {
        Product product = new Product(12.99, "Sample", "PROD-ID", "USD", TaxType.DOWNLOAD);
        product.setCustomPrice(true);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN");
        String builtLink = linkBuilder.build();
        assertNotNull(builtLink, "The generated link is null");
        assertTrue("The link does not match the expected regex -> "
                + builtLink, builtLink.matches(PLENIGO_CHECKOUT_BASE_REGEX));
    }

    /**
     * Test toString method
     */
    @Test
    public final void testToString() throws PlenigoException {
        Product product = new Product(12.99, "Sample", "PROD-ID", "USD", TaxType.DOWNLOAD);
        product.setCustomPrice(true);
        CheckoutSnippetBuilder linkBuilder = getBuilder(product)
                .withCSRFToken("TOKEN");
        assertNotNull(linkBuilder.toString());
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
