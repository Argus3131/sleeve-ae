package com.yan.missyou.controller;

import com.yan.missyou.common.CouponStatus;
import com.yan.missyou.exception.http.InsertSuccess;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.model.Activity;
import com.yan.missyou.model.Coupon;
import com.yan.missyou.service.ICouponService;
import com.yan.missyou.validators.ScopeLevel;
import com.yan.missyou.vo.ActivityCouponVO;
import com.yan.missyou.vo.CouponCategoryVO;
import com.yan.missyou.vo.CouponPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * @author Argus
 * @className ActivityController
 * @description: TODO
 * @date 2020/3/25 11:12
 * @Version V1.0
 */
@RestController
@RequestMapping("/v1/coupon")
public class CouponController {
    @Autowired
    private ICouponService iCouponService;

    /**
     * 获取某个二级分类的可用优惠券
     *
     * @param cid
     * @return
     */
    @GetMapping("/by/category/{cid}")
    public List<CouponPureVO> getCouponsByCategoryId(@PositiveOrZero @PathVariable Long cid) {
        List<CouponPureVO> coupons = iCouponService.getCouponByCategoryId(cid);
        if (null == coupons) {
            throw new NotFoundException(40000);
        }
        return coupons;
    }

    /**
     * 获取全场通用优惠券
     * <p>
     * Category和Coupon之间的关系：
     * 当前的SPU商品所属的分类 能使用的优惠券
     * Activity 和 Coupon 是 多对多的关系
     * 拆分为1对多 一个活动 多个Category
     * 而Coupon对应多个Activity的问题是通过模板这个CouponTemplate去解决的
     *
     * @return
     */
    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCoupons() {
        List<CouponPureVO> coupons = iCouponService.getWholeStoreCoupons();
        if (null == coupons) {
            throw new NotFoundException(40000);
        }
        return coupons;
    }

    /**
     * 领取可用优惠券
     *
     * @param id
     */
    @ScopeLevel(level = 8)
    @PostMapping("/collect/{id}")
    public void collectCouponId(@PathVariable Long id) {
        Boolean isGet = iCouponService.collectCoupon(id);
        if (isGet) {
            InsertSuccess.success(0);
        }
    }

    /**
     * 获取我的优惠券
     */
    @ScopeLevel(level = 8)
    @GetMapping("/myselft/by/status/{status}")
    public List<CouponPureVO> viewMyCoupon(@PathVariable @Positive Integer status) {
        return iCouponService.viewMyCoupon(status);
    }

    /**
     * 获取我可用的优惠券(带分类数据)
     * 此接口用于下单时检验用户的优惠券是否可以使用，所以需要携带分类数据
     * @return
     */
    @ScopeLevel(level = 8)
    @GetMapping("/myselft/available/with_category")
    public List<CouponCategoryVO> collectCouponWithCategory() {
        return iCouponService.collectCouponWithCategory(CouponStatus.AVAILABLE.getStatus());
    }


}