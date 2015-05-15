package com.plenigo.sdk.internal.models;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * <p>
 * Tests for {@link Configuration}.
 * </p>
 */
public class ConfigurationTest {

    @Test
    public void testToString() {
        Configuration config = new Configuration();
        config.setCompanyId("compId");
        config.setSecret("secret");
        config.setTestMode(true);
        config.setUrl("sampleUrl");
        assertNotNull(config.toString());
    }
}
