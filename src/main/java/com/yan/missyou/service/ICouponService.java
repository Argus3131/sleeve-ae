package com.yan.missyou.service;

import com.yan.missyou.model.Coupon;
import com.yan.missyou.vo.CouponCategoryVO;
import com.yan.missyou.vo.CouponPureVO;

import java.util.List;

/**
 * @author Argus
 * @className ICouponService
 * @description: TODO
 * @date 2020/3/25 14:26
 * @Version V1.0
 */
public interface ICouponService {

    List<CouponPureVO> getCouponByCategoryId(Long id);

    List<CouponPureVO> getWholeStoreCoupons();

    Boolean collectCoupon(Long cid);

    List<CouponPureVO> viewMyCoupon(Integer status);

    List<CouponCategoryVO> collectCouponWithCategory(Integer status);
}
