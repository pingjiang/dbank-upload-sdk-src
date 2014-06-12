package com.huawei.cloud.dbank;

import java.io.File;

/**
 * Created by pingjiang on 14-6-12.
 * <p/>
 * 3. File
 * 描述文件或者文件夹的属性。下面字段可以分为三类， 公共、文件特有、文件夹特有
 * 字段名
 * 类型
 * 必选
 * 说明
 * type	String	是	公共 。文件类型，可以为"File/Directory"，其特点是"File"不包含子文件，而"Directory"可以包含子文件
 * name	String	是	公共 。文件名称或全路径
 * createTime	String		公共 。创建时间
 * modifyTime	String		公共 。修改时间
 * accessTime	String		公共 。访问时间版本(系统保留，只提供查询，应用不使用这个)
 * size	String		文件特有 。文件大小创建时间
 * url	String		文件特有 。文件存放的URL
 * md5	String		文件特有 。文件的md5校验码
 * revisions	String		文件特有 。文件修订版本
 * space	String		文件夹特有 。目录大小，目录下所有子目录和文件大小总和
 * fileCount	int		文件夹特有 。目录下文件个数，不包含文件夹
 * dirCount	int		文件夹特有 。目录下文件夹个数，不包含文件
 * ts	String		文件特有 。 只有在nsp.vfs.mkfile,nsp.vfs.createFile时必须切有效，有文件上传服务器返回，描述上传服务器上传文件时的时间戳
 * sig	String		文件特有 。 只有在nsp.vfs.mkfile,nsp.vfs.createFile时必须切有效，有文件上传服务器返回，描述上传服务器上传文件时的签名，防止伪造创建虚假文件请求
 * merge	Boolean		文件特有 。 只有在nsp.vfs.mkfile时此参数才有效，描述创建文件时，如果用户该文件夹下面存在一个同名文件，此参数标识是否覆盖该文件
 */
public class DBankFile {
    private String name;
    private String type;
    private String size;
    private File file;
    private String url;
    private String fileMD5;
    private String revisions;
    private int fileCount = 0;
    private int dirCount = 0;
    private String ts;
    private String sig;
    private boolean merge;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public String getRevisions() {
        return revisions;
    }

    public void setRevisions(String revisions) {
        this.revisions = revisions;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getDirCount() {
        return dirCount;
    }

    public void setDirCount(int dirCount) {
        this.dirCount = dirCount;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public boolean isMerge() {
        return merge;
    }

    public void setMerge(boolean merge) {
        this.merge = merge;
    }
}
