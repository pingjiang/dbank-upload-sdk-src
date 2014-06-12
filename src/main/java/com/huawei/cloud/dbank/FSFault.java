package com.huawei.cloud.dbank;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pingjiang on 14-6-12.
 * <p/>
 * FS错误码
 */
public class FSFault extends Exception {
    private static Map<String, FSFault> _cache = new HashMap<String, FSFault>();

    private final String errCode;//	String	是	错误码
    private final String errMsg;//	String	是	错误描述
    private String name; // 文件或文件夹的包含全路径的名称

    static {
        _cache.put("101", new FSFault("101", "创建文件时，父目录不存在"));
        _cache.put("102", new FSFault("102", "移动，复制时源文件或文件夹不存在"));
        _cache.put("201", new FSFault("201", "更新文件属性操作缺少参数"));
        _cache.put("202", new FSFault("202", "文件URL或MD5参数错误"));
        _cache.put("203", new FSFault("203", "文件名或路径名参数错误"));
        _cache.put("204", new FSFault("204", "删除的文件不存在"));
        _cache.put("205", new FSFault("205", "上传创建文件超过用户等级单文件大小限制"));
        _cache.put("206", new FSFault("206", "创建文件时用户空间超容，需要用户购买而外容量或删除文件空出容量"));
        _cache.put("207", new FSFault("207", "文件size大小异常"));
        _cache.put("208", new FSFault("208", "文件名超过长度限制"));
        _cache.put("209", new FSFault("209", "在创建文件时，文件sig签名非法"));
        _cache.put("210", new FSFault("210", "操作时文件版本冲突"));
        _cache.put("301", new FSFault("301", "目标文件夹下面包含同名文件"));
        _cache.put("302", new FSFault("302", "目标文件夹下面包含同名文件夹"));
        _cache.put("303", new FSFault("303", "父目录不能复制或移动到子目录下"));
        _cache.put("401", new FSFault("401", "应用目录访问的目录受限"));
        _cache.put("402", new FSFault("402", "不允许删除或挪动系统文件夹"));
        _cache.put("888", new FSFault("888", "获取环境失败"));
        _cache.put("999", new FSFault("999", "未知错误"));
    }

    public FSFault(String errCode, String errMsg, String name) {
        this.name = name;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public FSFault(String errCode, String errMsg) {
        this(errCode, errMsg, null);
    }

    public FSFault() {
        this("0", null, null);
    }

    public static FSFault valueOf(String errorCode) {
        if (_cache.containsKey(errorCode)) {
            return _cache.get(errorCode);
        }

        return new FSFault(errorCode, "Unknown error");
    }
}
