package com.rookie.stack.im.common.exception;

import com.rookie.stack.im.common.domain.vo.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.rookie.stack.im.common.exception.CommonErrorEnum.PARAM_INVALID;

/**
 * @author eumenides
 * @description
 * @date 2024/3/27
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        StringBuilder errMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors()
                .forEach(x -> errMsg.append(x.getField()).append(x.getDefaultMessage()).append(","));
        String message = errMsg.toString();

        return ApiResult.fail(PARAM_INVALID.getCode(), message.substring(0, message.length() - 1));
    }

    /**
     * 自定义校验异常（如参数校验等）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessExceptionHandler(BusinessException e) {
        log.info("business exception！The reason is：{}", e.getMessage(), e);
        return ApiResult.fail(e.getErrorCode(), e.getMessage());
    }

}
