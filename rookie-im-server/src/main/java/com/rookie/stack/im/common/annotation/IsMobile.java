package com.rookie.stack.im.common.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = { IsMobileValidator.class }
)
public @interface IsMobile {
    boolean required() default true;
    String message() default "手机号码格式错误";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
