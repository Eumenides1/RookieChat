package com.rookie.stack.im.user.service;

import com.rookie.stack.im.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.stack.im.user.domain.req.ImportUserRequest;

/**
 * <p>
 * 菜鸟 IM 用户表 服务类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-03-26
 */
public interface IUserService {

    void importUser(ImportUserRequest importUserRequest);

}
