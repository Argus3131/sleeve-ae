package com.yan.missyou.validators;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Argus
 * @className TokenConstraintValidator
 * @description: TODO
 * @date 2020/3/21 9:53
 * @Version V1.0
 */
public class TokenConstraintValidator implements ConstraintValidator<TokenPassword, String> {
    private Integer min;
    private Integer max;

    /**
     * 获取打上注解后拿到的参数的值
     *
     * @param constraintAnnotation
     */
    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return value.length() <= this.max && value.length() >= this.min;
     }


}