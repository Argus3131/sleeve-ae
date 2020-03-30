package com.yan.missyou.dao;

import com.yan.missyou.model.Activity;
import com.yan.missyou.model.Coupon;
import com.yan.missyou.vo.CouponPureVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Argus
 * @className CouponDao
 * @description: TODO
 * @date 2020/3/25 14:30
 * @Version V1.0
 */
@Repository
public interface CouponDao extends JpaRepository<Coupon, Long> {

    /**
     * 使用JPQL的优势：
     * 通过join 多对多导航属性
     * 可以直接关联第三张表
     * 这边的时间基于业务要判断 因为一张券是有时效性
     * @param cid
     * @return List<Coupon>
     */
    @Query(value = "select c from Coupon c\n" +
            "join \n" +
            "c.categoryList ca\n" +
            "join\n" +
            "Activity a on a.id = c.activityId\n" +
            "where ca.id = :cid\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n")
    List<Coupon> getCouponByCategory(@Param("cid") Long cid, @Param("now") Date now);


    /**
     * JPQL 一定要使用Model 的属性去映射数据库的字段 否则会报错
     * join 代替 inner join
     *
     * @param wholeStore
     * @param now
     * @return List<Coupon>
     */
    @Query("select c from Coupon c \n" +
            "join Activity a \n" +
            "on a.id = c.activityId \n" +
            "where c.wholeStore = :wholeStore \n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n")
    List<Coupon> findByWholeStore(@Param("wholeStore")Boolean wholeStore,@Param("now") Date now);


    @Query("select c from Coupon c \n" +
            "where c.id = :cid\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n")
    Optional<Coupon> findById(@Param("cid") Long cid,@Param("now")Date now);




}