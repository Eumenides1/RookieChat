package com.rookie.stack.im.friendship.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("friendship")
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * app_id
     */
    @TableId("app_id")
    private Long appId;

    /**
     * from_id
     */
    @TableField("from_id")
    private String fromId;

    /**
     * to_id
     */
    @TableField("to_id")
    private String toId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态 1正常 2删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 1正常 2拉黑
     */
    @TableField("black")
    private Integer black;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("friend_sequence")
    private Long friendSequence;

    @TableField("black_sequence")
    private Long blackSequence;

    /**
     * 来源
     */
    @TableField("add_source")
    private String addSource;

    /**
     * 来源
     */
    @TableField("extra")
    private String extra;


}
