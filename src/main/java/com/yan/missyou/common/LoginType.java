package com.yan.missyou.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Argus
 * @className LoginType
 * @description: TODO
 * @date 2020/3/21 10:42
 * @Version V1.0
 */
public enum LoginType {

    USER_WX(0, "WECHAT"),
    USER_EMAIL(1, "EMAIL");

    //    // 成员变量
    private String description;
    private Integer value;

    private LoginType(Integer value,String description ) {
        this.value = value;
        this.description = description;
    }

}
