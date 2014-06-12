package com.huawei.cloud.dbank;

/**
 * Created by pingjiang on 14-6-12.
 */
public class FSResponse {
    private DBankFile[] successList;
    private FSFault[] failList;

    public DBankFile[] getSuccessList() {
        return successList;
    }

    public void setSuccessList(DBankFile[] successList) {
        this.successList = successList;
    }

    public FSFault[] getFailList() {
        return failList;
    }

    public void setFailList(FSFault[] failList) {
        this.failList = failList;
    }
}
