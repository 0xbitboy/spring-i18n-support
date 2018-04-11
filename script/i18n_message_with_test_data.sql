SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for i18n_message
-- ----------------------------
DROP TABLE IF EXISTS `i18n_message`;
CREATE TABLE `i18n_message` (
  `code` varchar(250) NOT NULL COMMENT 'mapping code',
  `locale` varchar(100) NOT NULL COMMENT 'language tag',
  `type` varchar(100) DEFAULT NULL COMMENT 'type for group',
  `message` text NOT NULL COMMENT 'message content',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'last modify time',
  PRIMARY KEY (`code`,`locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='i18n message data';

-- ----------------------------
-- Records of i18n_message
-- ----------------------------
BEGIN;
INSERT INTO `i18n_message` VALUES ('i18n.simple.test', 'en', NULL, 'i18n test simple', '2018-04-11 16:43:16', '2018-04-11 16:43:16');
INSERT INTO `i18n_message` VALUES ('i18n.simple.test', 'zh-CN', NULL, '多语言测试', '2018-04-11 16:43:16', '2018-04-11 16:43:16');
INSERT INTO `i18n_message` VALUES ('i18n.spel.test.1', 'en', NULL, 'i18n spel code,id =1', '2018-04-11 17:33:08', '2018-04-11 17:33:08');
INSERT INTO `i18n_message` VALUES ('i18n.spel.test.2', 'en', NULL, 'i18n spel code,id =2', '2018-04-11 17:33:08', '2018-04-11 17:33:08');
INSERT INTO `i18n_message` VALUES ('i18n.test', 'en', NULL, 'i18n test', '2018-04-11 14:50:16', '2018-04-11 14:50:16');
INSERT INTO `i18n_message` VALUES ('i18n.test', 'zh-CN', NULL, '多语言更新测试', '2018-04-11 14:48:53', '2018-04-11 15:01:59');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
