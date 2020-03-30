package com.yan.missyou.common;

import java.util.stream.Stream;

/**
 * @author Argus
 * @className CouponStatus
 * @description: 优惠券状态
 * @date 2020/3/30 11:59
 * @Version V1.0
 */
public enum CouponStatus {
    AVAILABLE(1,"可使用,未过期"),
    USED(2,"已使用"),
    EXPIRED(3,"已过期,未使用");


    CouponStatus(Integer status,String description) {
        this.status = status;
        this.description = description;
    }

    Integer status;
    String description;

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static CouponStatus getStatus(Integer status) {
        return Stream.of(CouponStatus.values())
                .filter(item -> item.status.equals(status))
                .findAny().orElse(null);
    }



}