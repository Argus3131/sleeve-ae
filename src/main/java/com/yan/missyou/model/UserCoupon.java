package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Argus
 * @className UserCoupon
 * @description: 具有实际业务意义的第三张表
 * @date 2020/3/24 21:30
 * @Version V1.0
 */
@Entity
@Getter
@Setter
public class UserCoupon extends BaseEntity{
    private static final long serialVersionUID = -2948734031831552123L;
    @Id
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Date createTime;
    private Integer orderId;
    private Date updateTime;
}