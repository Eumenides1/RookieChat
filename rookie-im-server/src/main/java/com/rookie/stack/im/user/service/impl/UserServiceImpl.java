package com.rookie.stack.im.user.service.impl;

import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.user.dao.UserDao;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;
import com.rookie.stack.im.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Service
public class UserServiceImpl implements IUserService {

    public static final int USER_MAX_IMPORT_SIZE = 1;
    @Autowired
    private UserDao userDao;

    @Override
    public ImportUserResp importUser(ImportUserRequest importUserRequest) {

        if (importUserRequest.getUserList().size() > USER_MAX_IMPORT_SIZE) {
            // TODO 这里需要处理异常
            throw new BusinessException("单次上传用户数量超过限制");
        }
        ImportUserResp importUserResp = new ImportUserResp();

        importUserRequest.getUserList().forEach(e -> {
            e.setUserId(UUID.randomUUID().toString());
            e.setAppId(importUserRequest.getAppId());
            boolean save = userDao.save(e);
            if (!save) {
                // TODO 这里需要需要抛出一个业务异常
            }
        });
        return null;
    }
}
