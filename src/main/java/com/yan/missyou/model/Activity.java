package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Argus
 * @className Activity
 * @description: TODO
 * @date 2020/3/24 21:51
 * @Version V1.0
 */
@Entity
@Getter
@Setter
public class Activity extends BaseEntity{
    private static final long serialVersionUID = 2958902154675393098L;
    @Id
    private Long id;
    private String title;
    private String description;
    private Date startTime;
    private Date endTime;
    private String remark;
    private Boolean online;
    private String entranceImg;
    private String internalTopImg;
    private String name;
}