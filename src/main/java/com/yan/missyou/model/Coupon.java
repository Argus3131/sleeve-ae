package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Argus
 * @className Coupon
 * @description: TODO
 * @date 2020/3/24 21:32
 * @Version V1.0
 */
@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class Coupon {
    @Id
    private Long id;
    private Long activityId;
    private String title;
    private Date startTime;
    private Date endTime;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal rate;
    private Integer type;
    private Long valitiy;
    private String remark;
    private Boolean wholeStore;
}