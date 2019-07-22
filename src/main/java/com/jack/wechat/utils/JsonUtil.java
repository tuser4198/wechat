package com.jack.wechat.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 数据类型转换工具类
 *
 * @author: zhangyh
 * @date: 2019/4/19
 */
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 序列化时，跳过null属性
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时，遇到空bean（无属性）时不会失败
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化时，遇到未知属性（在bean上找不到对应属性）时不会失败
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 反序列化时，将空数组([])当做null来处理（以便把空数组反序列化到对象属性上——对php生成的json的map属性很有用）
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        // 不通过fields来探测（仅通过标准getter探测）
        OBJECT_MAPPER.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
        //按字典顺序排序
        OBJECT_MAPPER.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    private JsonUtil() {
    }

    /**
     * 对象转换为string
     *
     * @param object
     * @return
     */
    public static String toJSon(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert object to json", e);
        }
    }

    /**
     * string转换为对象数组
     *
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T[] readValue2Array(String jsonStr, Class<T> tClass) {
        try {
            Class<?> arrayClass = Array.newInstance(tClass, 0)
                .getClass();
            return (T[])OBJECT_MAPPER.readValue(jsonStr, arrayClass);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为对象list
     *
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> readValue2List(String jsonStr, Class<T> tClass) {
        try {
            TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {
                @Override
                public Type getType() {
                    return ParameterizedTypeImpl.make(List.class, new Type[] {tClass}, null);
                }
            };
            return OBJECT_MAPPER.readValue(jsonStr, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为map
     *
     * @param jsonStr
     * @param keyClass
     * @param valueClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> readValue2Map(String jsonStr, Class<K> keyClass, Class<V> valueClass) {
        try {
            TypeReference<Map<K, V>> typeRef = new TypeReference<Map<K, V>>() {
                @Override
                public Type getType() {
                    return ParameterizedTypeImpl.make(Map.class, new Type[] {keyClass, valueClass}, null);
                }
            };
            return OBJECT_MAPPER.readValue(jsonStr, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * string转换为嵌套对象
     *
     * @param jsonStr
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

    /**
     * 多泛型嵌套
     *
     * @param jsonStr
     * @param types   内部嵌套的class
     * @param clazz   外层class
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonStr, Type[] types, Class<T> clazz) {
        final ParameterizedTypeImpl type = ParameterizedTypeImpl.make(clazz, types, clazz.getDeclaringClass());
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        return readValue(jsonStr, typeReference);
    }

    /**
     * string转换为对象
     *
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T readValue(String jsonStr, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, tClass);
        } catch (Exception e) {
            throw new RuntimeException("not able to convert json string:" + jsonStr, e);
        }
    }

}