package com.yan.missyou.controller;

import com.yan.missyou.dto.TokenDTO;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.exception.http.ServerErrorException;
import com.yan.missyou.service.IAuthenticationService;
import com.yan.missyou.service.IWxAuthenticationService;
import com.yan.missyou.utils.JWTToken;
import com.yan.missyou.validators.ScopeLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Argus
 * @className TokenController
 * @description: TODO
 * @date 2020/3/21 9:39
 * @Version V1.0
 */
@RestController
@RequestMapping("/v1")
public class TokenController {
    @Autowired
    private IWxAuthenticationService wxAuthenticationService;

    /**
     * 建议返回map 这样前端拿到的是js对象 而不是一个字符串
     * @param userData
     * @return
     */
    @PostMapping("/token")
    public Map<String, String> getToken(@RequestBody @Validated TokenDTO userData) {
        Map<String, String> map = new HashMap<>();
        String token = null;
        switch (userData.getType()) {
            case USER_EMAIL:
                break;
            case USER_WX:
                String code = userData.getAccount();
                // 微信登录 凭证code码
                token = wxAuthenticationService.code2session(code);
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token",token);
        return map;
    }

    /**
     * 给前端校验token是否合法是否过期
     * @param token
     * @return
     */
    @PostMapping("/verify")
    public Map<String,Boolean> verifyToken(@RequestBody String token) {
        System.out.println(token);
        Boolean valid = JWTToken.verify(token);
        Map<String,Boolean> map = new HashMap<>();
        map.put("is_valid",valid);
        return map;
    }
}