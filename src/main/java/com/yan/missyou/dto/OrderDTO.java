package com.yan.missyou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className OrderDTO
 * @description: 接收前端 发送的订单信息
 * @date 2020/3/31 13:05
 * @Version V1.0
 */
@ToString
@Getter
@Setter
public class OrderDTO {
    @DecimalMin(value = "0.00",message = "不在合法范围内")
    @DecimalMax(value = "99999999.99",message = "不在合法范围内")
    @JsonProperty("total_price")
    private BigDecimal totalPrice;
    // 前端计算的价格
    @JsonProperty("final_total_price")
    private BigDecimal finalTotalPrice;
    // 前端的优惠券
    @JsonProperty("coupon_id")
    private Long couponId;
    // 前端的sku 购买信息
    @JsonProperty("sku_info_list")
    private List<SkuInfoDTO> skuInfoList;
    // 地址
    private OrderAddressDTO address;

    /**
     * 返回购买的SKU的id集合
     * @return
     */
    public List<Long> getSkuIds() {
        return skuInfoList.stream().map(SkuInfoDTO::getId).collect(Collectors.toList());
    }
}