DROP TABLE IF EXISTS `friendship`;
CREATE TABLE `friendship`  (
       `app_id` int(20) NOT NULL COMMENT 'app_id',
       `from_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'from_id',
       `to_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'to_id',
       `remark` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
       `status` int(10) NULL DEFAULT NULL COMMENT '状态 1正常 2删除',
       `black` int(10) NULL DEFAULT NULL COMMENT '1正常 2拉黑',
       `create_time` bigint(20) NULL DEFAULT NULL,
       `friend_sequence` bigint(20) NULL DEFAULT NULL,
       `black_sequence` bigint(20) NULL DEFAULT NULL,
       `add_source` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
       `extra` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源',
       PRIMARY KEY (`app_id`, `from_id`, `to_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;