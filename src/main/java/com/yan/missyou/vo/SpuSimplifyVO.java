package com.yan.missyou.vo;

import com.yan.missyou.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Argus
 * @className SpuSimplifyVO
 * @description: TODO
 * @date 2020/3/16 19:14
 * @Version V1.0
 */
@Getter
@Setter
public class SpuSimplifyVO extends BaseEntity{
    private static final long serialVersionUID = 6888062383969599629L;
    private Long id;
    private String title;
    private String subtitle;
    private String img;
    private String forThemeImg;
    private String discountPrice;
    private String description;
    private String tags;
    private Integer sketchSpecId;
//    private String maxPurchaseQuantity;
//    private String minPurchaseQuantity;
}