package com.yan.missyou.controller;

import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.model.Activity;
import com.yan.missyou.service.IActivityService;
import com.yan.missyou.vo.ActivityCouponVO;
import com.yan.missyou.vo.ActivityPureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Argus
 * @className ActivityController
 * @description: TODO
 * @date 2020/3/25 11:12
 * @Version V1.0
 */
@RestController
@RequestMapping("/v1/activity/name")
public class ActivityController {
    @Autowired
    private IActivityService iActivityService;

    /**
     * 获取活动（不包含优惠券数据）
     * Category和Coupon之间的关系：
     * 当前的SPU商品所属的分类 能使用的优惠券
     * @param name
     * @return
     */
    @GetMapping("/{name}")
    public ActivityPureVO getHomeActivity(@PathVariable String name) {
        Activity activity = iActivityService.getHomeActivityByName(name);
        if (null == activity) {
            throw new NotFoundException(40001);
        }
        return new ActivityPureVO(activity);
    }

    /**
     * 获取活动（携带优惠券数据）
     * @param name
     * @return
     */
    @GetMapping("/{name}/with_spu")
    public ActivityCouponVO getActivityWithCoupons(@PathVariable String name) {
        Activity activity = iActivityService.getHomeActivityByName(name);
        if (null == activity) {
            throw new NotFoundException(40001);
        }
        return new ActivityCouponVO(activity);
    }



}