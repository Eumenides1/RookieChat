package com.rookie.stack.im.friendship.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.FriendShipErrorEnum;
import com.rookie.stack.im.friendship.dao.FriendshipDao;
import com.rookie.stack.im.friendship.domain.entity.Friendship;
import com.rookie.stack.im.friendship.domain.req.ImportFriendShipReq;
import com.rookie.stack.im.friendship.domain.resp.ImportFriendShipResp;
import com.rookie.stack.im.friendship.service.IFriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/4
 */
@Service
public class FriendShipServiceImpl implements IFriendshipService {

    public static final int IMPORT_FRIEND_MAX_LIMIT = 100;
    @Autowired
    private FriendshipDao friendshipDao;

    @Override
    public ImportFriendShipResp importFriendShip(ImportFriendShipReq req) {

        if (req.getFriendItem().size() > IMPORT_FRIEND_MAX_LIMIT) {
            throw new BusinessException(FriendShipErrorEnum.OUTBOUND_IMPORT_FRIEND_LIMIT);
        }

        ImportFriendShipResp resp = new ImportFriendShipResp();
        List<String> errorIds = new ArrayList<>();

        for (ImportFriendShipReq.ImportFriendDto importFriendDto : req.getFriendItem()) {
            Friendship friendship = new Friendship();
            BeanUtil.copyProperties(importFriendDto, friendship);
            friendship.setAppId(req.getAppId());
            // TODO 保存前你得去判断下，这个人在不在
            friendship.setFromId(req.getFromId());

            boolean save = friendshipDao.save(friendship);
            if (!save) {
                errorIds.add(importFriendDto.getToId());
            }
        }
        resp.setErrorId(errorIds);

        return resp;
    }

    @Override
    public void addFriend() {

    }
}
