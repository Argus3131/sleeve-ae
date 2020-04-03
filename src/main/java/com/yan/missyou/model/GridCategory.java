package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Argus
 * @className GridCategory
 * @description: TODO
 * @date 2020/3/18 13:19
 * @Version V1.0
 */
@Entity
@Getter
@Setter
@Where(clause = "delete_time is null")
public class GridCategory extends BaseEntity {
    private static final long serialVersionUID = -6043931951848274737L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String img;
    private String name;
    private Integer categoryId;
    private Integer rootCategoryId;
}