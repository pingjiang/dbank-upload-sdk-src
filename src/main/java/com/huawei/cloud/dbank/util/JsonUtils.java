package com.huawei.cloud.dbank.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.util.Map;

/**
 * Created by pingjiang on 14-6-11.
 */
public class JsonUtils {
    private static final Log log = LogFactory.getLog(JsonUtils.class);

    static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        SerializationConfig serializationConfig = objectMapper.getSerializationConfig()
                .with(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS)
                .withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        objectMapper.setSerializationConfig(serializationConfig);
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static Map toMap(String content)
            throws IOException {
        return (Map) toObject(content, Map.class);
    }

    public static Object toObject(String content, Class<?> type)
            throws IOException {
        return content != null && !content.isEmpty() ? objectMapper.readValue(content, type) : null;
    }

    public static String toJsonString(String md5s[])
            throws IOException {
        return objectMapper.writeValueAsString(md5s);
    }

}
