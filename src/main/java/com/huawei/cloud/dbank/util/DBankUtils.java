package com.huawei.cloud.dbank.util;

import com.huawei.cloud.dbank.DBankException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class DBankUtils {
    public static final String ENCODING = "UTF-8";
    private static final Log log = LogFactory.getLog(DBankUtils.class);

    /**
     * 编码HTTP请求参数
     *
     * @param mergedParams
     * @return
     * @throws com.huawei.cloud.dbank.DBankException
     */
    public static String generatePostData(Map<String, String> mergedParams) throws DBankException {
        //StringBuilder sb = new StringBuilder(128);
        try {
            return encodeHeaders(mergedParams, true);
        } catch (UnsupportedEncodingException e) {
            throw new DBankException(e);
        }
    }

    /**
     * 生成NSP KEY
     *
     * @param secret
     * @param mergedParams
     * @return
     */
    public static String generateNspKey(String secret, Map<String, String> mergedParams) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(64);
        sb.append(secret);

        for (Map.Entry<String, String> entry : mergedParams.entrySet()) {
            sb.append(entry.getKey() + entry.getValue());
        }

        return CryptUtils.encode("MD5", sb.toString().getBytes("UTF-8")).toUpperCase(Locale.getDefault());
    }

    public static void mergeMap(Map<String, String> params, Map<String, String> mergedParams, boolean allowDumplicates) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!allowDumplicates && mergedParams.containsKey(entry.getKey()))
                throw new IllegalArgumentException("param name " + entry.getKey() + " is reserved.");

            mergedParams.put(entry.getKey(), entry.getValue());
        }
    }

    public static Map<String, String> createParams(String appId) {
        Map<String, String> mergedParams = new TreeMap<String, String>();
        mergedParams.put("nsp_app", appId);
        mergedParams.put("nsp_fmt", "JSON");
        mergedParams.put("nsp_ts", String.valueOf(System.currentTimeMillis() / 1000L));
        mergedParams.put("nsp_ver", "1.0");
        mergedParams.put("nsp_svc", "nsp.ping.getupsrvip");
        return mergedParams;
    }

    public static String encodeHeaders(Map<String, String> headers, boolean encodeKeyValue) throws UnsupportedEncodingException {
        StringBuilder params = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (first) {
                first = false;
            } else {
                params.append("&");
            }

            String key = encodeKeyValue ? URLEncoder.encode(entry.getKey(), ENCODING) : entry.getKey();
            String val = encodeKeyValue ? URLEncoder.encode(entry.getValue(), ENCODING) : entry.getValue();
            params.append(key).append('=').append(val);
        }

        return params.toString();
    }

    /**
     * 加密算法
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
     *
     * @param httpMethod
     * @param path
     * @param headers
     * @throws java.io.UnsupportedEncodingException
     */
    public static void buildSig(String path, Map<String, String> headers, String secret, String httpMethod)
            throws DBankException {
        String params = null;
        String encodedPath = null;
        String encodedParams = null;

        try {
            params = encodeHeaders(headers, false);
            encodedPath = URLEncoder.encode(path, ENCODING);
            encodedParams = URLEncoder.encode(params, ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new DBankException(e);
        }

        String tempSecret = CryptUtils.hashMac((String) headers.get("nsp-ts"), secret);
        StringBuilder encryptSource = new StringBuilder(128);
        encryptSource.append(httpMethod + '&' + encodedPath + '&' + encodedParams);
        String nspSig = CryptUtils.base64HashMac(encryptSource.toString(), tempSecret);
        headers.put("nsp-sig", nspSig);
        headers.put("Expect", "100-continue");
    }

    public static String arrayToString(String[] items) {
        StringBuffer sb = new StringBuffer();
        sb.append('[');

        boolean first = true;
        for (String item : items) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }

            sb.append('"').append(item).append('"');
        }
        sb.append(']');

        return sb.toString();
    }
}
