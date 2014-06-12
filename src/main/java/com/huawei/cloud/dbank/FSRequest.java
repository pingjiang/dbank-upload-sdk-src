package com.huawei.cloud.dbank;

import java.util.Map;

/**
 * Created by pingjiang on 14-6-12.
 */
public class FSRequest {
    private String path = null;
    private String[] files = null;
    private String[] fields = null;
    private Boolean reserve = null;
    private Map<String, String> attributes = null;
    private Map<String, DBankFile> dbankFiles = null;

    /**
     * 备注：只有请求options参数中带上version时，结果中才会
     * 返回此属性。此属性有利于在移动网络环境下减少网络流
     * 量。文件夹获取浏览最新数据后，如果带上最新的版本请求
     * 下一次，服务器该文件夹下面如果没有变更，那么就不返回
     * childList子项，version版本号也没发生变更，这样就可以减少
     * 网络流量。
     */
    private String version;//可选	 文件夹本身的最新版本号。
    private ListOption listOptions;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public boolean hasFiles() {
        return files != null && files.length > 0;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public boolean hasFields() {
        return fields != null && fields.length > 0;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ListOption getListOptions() {
        return listOptions;
    }

    public void setListOptions(ListOption listOptions) {
        this.listOptions = listOptions;
    }

    public boolean isReserve() {
        return reserve;
    }

    public void setReserve(boolean reserve) {
        this.reserve = reserve;
    }

    public boolean needReserve() {
        return reserve != null && reserve;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, DBankFile> getDbankFiles() {
        return dbankFiles;
    }

    public void setDbankFiles(Map<String, DBankFile> dbankFiles) {
        this.dbankFiles = dbankFiles;
    }

    public boolean hasDBankFiles() {
        return dbankFiles != null && dbankFiles.size() > 0;
    }
}
