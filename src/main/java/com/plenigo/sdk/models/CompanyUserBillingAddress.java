package com.plenigo.sdk.models;


/**
 * <p>
 * This class is used to represent a company user billing address.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class CompanyUserBillingAddress {
    private String gender;
    private String firstName;
    private String name;
    private String company;
    private String street;
    private String additionalAddressInfo;
    private String postCode;
    private String city;
    private String state;
    private String country;
    private String vatNumber;

    /**
     * Constructor that builds an object with all the parameters.
     *
     * @param gender                gender
     * @param firstName             first name
     * @param name                  name
     * @param company               company
     * @param street                street
     * @param additionalAddressInfo additional address info
     * @param postCode              post code
     * @param city                  city
     * @param state                 state
     * @param country               country
     * @param vatNumber             vat number
     */
    public CompanyUserBillingAddress(String gender, String firstName, String name, String company, String street, String additionalAddressInfo,
                                     String postCode, String city, String state, String country, String vatNumber) {
        this.gender = gender;
        this.firstName = firstName;
        this.name = name;
        this.company = company;
        this.street = street;
        this.additionalAddressInfo = additionalAddressInfo;
        this.postCode = postCode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.vatNumber = vatNumber;
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
     * Returns the last name.
     *
     * @return last name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the company.
     *
     * @return company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns the street.
     *
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Returns additional address information.
     *
     * @return additional address information
     */
    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    /**
     * Returns the post code.
     *
     * @return post code
     */
    public String getPostCode() {
        return postCode;
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
     * Returns the vat number.
     *
     * @return vat number
     */
    public String getVatNumber() {
        return vatNumber;
    }
}
