package com.huawei.cloud.dbank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by pingjiang on 14-6-11.
 * <p/>
 * 网盘配置文件
 */
public class DBankConfiguration {
    public static final String FILE_NAME = "dbank.properties";
    private static final Log log = LogFactory.getLog(DBankConfiguration.class);
    private static DBankConfiguration instance = null;
    private Properties properties;

    private DBankConfiguration() {
        try {
            properties = load(ClassLoader.getSystemResource(FILE_NAME).getPath());

            log.debug("Load properties successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Properties load(String filePath) throws IOException {
        Properties props = new Properties();
        log.debug("load property file " + filePath);
        props.load(new FileInputStream(filePath));

        return props;
    }

    public static DBankConfiguration getInstance() {
        if (instance == null) {
            instance = new DBankConfiguration();
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key, int def) {
        String val = getProperty(key);
        return val == null ? def : Integer.decode(val);
    }
}
