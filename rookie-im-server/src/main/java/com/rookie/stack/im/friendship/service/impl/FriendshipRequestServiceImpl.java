package com.rookie.stack.im.friendship.service.impl;

import com.rookie.stack.im.common.enums.ApproveFriendRequestEnum;
import com.rookie.stack.im.common.enums.YesOrNoEnum;
import com.rookie.stack.im.friendship.dao.FriendshipRequestDao;
import com.rookie.stack.im.friendship.domain.dto.FriendDto;
import com.rookie.stack.im.friendship.domain.entity.FriendshipRequest;
import com.rookie.stack.im.friendship.service.IFriendshipRequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eumenides
 * @description
 * @date 2024/4/13
 */
@Service
public class FriendshipRequestServiceImpl implements IFriendshipRequestService {

    @Autowired
    private FriendshipRequestDao friendshipRequestDao;

    /**
     * 添加好友申请，如果存在那就更新
     * @param fromId
     * @param dto
     * @param appId
     * @return
     */
    @Override
    public Boolean addFriendshipRequest(String fromId, FriendDto dto, Long appId) {

        Boolean res;

        FriendshipRequest request = friendshipRequestDao.getFriendshipRequest(fromId, dto.getToId(), appId);
        // 这里不适用断言的原因是，判断完成后业务逻辑，并不是报错
        if (request == null) {
            // 不存在加好友申请，那就新建一个
            request = new FriendshipRequest();
            request.setFromId(fromId);
            request.setToId(dto.getToId());
            request.setAppId(appId);
            request.setAddLetter(dto.getAddWording());
            // TODO 这里得加一个枚举,这里我就用 yes or no 代替了
            request.setReadStatus(YesOrNoEnum.NO.getStatus());
            request.setApproveStatus(ApproveFriendRequestEnum.WAIT.getCode());
            request.setRemark(dto.getRemark());
            request.setAddSource(dto.getAddSource());

            res = friendshipRequestDao.save(request);
        } else {
            // 如果这条记录已经存在了，那就更新一下申请记录，说明是多次发送申请
            // TODO 这里的代码得优化
            if (StringUtils.isNoneBlank(dto.getAddWording())) {
                request.setAddLetter(dto.getAddWording());
            }

            if (StringUtils.isNoneBlank(dto.getRemark())) {
                request.setRemark(dto.getRemark());
            }

            if (StringUtils.isNoneBlank(dto.getAddSource())) {
                request.setAddSource(dto.getAddSource());
            }
            res = friendshipRequestDao.updateById(request);
        }
        return  res;
    }
}
