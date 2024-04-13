package com.rookie.stack.im.friendship.controller;


import com.rookie.stack.im.common.domain.vo.resp.ApiResult;
import com.rookie.stack.im.friendship.domain.req.AddFriendReq;
import com.rookie.stack.im.friendship.domain.req.ImportFriendShipReq;
import com.rookie.stack.im.friendship.domain.resp.ImportFriendShipResp;
import com.rookie.stack.im.friendship.service.IFriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/capi/friendship")
public class FriendshipController {


    @Autowired
    private IFriendshipService friendshipService;

    @PutMapping("/importFriendShip")
    public ApiResult<ImportFriendShipResp> importFriendShip(@RequestBody @Valid ImportFriendShipReq req) {
        return ApiResult.success(friendshipService.importFriendShip(req));
    }

    @PostMapping("/addFriendShip")
    public ApiResult<Void> addFriend(@RequestBody @Valid AddFriendReq req) {
        friendshipService.addFriend(req);
        return ApiResult.success();
    }



}

