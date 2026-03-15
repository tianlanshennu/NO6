-- 通知记录表
DROP TABLE IF EXISTS `tongzhijilu`;
CREATE TABLE `tongzhijilu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tongzhibianhao` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知编号',
  `yishengzhanghao` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '医生账号',
  `dianhua` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `jiuzhenshijian` datetime(0) NULL DEFAULT NULL COMMENT '就诊时间',
  `tongzhishijian` datetime(0) NULL DEFAULT NULL COMMENT '通知时间',
  `zhanghao` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `shouji` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `tongzhileixing` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知类型',
  `tongzhineirong` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '通知内容',
  `fasongzhuangtai` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '待发送' COMMENT '发送状态',
  `chongshicishu` int(11) NULL DEFAULT 0 COMMENT '重试次数',
  `chongshishijian` datetime(0) NULL DEFAULT NULL COMMENT '重试时间',
  `shibaiyuanyin` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '失败原因',
  `chulizhuangtai` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '未处理' COMMENT '处理状态',
  `chulibeizhu` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理备注',
  `yuyueid` bigint(20) NULL DEFAULT NULL COMMENT '预约ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_fasongzhuangtai`(`fasongzhuangtai`) USING BTREE,
  INDEX `idx_yuyueid`(`yuyueid`) USING BTREE,
  INDEX `idx_zhanghao`(`zhanghao`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '通知记录' ROW_FORMAT = Dynamic;
