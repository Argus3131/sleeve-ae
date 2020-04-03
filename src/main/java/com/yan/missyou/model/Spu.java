package com.yan.missyou.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Argus
 * @className Spu
 * @description: @where 过滤不存在的商品
 * @date 2020/3/16 10:38
 * @Version V1.0
 */
@Where(clause = "delete_time is null And online = 1")
@Entity
@Getter
@Setter
public class Spu extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -6119699006505529591L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subtitle;
    private Long categoryId;
    private Long rootCategoryId;
    private Boolean online;
    private String price;
    private Integer sketchSpecId;
    private Integer defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
//    private Object spuThemeImg;
    private String forThemeImg;
    @OneToMany(fetch = FetchType.LAZY) //关系维护端
    @JoinColumn(name = "spuId")
    private List<Sku> skuList; //sku 关系被维护端
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;

    //多对多关系映射 https://blog.csdn.net/qq_35357001/article/details/55505659
    @JsonBackReference
    @ManyToMany(fetch=FetchType.LAZY,mappedBy="spuList")
    private List<Theme> themeList = new ArrayList<>(0);
}