package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.*;
import com.huawei.cloud.dbank.service.DBankRestHandler;
import com.huawei.cloud.dbank.service.FSService;
import com.huawei.cloud.dbank.util.DBankUtils;
import com.huawei.cloud.dbank.util.IOUtils;
import com.huawei.cloud.dbank.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pingjiang on 14-6-12.
 */
public class DBankFSService implements FSService, DBankRestHandler {

    private static final Log log = LogFactory.getLog(DBankUtils.class);
    public static final String DBANK_URL = "http://api.dbank.com/rest.php";
    private final String appId;
    private String access_token;
    private String requestBody = null;
    private String serviceName;

    public DBankFSService(String appId) {
        this.appId = appId;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public FSResponse handle(String serviceName, FSRequest request) throws DBankException {
        this.serviceName = serviceName;

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("nsp_svc", serviceName);
        headers.put("nsp_app", appId);
        headers.put("access_token", access_token);
        headers.put("nsp_ts", String.valueOf(System.currentTimeMillis() / 1000L));
        headers.put("nsp_fmt", "JSON");

        if (request.needReserve()) {
            headers.put("reserve", "true");
        }

        try {
            if (request.hasFields()) {
                headers.put("fields", URLEncoder.encode(DBankUtils.arrayToString(request.getFields()), "UTF-8"));
            }

            if (request.hasFiles()) {
                headers.put("files", URLEncoder.encode(DBankUtils.arrayToString(request.getFiles()), "UTF-8"));
            }

            if (request.getPath() != null) {
                headers.put("path", URLEncoder.encode(request.getPath(), "UTF-8"));
            }

            requestBody = DBankUtils.encodeHeaders(headers, false);
        } catch (UnsupportedEncodingException e) {
            throw new DBankException(e);
        }

        RestConnector connector = new RestConnector(DBANK_URL, "POST", this);
        return (FSResponse)connector.connect();
    }

    public FSResponse listDir(String path, String[] fileds, ListOption options) throws DBankException {
        FSRequest request = new FSRequest();
        request.setPath(path);
        request.setFields(fileds);
        request.setListOptions(options);

        return handle("nsp.vfs.lsdir", request);
    }

    public FSResponse copyFile(String[] files, String path, Map<String, String> attribute) throws DBankException {
        FSRequest request = new FSRequest();
        request.setPath(path);
        request.setFiles(files);
        request.setAttributes(attribute);

        return handle("nsp.vfs.copyfile", request);
    }

    public FSResponse moveFile(String[] files, String path, Map<String, String> attribute) throws DBankException {
        FSRequest request = new FSRequest();
        request.setPath(path);
        request.setFiles(files);
        request.setAttributes(attribute);

        return handle("nsp.vfs.movefile", request);
    }

    public FSResponse removeFile(String[] files, boolean reserve) throws DBankException {
        FSRequest request = new FSRequest();
        request.setFiles(files);
        request.setReserve(reserve);

        return handle("nsp.vfs.rmfile", request);
    }

    public FSResponse getAttr(String[] files, String[] fields) throws DBankException {
        FSRequest request = new FSRequest();
        request.setFiles(files);
        request.setFields(fields);

        return handle("nsp.vfs.getattr", request);
    }

    public FSResponse setAttr(Map<String, DBankFile> files) throws DBankException {
        FSRequest request = new FSRequest();
        request.setDbankFiles(files);

        return handle("nsp.vfs.setattr", request);
    }

    public void onWrite(OutputStream out) throws DBankException {
        log.debug("Write into connection ...");

        if (requestBody != null) {
            try {
                log.debug("Encoded request body: " + requestBody);
                out.write(requestBody.getBytes("UTF-8"));
            } catch (IOException e) {
                throw new DBankException(e);
            }
        }
    }

    public Object onRead(int responseCode, InputStream in, int contentLength) throws DBankException {
        log.debug("Read from connection ...");

        String content = null;
        try {
            content = IOUtils.readInputStreamToString(in, contentLength);
        } catch (IOException e) {
            throw new DBankException(e);
        }
        if (DBankFSService.log.isDebugEnabled())
            DBankFSService.log.debug("FS response code=" + responseCode + ",body=" + content);

        FSResponse response = new FSResponse();
        try {
            Map jsonResponse = JsonUtils.toMap(content);
            String errorMsg = (jsonResponse == null) ? "null json response object" : (String) jsonResponse.get("error");
            if (errorMsg != null) {
                throw new DBankException(errorMsg);
            }
        } catch (IOException e) {
            throw new DBankException(e);
        }

        return response;
    }
}
