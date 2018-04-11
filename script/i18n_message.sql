
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

SET FOREIGN_KEY_CHECKS = 1;
