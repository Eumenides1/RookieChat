package com.rookie.stack.im.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author eumenides
 * @description
 * @date 2024/4/13
 */
@Getter
public enum AllowFriendTypeEnum {
    /**
     * 需要验证
     */
    NEED(0),
    /**
     * 不需要验证
     */
    NOT_NEED(1 ),
    ;

    private final int code;

    private static final Map<Integer, AllowFriendTypeEnum> cache;

    static {
        cache = Arrays.stream(AllowFriendTypeEnum.values())
                .collect(Collectors.toMap(AllowFriendTypeEnum::getCode, Function.identity(), (existing, replacement) -> existing));
    }

    AllowFriendTypeEnum(int code) {
        this.code = code;
    }

    public static AllowFriendTypeEnum of(int code) {
        return cache.get(code);
    }
}
