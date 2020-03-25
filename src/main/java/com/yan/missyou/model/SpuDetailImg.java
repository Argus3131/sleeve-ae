package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Argus
 * @className SpuDetailImg
 * @description: TODO
 * @date 2020/3/16 18:24
 * @Version V1.0
 */
@Entity
@Getter
@Setter
public class SpuDetailImg extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1741149160476732695L;
    @Id
    private int id;
    private String img;
    private Integer spuId;
    private int index;
}