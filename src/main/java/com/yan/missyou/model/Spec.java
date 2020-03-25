package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Argus
 * @className Spec
 * @description: 这个模型类并未在数据库中有对应表但是 是映射了spu的specs字段JSON
 * 为了将其转换 暂时测试得到的是要与数据库字段的key一一对应且相同 否则会为null
 * @date 2020/3/17 21:20
 * @Version V1.0
 */
@Getter
@Setter
public class Spec {
    Long key_id;
    String key;
    Long value_id;
    String value;
}