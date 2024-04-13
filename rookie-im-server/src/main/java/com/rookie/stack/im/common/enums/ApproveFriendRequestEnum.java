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
public enum ApproveFriendRequestEnum {
    /**
     * 待审批
     */
    WAIT(0),
    /**
     * 审批通过
     */
    APPROVE(1 ),
    /**
     * 拒绝
     */
    DISAPPROVE(2 ),
    ;

    private final int code;

    private static final Map<Integer, ApproveFriendRequestEnum> cache;

    static {
        cache = Arrays.stream(ApproveFriendRequestEnum.values())
                .collect(Collectors.toMap(ApproveFriendRequestEnum::getCode, Function.identity(), (existing, replacement) -> existing));
    }

    ApproveFriendRequestEnum(int code) {
        this.code = code;
    }

    public static ApproveFriendRequestEnum of(int code) {
        return cache.get(code);
    }
}
