package com.plenigo.sdk.models;

/**
 * Created by rtorres on 03/02/16.
 */
public class CompanyUser {
    private String userId;
    private String email;
    private String username;
    private String language;
    private String gender;
    private String firstName;
    private String name;
    private String mobileNumber;
    private String userState;
    private String birthday;
    private String postCode;
    private String street;
    private String additionalAddressInfo;
    private String city;
    private String state;
    private String country;
    private String agreementState;
    private CompanyUserBillingAddress companyUserBillingAddress;

    public CompanyUser(String userId, String email, String username, String language, String gender, String firstName, String name, String mobileNumber, String userState, String birthday, String postCode, String street, String additionalAddressInfo, String city, String state, String country, String agreementState, CompanyUserBillingAddress companyUserBillingAddress) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.language = language;
        this.gender = gender;
        this.firstName = firstName;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.userState = userState;
        this.birthday = birthday;
        this.postCode = postCode;
        this.street = street;
        this.additionalAddressInfo = additionalAddressInfo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.agreementState = agreementState;
        this.companyUserBillingAddress = companyUserBillingAddress;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getLanguage() {
        return language;
    }

    public String getGender() {
        return gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getUserState() {
        return userState;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getStreet() {
        return street;
    }

    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getAgreementState() {
        return agreementState;
    }

    public CompanyUserBillingAddress getCompanyUserBillingAddress() {
        return companyUserBillingAddress;
    }
}
