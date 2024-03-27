package com.rookie.stack.im.user.controller;


import com.rookie.stack.im.common.domain.vo.resp.ApiResult;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
public class UserController {

    @Autowired
    private IUserService userService;


    @PutMapping("/import")
    public ApiResult<Void> importUser(@RequestBody ImportUserRequest request, Long appId){
        request.setAppId(appId);
        userService.importUser(request);
        return ApiResult.success();
    }


}

