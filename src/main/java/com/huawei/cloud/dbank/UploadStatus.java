package com.huawei.cloud.dbank;

/**
 * 更新状态的Bean
 */
public class UploadStatus {

    /**
     * 当前状态
     */
    private int upload_status;

    /**
     * 完成度
     */
    private long completed_range[][];

    public int getUpload_status() {
        return upload_status;
    }

    public void setUpload_status(int upload_status) {
        this.upload_status = upload_status;
    }

    public long[][] getCompleted_range() {
        return completed_range;
    }

    public void setCompleted_range(long completed_range[][]) {
        this.completed_range = completed_range;
    }
}
