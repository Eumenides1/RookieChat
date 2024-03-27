package com.rookie.stack.im.user.domain.vo.req;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
@Data
public class ImportUserEntity {
    private String userName;
    /**
     * 用户手机号
     */
    private String mobile;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户性别（0：未知，1：男，2：女））
     */
    private Integer sex;

    /**
     * 个性签名
     */
    private String selfSignature;
}
