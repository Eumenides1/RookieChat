package com.rookie.stack.io.netty.upload;

import lombok.Data;

/**
 * @description:
 * @author: lld
 * @version: 1.0
 */
@Data
public class FileDto {

    private String fileName;    //文件名称

    private Integer command; // 1请求创建文件 2传输文件

    private byte[] bytes;       //文件字节；再实际应用中可以使用非对称加密，以保证传输信息安全

}
