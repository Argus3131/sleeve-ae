package com.yan.missyou.vo;

import com.yan.missyou.model.Activity;
import com.yan.missyou.model.Coupon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className ActivityCouponVO
 * @description: TODO
 * @date 2020/3/25 11:59
 * @Version V1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ActivityCouponVO extends ActivityPureVO {

    List<CouponPureVO> coupons;

    /**
     * vo转换 建议使用这种方式
     * @param activity
     */
    public ActivityCouponVO(Activity activity) {
        // 调用父类构造器去对继承属性赋值
        super(activity);
        this.coupons = activity.getCouponList()
                .stream().map(CouponPureVO::new)
                .collect(Collectors.toList());

    }

//      可以直接放在构造函数里面去实现
//    private CouponPureVO converToVO(Coupon coupon) {
//        CouponPureVO couponPureVO = new CouponPureVO();
//        try {
//            BeanUtils.copyProperties(couponPureVO, coupon);
//        } catch (IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return couponPureVO;
//    }


}