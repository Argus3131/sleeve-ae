package com.yan.missyou.service.impl;

import com.yan.missyou.common.CouponStatus;
import com.yan.missyou.dao.ActivityDao;
import com.yan.missyou.dao.CouponDao;
import com.yan.missyou.dao.UserCouponDao;
import com.yan.missyou.dto.LocalUser;
import com.yan.missyou.exception.http.ForbiddenException;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.exception.http.ServerErrorException;
import com.yan.missyou.exception.http.UnAuthenticatedException;
import com.yan.missyou.model.Activity;
import com.yan.missyou.model.Coupon;
import com.yan.missyou.model.User;
import com.yan.missyou.model.UserCoupon;
import com.yan.missyou.service.ICouponService;
import com.yan.missyou.utils.BeanMapper;
import com.yan.missyou.utils.CommonUtils;
import com.yan.missyou.validators.ScopeLevel;
import com.yan.missyou.vo.CouponCategoryVO;
import com.yan.missyou.vo.CouponPureVO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Argus
 * @className ICouponServiceImpl
 * @description: TODO
 * @date 2020/3/25 14:27
 * @Version V1.0
 */
@Service
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private UserCouponDao userCouponDao;

    @Override
    public List<CouponPureVO> getCouponByCategoryId(Long cid) {
        // 不能是过期的优惠券
        Date now = new Date();
        List<Coupon> coupons = couponDao.getCouponByCategory(cid, now);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons.stream().map(coupon -> BeanMapper.map(coupon, CouponPureVO.class)).collect(Collectors.toList());
    }

    @Override
    public List<CouponPureVO> getWholeStoreCoupons() {
        // 不能是过期的优惠券
        Date now = new Date();
        List<Coupon> coupons = couponDao.findByWholeStore(true, now);
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return coupons.stream().map(coupon -> BeanMapper.map(coupon, CouponPureVO.class)).collect(Collectors.toList());
    }

    /**
     * 这边要借助ThreadLocal 配合拦截器对于一开始的用户数据保存后 去实现全局的数据共享
     * ThreadLocal是除了加锁这种同步方式之外的一种保证一种规避多线程访问出现线程不安全的方法
     * 每个http的不同用户请求 相当于boot的多个线程
     *
     * @param cid
     */
    @Override
    public Boolean collectCoupon(Long cid) {
        HashMap<String, Object> map = LocalUser.localUser.get();
        // 获取uid
        User user = Optional.of((User) map.get("user"))
                .orElseThrow(() -> new UnAuthenticatedException(10005));
        Long uid = user.getId();
        // 获取优惠券 查看活动是否存在 因为业务规定活动 下面包含优惠券 1对多
        // 获取cid 查询活动是否存在 通过导航属性join连表的 从表id查询
        Optional<Activity> activityOptional = activityDao.getByCouponListId(cid);
        Date now = new Date();
        Activity activity = activityOptional.orElseThrow(() -> new NotFoundException(40010));
        // 活动是否过期
        boolean isIn = CommonUtils.isTimeIn(now, activity.getStartTime(), activity.getEndTime());
        if (!isIn) {
            throw new NotFoundException(40004);
        }
        // 查询优惠券是否存在
        Optional<Coupon> couponOptional = couponDao.findById(cid, now);
        couponOptional.orElseThrow(() -> new NotFoundException(40004));
        // 查询用户是否领取过
        Optional<UserCoupon> userCoupon = userCouponDao.getUserCoupon(uid, cid);
        if (!userCoupon.isPresent()) {
            UserCoupon uc = UserCoupon.builder()
                    .userId(uid)
                    .couponId(cid)
                    .status(CouponStatus.AVAILABLE.getStatus())
                    .createTime(now)
                    .updateTime(now)
                    .build();
            UserCoupon save = userCouponDao.save(uc);
            return save.getId() >= 0;
        } else {
            throw new ForbiddenException(40006);
        }

    }

    /**
     * 查看我的当前的优惠券
     *
     * @param status
     * @return
     */
    @Override
    public List<CouponPureVO> viewMyCoupon(@Positive Integer status) {
        // 假如不存在 此类型的券直接返回
        if (CouponStatus.getStatus(status) == null) throw new NotFoundException(40009);
        // 获取uid
        User user = LocalUser.getUser();
        if (user == null) {
            throw new UnAuthenticatedException(10005);
        }
        Long uid = user.getId();
        Date now = new Date();
        switch (CouponStatus.getStatus(status)) {
            case AVAILABLE:
                List<Coupon> couponsAvailable = userCouponDao.findCouponAvailable(uid, status, now);
                if (couponsAvailable.isEmpty()) {
                    return Collections.emptyList();
                }
                return CouponPureVO.getCouponPureVOList(couponsAvailable);
            case USED:
                List<Coupon> couponsUsed = userCouponDao.findCouponUsed(uid, status);
                if (couponsUsed.isEmpty()) {
                    return Collections.emptyList();
                }
                return CouponPureVO.getCouponPureVOList(couponsUsed);
            case EXPIRED:
                List<Coupon> couponsExpired = userCouponDao.findCouponExpired(uid, status, now);
                if (couponsExpired.isEmpty()) {
                    return Collections.emptyList();
                }
                return CouponPureVO.getCouponPureVOList(couponsExpired);
            default:
                throw new NotFoundException(40009);
        }
    }

    @Override
    public List<CouponCategoryVO> collectCouponWithCategory(@Positive Integer status) {
        // 假如不存在 此类型的券直接返回
        if (CouponStatus.getStatus(status) == null) throw new NotFoundException(40009);
        // 获取uid
        User user = LocalUser.getUser();
        if (user == null) {
            throw new UnAuthenticatedException(10005);
        }
        Long uid = user.getId();
        Date now = new Date();
        List<Coupon> couponsAvailable = userCouponDao.findCouponAvailable(uid, status, now);
        if (couponsAvailable.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponCategoryVO.getCouponCategoryVOList(couponsAvailable);
    }

}