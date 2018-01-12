/*
Navicat MySQL Data Transfer

Source Server         : xijing
Source Server Version : 50556
Source Host           : 140.143.189.227:3306
Source Database       : xijing

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-01-12 15:24:29
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
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0 正常 1 已发送通知',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of confirm
-- ----------------------------
INSERT INTO `confirm` VALUES ('1', '1', '1', '1', '1', '2018-01-03 16:15:27', null);

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
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL,
  `other_id` bigint(20) DEFAULT NULL COMMENT '各种ID',
  `template_type` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '模板类型',
  `number` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '模板号',
  `count` int(11) NOT NULL DEFAULT '1' COMMENT '可使用次数',
  `status` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES ('7', '1', '1', '1111', 'the formId is a mock one', '1', '1', '2017-12-28 18:10:14', null);

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
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruit
-- ----------------------------
INSERT INTO `recruit` VALUES ('1', '北京电视剧通告', 'Extra', '1', '北京市嘻嘻哈哈', null, null, 'Hot_Star', null, '0', '2017-12-16 15:27:38', '0', '0', '2017-12-15 15:27:46', null);

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
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recruit_info
-- ----------------------------
INSERT INTO `recruit_info` VALUES ('1', '2', '22', '1', '10', '0', '1', '1', null, '0', '2017-12-19 11:43:30', null);
INSERT INTO `recruit_info` VALUES ('2', '1', '22', '1', '50', '0', '1', '1', null, '0', '2017-12-15 15:41:03', null);

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
INSERT INTO `recruit_recruit_infos` VALUES ('1', '1');
INSERT INTO `recruit_recruit_infos` VALUES ('1', '2');

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
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NOT NULL COMMENT '用户ID',
  `amount` tinyint(4) NOT NULL,
  `report_type` varchar(32) NOT NULL COMMENT '报名状态',
  `content` varchar(255) NOT NULL COMMENT '留言',
  `report_recruit_id` bigint(32) NOT NULL COMMENT '报名招聘ID',
  `report_recruit_info_id` bigint(32) NOT NULL,
  `report_time` datetime NOT NULL COMMENT '报名时间',
  `notification` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否通知 0 false   1 true',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态0 未筛选 1 喜欢  2 不喜欢',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reports
-- ----------------------------
INSERT INTO `reports` VALUES ('1', '1', '0', 'Person', '', '1', '1', '2017-12-28 10:54:45', '0', '0', '2017-12-28 10:54:47', null);
INSERT INTO `reports` VALUES ('2', '17', '0', 'Person', '', '1', '1', '2018-01-12 12:22:23', '0', '1', '2018-01-12 12:22:27', null);
INSERT INTO `reports` VALUES ('3', '18', '0', 'Person', '', '1', '1', '2018-01-12 12:22:57', '0', '2', '2018-01-12 12:23:02', null);

-- ----------------------------
-- Table structure for tip
-- ----------------------------
DROP TABLE IF EXISTS `tip`;
CREATE TABLE `tip` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(32) NOT NULL COMMENT '举报人ID',
  `recruit_id` bigint(32) NOT NULL COMMENT '举报招聘ID',
  `type` varchar(32) NOT NULL COMMENT '举报类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
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
  `head_portrait` varchar(128) NOT NULL COMMENT '头像',
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
  `credit_degree` int(4) DEFAULT '500' COMMENT '信用度',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态 0 正常',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `openid` (`open_id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'oL7QE0TJ5zKxPK7rBmPmdsIPcR4A', '中国-北京市', '116.354', '39.9406', '%F0%9F%99%88+%E6%B5%A3%E8%8A%B1%E6%B0%B4%E6%A6%AD', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKlEeib4YaCkwyicbKvscxD3LNZeicWlKUoWyC47JjdnSCU0lqu0xU5QR5lTHuh1pPQy6vicMNOrVCiaKw/0', '2', null, null, '0', null, null, null, null, '0', '0', '0', null, '123456789', '0', '2018-01-05 12:13:01', '0', '2017-12-25 11:32:28', null);
INSERT INTO `user` VALUES ('17', 'oL7QE0f0zRL4I4FRgLRWSvrAMQ-o', '中国-北京市', '116.36611', '39.91231', '%E4%BD%A0%E7%9C%8B%E8%B5%B7%E6%9D%A5%E5%BE%88%E5%A5%BD%E5%90%83%F0%9F%91%8F', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJ9W9Tt4l7nuc08BZUWzzYUIAXXOxonAN569FGvfMja9IRiap4LMqC04I04AibibbNYbajvZxUAgdicxw/0', '1', null, null, '0', null, null, null, null, '0', '0', '0', null, null, '0', '2018-01-12 10:48:31', '0', '2017-12-25 16:07:10', null);
INSERT INTO `user` VALUES ('18', 'oL7QE0aHoTD0s2C79VcW58w2GixI', '中国-辽宁省-沈阳市', '123.45852', '41.79607', '%E6%B2%A1%E6%9C%89%E5%90%8D%E5%AD%97', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLfiboSpB2P298ImZPvDvmenbXA88ibhuYCLTVdyT57JftJI6UibYHhYY0CibzWCvkHCvJuaX4u2FerNA/0', '2', null, null, '0', null, null, null, null, '0', '0', '0', null, null, '0', '2018-01-11 17:21:47', '0', '2018-01-11 11:29:06', null);

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
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE
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
