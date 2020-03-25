package com.yan.missyou.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.yan.missyou.exception.http.ForbiddenException;
import com.yan.missyou.exception.http.UnAuthenticatedException;
import com.yan.missyou.utils.JWTToken;
import com.yan.missyou.validators.ScopeLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Argus
 * @className AuthorizationInterceptor
 * @description: TODO
 * @date 2020/3/24 13:45
 * @Version V1.0
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取scopeLevel注解
        ScopeLevel scopeLevel = this.getScopeLevel(handler);
        if (null == scopeLevel) {
            return true;
        }
        // 存在注解校验
        String header = request.getHeader("Authorization");
        // 执行认证
        if (StringUtils.isBlank(header)) {
            throw new UnAuthenticatedException(10004);
        }
        // token 约定 bearer
        if (!header.startsWith("Bearer ")) {
            throw new UnAuthenticatedException(10004);
        }
        String[] tokens = header.split(" ");
        // 防止缓存清空 数组长度为1 娶不到值而报错
        if (tokens.length != 2) {
            throw new UnAuthenticatedException(10004);
        }
        String token = tokens[1];
        // 解析token
        Map<String, Claim> claims = JWTToken.verifyAndGetClaims(token);
        int level = scopeLevel.level();
        if (null == claims) {
            throw new UnAuthenticatedException(10004);
        }
        return this.verifyPermission(claims, level);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private Boolean verifyPermission(Map<String, Claim> claims,Integer level) {
//        String uid = claims.get("uid").asString();
        System.out.println(claims);
        Integer scope = claims.get("scope").asInt();
        System.out.println(scope);
        Long now = claims.get("uid").asLong();
        System.out.println(now);
        if (scope == null) {
            throw new UnAuthenticatedException(10004);
        }
        // 约定 当前访问等级 >= 接口等级有权限
        return level >= scope;
    }

    private ScopeLevel getScopeLevel(Object handler) {
        // 如果映射到方法  则需要校验
        if ((handler instanceof HandlerMethod)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //检查是否有passtoken注释，有则跳过认证，注意:其中这个注解多余了
            if (method.isAnnotationPresent(ScopeLevel.class)) {
                ScopeLevel authorizeToken = method.getAnnotation(ScopeLevel.class);
                return authorizeToken;
            }

        }
        return null;
    }
}