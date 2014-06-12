package com.huawei.cloud.dbank.service;

import com.huawei.cloud.dbank.*;

import java.util.Map;

/**
 * Created by pingjiang on 14-6-11.
 *
 * 网盘服务
 */
public interface FSService {
    void setAccess_token(String access_token);

    /**
     * 处理文件操作命令
     *
     * @param serviceName 操作命令
     * @param request 请求
     * @return 响应
     * @throws DBankException 异常
     */
    FSResponse handle(String serviceName, FSRequest request) throws DBankException;

    /**
     * 浏览（获取）一个目录下面的内容
     *
     * @param path    可选	文件夹路径
     * @param fileds  可选	 需要查询的子项文件或文件夹对象的属性key数组。文件和文件夹属性，参见File对象描述。
     * @param options 可选	附加选项，定义参见数据类型定义
     * @throws DBankException 异常
     */
    FSResponse listDir(String path, String[] fileds, ListOption options) throws DBankException;

    /**
     * 复制（批量）文件和文件夹到指定目录
     *
     * @param files     必选	文件或文件夹路径数组
     * @param path      可选	 文件夹路径。注释：如果，上面文件对象中name字段如果仅仅只包含文件名称，而此处path指定路径，那么文件创建的实际全路径为 path+name
     * @param attribute 可选属性
     * @throws DBankException
     */
    FSResponse copyFile(String[] files, String path, Map<String, String> attribute) throws DBankException;

    /**
     * 复制（批量）文件和文件夹到指定目录
     *
     * @param files     必选	文件或文件夹路径数组
     * @param path      可选	 文件夹路径。注释：如果，上面文件对象中name字段如果仅仅只包含文件名称，而此处path指定路径，那么文件创建的实际全路径为 path+name
     * @param attribute 可选属性
     * @throws DBankException
     */
    FSResponse moveFile(String[] files, String path, Map<String, String> attribute) throws DBankException;

    /**
     * 删除（批量）文件和文件夹
     *
     * @param files   必选	文件或文件夹路径数组
     * @param reserve 可选	 删除文件或文件夹时是否保留，默认为false，直接删除文件或文件夹。如果为true的话，将文件或文件夹移动到系统回收站进行暂时保留。
     * @throws DBankException
     */
    FSResponse removeFile(String[] files, boolean reserve) throws DBankException;

    /**
     * 获取（批量）文件或文件夹的属性信息
     *
     * @param files 文件
     * @param fields 字段
     * @throws DBankException
     */
    FSResponse getAttr(String[] files, String[] fields) throws DBankException;

    /**
     * 设置（批量）文件或文件夹的属性信息
     *
     * @param files 文件
     * @throws DBankException
     */
    FSResponse setAttr(Map<String, DBankFile> files) throws DBankException;
}
