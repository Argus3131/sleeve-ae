package com.yan.missyou.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Argus
 * @className UserCoupon
 * @description: 用户使用的优惠券 具有实际业务意义
 * @date 2020/3/24 21:30
 * @Version V1.0
 */
@ToString
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCoupon implements Serializable {
    private static final long serialVersionUID = -2948734031831552123L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long couponId;
    // 枚举 1:未使用，2：已使用， 3：已过期
    private Integer status;
    private Date createTime;
    // 记录用户的优惠券在哪个订单里面使用了
    private Long orderId;
    private Date updateTime;
}