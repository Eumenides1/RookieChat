package com.rookie.stack.im.common.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)){
            return true;
        }else {
            return MobileValidate.isMobile(s);
        }
    }

}
