package com.yan.missyou.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Argus
 * @className DisHalfUp
 * @description: TODO
 * @date 2020/4/1 11:01
 * @Version V1.0
 */
@Component
public class DisHalfUp implements FullOff{
    @Override
    public BigDecimal discount(BigDecimal money) {
        return money.setScale(2, BigDecimal.ROUND_UP);
    }
}