package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.util.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used to represent a transaction list.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class TransactionList extends ElementList<Transaction> {

    private Date startDate;
    private Date endDate;

    /**
     * Builds a transaction list with the provided parameters.
     *
     * @param pageNumber    page number
     * @param size          page size
     * @param totalElements total elements
     * @param elements      list of elements
     */
    public TransactionList(int pageNumber, int size, long totalElements, List<Transaction> elements) {
        super(pageNumber, size, totalElements, elements);
    }

    /**
     * Builds a transaction list with the provided parameters.
     *
     * @param pageNumber    page number
     * @param size          page size
     * @param totalElements total elements
     * @param elements      list of elements
     * @param startDate     start date
     * @param endDate       start date
     */
    public TransactionList(int pageNumber, int size, long totalElements, List<Transaction> elements, Date startDate, Date endDate) {
        super(pageNumber, size, totalElements, elements);
        this.startDate = DateUtils.copy(startDate);
        this.endDate = DateUtils.copy(endDate);
    }

    /**
     * Returns the start date.
     *
     * @return start date
     */
    public Date getStartDate() {
        return DateUtils.copy(startDate);
    }

    /**
     * Returns the end date.
     *
     * @return end date
     */
    public Date getEndDate() {
        return DateUtils.copy(endDate);
    }
}
