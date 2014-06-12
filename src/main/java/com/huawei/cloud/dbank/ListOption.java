package com.huawei.cloud.dbank;

/**
 * Created by pingjiang on 14-6-12.
 */
public class ListOption {
    /**
     * 获取子项的类型，默认为3。具体含义
     * 1－只列举文件；2－只列举文件夹；3－列举文件和文件夹
     */
    private int type = 3;

    /**
     * 递归查询子目录，默认为1。其具体参数含义：0 - 递归所有目录；1~N - 递归层次为N
     */
    private int recursive = 1;

    private String version = "1.0";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRecursive() {
        return recursive;
    }

    public void setRecursive(int recursive) {
        this.recursive = recursive;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
