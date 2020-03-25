package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Argus
 * @className SpuImg
 * @description: TODO
 * @date 2020/3/16 18:24
 * @Version V1.0
 */
@Entity
@Getter
@Setter
public class SpuImg extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -2130888936875726764L;
    @Id
    private int id;
    private String img;
    private Integer spuId;

}