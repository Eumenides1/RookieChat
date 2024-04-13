package com.rookie.stack.im.friendship.service;

import com.rookie.stack.im.friendship.domain.dto.FriendDto;
import com.rookie.stack.im.friendship.domain.entity.FriendshipRequest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 加好友申请表 服务类
 * </p>
 *
 * @author jaguarliu
 * @since 2024-04-13
 */
public interface IFriendshipRequestService {

    Boolean addFriendshipRequest(String fromId, FriendDto dto, Long appId);

}
