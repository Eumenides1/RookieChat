package com.rookie.stack.im.friendship.dao;

import com.rookie.stack.im.common.enums.ApproveFriendRequestEnum;
import com.rookie.stack.im.friendship.domain.entity.FriendshipRequest;
import com.rookie.stack.im.friendship.mapper.FriendshipRequestMapper;
import com.rookie.stack.im.friendship.service.IFriendshipRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 加好友申请表 服务实现类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-13
 */
@Service
public class FriendshipRequestDao extends ServiceImpl<FriendshipRequestMapper, FriendshipRequest> {

    /**
     * 查询所有待审批的申请
     * @param fromId
     * @param toId
     * @param appId
     * @return
     */
    public FriendshipRequest getFriendshipRequest(String fromId, String toId, Long appId) {
        return lambdaQuery()
                .eq(FriendshipRequest::getFromId, fromId)
                .eq(FriendshipRequest::getToId, toId)
                .eq(FriendshipRequest::getAppId, appId)
                .eq(FriendshipRequest::getApproveStatus, ApproveFriendRequestEnum.WAIT.getCode())
                .one();
    }
}
