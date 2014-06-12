package com.huawei.cloud.dbank.service.impl;

import com.huawei.cloud.dbank.*;
import com.huawei.cloud.dbank.service.DBankRestHandler;
import com.huawei.cloud.dbank.service.HostService;
import com.huawei.cloud.dbank.service.UploadService;
import com.huawei.cloud.dbank.util.DBankUtils;
import com.huawei.cloud.dbank.util.IOUtils;
import com.huawei.cloud.dbank.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Map;

// Referenced classes of package com.huawei.cloud.dbank:
//            DefaultHostProvider, Utils, HostProvider, UploadStatus

public class DBankUploadService implements DBankRestHandler, UploadService {
    private static final Log log = LogFactory.getLog(DBankUploadService.class);
    //private static final String USER_AGENT = DBankConfiguration.getInstance().getProperty("dbank.USER_AGENT");
    //private static final int UPLOAD_STATUS_NEED_ALL = DBankConfiguration.getInstance().getIntProperty("dbank.UPLOAD_STATUS_NEED_ALL", 0);
    //private static final int UPLOAD_STATUS_NEED_PARTIAL = DBankConfiguration.getInstance().getIntProperty("dbank.UPLOAD_STATUS_NEED_PARTIAL", 0);
    //private static final int SEGMENT_SIZE = DBankConfiguration.getInstance().getIntProperty("dbank.SEGMENT_SIZE", 0);
    //private static final int SAMPLING_SIZE = DBankConfiguration.getInstance().getIntProperty("dbank.SAMPLING_SIZE", 0);
    //private static String DBANK_API_HOST = DBankConfiguration.getInstance().getProperty("dbank.api.host");

    private final HostService hostService;
    private final String appId;
    private final String appName;
    private final String secret;
    private int maxRetryTimes;
    private File file;
    private long offset = -1;
    private long length = 0;

    /**
     * 初始化应用信息
     *
     * @param appId
     * @param appName
     * @param secret
     */
    public DBankUploadService(String appId, String appName, String secret) {
        hostService = new DBankHostService();
        maxRetryTimes = 4;

        this.appId = appId;
        this.appName = appName;
        this.secret = secret;
    }

    public String getBasePath() {
        return "/dl/" + this.appName;
    }

    public String getFullPath(String... items) {
        return IOUtils.joinPaths(getBasePath(), items);
    }

    public String getMethod() {
        return "PUT";
    }

    /**
     * 上传文件
     *
     * @throws DBankException
     */
    public void upload(DBankRequest request) throws DBankException {
        // 验证参数
        if (!request.isValid(true)) {
            throw new DBankException("Invalid parameters for upload");
        }
        file = request.getLocalFile();

        // 获取最快的主机
        request.setHost(hostService.getUploadHost(appId, secret, null));

        offset = doInit(request);

        if (offset == -1L) {
            log.info("hit fast upload.");
            return;
        }

        int retryTimes = 0;
        do {
            if (offset == -1L)
                break;
            length = request.getFileLength() - offset;
            if (length > 0x500000L)
                length = 0x500000L;
            if (length <= 0L)
                break;
            if (log.isInfoEnabled())
                log.info("upload file=" + file.getAbsoluteFile() + ",offset=" + offset + ",length=" + length);
            try {
                offset = doUpload(request);
            } catch (DBankException e) {
                log.error("upload failed.", e);
                retryTimes++;
            }
        } while (retryTimes <= maxRetryTimes);
        log.info("upload completed");
    }

    private long doInit(DBankRequest request)
            throws DBankException {
        Map<String, String> initHeaders = request.createHeaders(false, 0, 0);
        DBankUtils.buildSig(request.getInitPath(), initHeaders, secret, getMethod());

        RestConnector connector = new RestConnector(request.getInitURL(), getMethod(), this);
        connector.setRequestProperties(initHeaders);

        return ((DBankResponse) connector.connect()).getErrorCodeLong();
    }

    /**
     * 处理上传
     *
     * @return
     * @throws DBankException
     * @throws UnsupportedEncodingException
     */
    private long doUpload(DBankRequest request)
            throws DBankException {
        String path = request.getPath();
        final File file = request.getLocalFile();
        long fileLength = request.getFileLength();

        Map<String, String> headers = request.createHeaders(true, offset, (int) length);
        headers.put("nsp-content-range", String.format("%d-%d/%d", offset, offset + (long) length - 1L, fileLength));
        DBankUtils.buildSig(path, headers, secret, getMethod());

        RestConnector connector = new RestConnector(request.getURL(), getMethod(), this);
        connector.setRequestProperties(headers);

        return ((DBankResponse) connector.connect()).getErrorCodeLong();
    }

    /**
     * 设置重试次数
     *
     * @param maxRetryTimes
     */
    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public void onWrite(OutputStream out) throws DBankException {
        log.debug("write into connection ...");

        if (length <= 0) {
            return;
        }

        try {
            IOUtils.copyFile(file, offset, (int) length, out);
        } catch (IOException e) {
            throw new DBankException(e);
        }
    }

    public DBankResponse onRead(int responseCode, InputStream in, int contentLength) throws DBankException {
        log.debug("read from connection ...");

        String content = null;
        try {
            content = IOUtils.readInputStreamToString(in, contentLength);
        } catch (IOException e) {
            throw new DBankException(e);
        }
        if (DBankUploadService.log.isDebugEnabled())
            DBankUploadService.log.debug("init:http code=" + responseCode + ",body=" + content);

        DBankResponse response = new DBankResponse();
        if (responseCode == 200)
            return response;
        if (responseCode != 201)
            throw new DBankException("unknown http code for init upload,code=" + responseCode);
        UploadStatus status = null;
        try {
            status = (UploadStatus) JsonUtils.toObject(content, UploadStatus.class);
        } catch (IOException e) {
            throw new DBankException(e);
        }

        response.setUploadStatus(status);
        return response;
    }
}
