/*
Navicat MySQL Data Transfer

Source Server         : xijing
Source Server Version : 50556
Source Host           : 140.143.189.227:3306
Source Database       : xijing

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-01-30 12:22:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for confirm
-- ----------------------------
DROP TABLE IF EXISTS `confirm`;
CREATE TABLE `confirm` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户id',
  `recruit_id` bigint(32) NOT NULL COMMENT '确认招聘ID',
  `recruit_info_id` bigint(32) DEFAULT NULL COMMENT '确认招聘详情ID',
  `confirm_time` datetime NOT NULL COMMENT '确认时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0 正常 1 已发送通知',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of confirm
-- ----------------------------

-- ----------------------------
-- Table structure for follows
-- ----------------------------
DROP TABLE IF EXISTS `follows`;
CREATE TABLE `follows` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '用户表',
  `follow_user_id` bigint(32) NOT NULL COMMENT '被关注ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follows
-- ----------------------------

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL,
  `confirm_id` bigint(32) DEFAULT NULL COMMENT '二次确认ID',
  `recruit_info_id` bigint(32) DEFAULT NULL COMMENT '招聘详情ID',
  `template_type` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '模板类型',
  `number` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '模板号',
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '可使用次数',
  `status` int(11) NOT NULL DEFAULT '0',
  `create_time` bigint(13) NOT NULL,
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of notification
-- ----------------------------

-- ----------------------------
-- Table structure for recruit
-- ----------------------------
DROP TABLE IF EXISTS `recruit`;
CREATE TABLE `recruit` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `type` varchar(32) NOT NULL COMMENT '类型',
  `user_id` bigint(32) NOT NULL COMMENT '招聘人ID',
  `point` varchar(32) DEFAULT NULL,
  `longitude` varchar(8) DEFAULT NULL COMMENT '经度',
  `latitude` varchar(8) DEFAULT NULL COMMENT '纬度',
  `super_star` varchar(32) NOT NULL COMMENT '是否有明星',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `participation_time` datetime NOT NULL COMMENT '参加时间',
  `urgent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否紧急 0 false   1 true',
  `recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '推荐  0 false   1 true',
  `create_time` bigint(13) NOT NULL,
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruit
-- ----------------------------

-- ----------------------------
-- Table structure for recruit_info
-- ----------------------------
DROP TABLE IF EXISTS `recruit_info`;
CREATE TABLE `recruit_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `sex` tinyint(1) NOT NULL COMMENT '性别',
  `age` int(1) NOT NULL COMMENT '年龄',
  `organization` tinyint(1) NOT NULL,
  `amount` int(11) NOT NULL COMMENT '数量',
  `salary` int(11) NOT NULL DEFAULT '0' COMMENT '工资 0 无薪酬  -1 价格面议',
  `video` tinyint(1) NOT NULL DEFAULT '1' COMMENT '自我介绍视频',
  `moka` tinyint(1) NOT NULL DEFAULT '1' COMMENT '摩卡',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 未结束 1 已结束',
  `create_time` bigint(13) NOT NULL,
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruit_info
-- ----------------------------

-- ----------------------------
-- Table structure for recruit_recruit_infos
-- ----------------------------
DROP TABLE IF EXISTS `recruit_recruit_infos`;
CREATE TABLE `recruit_recruit_infos` (
  `recruit_id` bigint(32) DEFAULT NULL,
  `recruit_infos_id` bigint(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of recruit_recruit_infos
-- ----------------------------

-- ----------------------------
-- Table structure for recruit_user_files
-- ----------------------------
DROP TABLE IF EXISTS `recruit_user_files`;
CREATE TABLE `recruit_user_files` (
  `recruit_id` bigint(32) DEFAULT NULL,
  `user_files_id` bigint(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of recruit_user_files
-- ----------------------------

-- ----------------------------
-- Table structure for reports
-- ----------------------------
DROP TABLE IF EXISTS `reports`;
CREATE TABLE `reports` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `amount` tinyint(4) NOT NULL,
  `report_type` varchar(32) NOT NULL COMMENT '报名状态',
  `content` varchar(255) NOT NULL COMMENT '留言',
  `report_recruit_id` bigint(32) NOT NULL COMMENT '报名招聘ID',
  `report_recruit_info_id` bigint(32) NOT NULL,
  `report_time` datetime NOT NULL COMMENT '报名时间',
  `notification` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通知 0 false   1 true',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态0 未筛选 1 喜欢  2 不喜欢',
  `create_time` bigint(13) NOT NULL,
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reports
-- ----------------------------

-- ----------------------------
-- Table structure for tip
-- ----------------------------
DROP TABLE IF EXISTS `tip`;
CREATE TABLE `tip` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '举报人ID',
  `recruit_id` bigint(32) NOT NULL COMMENT '举报招聘ID',
  `type` varchar(32) NOT NULL COMMENT '举报类型',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tip
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `open_id` varchar(64) NOT NULL COMMENT '微信唯一值',
  `place` varchar(32) DEFAULT NULL COMMENT '所在地点',
  `longitude` varchar(12) DEFAULT NULL,
  `latitude` varchar(12) DEFAULT NULL,
  `nickname` varchar(128) NOT NULL COMMENT '昵称',
  `head_portrait` varchar(128) DEFAULT NULL COMMENT '头像',
  `sex` tinyint(1) NOT NULL COMMENT '性别 ',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证信息',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `age` tinyint(3) DEFAULT NULL COMMENT '年龄',
  `moka` bigint(32) DEFAULT NULL COMMENT '摩卡',
  `video` bigint(32) DEFAULT NULL COMMENT '自我介绍视频',
  `introduction` varchar(255) DEFAULT NULL COMMENT '个人说明',
  `user_info_id` bigint(32) DEFAULT NULL COMMENT '用户详情ID',
  `role` tinyint(1) NOT NULL DEFAULT '0' COMMENT '角色',
  `auth_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否实名认证，0 未认证 1 已认证',
  `wx_image` bigint(32) DEFAULT NULL COMMENT '微信二维码',
  `wx_number` varchar(32) DEFAULT NULL COMMENT '微信号',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号',
  `credit_degree` int(4) NOT NULL DEFAULT '500' COMMENT '信用度',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0 正常',
  `create_time` bigint(13) NOT NULL,
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `openid` (`open_id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'oL7QE0f0zRL4I4FRgLRWSvrAMQ-o', '中国-北京市', '116.36611', '39.91231', '你看起来很好吃', null, '1', null, null, '0', null, null, null, null, '0', '0', null, null, null, '0', '2018-01-30 12:19:56', '0', '1517285996176', '1517285996176');

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(64) NOT NULL COMMENT '文件名',
  `url` varchar(64) NOT NULL COMMENT '地址',
  `format` varchar(64) NOT NULL COMMENT '格式',
  `type` varchar(32) NOT NULL COMMENT '类型',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` bigint(13) NOT NULL COMMENT '创建时间',
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_file
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `chest` float(8,2) DEFAULT NULL,
  `waist` float(8,2) DEFAULT NULL,
  `hipline` float(8,2) DEFAULT NULL,
  `shoe_size` float(8,2) DEFAULT NULL,
  `create_time` bigint(13) NOT NULL,
  `update_time` bigint(13) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_user_files
-- ----------------------------
DROP TABLE IF EXISTS `user_user_files`;
CREATE TABLE `user_user_files` (
  `user_id` bigint(32) DEFAULT NULL,
  `user_files_id` bigint(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user_user_files
-- ----------------------------
