package com.rookie.stack.im.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rookie.stack.common.domain.resp.PagedResponse;
import com.rookie.stack.im.user.domain.dto.UserEntity;
import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.domain.vo.req.GetUserInfoReq;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.req.ModifyUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;
import com.rookie.stack.im.user.domain.vo.resp.GetUserInfoResp;

import java.util.List;

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

    PagedResponse<UserEntity> getAllUserInfo(Long appId, Integer page, Integer pageSize);

}
