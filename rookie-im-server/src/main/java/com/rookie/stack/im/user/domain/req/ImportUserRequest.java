package com.rookie.stack.im.user.domain.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import com.rookie.stack.im.user.domain.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Data
public class ImportUserRequest extends BaseRequest {

    private  List<User> userList;

}
