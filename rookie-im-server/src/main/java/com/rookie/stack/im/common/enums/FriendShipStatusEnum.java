package com.rookie.stack.im.common.enums;


import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author eumenides
 * @description
 * @date 2024/4/4
 */
@Getter
public enum FriendShipStatusEnum {

    FRIEND_STATUS_NO_FRIEND(0, "未添加"),
    FRIEND_STATUS_NORMAL(1, "正常"),
    FRIEND_STATUS_DELETE(2, "删除"),
    BLACK_STATUS_NORMAL(1, "黑名单正常"),  // 由于与 FRIEND_STATUS_NORMAL 的 code 相同，这可能导致映射冲突
    BLACK_STATUS_BLACKED(2, "已拉黑"),
    ;

    private final int code;
    private final String desc;

    private static final Map<Integer, FriendShipStatusEnum> cache;

    static {
        cache = Arrays.stream(FriendShipStatusEnum.values())
                .collect(Collectors.toMap(FriendShipStatusEnum::getCode, Function.identity(), (existing, replacement) -> existing));
    }

    FriendShipStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FriendShipStatusEnum of(int code) {
        return cache.get(code);
    }
}
