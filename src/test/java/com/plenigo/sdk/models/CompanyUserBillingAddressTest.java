package com.plenigo.sdk.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p>
 * Tests for {@link CompanyUserBillingAddress}.
 * </p>
 */
public class CompanyUserBillingAddressTest {
    public static final String GENDER = "MALE";
    public static final String FIRST_NAME = "Jonas";
    public static final String NAME = "Bishop";
    public static final String COMPANY = "US Company";
    public static final String STREET = "Street 5";
    public static final String ADDITIONAL_ADDRESS_INFO = "Near Jonas Street";
    public static final String POST_CODE = "00101";
    public static final String CITY = "New York";
    public static final String STATE = "New York";
    public static final String COUNTRY = "United States";
    public static final String VAT_NUMBER = "090123123";
    CompanyUserBillingAddress companyUserBillingAddress;

    @Before
    public void setup() {
        companyUserBillingAddress = new CompanyUserBillingAddress(GENDER, FIRST_NAME, NAME, COMPANY, STREET, ADDITIONAL_ADDRESS_INFO, POST_CODE,
                CITY, STATE, COUNTRY, VAT_NUMBER);
    }

    @Test
    public void testGetGender() {
        assertEquals("Customer id is not equals", GENDER, companyUserBillingAddress.getGender());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("First name is not equals", FIRST_NAME, companyUserBillingAddress.getFirstName());
    }

    @Test
    public void testGetName() {
        assertEquals("Name is not equals", NAME, companyUserBillingAddress.getName());
    }

    @Test
    public void testGetCompany() {
        assertEquals("Company is not equals", COMPANY, companyUserBillingAddress.getCompany());
    }

    @Test
    public void testGetStreet() {
        assertEquals("Street is not equals", STREET, companyUserBillingAddress.getStreet());
    }

    @Test
    public void testGetAdditionalAddress() {
        assertEquals("Additional address is not equals", ADDITIONAL_ADDRESS_INFO, companyUserBillingAddress.getAdditionalAddressInfo());
    }

    @Test
    public void testGetPostCode() {
        assertEquals("Post code is not equals", POST_CODE, companyUserBillingAddress.getPostCode());
    }

    @Test
    public void testGetCity() {
        assertEquals("City is not equals", CITY, companyUserBillingAddress.getCity());
    }

    @Test
    public void testGetState() {
        assertEquals("State is not equals", STATE, companyUserBillingAddress.getState());
    }

    @Test
    public void testGetCountry() {
        assertEquals("Country is not equals", COUNTRY, companyUserBillingAddress.getCountry());
    }

    @Test
    public void testGetVatNumber() {
        assertEquals("Vat Number is not equals", VAT_NUMBER, companyUserBillingAddress.getVatNumber());
    }
}
