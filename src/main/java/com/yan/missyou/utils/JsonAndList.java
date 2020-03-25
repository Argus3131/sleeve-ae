package com.yan.missyou.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yan.missyou.exception.http.ServerErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * https://notemi.cn/entity-attribute-type-converter-spring-data-jpa-attributeconverter.html
 * @author Argus
 * @className JsonAndList
 * @description: 转换数组类型的JSON
 *  [{"key": "颜色", "value": "黑色", "key_id": 1, "value_id": 12}]
 * @date 2020/3/17 22:34
 * @Version V1.0
 */
@Converter
public class JsonAndList  implements AttributeConverter<List<Object>, String> {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            return mapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> convertToEntityAttribute(String s) {
        try {
           if(StringUtils.isNotEmpty(s)) {
               return mapper.readValue(s,List.class);
           }
             return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerErrorException(999);
        }
    }
}