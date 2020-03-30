package com.yan.missyou.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Argus
 * @className CouponCategory
 * @description: TODO
 * @date 2020/3/25 14:21
 * @Version V1.0
 */
@Entity
@Table(name = "coupon_category", schema = "sleeve", catalog = "")
public class CouponCategory {
    @Id
    private Long id;
    private Long categoryId;
    private Long couponId;
}