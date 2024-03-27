package com.rookie.stack.im.common.annotation;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
public class MobileValidate {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$");
    public static boolean isMobile(String mobile){
        if (StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }
}
