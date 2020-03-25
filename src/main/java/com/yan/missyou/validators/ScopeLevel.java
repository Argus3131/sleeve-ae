package com.yan.missyou.validators;

import java.lang.annotation.*;

/**
 * @author Argus
 * @className ScopeLevel
 * @description: 设定注解等级 8的需要验证
 *               高等级可以访问低等级的
 * @date 2020/3/24 13:36
 * @Version V1.0
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeLevel {
    int level() default 8;
}