package com.huawei.cloud.dbank.service;

import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.DBankRequest;

/**
 * Created by pingjiang on 14-6-12.
 */
public interface UploadService {
    String getBasePath();

    String getFullPath(String... items);

    String getMethod();

    void upload(DBankRequest request) throws DBankException;
}
