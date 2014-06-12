package com.huawei.cloud.dbank.service;

import com.huawei.cloud.dbank.DBankException;
import com.huawei.cloud.dbank.DBankRequest;

/**
 * Created by pingjiang on 14-6-12.
 *
 * 文件下载服务
 *
 */
public interface DownloadService {

    String generateKey(String path, long ts);

    void download(DBankRequest request) throws DBankException;
}
