package com.rookie.stack.im.friendship.domain.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import com.rookie.stack.im.friendship.domain.dto.FriendDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author eumenides
 * @description
 * @date 2024/4/7
 */
@Data
public class AddFriendReq extends BaseRequest {

    @NotBlank( message = "fromId不能为空")
    private String fromId;

    private FriendDto toItem;


}
