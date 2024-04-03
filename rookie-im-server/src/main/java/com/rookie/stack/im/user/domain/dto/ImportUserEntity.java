package com.rookie.stack.im.user.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.rookie.stack.im.common.annotation.IsMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
@Data
@ApiModel("导入用户资料实体")
public class ImportUserEntity {

    @NotNull(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String userName;
    /**
     * 用户手机号
     */
    @NotNull(message = "手机号不能为空")
    @IsMobile
    @ApiModelProperty("用户手机号")
    private String mobile;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 用户性别（0：未知，1：男，2：女））
     */
    @ApiModelProperty("用户性别")
    private Integer sex;

    /**
     * 个性签名
     */
    @ApiModelProperty("用户个性签名")
    private String selfSignature;
}
