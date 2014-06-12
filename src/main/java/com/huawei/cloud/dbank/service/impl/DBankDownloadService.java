package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.*;
import com.huawei.cloud.dbank.service.DBankRestHandler;
import com.huawei.cloud.dbank.service.DownloadService;
import com.huawei.cloud.dbank.util.CryptUtils;
import com.huawei.cloud.dbank.util.DBankUtils;
import com.huawei.cloud.dbank.util.IOUtils;
import com.huawei.cloud.dbank.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pingjiang on 14-6-12.
 */
public class DBankDownloadService implements DownloadService, DBankRestHandler {
    private static final Log log = LogFactory.getLog(DBankDownloadService.class);
    private final String appId;
    private final String appName;
    private final String secret;
    private File localFile;

    public DBankDownloadService(String appId, String appName, String secret) {
        this.appId = appId;
        this.appName = appName;
        this.secret = secret;
    }

    /**
     * 下载URL如果设置API授权，URL的拼接规则
     1. 签名之后的url为： http://{appname}.dbankcloud.com/{path}/{filename}?ts=1302392423&key=xxxxxxxxx
     2. ts ： 过期时间，1970/1/1开始计算的秒数，时间需要与时间服务器时间同步。
     3. key = substr(0, 8, hmac_sha1("/dl/to_lowcase({appname})/{path}/{filename}?ts=1302392423", appsecret))，appsecret为申请云加速时分配的appID对应的密钥。
     * @return
     */
    public String generateKey(String path, long ts) {
        String s = "/dl/" + appName.toLowerCase() + (path.startsWith("/") ? path : ("/" + path)) + "?ts=" + ts;
        String c = CryptUtils.hashMac(s, secret);
        return c.substring(0, 8);
    }

    public void download(DBankRequest request) throws DBankException {
        long ts = DBankUtils.nowSeconds();
        String key = generateKey(request.getPath(), ts);
        String url = request.getDownloadURL(appName, ts, key);
        localFile = request.getLocalFile();

        log.debug("Download URL: " + url);
        RestConnector connector = new RestConnector(url, "GET", this);
        connector.connect();
    }

    public void onWrite(OutputStream out) throws DBankException {
        log.debug("write into connection ...");
    }

    public Object onRead(int responseCode, InputStream in, int contentLength) throws DBankException {
        log.debug("read from connection code=" + responseCode + " ...");
        try {
            IOUtils.writeInputStreamToFile(localFile, in, contentLength);
        } catch (IOException e) {
            throw new DBankException(e);
        }

        return null;
    }
}
