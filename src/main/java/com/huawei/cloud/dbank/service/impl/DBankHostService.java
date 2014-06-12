package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.DBankConfiguration;
import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.DBankResponse;
import com.huawei.cloud.dbank.RestConnector;
import com.huawei.cloud.dbank.service.DBankRestHandler;
import com.huawei.cloud.dbank.service.HostService;
import com.huawei.cloud.dbank.util.DBankUtils;
import com.huawei.cloud.dbank.util.IOUtils;
import com.huawei.cloud.dbank.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.huawei.cloud.dbank:
//            HostProvider, Utils

public class DBankHostService implements HostService, DBankRestHandler {
    private static final Log log = LogFactory.getLog(DBankHostService.class);
    private static final String DBANK_API_URL = DBankConfiguration.getInstance().getProperty("dbank.service.url");

    private String postData = null;

    public String getUploadHost(String appId, String secret, Map<String, String> params)
            throws DBankException {
        if (params == null) {
            params = new HashMap<String, String>();
            params.put("rip", "");
        }

        Map<String, String> mergedParams = DBankUtils.createParams(appId);
        DBankUtils.mergeMap(params, mergedParams, false);
        try {
            mergedParams.put("nsp_key", DBankUtils.generateNspKey(secret, mergedParams));
        } catch (UnsupportedEncodingException e) {
            throw new DBankException(e);
        }

        postData = DBankUtils.generatePostData(mergedParams);
        if (log.isDebugEnabled())
            log.debug("get upload host,data=" + postData);

        RestConnector connector = new RestConnector(DBANK_API_URL, this);

        return ((DBankResponse) connector.connect()).getFastHostIPAddress();
    }

    public void onWrite(OutputStream out) throws DBankException {
        try {
            out.write(postData.getBytes("UTF-8"));
        } catch (IOException e) {
            throw new DBankException(e);
        }
    }

    public DBankResponse onRead(int responseCode, InputStream in, int contentLength) throws DBankException {
        String content = null;
        try {
            content = IOUtils.readInputStreamToString(in, contentLength);
        } catch (IOException e) {
            throw new DBankException(e);
        }
        if (DBankHostService.log.isDebugEnabled())
            DBankHostService.log.debug("get upload host code=" + responseCode + ",body=" + content);
        if (responseCode != 200)
            throw new DBankException("can't get upload host,code=" + responseCode + ",body=" + content);
        Map result = null;
        try {
            result = JsonUtils.toMap(content);
        } catch (IOException e) {
            throw new DBankException(e);
        }
        String ip = (String) result.get("ip");
        if (ip == null || ip.isEmpty())
            throw new DBankException("can't get upload host,code=" + responseCode + ",body=" + content);

        DBankResponse response = new DBankResponse();
        response.setFastHostIPAddress(ip);
        return response;
    }
}