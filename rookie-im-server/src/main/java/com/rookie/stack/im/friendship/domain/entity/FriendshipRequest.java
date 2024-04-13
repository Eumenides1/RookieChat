package com.rookie.stack.im.friendship.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 加好友申请表
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("friendship_request")
public class FriendshipRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("app_id")
    private Long appId;

    @TableField("from_id")
    private String fromId;

    @TableField("to_id")
    private String toId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否已读 1已读
     */
    @TableField("read_status")
    private Integer readStatus;

    /**
     * 好友来源
     */
    @TableField("add_source")
    private String addSource;

    /**
     * 验证信息
     */
    @TableField("add_letter")
    private String addLetter;

    /**
     * 审批状态 1同意 2拒绝
     */
    @TableField("approve_status")
    private Integer approveStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("sequence")
    private Long sequence;


}
