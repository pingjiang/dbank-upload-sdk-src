package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.DBankConfiguration;
import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.DBankRequest;
import com.huawei.cloud.dbank.service.UploadService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

public class DBankUploadServiceTest extends TestCase {
    public static final String APP_NAME = DBankConfiguration.getInstance().getProperty("app.name");
    public static final String APP_ID = DBankConfiguration.getInstance().getProperty("app.id");
    public static final String APP_SECRET = DBankConfiguration.getInstance().getProperty("app.secret");
    private static final Log log = LogFactory.getLog(DBankUploadServiceTest.class);

    public void testUpload() throws IOException, DBankException {
        UploadService cloud = new DBankUploadService(APP_ID, APP_NAME, APP_SECRET);

        // file -> /test/file
        // http://opendocs.dbankcloud.com/json.sh
        DBankRequest request = new DBankRequest(cloud.getFullPath("/test/",
                DBankConfiguration.getInstance().getProperty("test.local.file.name") + "3"),
                new File(DBankConfiguration.getInstance().getProperty("test.local.file.url")));

        cloud.upload(request);
    }

    public void testGetFullPath() throws IOException, DBankException {
        UploadService cloud = new DBankUploadService(APP_ID, APP_NAME, APP_SECRET);

        assertEquals("/dl/opendocs", cloud.getBasePath());
        assertEquals("/dl/opendocs/test", cloud.getFullPath("test"));
        assertEquals("/dl/opendocs/test", cloud.getFullPath("/test"));
        assertEquals("/dl/opendocs/a/b", cloud.getFullPath("/a", "b"));
        assertEquals("/dl/opendocs/a/b", cloud.getFullPath("/a", "/b"));
        assertEquals("/dl/opendocs/a/b", cloud.getFullPath("/a/", "/b"));
    }
}