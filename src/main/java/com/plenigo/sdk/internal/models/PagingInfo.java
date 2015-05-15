package com.plenigo.sdk.internal.models;


/**
 * <p>
 * This class represents the paging information required for retrieving lists used in the plenigo API.
 * </p>
 * <p>
 * <b>IMPORTANT:</b> This class is part of the internal API, please do not use it, because it can
 * be removed in future versions of the SDK or access to such elements could
 * be changed from 'public' to 'default' or less.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is <b>not</b> thread safe.
 * </p>
 */
public class PagingInfo {
    private String lastId;
    private int pageSize;
    private int totalElements;

    /**
     * Required constructor.
     *
     * @param lastId        The last id of the returned elements
     * @param pageSize      The size of elements returned
     * @param totalElements The amount of total elements
     */
    public PagingInfo(String lastId, int pageSize, int totalElements) {
        this.lastId = lastId;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    /**
     * Retrieves the last id of the returned elements.
     *
     * @return the last id
     */
    public String getLastId() {
        return lastId;
    }


    /**
     * Retrieves the page size of the returned elements.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Retrieves the amount of total elements.
     *
     * @return the total elements
     */
    public int getTotalElements() {
        return totalElements;
    }
}
