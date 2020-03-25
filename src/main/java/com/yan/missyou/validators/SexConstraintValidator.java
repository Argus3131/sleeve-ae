/**
 * @ClassName SexConstraintValidator
 * @description: TODO
 * @author Argus
 * @Date 2020/3/11 13:57
 * @Version V1.0
 */
package com.yan.missyou.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SexConstraintValidator implements ConstraintValidator<Sex, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && (value.equals("男") || value.equals("女"));
    }
}
