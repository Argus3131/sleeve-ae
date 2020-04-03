package com.yan.missyou.model;

import com.yan.missyou.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Argus
 * @className OrderSku
 * @description: 保存OrderDTO 和 Sku
 * @date 2020/4/1 10:40
 * @Version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderSku {
    // sku的 id
    private Long id;
    private Long spuId;
    private BigDecimal finalPrice;
    private BigDecimal singlePrice;
    private List<String> specValues;
    private Integer count;
    private String img;
    private String title;


    public OrderSku(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.singlePrice = sku.getActcalMoney();
        this.finalPrice = sku.getActcalMoney().multiply(new BigDecimal(skuInfoDTO.getCount()));
        this.count = skuInfoDTO.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValueList();
    }
}