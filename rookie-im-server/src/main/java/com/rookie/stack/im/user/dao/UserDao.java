package com.rookie.stack.im.user.dao;

import com.rookie.stack.im.common.enums.YesOrNoEnum;
import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.mapper.UserMapper;
import com.rookie.stack.im.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 菜鸟 IM 用户表 服务实现类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-03-26
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getUserInfoByUserId(String userId, Long appId) {
        return lambdaQuery()
                .eq(User::getAppId, appId)
                .eq(User::getUserId,userId)
                .eq(User::getForbiddenFlag, YesOrNoEnum.NO.getStatus())
                .one();
    }

    public boolean updateUserInfo(User update) {
         return lambdaUpdate()
                 .eq(User::getUserId, update.getUserId())
                 .set(User::getUserName, update.getUserName())
                 .set(User::getAvatar, update.getAvatar())
                 .set(User::getSelfSignature, update.getSelfSignature())
                 .set(User::getExtra, update.getExtra())
                 .set(User::getFriendAllowType, update.getFriendAllowType())
                 .update();
    }
}
