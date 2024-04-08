CREATE TABLE `user` (
        `id` int NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
        `user_id` varchar(50) NOT NULL COMMENT '用户 ID',
        `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
        `app_id` bigint NOT NULL COMMENT '应用 ID',
        `open_id` varchar(100) DEFAULT NULL COMMENT '第三方应用 ID',
        `mobile` varchar(50) DEFAULT NULL COMMENT '用户手机号',
        `email` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
        `password` varchar(100) DEFAULT NULL,
        `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
        `sex` int DEFAULT NULL COMMENT '用户性别（0：未知，1：男，2：女））',
        `self_signature` varchar(255) DEFAULT NULL COMMENT '个性签名',
        `ip_info` json DEFAULT NULL COMMENT '用户 IP 信息',
        `friend_allow_type` int DEFAULT NULL COMMENT '加好友验证类型（Friend_AllowType） 1无需验证 2需要验证',
        `forbidden_flag` int DEFAULT NULL COMMENT '禁用标识 1禁用',
        `disable_add_friend` int DEFAULT NULL COMMENT '管理员禁止用户添加加好友：0 未禁用 1 已禁用',
        `silent_flag` int DEFAULT NULL COMMENT '禁言标识 1禁言',
        `user_type` int DEFAULT NULL COMMENT '用户类型 1普通用户 2客服 3机器人',
        `extra` json DEFAULT NULL COMMENT '扩展字段',
        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
        `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='菜鸟 IM 用户表'