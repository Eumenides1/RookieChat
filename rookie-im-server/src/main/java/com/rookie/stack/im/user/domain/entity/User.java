package com.rookie.stack.im.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜鸟 IM 用户表
 * </p>
 *
 * @author jaguarliu
 * @since 2024-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户 ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 应用 ID
     */
    @TableField("app_id")
    private Long appId;

    /**
     * 第三方应用 ID
     */
    @TableField("open_id")
    private String openId;

    /**
     * 用户手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 用户性别（0：未知，1：男，2：女））
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 个性签名
     */
    @TableField("self_signature")
    private String selfSignature;

    /**
     * 用户 IP 信息
     */
    @TableField("ip_info")
    private String ipInfo;

    /**
     * 加好友验证类型（Friend_AllowType） 1无需验证 2需要验证
     */
    @TableField("friend_allow_type")
    private Integer friendAllowType;

    /**
     * 禁用标识 1禁用
     */
    @TableField("forbidden_flag")
    private Integer forbiddenFlag;

    /**
     * 管理员禁止用户添加加好友：0 未禁用 1 已禁用
     */
    @TableField("disable_add_friend")
    private Integer disableAddFriend;

    /**
     * 禁言标识 1禁言
     */
    @TableField("silent_flag")
    private Integer silentFlag;

    /**
     * 用户类型 1普通用户 2客服 3机器人
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 扩展字段
     */
    @TableField("extra")
    private String extra;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
