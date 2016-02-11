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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.plenigo.sdk.internal.util.SdkUtils.buildUrlQueryString;
import static com.plenigo.sdk.internal.util.SdkUtils.getValueIfNotNull;
import static com.plenigo.sdk.internal.util.SdkUtils.toCsv;


/**
 * <p>
 * This contains the services related to user managemet with plenigo,
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public final class TransactionService {
    private static final Logger LOGGER = Logger.getLogger(TransactionService.class.getName());
    private static final String EXPECTED_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss Z";


    /**
     * Default constructor.
     */
    private TransactionService() {

    }


    public static ElementList<CompanyUser> getUserList(PageRequest request) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        ValidationUtils.validate(request);
        params.put(ApiParams.PAGE_SIZE, request.getPageSize());
        params.put(ApiParams.PAGE_NUMBER, request.getPageNumber());
        Map<String, Object> objectMap = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.COMPANY_USERS, buildUrlQueryString(params)
                , JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        return buildElementListForCompanyUsers(objectMap, request.getPageNumber());
    }


    public static List<CompanyUser> getUserList(List<String> userList) throws PlenigoException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(ApiParams.USER_IDS, toCsv(userList));
        Map<String, Object> objectMap = HttpConfig.get().getClient().get(PlenigoManager.get().getUrl(), ApiURLs.COMPANY_USERS_SELECT, buildUrlQueryString(params)
                , JWT.generateJWTTokenHeader(PlenigoManager.get().getCompanyId(), PlenigoManager.get().getSecret()));
        Object companyUsersObj = objectMap.get(ApiResults.ELEMENTS);
        return buildCompanyUserList(companyUsersObj);
    }

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
                String birthday = getValueIfNotNull(companyUser, ApiResults.BIRTHDAY);
                String postCode = getValueIfNotNull(companyUser, ApiResults.POST_CODE);
                String street = getValueIfNotNull(companyUser, ApiResults.STREET);
                String additionalAddressInfo = getValueIfNotNull(companyUser, ApiResults.ADDITIONAL_ADDRESS_INFO);
                String city = getValueIfNotNull(companyUser, ApiResults.CITY);
                String state = getValueIfNotNull(companyUser, ApiResults.STATE);
                String country = getValueIfNotNull(companyUser, ApiResults.COUNTRY);
                String agreementState = getValueIfNotNull(companyUser, ApiResults.AGREEMENT_STATE);
                CompanyUserBillingAddress billingAddress = buildBillingAddressInfo(companyUser);
                companyUsersList.add(new CompanyUser(customerId, email, userName, language, gender, firstName, name, mobileNumber, userState, birthday, postCode
                        , street, additionalAddressInfo, city, state, country, agreementState, billingAddress));
            }
        }
        return companyUsersList;
    }

    private static CompanyUserBillingAddress buildBillingAddressInfo(Map<String, Object> companyUser) {
        String gender = getValueIfNotNull(companyUser, ApiResults.GENDER);
        String firstName = getValueIfNotNull(companyUser, ApiResults.FIRST_NAME);
        String name = getValueIfNotNull(companyUser, ApiResults.LAST_NAME);
        String company = getValueIfNotNull(companyUser, ApiResults.COMPANY);
        String street = getValueIfNotNull(companyUser, ApiResults.STREET);
        String additionalAddressInfo = getValueIfNotNull(companyUser, ApiResults.ADDITIONAL_ADDRESS_INFO);
        String postCode = getValueIfNotNull(companyUser, ApiResults.POST_CODE);
        String city = getValueIfNotNull(companyUser, ApiResults.CITY);
        String state = getValueIfNotNull(companyUser, ApiResults.STATE);
        String country = getValueIfNotNull(companyUser, ApiResults.COUNTRY);
        String vatNumber = getValueIfNotNull(companyUser, ApiResults.VAT_NUMBER);
        return new CompanyUserBillingAddress(gender, firstName, name, company, street, additionalAddressInfo, postCode, city, state, country, vatNumber);
    }
}
