package com.rookie.stack.im.user.service.impl;

import com.rookie.stack.im.common.exception.BusinessException;
import com.rookie.stack.im.common.exception.UserErrorEnum;
import com.rookie.stack.im.user.dao.UserDao;
import com.rookie.stack.im.user.domain.entity.User;
import com.rookie.stack.im.user.domain.vo.req.ImportUserRequest;
import com.rookie.stack.im.user.domain.vo.resp.ImportUserResp;
import com.rookie.stack.im.user.service.IUserService;
import com.rookie.stack.im.user.service.adapter.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@Service
public class UserServiceImpl implements IUserService {

    public static final int USER_MAX_IMPORT_SIZE = 100;
    @Autowired
    private UserDao userDao;

    @Override
    public ImportUserResp importUser(ImportUserRequest importUserRequest) {

        if (importUserRequest.getUserList().size() > USER_MAX_IMPORT_SIZE) {
            throw new BusinessException(UserErrorEnum.OUTBOUND_INPORT_USER_LIMIT);
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
            importUserResp.setErrorImportUserName(errorUserNameList);
        });
        return importUserResp;
    }
}
