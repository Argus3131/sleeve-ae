package com.yan.missyou.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.yan.missyou.dao.UserDao;
import com.yan.missyou.dto.LocalUser;
import com.yan.missyou.exception.http.ForbiddenException;
import com.yan.missyou.exception.http.NotFoundException;
import com.yan.missyou.exception.http.UnAuthenticatedException;
import com.yan.missyou.model.User;
import com.yan.missyou.utils.JWTToken;
import com.yan.missyou.validators.ScopeLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * https://www.jianshu.com/p/d242d1f21633
 * @author Argus
 * @className AuthorizationInterceptor
 * @description: 拦截用户有@ScopeLevel注解 的请求判断是否有token
 * @date 2020/3/24 13:45
 * @Version V1.0
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDao userDao;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //log.info("---------------------开始进入请求地址拦截----------------------------");
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
        boolean valid = this.verifyPermission(claims, level);
        // 校验通过 保存用户信息到当前线程 ThreadLocal是除了加锁这种同步方式之外的一种保证一种规避多线程访问出现线程不安全的方法
        if (valid) {
            Integer scope = claims.get("scope").asInt();
            Long uid = claims.get("uid").asLong();
            // 查数据库
            System.out.println(uid);
            User user = userDao.findById(uid)
                    .orElseThrow(() -> {
                        return new NotFoundException(20002);
                    });
            LocalUser.setLocalUser(user,scope);
        }
        return valid;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //log.info("---------------视图渲染之后的操作-------------------------0");
        LocalUser.localUser.set(null);
    }


    /**
     * 校验接口是否有权限
     * @param claims
     * @param level
     * @return
     */
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