package com.yan.missyou.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yan.missyou.utils.GenericAndJson;
import com.yan.missyou.utils.JsonAndList;
import com.yan.missyou.utils.JsonAndMap;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Argus
 * @className Sku
 * @description: TODO
 * @date 2020/3/16 18:24
 * @Version V1.0
 */
@Where(clause = "delete_time is null And online = 1")
@Entity
@Getter
@Setter
public class Sku {
    @Id
    private int id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    private int spuId;
    // 用String去接收Json数据转成JSON字符串
    // todo 为了解决Json转换成List或者Map的结构 给前端 提出的一个解决方案
    private String specs;

    /**
     * 修改getter和setter 让前端拿到的是转换后的数据
     * 模型类 可以适度的封装一些业务逻辑
     * @return List<Spec>
     */
    public List<Spec> getSpecs() {
        return GenericAndJson.jsonToObjOrCollection(this.specs, new TypeReference<List<Spec>>() {
        });
    }

    public void setSpecs(List<Spec> specList) {
        this.specs = GenericAndJson.ObjToJson(specList);
    }

    private String code;
    private int stock;
    private Integer categoryId;
    private Integer rootCategoryId;
//    private String test;
}