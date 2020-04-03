package com.yan.missyou.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author Argus
 * @className Category
 * @description: {List1级节点,List2级节点}
 * @date 2020/3/18 12:07
 * @Version V1.0
 */
@Where(clause = "delete_time is null And online = 1")
@Entity
@Getter
@Setter
public class Category extends BaseEntity{
    private static final long serialVersionUID = 3196433383135956450L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isRoot;
    private Integer parentId;
    private String img;
    private Integer index;
    private Integer online;
    private Integer level;
    @ManyToMany(fetch=FetchType.LAZY,mappedBy="categoryList")
    @JsonBackReference
    private List<Coupon> couponList;



}