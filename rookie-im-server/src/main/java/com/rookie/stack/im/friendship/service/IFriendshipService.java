package com.rookie.stack.im.friendship.service;

import com.rookie.stack.im.friendship.domain.entity.Friendship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rookie.stack.im.friendship.domain.req.ImportFriendShipReq;
import com.rookie.stack.im.friendship.domain.resp.ImportFriendShipResp;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-04
 */
public interface IFriendshipService {

    ImportFriendShipResp importFriendShip(ImportFriendShipReq req);

}
