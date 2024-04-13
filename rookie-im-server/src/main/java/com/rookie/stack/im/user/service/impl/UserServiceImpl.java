package com.rookie.stack.im.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rookie.stack.common.domain.resp.PagedResponse;
import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.UserErrorEnum;
import com.rookie.stack.im.common.utils.AssertUtil;
import com.rookie.stack.im.user.dao.UserDao;
import com.rookie.stack.im.user.domain.dto.UserEntity;
import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.domain.vo.req.GetUserInfoReq;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.req.ModifyUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;
import com.rookie.stack.im.user.domain.vo.resp.GetUserInfoResp;
import com.rookie.stack.im.user.mapper.UserMapper;
import com.rookie.stack.im.user.service.IUserService;
import com.rookie.stack.im.user.service.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService  {

    public static final int USER_MAX_IMPORT_SIZE = 100;

    @Autowired
    private UserDao userDao;

    @Override
    public ImportUserResp importUser(ImportUserRequest importUserRequest) {

        if (importUserRequest.getUserList().size() > USER_MAX_IMPORT_SIZE) {
            throw new BusinessException(UserErrorEnum.OUTBOUND_IMPORT_USER_LIMIT);
        }
        ImportUserResp importUserResp = new ImportUserResp();
        List<String> errorUserNameList = new ArrayList<>();
        importUserRequest.getUserList().forEach(e -> {
            User insert = UserAdapter.importUserSave(importUserRequest.getAppId(), e);
            // TODO 在这里可以限制手机号唯一/用户名唯一
            boolean save = userDao.save(insert);
            if (!save) {
                errorUserNameList.add(insert.getUserName());
            }
        });
        importUserResp.setErrorImportUserName(errorUserNameList);
        return importUserResp;
    }

    @Override
    public void modifyUserInfo(ModifyUserRequest modifyUserRequest) {
        // 首先根据 userId 去查询下，有没有这个人，如果没直接抛出异常
        User user = userDao.getUserInfoByUserId(modifyUserRequest.getUserId(), modifyUserRequest.getAppId());
        AssertUtil.isNotEmpty(user,"待更新用户不存在");
        User update = new User();
        BeanUtil.copyProperties(modifyUserRequest, update);
        boolean updated = userDao.updateUserInfo(update);
        if (!updated) {
            throw new BusinessException(UserErrorEnum.MODIFY_USER_INFO_ERROR);
        }
    }

    @Override
    public GetUserInfoResp getUserInfo(GetUserInfoReq req) {
        GetUserInfoResp resp = new GetUserInfoResp();
        List<UserEntity> userEntities = new ArrayList<>();
        List<String> errorIds = new ArrayList<>();
        for (String userId : req.getUserIds()) {
            User user = userDao.getUserInfoByUserId(userId, req.getAppId());
            if (user != null) {
                UserEntity entity = UserAdapter.buildUserInfo(user);
                userEntities.add(entity);
            } else {
                errorIds.add(userId);
            }
        }
        resp.setUserInfoList(userEntities);
        resp.setErrorIds(errorIds);
        return resp;
    }

    @Override
    public UserEntity getSingleUserInfo(String userId, Long appId) {
        User user = userDao.getUserInfoByUserId(userId, appId);
        AssertUtil.isEmpty(user, UserErrorEnum.USER_IS_NOT_EXIST,"");
        return UserAdapter.buildUserInfo(user);
    }

    @Override
    public PagedResponse<UserEntity> getAllUserInfo(Long appId, Integer page, Integer pageSize) {
        List<UserEntity> userInfoList = new ArrayList<>();

        Page<User> userPage= new Page<>(page, pageSize);

        IPage<User> allUser = userDao.getAllUser(appId, userPage);
        allUser.getRecords().forEach(record -> {
            UserEntity user = UserAdapter.buildUserInfo(record);
            userInfoList.add(user);
        });

        PagedResponse<UserEntity> response = new PagedResponse<>();
        response.setRecords(userInfoList);
        response.setTotal(allUser.getTotal());
        response.setPage(page);
        response.setPageSize(pageSize);

        return response;

    }
}
