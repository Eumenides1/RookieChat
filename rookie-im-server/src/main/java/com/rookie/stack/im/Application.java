package com.rookie.stack.im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author eumenides
 * @description
 * @date 2024/3/26
 */
@SpringBootApplication(scanBasePackages = {"com.rookie.stack.im"})
@MapperScan({"com.rookie.stack.im.**.**.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
