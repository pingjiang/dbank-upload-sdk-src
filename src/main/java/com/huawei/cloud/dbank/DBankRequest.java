package com.huawei.cloud.dbank;

import com.huawei.cloud.dbank.util.CryptUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by pingjiang on 14-6-11.
 * <p/>
 * 消息请求参数
 */
public class DBankRequest {
    private String host;
    private String path;
    private File localFile;
    private long fileLength;
    private String callbackUrl = null;
    private String callbackStatus = null;

    private String _fileURL = null;
    private String _fileMD5 = null;
    private String _fileContentMD5 = null;

    //private Map<String, String> headers = new HashMap<String, String>();

    public DBankRequest() {
    }

    public DBankRequest(String path, File localFile) {
        setPath(path);
        setLocalFile(localFile);
    }

    /**
     * 网盘里面的路径，必须/开头，并且不能以/结尾
     *
     * @param forUpload
     * @return
     */
    public boolean isValid(boolean forUpload) {
        if (path == null || path.charAt(0) != '/' || path.charAt(path.length() - 1) == '/')
            return false;
        if (forUpload && (localFile == null || localFile.length() == 0L))
            return false;

        return true;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getInitPath() {
        return path + "?init";
    }

    public String getURL() {
        if (_fileURL == null) {
            _fileURL = "http://" + host + path;
        }

        return _fileURL;
    }

    public String getInitURL() {
        return getURL() + "?init";
    }

    public File getLocalFile() {
        return localFile;
    }

    public void setLocalFile(File localFile) {
        this.localFile = localFile;
        if (localFile != null) {
            setFileLength(localFile.length());
        }
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackStatus() {
        return callbackStatus;
    }

    public void setCallbackStatus(String callbackStatus) {
        this.callbackStatus = callbackStatus;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public Map<String, String> createHeaders(boolean includeContentRange, long offset, int length) throws DBankException {
        Map<String, String> headers = new TreeMap<String, String>();
        headers.put("nsp-ts", String.valueOf(System.currentTimeMillis() / 1000L));
        headers.put("nsp-file-md5", getFileMD5());
        headers.put("nsp-file-size", String.valueOf(localFile == null ? fileLength : localFile.length()));
        headers.put("nsp-content-md5", getContentMD5());

        if (callbackUrl != null && callbackStatus != null) {
            headers.put("nsp-callback", callbackUrl);
            headers.put("nsp-callback-status", callbackStatus);
        }

        if (includeContentRange) {
            headers.put("nsp-content-range", String.format("%d-%d/%d", offset, offset + (long) length - 1L, fileLength));
        }

        return headers;
    }

    public String getFileMD5() throws DBankException {
        if (_fileMD5 == null) {
            try {
                _fileMD5 = CryptUtils.getFileMd5(localFile);
            } catch (IOException e) {
                throw new DBankException(e);
            }
        }

        return _fileMD5;
    }

    public String getContentMD5() throws DBankException {
        if (_fileContentMD5 == null) {
            try {
                _fileContentMD5 = CryptUtils.getContentMd5(getFileMD5(), localFile);
            } catch (IOException e) {
                throw new DBankException(e);
            }
        }

        return _fileContentMD5;
    }
}
