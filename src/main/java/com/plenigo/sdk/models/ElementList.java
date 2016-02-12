package com.plenigo.sdk.models;

import java.util.List;


/**
 * <p>
 * This class is used to represent an elements list.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 *
 * @param <E> type of list to support
 */
public class ElementList<E> {
    private int pageNumber;
    private int size;
    private long totalElements;
    private List<E> elements;

    /**
     * Builds an element list with the provided parameters.
     *
     * @param pageNumber    page number
     * @param size          page size
     * @param totalElements total amount of elements
     * @param elements      list of elements
     */
    public ElementList(int pageNumber, int size, long totalElements, List<E> elements) {
        this.pageNumber = pageNumber;
        this.size = size;
        this.totalElements = totalElements;
        this.elements = elements;
    }

    /**
     * Returns the page number.
     *
     * @return page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Returns the page size.
     *
     * @return page size
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the list of elements.
     *
     * @return elements
     */
    public List<E> getElements() {
        return elements;
    }

    /**
     * Return the total amount of elements.
     *
     * @return total amount of elements
     */
    public long getTotalElements() {
        return totalElements;
    }
}
