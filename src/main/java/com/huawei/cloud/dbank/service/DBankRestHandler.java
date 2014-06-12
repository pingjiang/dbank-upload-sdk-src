package com.huawei.cloud.dbank.service;

import com.huawei.cloud.dbank.DBankException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by pingjiang on 14-6-12.
 */
public interface DBankRestHandler {
    /**
     * 将内容写入到连接里面
     *
     * @param out 连接输出流
     * @throws com.huawei.cloud.dbank.DBankException
     */
    void onWrite(OutputStream out) throws DBankException;

    /**
     * 从连接里面读取内容并转换为响应对象
     *
     * @param responseCode
     * @param in            连接输入流
     * @param contentLength
     * @return
     * @throws DBankException
     */
    Object onRead(int responseCode, InputStream in, int contentLength) throws DBankException;
}
