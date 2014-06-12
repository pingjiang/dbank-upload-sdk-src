package com.huawei.cloud.dbank;

import junit.framework.TestCase;

public class DBankConfigurationTest extends TestCase {

    public void testGetProperty() throws Exception {
        assertEquals("aopenworld@163.com", DBankConfiguration.getInstance().getProperty("user.name"));
        assertEquals("opendocs", DBankConfiguration.getInstance().getProperty("app.name"));
        assertEquals("SPEEDUP-UPLOAD-SDK 1.0.0", DBankConfiguration.getInstance().getProperty("dbank.USER_AGENT"));
    }

    public void testGetIntProperty() throws Exception {
        assertEquals(5, DBankConfiguration.getInstance().getIntProperty("user.throughout", 0));
        assertEquals(0x100001, DBankConfiguration.getInstance().getIntProperty("dbank.SAMPLING_SIZE", 0));
        assertEquals(15000, DBankConfiguration.getInstance().getIntProperty("dbank.connection.timeout", 15));
    }
}