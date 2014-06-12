package com.huawei.cloud.dbank;

import com.huawei.cloud.dbank.service.DBankRestHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

/**
 * Created by pingjiang on 14-6-11.
 */
public class RestConnector implements Closeable {
    private static final Log log = LogFactory.getLog(RestConnector.class);
    private final HttpURLConnection connection;
    private final DBankRestHandler handler;

    public RestConnector(String httpUrl, DBankRestHandler handler) throws DBankException {
        this(createURL(httpUrl), handler);
    }

    public RestConnector(URL url, DBankRestHandler handler) throws DBankException {
        this.handler = handler;

        try {
            log.debug("Opening connection " + url.toString());
            connection = (HttpURLConnection) url.openConnection();
            //connection.setConnectTimeout(DBankConfiguration.getInstance().getIntProperty("dbank.connection.timeout", 15000));
        } catch (IOException e) {
            throw new DBankException("open connection exception", e);
        }

        // 需要发送东西出去
        connection.setDoOutput(true);
    }

    public RestConnector(String url, String method, DBankRestHandler handler) throws DBankException {
        this(url, handler);
        setRequestMethod(method);
    }

    private static URL createURL(String httpUrl) throws DBankException {
        try {
            return new URL(httpUrl);
        } catch (MalformedURLException e) {
            throw new DBankException(e);
        }
    }

    public void setRequestMethod(String method) throws DBankException {
        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            throw new DBankException("Invalid HTTP method" + method, e);
        } finally {
            connection.disconnect();
        }
    }

    public void setRequestProperties(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public void close() throws IOException {
        connection.disconnect();
    }

    public Object connect() throws DBankException {
        try {
            log.debug("Connecting ...");
            connection.connect();

            OutputStream out = null;
            try {
                out = connection.getOutputStream();
                handler.onWrite(out);
            } catch (IOException e) {
                throw new DBankException("connect out exception", e);
            } finally {
                try {
                    if (out != null) {
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            InputStream in = null;
            try {
                in = connection.getInputStream();
                return handler.onRead(connection.getResponseCode(), in, connection.getContentLength());
            } catch (IOException e) {
                throw new DBankException("connect in exception", e);
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e) {
            throw new DBankException("connect exception", e);
        } finally {
            connection.disconnect();
        }
    }
}
