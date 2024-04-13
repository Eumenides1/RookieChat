package com.rookie.stack.im.friendship.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.rookie.stack.im.common.enums.AllowFriendTypeEnum;
import com.rookie.stack.im.common.enums.FriendShipStatusEnum;
import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.FriendShipErrorEnum;
import com.rookie.stack.im.common.utils.AssertUtil;
import com.rookie.stack.im.friendship.dao.FriendshipDao;
import com.rookie.stack.im.friendship.domain.dto.FriendDto;
import com.rookie.stack.im.friendship.domain.entity.Friendship;
import com.rookie.stack.im.friendship.domain.req.AddFriendReq;
import com.rookie.stack.im.friendship.domain.req.ImportFriendShipReq;
import com.rookie.stack.im.friendship.domain.resp.ImportFriendShipResp;
import com.rookie.stack.im.friendship.service.IFriendshipRequestService;
import com.rookie.stack.im.friendship.service.IFriendshipService;
import com.rookie.stack.im.user.domain.dto.UserEntity;
import com.rookie.stack.im.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private IUserService userService;

    @Autowired
    private IFriendshipRequestService friendshipRequestService;

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
    public void addFriend(AddFriendReq req) {
        UserEntity fromUser = userService.getSingleUserInfo(req.getFromId(), req.getAppId());
        AssertUtil.isNotEmpty(fromUser, "查询的用户不存在");

        UserEntity toUser = userService.getSingleUserInfo(req.getToItem().getToId(), req.getAppId());

        AssertUtil.isNotEmpty(toUser, "查询的用户不存在");

        if (toUser.getFriendAllowType() != null && toUser.getFriendAllowType() == AllowFriendTypeEnum.NOT_NEED.getCode()) {
            // 用户关闭了添加需要校验,直接添加
            this.doAddFriend(fromUser.getUserId(), req.getToItem(), req.getAppId());

        } else {
            Friendship friendShip = friendshipDao.getFriendShip(fromUser.getUserId(), toUser.getUserId(), req.getAppId());
            // 这里用户关系如果查出来有值，即用户关系存在， 两人是好友
            //TODO 这里要去新增一个判断 null 的方法
            // AssertUtil.isNotEmpty(friendShip, "该用户已经是你的好友啦");
            if (friendShip != null) {
                throw new BusinessException(FriendShipErrorEnum.TO_IS_YOUR_FRIEND);
            }
            // TODO 走到这里就说明要添加的人开启了验证，所以这里首先得发送验证消息
            Boolean save = friendshipRequestService.addFriendshipRequest(fromUser.getUserId(), req.getToItem(), req.getAppId());
            AssertUtil.isTrue(save, "发送好友申请失败！");
        }
    }

    @Override
    @Transactional
    public void doAddFriend(String fromId, FriendDto dto, Long appId) {
        // TODO 这里要生成两条好友记录的；a->b b->a
        // 先查询用户关系存不存在，然后判断添加状态
        Friendship friendShip = friendshipDao.getFriendShip(fromId, dto.getToId(), appId);
        if (friendShip == null) {
            friendShip = new Friendship();
            friendShip.setAppId(appId);
            friendShip.setFromId(fromId);
            BeanUtils.copyProperties(dto, friendShip);
            friendShip.setStatus(FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode());
            boolean save = friendshipDao.save(friendShip);
            AssertUtil.isTrue(save, "添加好友失败！");
        } else {
            if (friendShip.getStatus() == FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode()) {
                throw new BusinessException(FriendShipErrorEnum.TO_IS_YOUR_FRIEND);
            } else {
                if (StringUtils.isNoneBlank(dto.getExtra())) {
                    friendShip.setExtra(dto.getExtra());
                }

                if (StringUtils.isNoneBlank(dto.getRemark())) {
                    friendShip.setRemark(dto.getRemark());
                }

                if (StringUtils.isNoneBlank(dto.getAddSource())) {
                    friendShip.setAddSource(dto.getAddSource());
                }

                friendShip.setStatus(FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode());
                friendshipDao.updateById(friendShip);
            }
        }
        // TODO 这里属于重复代码，需要优化下
        Friendship toFriendShip = friendshipDao.getFriendShip(dto.getToId(), fromId, appId);
        if (toFriendShip == null) {
            toFriendShip = new Friendship();
            toFriendShip.setAppId(appId);
            toFriendShip.setFromId(dto.getToId());
            BeanUtils.copyProperties(dto, toFriendShip);
            toFriendShip.setToId(fromId);
            toFriendShip.setStatus(FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode());
            boolean save = friendshipDao.save(toFriendShip);
            AssertUtil.isTrue(save, "添加好友失败！");
        } else {
            toFriendShip.setStatus(FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode());
            friendshipDao.updateById(toFriendShip);
        }


    }
}
