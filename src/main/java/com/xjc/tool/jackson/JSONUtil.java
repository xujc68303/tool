package com.xjc.tool.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author jiachenxu
 * @Date 2021/6/24
 * @Descripetion
 */
public class JSONUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 对于空的对象转json的时候不抛出错误
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 允许属性名称没有引号
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 设置输入时忽略在json字符串中存在但在java对象实际没有的属性
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 设置输出时包含属性的风格
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String toJsonString(Object data) {
        if (data == null) {
            return null;
        }
        String r = null;
        try {
            r = MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return r;
    }

    public static String toJsonString(String... str) {
        if (str.length == 0) {
            return null;
        }
        String r = null;
        try {
            r = MAPPER.writeValueAsString(str);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return r;
    }

    public static <clazz> List<?> jsonToList(String json, clazz clazz) {
        if (json == null) {
            return null;
        }
        List<clazz> clazzes = null;
        try {
            clazzes = MAPPER.readValue(json, new TypeReference<List<clazz>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return clazzes;
    }

    public static Map<Object, Object> jsonToMap(String json) {
        if (json == null) {
            return null;
        }
        Map<Object, Object> result = null;
        try {
            result = MAPPER.readValue(json, new TypeReference<Map<Object, Object>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (json == null || clazz == null) {
            return null;
        }
        T t = null;
        try {
            t = MAPPER.convertValue(json, clazz);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T parse(TreeNode treeNode, Class<T> clazz) {
        if (treeNode.size() == 0 || clazz == null) {
            return null;
        }
        T t = null;
        try {
            t = MAPPER.treeToValue(treeNode, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static Collection<?> parseList(String json, Class<? extends Collection> collectionClass, Class<?> clazz) {
        if (json == null || clazz == null) {
            return null;
        }
        Collection<?> result = null;
        try {
            CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(collectionClass, clazz);
            result = MAPPER.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JsonNode parseTree(String json) {
        if (json == null) {
            return null;
        }
        JsonNode jsonNode = null;
        try {
            jsonNode = MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonNode;
    }

}
