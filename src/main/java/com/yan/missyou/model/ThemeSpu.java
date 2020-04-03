package com.yan.missyou.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Argus
 * @className ThemeSpu
 * @description: TODO
 * @date 2020/3/16 10:36
 * @Version V1.0
 */
@Entity
@Getter
@Setter
@Table(name = "theme_spu", schema = "sleeve", catalog = "")
public class ThemeSpu implements Serializable {
    private static final long serialVersionUID = -3404110134856798648L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int themeId;
    private int spuId;

}