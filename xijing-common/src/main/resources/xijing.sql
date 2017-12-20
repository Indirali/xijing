/*
Navicat MySQL Data Transfer

Source Server         : xijing
Source Server Version : 50638
Source Host           : 140.143.189.227:3306
Source Database       : xijing

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2017-12-20 18:15:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for applicants
-- ----------------------------
DROP TABLE IF EXISTS `applicants`;
CREATE TABLE `applicants` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '举报人ID',
  `recruit_id` bigint(32) NOT NULL COMMENT '举报招聘ID',
  `type` varchar(32) NOT NULL COMMENT '举报类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of applicants
-- ----------------------------

-- ----------------------------
-- Table structure for confirm
-- ----------------------------
DROP TABLE IF EXISTS `confirm`;
CREATE TABLE `confirm` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `recruit_id` bigint(32) DEFAULT NULL COMMENT '招聘ID',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
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
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户表',
  `follow_user_id` bigint(32) DEFAULT NULL COMMENT '被关注ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follows
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
  `point` point DEFAULT NULL,
  `super_star` varchar(32) NOT NULL COMMENT '是否有明星',
  `file_ids` varchar(255) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(2) NOT NULL COMMENT '状态',
  `participation_time` datetime NOT NULL COMMENT '参加时间',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruit
-- ----------------------------
INSERT INTO `recruit` VALUES ('1', '北京电视剧通告', 'Extra', '7', null, 'Hot_Star', null, null, '0', '2017-12-16 15:27:38', '2017-12-15 15:27:46', null);

-- ----------------------------
-- Table structure for recruit_info
-- ----------------------------
DROP TABLE IF EXISTS `recruit_info`;
CREATE TABLE `recruit_info` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `recruit_id` bigint(32) NOT NULL COMMENT '招聘ID',
  `sex` tinyint(1) NOT NULL COMMENT '性别',
  `age` int(1) NOT NULL COMMENT '年龄',
  `organization` tinyint(1) NOT NULL,
  `amount` int(11) NOT NULL COMMENT '数量',
  `video` tinyint(1) NOT NULL DEFAULT '1' COMMENT '自我介绍视频',
  `moka` tinyint(1) NOT NULL DEFAULT '1' COMMENT '摩卡',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruit_info
-- ----------------------------
INSERT INTO `recruit_info` VALUES ('1', '1', '2', '22', '1', '10', '1', '1', null, '2017-12-19 11:43:30', null);
INSERT INTO `recruit_info` VALUES ('2', '1', '1', '22', '1', '50', '1', '1', null, '2017-12-15 15:41:03', null);

-- ----------------------------
-- Table structure for reports
-- ----------------------------
DROP TABLE IF EXISTS `reports`;
CREATE TABLE `reports` (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `amount` tinyint(4) NOT NULL,
  `report_type` varchar(32) NOT NULL COMMENT '报名状态',
  `content` varchar(255) NOT NULL COMMENT '留言',
  `report_recruit_id` bigint(32) NOT NULL COMMENT '报名招聘ID',
  `report_recruit_info_id` bigint(32) NOT NULL,
  `report_time` datetime NOT NULL COMMENT '报名时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reports
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(64) NOT NULL COMMENT '微信唯一值',
  `place` varchar(32) DEFAULT NULL COMMENT '所在地点',
  `nickname` varchar(128) NOT NULL COMMENT '昵称',
  `head_portrait` varchar(128) NOT NULL COMMENT '头像',
  `sex` tinyint(1) NOT NULL COMMENT '性别 ',
  `id_card` varchar(18) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `age` tinyint(3) DEFAULT NULL,
  `moka` varchar(255) DEFAULT NULL COMMENT '摩卡',
  `video` varchar(255) DEFAULT NULL COMMENT '自我介绍视频',
  `introduction` varchar(255) DEFAULT NULL,
  `user_info_id` bigint(32) DEFAULT NULL,
  `role` tinyint(1) NOT NULL,
  `auth_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否实名认证，0 未认证 1 已认证',
  `wx_image` varchar(255) DEFAULT NULL,
  `wx_number` varchar(32) DEFAULT NULL,
  `phone_number` varchar(32) DEFAULT NULL,
  `credit_degree` varchar(255) DEFAULT NULL,
  `last_login_time` datetime NOT NULL,
  `status` int(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7', 'oL7QE0f0zRL4I4FRgLRWSvrAMQ-o', null, '%E4%BD%A0%E7%9C%8B%E8%B5%B7%E6%9D%A5%E5%BE%88%E5%A5%BD%E5%90%83%F0%9F%91%8F', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ9W9Tt4l7nuc08BZUWzzYUIAXXOxonAN569FGvfMja9IRiap4LMqC04I04AibibbNYbajvZxUAgdicxw/0', '1', null, null, '0', null, null, null, '1', '0', '0', null, null, null, '0', '2017-12-14 15:34:37', '0', '2017-12-14 15:09:08', null);

-- ----------------------------
-- Table structure for user_file
-- ----------------------------
DROP TABLE IF EXISTS `user_file`;
CREATE TABLE `user_file` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(64) NOT NULL COMMENT '文件名',
  `url` varchar(64) NOT NULL COMMENT '地址',
  `format` varchar(64) NOT NULL COMMENT '格式',
  `type` tinyint(4) NOT NULL COMMENT '类型',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
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
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '66.50', '99.50', '66.50', '42.50', '2017-12-14 15:48:33', null);

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
