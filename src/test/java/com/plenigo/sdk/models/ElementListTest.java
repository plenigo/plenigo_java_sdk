package com.plenigo.sdk.models;

import com.plenigo.sdk.internal.models.PagingInfo;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertNotNull;


/**
 * <p>
 * Tests for {@link PagedList}.
 * </p>
 */
public class ElementListTest {
    @Test
    public void testToString() {
        assertNotNull(new PagedList<ProductInfo>(Collections.singletonList(new ProductInfo("prodId", "title", "desc")), new PagingInfo("", 0, 0)).toString());
    }
    @Test
    public void testWithEmptyPagingInfo() {
        assertNotNull(new PagedList<ProductInfo>(Collections.singletonList(new ProductInfo("prodId", "title", "desc")), null));
    }


    @Test
    public void testGetters() {
        PagedList<ProductInfo> pagedList = new PagedList<ProductInfo>(Collections.singletonList(new ProductInfo("prodId", "title", "desc")),
                new PagingInfo("", 0, 0));
        assertNotNull(pagedList.getList());
        assertNotNull(pagedList.getLastId());
        assertNotNull(pagedList.getPageSize());
        assertNotNull(pagedList.getTotalElements());
    }
}
