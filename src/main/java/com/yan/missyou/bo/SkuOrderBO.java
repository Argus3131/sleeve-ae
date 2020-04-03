package com.yan.missyou.bo;

import com.yan.missyou.dto.OrderDTO;
import com.yan.missyou.dto.SkuInfoDTO;
import com.yan.missyou.model.Sku;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author Argus
 * @className OrderSkuBO
 * @description: 记录多个Sku包含的一些待校验信息
 * @date 2020/4/2 18:11
 * @Version V1.0
 */
@ToString
@Getter
@Setter
public class SkuOrderBO {

    private BigDecimal price;

    private Integer count;

    private Long categoryId;



    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.price = sku.getActcalMoney();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }


    public BigDecimal getSkuPrice() {
        return this.price.multiply(new BigDecimal(count));
    }
}