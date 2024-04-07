package com.rookie.stack.im.user.controller;


import com.rookie.stack.common.domain.resp.PagedResponse;
import com.rookie.stack.im.common.domain.vo.resp.ApiResult;
import com.rookie.stack.im.user.domain.dto.UserEntity;
import com.rookie.stack.im.user.domain.vo.req.GetUserInfoReq;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.req.ModifyUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;
import com.rookie.stack.im.user.domain.vo.resp.GetUserInfoResp;
import com.rookie.stack.im.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜鸟 IM 用户表 前端控制器
 * </p>
 *
 * @author jaguarliu
 * @since 2024-03-26
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "用户模块相关接口")
public class UserController {

    @Autowired
    private IUserService userService;


    @PutMapping("/import")
    @ApiOperation(value = "导入用户资料")
    public ApiResult<ImportUserResp> importUser(@Valid @RequestBody ImportUserRequest request){
        ImportUserResp importUserResp = userService.importUser(request);
        return ApiResult.success(importUserResp);
    }

    @PostMapping("/modify")
    @ApiOperation(value = "修改用户资料")
    public ApiResult<Void> modifyUserInfo(@RequestBody @Valid ModifyUserRequest request){
        userService.modifyUserInfo(request);
        return ApiResult.success();
    }

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "批量获取用户信息")
    public ApiResult<GetUserInfoResp> getUserInfo(GetUserInfoReq request){
        GetUserInfoResp userInfo = userService.getUserInfo(request);
        return ApiResult.success(userInfo);
    }

    @GetMapping("/getSingleUserInfo")
    @ApiOperation(value = "获取单个用户信息")
    public ApiResult<UserEntity> getSingleUserInfo(String userId,  Long appId){
        UserEntity singleUserInfo = userService.getSingleUserInfo(userId, appId);
        return ApiResult.success(singleUserInfo);
    }

    @GetMapping("/getAllUserInfo")
    @ApiOperation(value = "获取所有用户信息列表")
    public ApiResult<PagedResponse<UserEntity>> getAllUserInfo(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                               Long appId) {
        PagedResponse<UserEntity> userEntityList = userService.getAllUserInfo(appId, page, pageSize);
        return ApiResult.success(userEntityList);
    }





}

