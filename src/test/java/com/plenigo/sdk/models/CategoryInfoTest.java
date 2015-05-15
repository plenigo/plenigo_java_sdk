package com.plenigo.sdk.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;


/**
 * <p>
 * Tests for {@link CategoryInfo}.
 * </p>
 */
public class CategoryInfoTest {
    @Test
    public void testToString() {
        assertNotNull(new CategoryInfo("catId", "title").toString());
    }

    @Test
    public void testGetters() {
        CategoryInfo categoryInfo = new CategoryInfo("catId", "title");
        assertNotNull(categoryInfo.getCategoryId());
        assertNotNull(categoryInfo.getTitle());
    }
}
