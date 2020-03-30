package com.yan.missyou.core.configration;

import com.yan.missyou.core.interceptors.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Argus
 * @className InterceptorConfig
 * @description: 注册用户权限的拦截器
 * @date 2020/3/24 14:25
 * @Version V1.0
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    AuthorizationInterceptor authorizationInterceptor;

    // 注册拦截器
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**");
    }
}