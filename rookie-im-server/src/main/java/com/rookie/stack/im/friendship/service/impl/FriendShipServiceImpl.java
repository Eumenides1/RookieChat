package com.rookie.stack.im.friendship.service.impl;

import com.rookie.stack.im.friendship.dao.FriendshipDao;
import com.rookie.stack.im.friendship.domain.req.ImportFriendShipReq;
import com.rookie.stack.im.friendship.domain.resp.ImportFriendShipResp;
import com.rookie.stack.im.friendship.service.IFriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eumenides
 * @description
 * @date 2024/4/4
 */
@Service
public class FriendShipServiceImpl implements IFriendshipService {

    @Autowired
    private FriendshipDao friendshipDao;

    @Override
    public ImportFriendShipResp importFriendShip(ImportFriendShipReq req) {



        return null;
    }
}
