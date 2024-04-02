package com.rookie.stack.im.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author eumenides
 * @description
 * @date 2024/4/2
 */
@Component
public class MymetaObjectHandler implements MetaObjectHandler {
    /*插入时候的填充策略*/
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class,new Date());
        this.strictInsertFill(metaObject, "updatedTime", Date.class,new Date());

    }
    /*更新时候的填充策略*/
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updatedTime", Date.class,new Date());
    }
}
