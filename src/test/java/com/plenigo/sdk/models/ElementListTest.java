package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.models.PagingInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * <p>
 * Tests for {@link ElementList}.
 * </p>
 */
public class ElementListTest {
    public static final int PAGE_NUMBER = 0;
    public static final int SIZE = 1;
    public static final int TOTAL_ELEMENTS = 1;
    public static final List<String> LIST = Collections.singletonList("sample");
    ElementList<String> list;

    @Before
    public void setup(){
        list = new ElementList<String>(PAGE_NUMBER, SIZE, TOTAL_ELEMENTS, LIST);
    }

    @Test
    public void testGetPageNumber(){
        assertEquals("Page number is not equals", PAGE_NUMBER, list.getPageNumber());
    }

    @Test
    public void testGetPageSize(){
        assertEquals("Page size is not equals", SIZE, list.getSize());
    }

    @Test
    public void testGetTotalElements(){
        assertEquals("Total elements is not equals", TOTAL_ELEMENTS, list.getTotalElements());
    }

    @Test
    public void testGetElements(){
        assertEquals("Elements are not equals", LIST, list.getElements());
    }
}
