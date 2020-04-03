package com.yan.missyou.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Argus
 * @className Discount
 * @description: TODO
 * @date 2020/4/1 10:59
 * @Version V1.0
 */

public interface FullOff {

    BigDecimal discount(BigDecimal money);
}
