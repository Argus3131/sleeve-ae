package com.yan.missyou.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
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
public class Coupon extends BaseEntity{
    private static final long serialVersionUID = -8672094403249648727L;
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
//    private Long valitiy;
    private String remark;
    private Boolean wholeStore;
    @JsonManagedReference
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "coupon_category",joinColumns = @JoinColumn(name = "couponId"), inverseJoinColumns = @JoinColumn(name = "categoryId"))
    private List<Category> categoryList;
}