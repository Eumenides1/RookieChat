package com.rookie.stack.im.user.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import com.rookie.stack.im.common.enums.YesOrNoEnum;
import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.domain.vo.req.ImportUserEntity;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;

import java.util.UUID;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
public class UserAdapter {

    public static User importUserSave(Long appId, ImportUserEntity entity) {
        User user = new User();
        BeanUtil.copyProperties(entity, user);
        // TODO 在这里设置默认属性
        user.setAppId(appId);
        // TODO 这里可以使用雪花算法
        user.setUserId(UUID.randomUUID().toString());
        user.setForbiddenFlag(YesOrNoEnum.NO.getStatus());
        return user;
    }

}
