package com.rookie.stack.common.enums;

/**
 * @author eumenides
 * @description
 * @date 2024/4/28
 */
public enum ImConnectStatusEnum {
    ONLINE_STATUS(1),
    OFFLINE_STATUS(2);

    private Integer code;

    ImConnectStatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
