package com.rookie.stack.im.common.domain.vo.resp;

import lombok.Data;

/**
 * @author eumenides
 * @description 通用返回体
 * @date 2024/3/26
 */
@Data
public class ApiResult<T> {

    private Boolean success;

    private Integer errCode;

    private String errMsg;

    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }




}
