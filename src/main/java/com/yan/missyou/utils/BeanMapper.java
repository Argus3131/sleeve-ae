package com.yan.missyou.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Argus
 * @className BeanMapper
 * @description: TODO
 * @date 2020/3/16 14:24
 * @Version V1.0
 */
public class BeanMapper {

    private static Mapper dozer = DozerBeanMapperBuilder.buildDefault();

    /**
     * 普通对象转换 比如: ADO -> AVO
     * @param: [source 源对象, destinationClass 目标对象class]
     * @return: T
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        if (source == null) {
            return null;
        }
        return dozer.map(source, destinationClass);
    }

    /**
     * List转换 比如: List<ADO> -> List<AVO>
     * @param: [sourceList 源对象List, destinationClass 目标对象class]
     * @return: java.util.List<T>
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        if (sourceList == null) {
            return destinationList;
        }
        for (Object sourceObject : sourceList) {
            if (sourceObject != null) {
                T destinationObject = dozer.map(sourceObject, destinationClass);
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }
}
