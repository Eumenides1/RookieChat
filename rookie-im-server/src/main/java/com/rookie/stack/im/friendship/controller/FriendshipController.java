package com.rookie.stack.im.friendship.controller;


import com.rookie.stack.im.common.domain.vo.resp.ApiResult;
import com.rookie.stack.im.friendship.domain.req.ImportFriendShipReq;
import com.rookie.stack.im.friendship.domain.resp.ImportFriendShipResp;
import com.rookie.stack.im.friendship.service.IFriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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



}

