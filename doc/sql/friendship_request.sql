CREATE TABLE `friendship_request` (
      `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
      `app_id` bigint NOT NULL,
      `from_id` varchar(20) DEFAULT NULL,
      `to_id` varchar(20) DEFAULT NULL,
      `remark` varchar(50) DEFAULT NULL COMMENT '备注',
      `read_status` int DEFAULT NULL COMMENT '是否已读 1已读',
      `add_source` varchar(20) DEFAULT NULL COMMENT '好友来源',
      `add_letter` varchar(50) DEFAULT NULL COMMENT '验证信息',
      `approve_status` int DEFAULT NULL COMMENT '审批状态 1同意 2拒绝',
      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
      `update_time` datetime DEFAULT NULL,
      `sequence` bigint DEFAULT NULL,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='加好友申请表'