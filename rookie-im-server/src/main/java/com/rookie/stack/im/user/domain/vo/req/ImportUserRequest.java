package com.rookie.stack.im.user.domain.vo.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import com.rookie.stack.im.user.domain.dto.ImportUserEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Data
public class ImportUserRequest extends BaseRequest {

    @ApiModelProperty("导入用户资料列表")
    private  List<@Valid ImportUserEntity> userList;

}
