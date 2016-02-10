package com.plenigo.sdk.models;

import java.util.List;

/**
 * Created by rtorres on 10/02/16.
 */
public class ElementList<E> {
    private int page;
    private int size;
    private List<E> elements;

    public ElementList(int page, int size, List<E> elements) {
        this.page = page;
        this.size = size;
        this.elements = elements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<E> getElements() {
        return elements;
    }
}
