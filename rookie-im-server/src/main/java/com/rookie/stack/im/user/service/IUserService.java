package com.rookie.stack.im.user.service;

import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;

/**
 * <p>
 * 菜鸟 IM 用户表 服务类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-03-26
 */
public interface IUserService {

    ImportUserResp importUser(ImportUserRequest importUserRequest);



}
