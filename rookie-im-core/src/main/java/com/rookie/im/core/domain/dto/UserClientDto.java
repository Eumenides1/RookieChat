package com.rookie.im.core.domain.dto;

import lombok.Data;

/**
 * @author eumenides
 * @description
 * @date 2024/4/24
 */
@Data
public class UserClientDto {

    private Integer appId;

    private Integer clientType;

    private String userId;

    private String imei;

}
