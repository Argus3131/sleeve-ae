package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Argus
 * @className BannerItem
 * @description: TODO
 * @date 2020/3/12 23:08
 * @Version V1.0
 */
@Entity
@Getter
@Setter
public class BannerItem extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 8281625482736840311L;
    @Id
    private Long id;
    private String img;
    private String keyword;
    private short type;
    private int bannerId;
    private String name;
}