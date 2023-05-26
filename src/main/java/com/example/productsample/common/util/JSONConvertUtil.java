package com.example.productsample.common.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jakarta.transaction.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONConvertUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONConvertUtil.class);

    public JSONConvertUtil() {
    }

    public static <T> T getBean(String jsonString, Class<T> clazz) {
        try {
            return StringUtils.isEmpty(jsonString) ? null : (new Gson()).fromJson(jsonString, clazz);
        } catch (Exception var3) {
            LOGGER.error("{}", var3);
            throw new RuntimeException(var3);
        }
    }

    public static <T> T getBeanFromObject(Object object, Class<T> clazz) {
        try {
            return object == null ? null : (new Gson()).fromJson((new GsonBuilder()).create().toJson(object), clazz);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T getBeanWithCollection(String jsonString) {
        try {
            if (jsonString == null) {
                return null;
            } else {
                Type type = (new TypeToken<T>() {
                }).getType();
                return (new Gson()).fromJson(jsonString, type);
            }
        } catch (Exception var2) {
            LOGGER.error("{}", var2);
            throw new RuntimeException(var2);
        }
    }

    public static Map<String, Object> getMapObject(String jsonString) {
        JSONParser parser = new JSONParser();

        try {
            return (Map)parser.parse(jsonString.trim(), getContainerFactory());
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String toString(Object object) {
        try {
            return object == null ? null : (new GsonBuilder()).create().toJson(object);
        } catch (Exception var2) {
            LOGGER.error("{}", var2);
            throw new RuntimeException(var2);
        }
    }

    public static String toPrettyString(Object object) {
        try {
            return object == null ? null : (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss zzz, E").setPrettyPrinting().create().toJson(object);
        } catch (Exception var2) {
            LOGGER.error("{}", var2);
            throw new RuntimeException(var2);
        }
    }

    private static ContainerFactory getContainerFactory() {
        return new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }
        };
    }
}
