package com.yan.missyou.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Argus
 * @className Theme
 * @description: TODO
 * @date 2020/3/16 10:36
 * @Version V1.0
 */
@Entity
@Getter
@Setter
public class Theme extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5275891955960796480L;
    @Id
    private Long id;
    private String title;
    private String description;
    private String name;
    private String tplName;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private Boolean online;
    /**
     * 建立导航属性
     * 因为theme会包含多个spu 但是一个spu可能会在不同专题中出现
     * 使用@JsonManagedReference 能避免一个多对多死循环序列化
     * 当然这边还有一个解决方案就是用VO去 拷贝属性
     */
    @JsonManagedReference
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "theme_spu",joinColumns = @JoinColumn(name = "themeId"), inverseJoinColumns = @JoinColumn(name = "spuId"))
    private List<Spu> spuList = new ArrayList<>(0);
}