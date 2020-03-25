package com.yan.missyou.core.configration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 解决jpa no session 懒加载报错问题
 * https://www.oschina.net/question/1431056_2272203
 * @author Argus
 * @className BeanConfig
 * @description: TODO
 * @date 2020/3/24 16:59
 * @Version V1.0
 */
@Component
public class BeanConfig {

    @Bean
    public FilterRegistrationBean registerOpenSessionInViewFilterBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new OpenEntityManagerInViewFilter());
        filterRegistrationBean.setOrder(-10);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*,/webjars/*,/druid/*,/static/*");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}