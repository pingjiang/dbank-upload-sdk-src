package com.huawei.cloud.dbank.service;

import com.huawei.cloud.dbank.DBankException;

import java.util.Map;

/**
 * 1. 接口地址
 * 提供两种下载模式，用户可以自行切换或同时打开两种下载模式：
 * <p/>
 * i. 动态测速地址（速度可靠性更佳）：
 * 支持302跳转。域名为appname.dbankcloud.com
 * 下载地址格式：http://{appname}. dbankcloud .com/{path}/{filename}
 * <p/>
 * ii. 智能DNS地址（更适合在线播放）：
 * 不支持302跳转。域名为appname.dbankcdn.com。
 * 下载地址格式：http://{appname}. dbankcdn .com/{path}/{filename}
 * 其中，各变量描述如下：
 * 名称
 * 必须
 * 描述
 * appName	是	给应用分配的名称，为英文字母和-_组成
 * path	否	文件在云存储中的保存路径
 * filename	是	要保存的文件名称
 * 【说明】
 * 下载URL如果设置API授权，URL的拼接规则
 * 1. 签名之后的url为： http://{appname}.dbankcloud.com/{path}/{filename}?ts=1302392423&key=xxxxxxxxx
 * 2. ts ： 过期时间，1970/1/1开始计算的秒数，时间需要与时间服务器时间同步。
 * 3. key = substr(0, 8, hmac_sha1("/dl/to_lowcase({appname})/{path}/{filename}?ts=1302392423", appsecret))，appsecret为申请云加速时分配的appID对应的密钥。
 * 举例：
 * <? php
 * // 申请的应用名称
 * $appname = "appname";
 * // 文件的路径
 * $path = "/test/a.txt";
 * // 设定为5分钟后过期
 * $ts = time() + 300;
 * // 设置的秘钥
 * $secret = "ea480aef61pms24";
 * // 组合成的URL
 * $url = "/dl/".strtolower($appname).$path."?ts=".$ts;
 * $key = substr(hash_hmac("sha1", $url, $secret), 0, 8);
 * var_dump($key);
 * ?>
 * 生成的地址为：http://$appname.dbankcloud.com/$path?ts=$ts&key=$key
 * <p/>
 * 1. 动态测速地址样例：
 * 返回 302：
 * GET /files/com.ea.game.realracing2_row.apk HTTP/1.1
 * Host: hitopdl.dbankcloud.com
 * Connection: keep-alive
 * Accept: text/html,application/xhtml+xml,application/xml;q=0.9,* / * ;q=0.8
 * User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.21 (KHTML, like Gecko)
 * Chrome/25.0.1359.3 Safari/537.21
 * Accept-Encoding: gzip,deflate,sdch
 * Accept-Language: zh-CN,zh;q=0.8
 * Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3
 * HTTP/1.1 302 Moved Temporarily
 * Server: nginx/1.2.6
 * Date: Mon, 04 Mar 2013 11:35:26 GMT
 * Content-Type: text/html
 * Transfer-Encoding: chunked
 * Connection: close
 * Location: http://183.60.143.184/dl/hitopdl/files/com.ea.game.realracing2_row.apk
 * <p/>
 * 跳转到真正的下载地址：
 * GET /dl/hitopdl/files/com.ea.game.realracing2_row.apk HTTP/1.1
 * Host: 183.60.143.184
 * Connection: keep-alive
 * Accept: text/html,application/xhtml+xml,application/xml;q=0.9,* / *;q=0.8
 * User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.21 (KHTML, like Gecko)
 * Chrome/25.0.1359.3 Safari/537.21
 * Accept-Encoding: gzip,deflate,sdch
 * Accept-Language: zh-CN,zh;q=0.8
 * Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3
 * HTTP/1.1 200 OK
 * Server: nginx/1.1.13
 * Date: Mon, 04 Mar 2013 11:35:26 GMT
 * Content-Length: 2590347
 * Connection: close
 * Content-Type: application/vnd.android.package-archive
 * Last-Modified: Tue, 05 Mar 2013 11:35:26 GMT
 * Cache-Control: max-age=86400
 * Expires: Tue, 05 Mar 2013 11:35:26 GMT
 * Content-Disposition: attachment; filename*="com.ea.game.realracing2_row.apk"
 * xxxxxxx
 * 2. 智能DNS地址样例：
 * GET /files/com.ea.game.realracing2_row.apk HTTP/1.1
 * Host: hitopdl.dbankcdn.com
 * Connection: keep-alive
 * Accept: text/html,application/xhtml+xml,application/xml;q=0.9,* / * ;q=0.8
 * User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.21 (KHTML, like Gecko)
 * Chrome/25.0.1359.3 Safari/537.21
 * Accept-Encoding: gzip,deflate,sdch
 * Accept-Language: zh-CN,zh;q=0.8
 * Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3
 * HTTP/1.1 200 OK
 * Server: nginx/1.1.13
 * Date: Mon, 04 Mar 2013 11:35:26 GMT
 * Content-Length: 2590347
 * Connection: close
 * Content-Type: application/vnd.android.package-archive
 * Last-Modified: Tue, 05 Mar 2013 11:35:26 GMT
 * Cache-Control: max-age=86400
 * Expires: Tue, 05 Mar 2013 11:35:26 GMT
 * Content-Disposition: attachment; filename*="com.ea.game.realracing2_row.apk"
 * xxxxxxx
 * <p/>
 * 1. 接口名nsp.ping.getupsrvip
 * 获取上传最佳网速服务器ip。
 * 2. 原型
 * getupsrvip(String client_ip, PingOption option)
 * 3. 接口参数Parameters:
 * getupsrvip(String client_ip, PingOption option)
 * 参数
 * 类型
 * 可选
 * 说明
 * client_ip	String	可选	客户端IP
 * option	PingOption	可选	附加选项
 * 4. 接口返回结果
 * 参数
 * 类型
 * 可选
 * 说明
 * PingRet	可选	结果，参考对象类型说明。
 * <p/>
 * 1. 接口地址
 * http://{ip}/dl/{appName}/{path}/{filename}
 * 其中，各变量描述如下：
 * 名称
 * 必须
 * 描述
 * ip	是	上传地址或域名，可通过调用接口nsp.ping.getupsrvip获取
 * appName	是	应用的名称，为英文字母、数字和中划线组成
 * path	否	文件在云存储中的保存路径
 * filename	是	要保存的文件名称
 * 2. 请求协议
 * 上传使用标准HTTP协议， 采用PUT请求方式， 使用自定义HTTP HEADER来实现鉴权， 附加的头信息如下
 * Header Name
 * 必须
 * 描述
 * Content-Length	是	上传文件的大小
 * nsp-sig	是	签名信息，签名方法见后面的描述
 * Expect	否	100-continue
 * nsp-callback	否	必须是http协议，上传成功后，服务器会访问一下此地址，获取访问的HTTP STATUS。
 * nsp-callback-status	否	必须为数字，回调期望的HTTP STATUS。
 * nsp-ts	否	客户端上传时使用。 与服务器时间偏差不能超过10分钟。
 * nsp-file-md5	否	文件内容MD5（转为小写）。当文件上传完之后，服务器会校验已上传文件的MD5。
 * 签名计算步骤：
 * 1） 构造源串
 * 源串由3部分内容用"&"拼接起来：
 * HTTP请求方式 & urlencode(URI) & urlencode(a=x&b=y&...)
 * 构造步骤
 * a) 将请求的URI路径进行URLEncode编码。
 * b) 将HTTP header里除“nsp-sig”外的所有nsp开头的参数按key进行字典升序排列。
 * c) 将上一步中排序后的参数(key=value)用&拼接起来，并进行URL编码。
 * d) 将HTTP请求方式（GET或者POST或者PUT）以及第1步和第3步中的字符串用&拼接起来
 * <p/>
 * 2） 计算密钥
 * 如果使用场景1， 上传中无nsp-ts， 此时密钥为app对应的密钥（由申请获得）；
 * 如果使用场景2， 密钥使用HMAC-SHA1加密获取， 算法为:
 * temp_secret = hash_hmac ("sha1", {nsp_ts}, {app_secret})
 * 3） 计算签名
 * 使用HMAC-SHA1加密算法，将步骤1中的到的源串以及Step2中得到的密钥进行加密, 再进行base64获得，算法如下：
 * nsp-sig = base64_encode(hash_hmac("sha1", 源串, 密钥, true));
 * <p/>
 * 3. 返回结果
 * HTTP/1.1 100 Continue
 * HTTP/1.1 200 OK
 * Date: Thu, 02 Sep 2010 12:54:43 GMT
 * Connection: close
 * Content-Length: 0
 * 其中100 Continue是中途返回的结果。
 * 如果上传失败，则HTTP Status不为200；
 * 响应参数：
 * 参数	 类型	 可选	 说明
 * name	String	否	文件名称或全路径
 * size	String	否	文件大小
 * createTime	String	否	创建时间
 * modifyTime	String	否	修改时间
 * md5	String	否	文件的md5校验码
 * 返回码：
 * Status
 * Message
 * Description
 * 200	ok	正常返回，上传成功
 * 400	only support PUT method	请求出错，暂时仅支持PUT方法
 * 400	callback nsp-callback failed	执行回调URL与预期得到的状态码不符
 * 401	authentication failure	nsp-sig鉴权失败
 * 401	nsp-sig time out	nsp-ts超时，须重新计算nsp-sig
 * 403	create file forbidden	无权限创建文件
 * 500	server busy, please try again later	服务器繁忙，请稍后再试
 * 500	internal error	服务器内部错误
 * 500	create file error	内部错误，创建文件失败
 * 502	bad gateway	内部错误
 * 507	over space	用户空间已满
 * <p/>
 * 样例
 * 请求:
 * PUT /dl/appName/public/test.jpg HTTP/1.1
 * Host: upload.dbank.com
 * User-Agent: browser_data
 * Accept:  /
 * Content-Length: 10240003
 * Expect: 100-continue
 * Content-Type: image/jpg
 * nsp-callback: http://app.domain/upload-callback-url
 * nsp-callback-status: 200
 * nsp-ts: 1357869817
 * nsp-sig: VPgToHyrlbJH5OFiznX1fQujrrw=
 * file_content
 * 响应:
 * HTTP/1.1 200 OK
 * Date: Thu, 02 Sep 2010 12:54:43 GMT
 * Connection: close
 * Content-Length: 78
 * {
 * "name": "/apps/album/1.jpg",
 * "size": 372121,
 * "accessTime": "2013-06-29 07:53:03",
 * "modifyTime": "2013-06-29 07:48:11",
 * "md5": "cb123afcc12453543ef"
 * }
 * 1. nsp-callback：
 * 只有在上传成功时才会回调；如果不需要回调，则不需要使用该参数；
 * 如果使用该参数，请务必同时设置nsp-callback-status，否则会导致上传失败。
 * 2. 客户端需要支持100-Continue处理。
 * For more information on the 100-continue HTTP status code, go to Section 8.2.3 of http://www.ietf.org/rfc/rfc2616.txt。为了更好的交互，请设置HTTP头部Expect: 100-continue，当鉴权失败时，服务器会主动断开请求并返回出错信息。如果没有设置HTTP头部Expect: 100-continue，鉴权失败时服务器会返回出错信息并断开连接。
 * 3. 客户端与服务器时间偏差不能过大，否则会鉴权错误。
 * 关于时间纠偏，参见参考文档场景2，请务必设置nsp-ts参数。
 */
public interface HostService {
    /**
     * 获取最快的上传IP
     *
     * @param appId
     * @param appSecret
     * @param params
     * @return IP地址
     * @throws com.huawei.cloud.dbank.DBankException
     */
    String getUploadHost(String appId, String appSecret, Map<String, String> params) throws DBankException;
}
