-- 就诊通知功能修复 SQL 脚本
-- 执行日期：2026-03-16
-- 说明：添加通知发送记录表，用于记录通知发送状态和重试机制

-- 创建通知发送记录表
DROP TABLE IF EXISTS `tongzhisongji`;
CREATE TABLE `tongzhisongji`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tongzhibianhao` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '通知编号',
  `yishengzhanghao` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '医生账号',
  `dianhua` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `jiuzhenshijian` datetime(0) NULL DEFAULT NULL COMMENT '就诊时间',
  `songjishijian` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `zhanghao` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `shouji` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `songjizhuangtai` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '发送中' COMMENT '发送状态 (发送中/成功/失败/已处理)',
  `retrycount` int(11) NULL DEFAULT 0 COMMENT '重试次数',
  `shibaiyuanyin` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '失败原因',
  PRIMARY KEY (`id`),
  KEY `idx_songjizhuangtai` (`songjizhuangtai`),
  KEY `idx_tongzhibianhao` (`tongzhibianhao`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8mb4 COMMENT = '通知发送记录';

-- 初始化数据（可选）
-- INSERT INTO `tongzhisongji` VALUES (1, NOW(), 'TEST001', '医生账号 1', '13800138000', '2026-03-16 10:00:00', NOW(), 'user001', '13900139000', '成功', 0, NULL);
