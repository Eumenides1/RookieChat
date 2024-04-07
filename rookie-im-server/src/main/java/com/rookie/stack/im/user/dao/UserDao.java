package com.rookie.stack.im.user.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.stack.im.common.enums.YesOrNoEnum;
import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.mapper.UserMapper;
import com.rookie.stack.im.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private UserMapper userMapper;

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

    public Page<User> getAllUser(Long appId, Page<User> userPage) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAppId, appId);
        queryWrapper.eq(User::getForbiddenFlag, YesOrNoEnum.NO.getStatus());

        return userMapper.selectPage(userPage, queryWrapper);
    }
}
