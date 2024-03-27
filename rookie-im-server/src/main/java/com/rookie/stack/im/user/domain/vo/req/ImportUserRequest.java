package com.rookie.stack.im.user.domain.vo.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import com.rookie.stack.im.user.domain.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Data
public class ImportUserRequest extends BaseRequest {

    @ApiModelProperty("导入用户资料列表")
    private  List<User> userList;

}
