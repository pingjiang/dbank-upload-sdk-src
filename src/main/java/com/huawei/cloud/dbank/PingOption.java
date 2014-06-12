package com.huawei.cloud.dbank;

/**
 * Created by pingjiang on 14-6-12.
 * 响应为
 * succ	boolean	是	是否成功
 * ip	String	否	IP
 */
public class PingOption {
    private String area;//	String	否	 指定下载区域，通过 '_' 连接国家和省份/州，比如CN_guangdong，表示优先从中国广东下载；IN，表示优先从印度下载；
    private String vip_level;//		String	否	等级
    private int fileid;//		int	否	下载文件的fileId
    private int isp;//		int	否	 指定运营商，ct代表电信、ut代表联通、mt代表移动和铁通，可以通过'ct|mt'指定多个运营商。系统决策时会考虑其他因素，优先选择用户指定运营商。
    private int ignored_ip;//		int	否	客户端主动忽略的服务IP。如果是下载的话，请务必设置fileid参数。

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVip_level() {
        return vip_level;
    }

    public void setVip_level(String vip_level) {
        this.vip_level = vip_level;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public int getIsp() {
        return isp;
    }

    public void setIsp(int isp) {
        this.isp = isp;
    }

    public int getIgnored_ip() {
        return ignored_ip;
    }

    public void setIgnored_ip(int ignored_ip) {
        this.ignored_ip = ignored_ip;
    }
}
