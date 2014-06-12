package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.DBankConfiguration;
import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.DBankRequest;
import com.huawei.cloud.dbank.service.DownloadService;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;

public class DBankDownloadServiceTest extends TestCase {
    public static final String APP_NAME = DBankConfiguration.getInstance().getProperty("app.name");
    public static final String APP_ID = DBankConfiguration.getInstance().getProperty("app.id");
    public static final String APP_SECRET = DBankConfiguration.getInstance().getProperty("app.secret");
    private static final Log log = LogFactory.getLog(DBankDownloadServiceTest.class);

    public void testDownload() throws DBankException {
        DownloadService downloadService = new DBankDownloadService(APP_ID, APP_NAME, APP_SECRET);
        DBankRequest request = new DBankRequest("/json.sh", new File("/Users/pingjiang/Code/svn/github/upload-sdk-src/logs/json.sh", "r"));
        //downloadService.download(request);
    }
}