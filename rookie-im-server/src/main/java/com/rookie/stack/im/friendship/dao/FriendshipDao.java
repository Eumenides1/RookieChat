package com.rookie.stack.im.friendship.dao;

import com.rookie.stack.im.friendship.domain.entity.Friendship;
import com.rookie.stack.im.friendship.mapper.FriendshipMapper;
import com.rookie.stack.im.friendship.service.IFriendshipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import static com.rookie.stack.im.common.enums.FriendShipStatusEnum.FRIEND_STATUS_NORMAL;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-04
 */
@Service
public class FriendshipDao extends ServiceImpl<FriendshipMapper, Friendship>{


    public Friendship getFriendShip(String fromId, String toId, Long appId) {

        return lambdaQuery()
                .eq(Friendship::getFromId, fromId)
                .eq(Friendship::getToId, toId)
                .eq(Friendship::getAppId, appId)
                .eq(Friendship::getStatus, FRIEND_STATUS_NORMAL)
                .one();

    }
}
