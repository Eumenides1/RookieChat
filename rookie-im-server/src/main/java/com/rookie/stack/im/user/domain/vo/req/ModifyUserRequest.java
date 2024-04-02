package com.rookie.stack.im.user.domain.vo.req;

import com.rookie.stack.common.domain.req.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Data
@ApiModel("修改用户参数")
public class ModifyUserRequest extends BaseRequest {

    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户头像")
    private String avatar;
    /**
     * 个性签名
     */
    @ApiModelProperty("用户个性签名")
    private String selfSignature;

    @ApiModelProperty("加好友验证类型（Friend_AllowType） 1无需验证 2需要验证")
    private Integer friendAllowType;

    @ApiModelProperty("扩展字段")
    private String extra;



}
