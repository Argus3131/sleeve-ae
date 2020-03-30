package com.yan.missyou.dao;

import com.yan.missyou.model.Coupon;
import com.yan.missyou.model.Theme;
import com.yan.missyou.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className UserCouponDao
 * @description: TODO
 * @date 2020/3/30 13:54
 * @Version V1.0
 */
@Repository
public interface UserCouponDao extends JpaRepository<UserCoupon, Long> {


    @Query("select uc from UserCoupon uc\n" +
            "where uc.userId = :uid\n" +
            "and uc.couponId = :cid\n")
    Optional<UserCoupon> getUserCoupon(@Param("uid") Long uid, @Param("cid") Long cid);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc \n" +
            "on c.id = uc.couponId\n" +
            "where" +
            " uc.userId = :uid\n" +
            "and uc.status = :status\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null\n")
    List<Coupon> findCouponAvailable(@Param("uid") Long uid, @Param("status") Integer status, @Param("now") Date now);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc \n" +
            "on c.id = uc.couponId\n" +
            "where uc.status = :status\n" +
            "and uc.userId = :uid\n" +
            "and uc.orderId is not null\n" +
            "\n")
    List<Coupon> findCouponUsed(@Param("uid") Long uid, @Param("status") Integer status);

    @Query("select c from Coupon c\n" +
            "join UserCoupon uc \n" +
            "on c.id = uc.couponId\n" +
            "where uc.userId = :uid\n" +
            "and uc.status = :status\n" +
            "and c.endTime < :now\n" +
            "and uc.orderId is null\n")
    List<Coupon> findCouponExpired(@Param("uid") Long uid, @Param("status") Integer status, @Param("now") Date now);
}