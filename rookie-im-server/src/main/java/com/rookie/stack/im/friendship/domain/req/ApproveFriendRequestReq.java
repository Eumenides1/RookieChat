package com.rookie.stack.im.friendship.domain.req;

import lombok.Data;

/**
 * @author eumenides
 * @description
 * @date 2024/4/22
 */
@Data
public class ApproveFriendRequestReq {
    private Long id;

    //1同意 2拒绝
    private Integer status;
}
