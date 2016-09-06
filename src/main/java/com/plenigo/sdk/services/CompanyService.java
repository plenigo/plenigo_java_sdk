package com.plenigo.sdk.services;

import com.plenigo.sdk.PlenigoException;
import com.plenigo.sdk.PlenigoManager;
import com.plenigo.sdk.internal.ApiParams;
import com.plenigo.sdk.internal.ApiResults;
import com.plenigo.sdk.internal.ApiURLs;
import com.plenigo.sdk.internal.util.HttpConfig;
import com.plenigo.sdk.internal.util.JWT;
import com.plenigo.sdk.internal.util.SdkUtils;
import com.plenigo.sdk.internal.util.ValidationUtils;
import com.plenigo.sdk.models.CompanyUser;
import com.plenigo.sdk.models.CompanyUserBillingAddress;
import com.plenigo.sdk.models.ElementList;
import com.plenigo.sdk.models.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.plenigo.sdk.internal.util.SdkUtils.buildUrlQueryString;
import static com.plenigo.sdk.internal.util.SdkUtils.getValueIfNotNull;
import static com.plenigo.sdk.internal.util.SdkUtils.isNotBlank;
import static com.plenigo.sdk.internal.util.SdkUtils.toCsv;


/**
 * <p>
 * This contains the services related to companies with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class CompanyService {
    private static final Logger LOGGER = Logger.getLogger(CompanyService.class.getName());
    private static final String RESPONSE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";


    /**
     * Default constructor.
     */
    private CompanyService() {

    }

    /**
     * Returns an user list based on a page request.
     *
     * @param request search criteria
     *
     * @return an element list of company users
     *
     * @throws PlenigoException if any error happens
     */
    public static ElementList<CompanyUser> getUserList(PageRequest request) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        ValidationUtils.validate(request);
        params.put(ApiParams.PAGE_SIZE, request.getPageSize());
        params.put(ApiParams.PAGE_NUMBER, request.getPageNumber());
        Map<String, Object> objectMap = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.COMPANY_USERS, ApiURLs.COMPANY_USERS,
                buildUrlQueryString(params),
                JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return buildElementListForCompanyUsers(objectMap, request.getPageNumber());
    }

    /**
     * Returns an user list based on the provided user list.
     *
     * @param userList user list to find
     *
     * @return the users found related to the company
     *
     * @throws PlenigoException if any error happens
     */
    public static List<CompanyUser> getUserList(List<String> userList) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.USER_IDS, toCsv(userList));
        Map<String, Object> objectMap = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.COMPANY_USERS_SELECT,
                ApiURLs.COMPANY_USERS_SELECT, buildUrlQueryString(params), JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(),
                        PlenigoManager.get().getSecret()));
        Object companyUsersObj = objectMap.get(ApiResults.ELEMENTS);
        return buildCompanyUserList(companyUsersObj);
    }

    /**
     * Builds an element list of company users.
     *
     * @param objectMap  the json representation
     * @param pageNumber the page number to use
     *
     * @return an element list
     */
    private static ElementList<CompanyUser> buildElementListForCompanyUsers(Map<String, Object> objectMap, int pageNumber) {
        String totalElementsStr = SdkUtils.getValueIfNotNull(objectMap, ApiResults.TOTAL_ELEMENTS);
        long totalElements = 0;
        if (!totalElementsStr.isEmpty()) {
            totalElements = Long.parseLong(totalElementsStr);
        }
        String pageSizeStr = SdkUtils.getValueIfNotNull(objectMap, ApiResults.PAGE_SIZE);
        int pageSize = 0;
        if (!pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        Object companyUsersObj = objectMap.get(ApiResults.ELEMENTS);
        List<CompanyUser> companyUsersList = buildCompanyUserList(companyUsersObj);
        return new ElementList<CompanyUser>(pageNumber, pageSize, totalElements, companyUsersList);
    }

    /**
     * Builds a list of company users from the company user json list.
     *
     * @param companyUsersObj the json list representation
     *
     * @return a list of company users
     */
    private static List<CompanyUser> buildCompanyUserList(Object companyUsersObj) {
        List<CompanyUser> companyUsersList = new LinkedList<CompanyUser>();
        if (companyUsersObj != null && companyUsersObj instanceof List) {
            List<Map<String, Object>> companyUsers = (List<Map<String, Object>>) companyUsersObj;
            for (Map<String, Object> companyUser : companyUsers) {
                String customerId = getValueIfNotNull(companyUser, ApiResults.CUST_ID);
                String email = getValueIfNotNull(companyUser, ApiResults.EMAIL);
                String userName = getValueIfNotNull(companyUser, ApiResults.USERNAME);
                String language = getValueIfNotNull(companyUser, ApiResults.LANGUAGE);
                String gender = getValueIfNotNull(companyUser, ApiResults.GENDER);
                String firstName = getValueIfNotNull(companyUser, ApiResults.FIRST_NAME);
                String name = getValueIfNotNull(companyUser, ApiResults.LAST_NAME);
                String mobileNumber = getValueIfNotNull(companyUser, ApiResults.MOBILE_NUMBER);
                String userState = getValueIfNotNull(companyUser, ApiResults.USER_STATE);
                String birthdayStr = getValueIfNotNull(companyUser, ApiResults.BIRTHDAY);
                Date birthday = null;
                if (isNotBlank(birthdayStr)) {
                    try {
                        birthday = new SimpleDateFormat(RESPONSE_DATE_FORMAT).parse(birthdayStr);
                    } catch (ParseException e) {
                        LOGGER.log(Level.SEVERE, "Could not parse the following date string: " + birthdayStr, e);
                    }
                }
                String postCode = getValueIfNotNull(companyUser, ApiResults.POST_CODE);
                String street = getValueIfNotNull(companyUser, ApiResults.STREET);
                String additionalAddressInfo = getValueIfNotNull(companyUser, ApiResults.ADDITIONAL_ADDRESS_INFO);
                String city = getValueIfNotNull(companyUser, ApiResults.CITY);
                String state = getValueIfNotNull(companyUser, ApiResults.STATE);
                String country = getValueIfNotNull(companyUser, ApiResults.COUNTRY);
                String agreementState = getValueIfNotNull(companyUser, ApiResults.AGREEMENT_STATE);
                CompanyUserBillingAddress billingAddress = buildBillingAddressInfo(companyUser);
                companyUsersList.add(new CompanyUser(customerId, email, userName, language, gender, firstName, name, mobileNumber, userState, birthday,
                        postCode, street, additionalAddressInfo, city, state, country, agreementState, billingAddress));
            }
        }
        return companyUsersList;
    }

    /**
     * Builds a billing address info object from the provided json object representation.
     *
     * @param companyUser company user json object representation
     *
     * @return a company user billing address object
     */
    private static CompanyUserBillingAddress buildBillingAddressInfo(Map<String, Object> companyUser) {
        Object billingAddresses = companyUser.get("billingAddresses");
        if (billingAddresses != null && billingAddresses instanceof List) {
            List<Map<String, Object>> billingAddresssesList = (List<Map<String, Object>>) billingAddresses;
            if (!billingAddresssesList.isEmpty()) {
                Map<String, Object> billingAddress = billingAddresssesList.get(0);
                String gender = getValueIfNotNull(billingAddress, ApiResults.GENDER);
                String firstName = getValueIfNotNull(billingAddress, ApiResults.FIRST_NAME);
                String name = getValueIfNotNull(billingAddress, ApiResults.LAST_NAME);
                String company = getValueIfNotNull(billingAddress, ApiResults.COMPANY);
                String street = getValueIfNotNull(billingAddress, ApiResults.STREET);
                String additionalAddressInfo = getValueIfNotNull(billingAddress, ApiResults.ADDITIONAL_ADDRESS_INFO);
                String postCode = getValueIfNotNull(billingAddress, ApiResults.POST_CODE);
                String city = getValueIfNotNull(billingAddress, ApiResults.CITY);
                String state = getValueIfNotNull(billingAddress, ApiResults.STATE);
                String country = getValueIfNotNull(billingAddress, ApiResults.COUNTRY);
                String vatNumber = getValueIfNotNull(billingAddress, ApiResults.VAT_NUMBER);
                return new CompanyUserBillingAddress(gender, firstName, name, company, street, additionalAddressInfo, postCode, city, state, country, vatNumber);
            }
        }
        return null;
    }
}
