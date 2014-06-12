package com.huawei.cloud.dbank.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.zip.CRC32;

/**
 * Created by pingjiang on 14-6-11.
 */
public class CryptUtils {
    public static final String ENCODING = "UTF-8";
    private static final Log log = LogFactory.getLog(CryptUtils.class);
    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static String encode(String algorithm, byte source[]) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(source);
            byte hash[] = md.digest();
            return toHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String hashMac(String data, String key) {
        return toHexString(byteHashMac(data, key)).toLowerCase(Locale.getDefault());
    }

    private static byte[] byteHashMac(String data, String key) {
        try {
            byte keyBytes[] = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(signingKey);
            return mac.doFinal(data.getBytes(ENCODING));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static String base64HashMac(String data, String key) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(byteHashMac(data, key));
    }

    private static String toHexString(byte array[]) {
        StringBuilder result = new StringBuilder();
        for (byte b : array) {
            result.append(DIGITS[b >>> 4 & 0xf]);
            result.append(DIGITS[b & 0xf]);
        }

        return result.toString();
    }

    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMd5(File file) throws IOException {
        return getFileMd5(file, 0, file.length());
    }

    private static String getFileMd5(File file, long offset, long length) throws IOException {
        String resultValue;
        byte buffer[];
        RandomAccessFile raFile = null;
        resultValue = null;
        buffer = new byte[256];
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            raFile = new RandomAccessFile(file, "r");
            raFile.seek(offset);
            do {
                int byteRead;
                if ((byteRead = raFile.read(buffer)) == -1 || length <= 0L)
                    break;
                if ((long) byteRead >= length) {
                    digest.update(buffer, 0, (int) length);
                    break;
                }
                digest.update(buffer, 0, byteRead);
                length -= byteRead;
            } while (true);
            resultValue = toHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (raFile != null) {
                try {
                    raFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultValue.toLowerCase(Locale.getDefault());
    }

    /**
     * 获取文件内容的MD5值
     *
     * @param fileMd5
     * @param file
     * @return 返回的是数组的json格式
     * @throws java.io.IOException
     */
    public static String getContentMd5(String fileMd5, File file)
            throws IOException {
        long fileLength = file.length();
        String md5s[] = new String[2];
        for (int i = 1; i <= 2; i++) {
            CRC32 crc32 = new CRC32();
            crc32.update((fileMd5 + i).getBytes());
            long offset = crc32.getValue() % fileLength;
            long length = 0x100001L;
            if (fileLength - offset < length)
                length = fileLength - offset;
            //if (log.isDebugEnabled())
            //    log.debug("content md5 range:offset=" + offset + ",length=" + length);
            md5s[i - 1] = getFileMd5(file, offset, length).toLowerCase();
        }

        return JsonUtils.toJsonString(md5s);
    }
}
