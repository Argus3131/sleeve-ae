package com.yan.missyou.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.missyou.exception.http.ServerErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://notemi.cn/entity-attribute-type-converter-spring-data-jpa-attributeconverter.html
 *
 * @author Argus
 * @className JacksonUtil
 * @description: 泛型JSON序列化与反序列化转换方法
 * @date 2020/3/17 23:23
 * @Version V1.0
 */
@Component
public class GenericAndJson {
    public static ObjectMapper mapper;

    /**
     * 大前提 打上Component注解放入ioc中 不然spring无法识别
     * 使用构造函数注入 Jackson是Springboot内置的json转换类库
     *
     * @param mapper jackson单例
     */
    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }


    /**
     * 转换为json字符串
     *
     * @param t 可以将比如List或Map或pojo转换成JSON
     * @return
     */
    public static <T> String ObjToJson(T t) {
        if (t == null) return null;
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    /**
     * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
     *
     * @param jsonStr
     * @param tClass
     * @return
     */
//    public static <T> T jsonToBean(String jsonStr, Class<T> tClass) {
//
//        try {
//            return mapper.readValue(jsonStr, tClass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * json数组转List
     *
     * @param jsonStr
     * @param tr
     * @return
     */
    public static <T> T jsonToObjOrCollection(String jsonStr, TypeReference<T> tr) {
        if (StringUtils.isEmpty(jsonStr)) return null;
        try {
            return mapper.readValue(jsonStr, tr);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    /**
     * 不推荐 会在运行时将对象转换成hashmap
     * @param jsonStr
     * @param <T>
     * @return
     */
//    public static <T> List<T> jsonToObjOrCollection(String jsonStr) {
//        if (StringUtils.isEmpty(jsonStr)) return null;
//        try {
//            List<T> tList = mapper.readValue(jsonStr, new TypeReference<List<T>>(){});
//
//            return tList;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ServerErrorException(999);
//        }
//    }

}