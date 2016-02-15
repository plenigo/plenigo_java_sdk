package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.util.DateUtils;

import java.util.Date;

/**
 * <p>
 * This class is used to represent a company user.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class CompanyUser {
    private String customerId;
    private String email;
    private String username;
    private String language;
    private String gender;
    private String firstName;
    private String name;
    private String mobileNumber;
    private String userState;
    private Date birthday;
    private String postCode;
    private String street;
    private String additionalAddressInfo;
    private String city;
    private String state;
    private String country;
    private String agreementState;
    private CompanyUserBillingAddress companyUserBillingAddress;

    /**
     * Constructor that builds an object with all the parameters.
     *
     * @param customerId                customer id
     * @param email                     email
     * @param username                  username
     * @param language                  language
     * @param gender                    gender
     * @param firstName                 first name
     * @param name                      name
     * @param mobileNumber              mobile number
     * @param userState                 user state
     * @param birthday                  birthday
     * @param postCode                  post code
     * @param street                    street
     * @param additionalAddressInfo     additional address info
     * @param city                      city
     * @param state                     state
     * @param country                   country
     * @param agreementState            agreement state
     * @param companyUserBillingAddress company user billing address
     */
    public CompanyUser(String customerId, String email, String username, String language, String gender, String firstName, String name, String mobileNumber,
                       String userState, Date birthday, String postCode, String street, String additionalAddressInfo, String city, String state,
                       String country, String agreementState, CompanyUserBillingAddress companyUserBillingAddress) {
        this.customerId = customerId;
        this.email = email;
        this.username = username;
        this.language = language;
        this.gender = gender;
        this.firstName = firstName;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.userState = userState;
        this.birthday = DateUtils.copy(birthday);
        this.postCode = postCode;
        this.street = street;
        this.additionalAddressInfo = additionalAddressInfo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.agreementState = agreementState;
        this.companyUserBillingAddress = companyUserBillingAddress;
    }

    /**
     * Returns the customer id.
     *
     * @return customer id
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns the email.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the username.
     *
     * @return user name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the language.
     *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Returns the gender.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the first name.
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the mobile number.
     *
     * @return mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Returns the user state.
     *
     * @return user state
     */
    public String getUserState() {
        return userState;
    }

    /**
     * Returns the birthday.
     *
     * @return birthday
     */
    public Date getBirthday() {
        return DateUtils.copy(birthday);
    }

    /**
     * Returns a post code.
     *
     * @return post code.
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Returns the street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Returns teh additional address info.
     *
     * @return additional address info
     */
    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    /**
     * Returns the city.
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the state.
     *
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the country.
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the newsletter agreement state.
     *
     * @return agreement state
     */
    public String getAgreementState() {
        return agreementState;
    }

    /**
     * Returns the company user billing adress information.
     *
     * @return billing address information
     */
    public CompanyUserBillingAddress getCompanyUserBillingAddress() {
        return companyUserBillingAddress;
    }
}
