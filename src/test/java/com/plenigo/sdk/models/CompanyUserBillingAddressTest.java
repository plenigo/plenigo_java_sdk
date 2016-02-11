package com.plenigo.sdk.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Tests for {@link CompanyUser}.
 * </p>
 */
public class CompanyUserBillingAddressTest {

    public static final String CUSTOMER_ID = "customerId";
    public static final String EMAIL = "email@example.com";
    public static final String USERNAME = "sampleuser";
    public static final String LANGUAGE = "en";
    public static final String GENDER = "MALE";
    public static final String FIRST_NAME = "john";
    public static final String NAME = "powers";
    public static final String MOBILE_NUMBER = "0011234123";
    public static final String USER_STATE = "ACTIVE";
    public static final Date BIRTHDAY = new Date();
    public static final String POST_CODE = "00123";
    public static final String STREET = "Sam Street";
    public static final String ADDITIONAL_ADDRESS_INFO = "Near Joseph Street";
    public static final String CITY = "New York";
    public static final String STATE = "New York";
    public static final String COUNTRY = "United States";
    public static final String AGREEMENT_STATE = "AGREE";
    public static final CompanyUserBillingAddress COMPANY_USER_BILLING_ADDRESS = new CompanyUserBillingAddress("MALE", "Jonas", "Bishop", "US Company", "Street 5", "Near Jonas Street", "00101", "New York", "New York",
            "United States", "012312309");
    CompanyUser companyUser;

    @Before
    public void setup(){
        companyUser = new CompanyUser(CUSTOMER_ID, EMAIL, USERNAME, LANGUAGE, GENDER, FIRST_NAME, NAME, MOBILE_NUMBER, USER_STATE,
                BIRTHDAY, POST_CODE, STREET, ADDITIONAL_ADDRESS_INFO, CITY, STATE, COUNTRY, AGREEMENT_STATE,
                COMPANY_USER_BILLING_ADDRESS);
    }

    @Test
    public void testGetCustomerId(){
        assertEquals("Customer id is not equals", CUSTOMER_ID, companyUser.getCustomerId());
    }

    @Test
    public void testGetEmail(){
        assertEquals("Email is not equals", EMAIL, companyUser.getEmail());
    }

    @Test
    public void testGetUsername(){
        assertEquals("Username is not equals", USERNAME, companyUser.getUsername());
    }


    @Test
    public void testGetLanguage(){
        assertEquals("Language is not equals", LANGUAGE, companyUser.getLanguage());
    }


    @Test
    public void testGetGender(){
        assertEquals("Gender is not equals", GENDER, companyUser.getGender());
    }

    @Test
    public void testGetFirstName(){
        assertEquals("First name is not equals", FIRST_NAME, companyUser.getFirstName());
    }

    @Test
    public void testGetName(){
        assertEquals("Name is not equals", NAME, companyUser.getName());
    }

    @Test
    public void testGetMobileNumber(){
        assertEquals("Mobile Number is not equals", MOBILE_NUMBER, companyUser.getMobileNumber());
    }

    @Test
    public void testGetUserState(){
        assertEquals("User state is not equals", USER_STATE, companyUser.getUserState());
    }

    @Test
    public void testGetBirthday(){
        assertEquals("Birthday is not equals", BIRTHDAY, companyUser.getBirthday());
    }

    @Test
    public void testGetPostCode(){
        assertEquals("Post code is not equals", POST_CODE, companyUser.getPostCode());
    }

    @Test
    public void testGetStreet(){
        assertEquals("Street is not equals", STREET, companyUser.getStreet());
    }

    @Test
    public void testGetAdditionalAddressInfo(){
        assertEquals("Additional address info is not equals", ADDITIONAL_ADDRESS_INFO, companyUser.getAdditionalAddressInfo());
    }

    @Test
    public void testGetCity(){
        assertEquals("City is not equals", CITY, companyUser.getCity());
    }

    @Test
    public void testGetState(){
        assertEquals("State is not equals", STATE, companyUser.getState());
    }

    @Test
    public void testGetCountry(){
        assertEquals("Country is not equals", COUNTRY, companyUser.getCountry());
    }

    @Test
    public void testGetAgreementState(){
        assertEquals("Agreement state is not equals", AGREEMENT_STATE, companyUser.getAgreementState());
    }

    @Test
    public void testGetCompanyUserBillingAddress(){
        assertEquals("Company user billing address is not equals", COMPANY_USER_BILLING_ADDRESS, companyUser.getCompanyUserBillingAddress());
    }
}
