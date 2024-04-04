package com.rookie.stack.im.friendship.domain.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import com.rookie.stack.im.common.enums.FriendShipStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/4
 */
@Data
public class ImportFriendShipReq extends BaseRequest {

    @NotBlank(message = "fromId不能为空")
    private String fromId;

    private List<ImportFriendDto> friendItem;

    @Data
    public static class ImportFriendDto{

        private String toId;

        private String remark;

        private String addSource;

        private Integer status = FriendShipStatusEnum.FRIEND_STATUS_NO_FRIEND.getCode();

        private Integer black = FriendShipStatusEnum.BLACK_STATUS_NORMAL.getCode();
    }


}
