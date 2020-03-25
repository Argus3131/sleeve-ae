package com.yan.missyou.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Argus
 * @className TokenPassword
 * @description: TODO
 * @date 2020/3/21 9:47
 * @Version V1.0
 */
@Documented
@Target({ ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TokenConstraintValidator.class)
public @interface TokenPassword {
    int min() default 6;

    int max() default 32;

    String message() default "字段不在指定范围之内";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
