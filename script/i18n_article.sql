/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 24/12/2018 21:22:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for i18n_article
-- ----------------------------
DROP TABLE IF EXISTS `i18n_article`;
CREATE TABLE `i18n_article` (
  `id` int(11) NOT NULL COMMENT '主键',
  `locale` varchar(20) NOT NULL COMMENT '语言信息',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `a` varchar(100) DEFAULT NULL COMMENT '字段a',
  `b` varchar(100) DEFAULT NULL COMMENT '字段b',
  PRIMARY KEY (`id`,`locale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of i18n_article
-- ----------------------------
BEGIN;
INSERT INTO `i18n_article` VALUES (1, 'en', '英文标题', '英文描述', '英文A', '英文B');
INSERT INTO `i18n_article` VALUES (1, 'zh', '中文标题', '中文描述', '中文A', '中文B');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
