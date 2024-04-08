package com.rookie.stack.im.friendship.domain.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import lombok.Data;

/**
 * @author eumenides
 * @description
 * @date 2024/4/7
 */
@Data
public class AddFriendReq extends BaseRequest {


    private String fromId;


}
