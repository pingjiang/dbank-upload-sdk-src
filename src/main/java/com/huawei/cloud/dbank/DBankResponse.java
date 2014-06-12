package com.huawei.cloud.dbank;

/**
 * Created by pingjiang on 14-6-11.
 */
public class DBankResponse {
    private long errorCodeLong = -1L;

    private String fastHostIPAddress;

    private UploadStatus uploadStatus;

    public String getFastHostIPAddress() {
        return fastHostIPAddress;
    }

    public void setFastHostIPAddress(String fastHostIPAddress) {
        this.fastHostIPAddress = fastHostIPAddress;
    }

    public UploadStatus getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(UploadStatus uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public long getErrorCodeLong() {
        if (uploadStatus != null) {
            return uploadStatus.getUpload_status() == 1 ? uploadStatus.getCompleted_range()[0][1] : 0L;
        }

        return errorCodeLong;
    }

    public void setErrorCodeLong(long errorCodeLong) {
        this.errorCodeLong = errorCodeLong;
    }
}
