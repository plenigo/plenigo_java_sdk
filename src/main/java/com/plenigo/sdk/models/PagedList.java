package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.models.PagingInfo;

import java.util.List;

/**
 * <p>
 * This object represents a paged list.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 *
 * @param <E> The type of list to be used
 */
public class PagedList<E> {
    private List<E> list;
    private PagingInfo pagingInfo;

    /**
     * Required constructor.
     *
     * @param list       The paged list
     * @param pagingInfo The paging information
     */
    public PagedList(List<E> list, PagingInfo pagingInfo) {
        this.list = list;
        this.pagingInfo = pagingInfo;
        if (pagingInfo == null) {
            this.pagingInfo = new PagingInfo("", 0, 0);
        }
    }

    /**
     * Retrieves the list of paged elements.
     *
     * @return a list of elements
     */
    public List<E> getList() {
        return list;
    }


    /**
     * The last id for further paged requests.
     *
     * @return The last id
     */
    public String getLastId() {
        return pagingInfo.getLastId();
    }

    /**
     * The amount of returned elements.
     *
     * @return the amount of returned elements
     */
    public int getPageSize() {
        return pagingInfo.getPageSize();
    }

    /**
     * Amout of total elements.
     *
     * @return The total elements
     */
    public int getTotalElements() {
        return pagingInfo.getTotalElements();
    }

    @Override
    public String toString() {
        return "PagedList{" + "list=" + list + ", pagingInfo=" + pagingInfo + '}';
    }
}
