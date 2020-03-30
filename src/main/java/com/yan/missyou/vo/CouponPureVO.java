package com.yan.missyou.vo;

import com.yan.missyou.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className CouponPureVO
 * @description: TODO
 * @date 2020/3/25 15:14
 * @Version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class CouponPureVO {
    private Long id;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private String remark;
    private Boolean wholeStore;

    public CouponPureVO(Coupon coupon) {
        try {
            BeanUtils.copyProperties(this, coupon);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static List<CouponPureVO> getCouponPureVOList(List<Coupon> couponList) {
        return couponList.stream().map(CouponPureVO::new).collect(Collectors.toList());
    }
}