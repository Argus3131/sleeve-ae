package com.yan.missyou.dto;

import com.yan.missyou.common.LoginType;
import com.yan.missyou.validators.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Argus
 * @className TokenDTO
 * @description: 作为接受前端 登录相关的参数设定的
 * @date 2020/3/20 21:48
 * @Version V1.0
 */
@Getter
@Setter
public class TokenDTO {
    @NotBlank(message = "account不允许为空")
    private String account;
    @TokenPassword(min=6,max=30,message = "{token.password}")
    private String password;

    private LoginType type;
}