package com.yan.missyou.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Argus
 * @className SkuInfoDTO
 * @description: TODO
 * @date 2020/3/31 13:07
 * @Version V1.0
 */
@Getter
@Setter
public class SkuInfoDTO {

    private Long id; // 对应sku id

    private Integer count; // 购买数量
}