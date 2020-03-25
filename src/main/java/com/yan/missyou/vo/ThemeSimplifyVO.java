package com.yan.missyou.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author Argus
 * @className ThemeSimplifyVo
 * @description: TODO
 * @date 2020/3/16 13:56
 * @Version V1.0
 */
@Getter
@Setter
public class ThemeSimplifyVO implements Serializable {
    private static final long serialVersionUID = -6190442667641342479L;
    private int id;
    private String title;
    private String description;
    private String name;
    private String tplName;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private Boolean online;
}