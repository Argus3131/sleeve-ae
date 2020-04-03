package com.yan.missyou.common;

import com.yan.missyou.exception.http.ServerErrorException;

import java.util.stream.Stream;

/**
 * @author Argus
 * @className OrderStatus
 * @description: TODO
 * @date 2020/4/3 10:11
 * @Version V1.0
 */
public enum OrderStatus {
    ALL(0,"全部"),
    UNPAID(1,"待支付"),
    PAID(2,"已支付"),
    DELIVERED(3,"已发货"),
    FINISHED(4,"已完成"),
    CANCELED(5,"已取消"),

    // 预扣除库存 不存在以下两种情况 避免超卖
    PAID_BUT_OUT_OF(21,"已支付，但无货或库存不足"),
    DEAL_OUT_OF(22,"已处理缺货但已支付的情况");


    private int value;

    private OrderStatus(int value, String text) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static OrderStatus toType(int value) {
        return Stream.of(OrderStatus.values())
                    .filter(c->c.value==value)
                    .findAny().orElseThrow(()->new ServerErrorException(999));
    }
}