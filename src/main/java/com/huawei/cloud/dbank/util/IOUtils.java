package com.huawei.cloud.dbank.util;

import java.io.*;

/**
 * Created by pingjiang on 14-6-12.
 */
public class IOUtils {
    /**
     * 读取InputStream
     *
     * @param body
     * @param len  总长度（URLConnection返回的长度为-1，所以我们不能预先知道消息内容的长度，默认为512
     * @return
     * @throws java.io.IOException
     */
    public static String readInputStreamToString(InputStream body, int len) throws IOException {
        byte buffer[] = new byte[512];
        ByteArrayOutputStream bout = new ByteArrayOutputStream(len <= 0 ? 512 : len);
        int read;
        while ((read = body.read(buffer)) != -1) {
            bout.write(buffer, 0, read);
        }

        return new String(bout.toByteArray(), "UTF-8");
    }

    /**
     * 读取文件
     * <p/>
     * new OutputStreamWriter(new FileReader(new RandomAccessFile(file, "r")), out);
     *
     * @param file
     * @param offset
     * @param length
     * @param out
     * @throws IOException
     */
    public static void copyFile(File file, long offset, int length, OutputStream out)
            throws IOException {
        RandomAccessFile raf = null;
        raf = new RandomAccessFile(file, "r");
        raf.seek(offset);
        byte buff[] = new byte[1024];
        int remain = length;
        do {
            int read;
            if ((read = raf.read(buff, 0, remain >= buff.length ? buff.length : remain)) == -1)
                break;
            out.write(buff, 0, read);
            remain -= read;
        } while (remain > 0);

        if (raf != null) {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String joinPath(String basePath, String item) {
        if (item == null || item.isEmpty() || item == "/") {
            return basePath;
        }

        if (basePath.endsWith("/")) {
            return basePath + (item.startsWith("/") ? item.substring(1) : item);
        }

        return basePath + (item.startsWith("/") ? item : ("/" + item));
    }

    public static String joinPaths(String basePath, String... items) {
        String path = basePath;

        for (String item : items) {
            path = joinPath(path, item);
        }

        return path;
    }

    /**
     * 保存文件
     *
     * @param localFile
     * @param in
     * @param contentLength
     * @throws IOException
     */
    public static void writeInputStreamToFile(File localFile, InputStream in, int contentLength) throws IOException {
        final int BUFFER_SIZE = 1024;
        OutputStream outPutStream = null;
        try {
            outPutStream = new FileOutputStream(localFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = -1;
            int offset = 0;
            while ((read = in.read(buffer)) != -1) {
                outPutStream.write(buffer, offset, read);
                offset += read;
            }
        } finally {
            outPutStream.flush();
            outPutStream.close();
        }
    }
}

