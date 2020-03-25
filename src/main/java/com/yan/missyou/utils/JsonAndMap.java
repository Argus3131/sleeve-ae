package com.yan.missyou.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.missyou.exception.http.ServerErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Argus
 * @className SpuAttributeConverter
 * @description: 可将属性JSON类型的数据库字段转换为Map<String, Object>
 * {"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}
 * @date 2020/3/17 21:24
 * @Version V1.0
 */
@Converter
public class JsonAndMap implements AttributeConverter<Map<String, Object>, String> {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    /**
     * 为了规避JSON查到的为null需要判空
     * @param s 查询得到的JSON串
     * @return HashMap<String, Object>
     */
    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> convertToEntityAttribute(String s) {
        try {
            if (StringUtils.isNotEmpty(s)) {
                return mapper.readValue(s, HashMap.class);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}