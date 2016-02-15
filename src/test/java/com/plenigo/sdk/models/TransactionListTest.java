package com.plenigo.sdk.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * <p>
 * Tests for {@link ElementList}.
 * </p>
 */
public class TransactionListTest {
    public static final int PAGE_NUMBER = 0;
    public static final int SIZE = 1;
    public static final int TOTAL_ELEMENTS = 1;
    public static final Transaction TRANSACTION = new Transaction("id", "custId", "p", "t", 1.00, 2.00, 3.00, "DE", "USD", null,
            new Date(), null, 2.00, 3.00, 4.00, 123L, "1235D", "12354A");
    public static final List<Transaction> LIST = Collections.singletonList(TRANSACTION);
    public static final Date START_DATE = new Date();
    public static final Date END_DATE = new Date();
    TransactionList list;

    @Before
    public void setup(){
        list = new TransactionList(PAGE_NUMBER, SIZE, TOTAL_ELEMENTS, LIST, START_DATE, END_DATE);
    }

    @Test
    public void testGetStartDate(){
        assertEquals("Start dateis not equals", START_DATE, list.getStartDate());
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
