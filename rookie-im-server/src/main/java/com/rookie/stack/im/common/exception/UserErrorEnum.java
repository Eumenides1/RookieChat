package com.rookie.stack.im.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
@AllArgsConstructor
@Getter
public enum UserErrorEnum implements ErrorEnum{
    OUTBOUND_IMPORT_USER_LIMIT(-11, "单次上传用户数量超过限制"),
    MODIFY_USER_INFO_ERROR(-12, "修改用户信息错误")

    ;
    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.msg;
    }
}
