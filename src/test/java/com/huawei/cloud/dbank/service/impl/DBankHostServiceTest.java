package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.DBankConfiguration;
import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.service.HostService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBankHostServiceTest extends TestCase {

    //public static final String APP_NAME = DBankConfiguration.getInstance().getProperty("app.name");
    public static final String APP_ID = DBankConfiguration.getInstance().getProperty("app.id");
    public static final String APP_SECRET = DBankConfiguration.getInstance().getProperty("app.secret");
    private static final Log log = LogFactory.getLog(DBankHostServiceTest.class);

    public void testGetUploadHost() throws Exception, DBankException {
        HostService hostService = new DBankHostService();
        String ipAddress = hostService.getUploadHost(APP_ID, APP_SECRET, null);
        log.debug(ipAddress);

        assertTrue(ipAddress != null && ipAddress.length() > 0);
    }
}