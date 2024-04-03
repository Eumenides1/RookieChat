package com.rookie.stack.im.user.domain.vo.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import lombok.Data;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/3
 */
@Data
public class GetUserInfoReq extends BaseRequest {
    private List<String> userIds;
}
