package com.rookie.stack.im.user.service;

import com.rookie.stack.im.user.domain.dto.UserEntity;
import com.rookie.stack.im.user.domain.vo.req.GetUserInfoReq;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.req.ModifyUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;
import com.rookie.stack.im.user.domain.vo.resp.GetUserInfoResp;

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

    void modifyUserInfo(ModifyUserRequest modifyUserRequest);

    GetUserInfoResp getUserInfo(GetUserInfoReq req);

    UserEntity getSingleUserInfo(String userId, Long appId);

}
