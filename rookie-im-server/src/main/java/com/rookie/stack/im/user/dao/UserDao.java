package com.rookie.stack.im.user.dao;

import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.mapper.UserMapper;
import com.rookie.stack.im.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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



}
