package com.rookie.stack.im.user.domain.vo.resp;

import com.rookie.stack.im.user.domain.dto.UserEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author eumenides
 * @description
 * @date 2024/4/3
 */
@Data
@ApiModel("查询用户信息返回值实体")
public class GetUserInfoResp {

    List<UserEntity> userInfoList;

    List<String> errorIds;

}
