/*
Navicat MySQL Data Transfer

Source Server         : ec
Source Server Version : 50727
Source Host           : localhost:3305
Source Database       : ec

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-04-19 20:23:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `b_branch`
-- ----------------------------
DROP TABLE IF EXISTS `b_branch`;
CREATE TABLE `b_branch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `area` varchar(255) DEFAULT NULL COMMENT '区域（云南昆明五华区）',
  `buid` int(255) DEFAULT NULL COMMENT '绑定商家信息外键',
  `detailed` varchar(255) DEFAULT NULL COMMENT 'xx街道门牌号',
  `latitude` float DEFAULT NULL COMMENT '商家纬度',
  `longitude` float DEFAULT NULL COMMENT '商家经度',
  `name` varchar(255) DEFAULT NULL COMMENT '分店名称',
  `firstmoney` decimal(10,0) DEFAULT '0' COMMENT '多少钱起送',
  `firstorder` decimal(10,0) DEFAULT '0' COMMENT '首单用户-x元',
  `psfcost` decimal(10,0) DEFAULT '0' COMMENT '配送费',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商家分店';

-- ----------------------------
-- Records of b_branch
-- ----------------------------
INSERT INTO `b_branch` VALUES ('1', '2020-04-11 13:17:51', '2', '云南昆明嵩明县杨林', '1', '商务学院一食堂一楼', '0', '0', '饭团商务学院2店', '0', '0', '0');
INSERT INTO `b_branch` VALUES ('2', '2020-04-11 13:20:43', '1', '云南昆明嵩明县杨林', '1', '海源学院一食堂', '0', '0', '饭团(海源学院总店)', '15', '3', '1');

-- ----------------------------
-- Table structure for `b_business`
-- ----------------------------
DROP TABLE IF EXISTS `b_business`;
CREATE TABLE `b_business` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `bankcard` varchar(150) DEFAULT NULL COMMENT '银行卡号',
  `name` varchar(150) DEFAULT NULL COMMENT '商家名称',
  `note` varchar(220) DEFAULT NULL COMMENT '备注',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `wxzfzh` varchar(150) DEFAULT NULL COMMENT '支付微信账号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商家信息表';

-- ----------------------------
-- Records of b_business
-- ----------------------------
INSERT INTO `b_business` VALUES ('1', '2020-04-14 22:44:14', '0', null, '饭团', '饭团', '18725082372', null);

-- ----------------------------
-- Table structure for `b_category`
-- ----------------------------
DROP TABLE IF EXISTS `b_category`;
CREATE TABLE `b_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `businessid` int(255) DEFAULT NULL COMMENT '关联商家id',
  `name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `pid` int(255) DEFAULT NULL COMMENT '分类上级id自关联',
  `branchid` int(255) DEFAULT NULL COMMENT '关联分店id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品分类表';

-- ----------------------------
-- Records of b_category
-- ----------------------------
INSERT INTO `b_category` VALUES ('1', '2020-04-14 22:51:13', '1', '1', '台湾饭团', '台湾饭团', null, null);
INSERT INTO `b_category` VALUES ('2', '2020-04-14 22:51:13', '2', '1', '鸡蛋煎饼', '鸡蛋煎饼', null, null);
INSERT INTO `b_category` VALUES ('3', '2020-04-14 22:51:13', '3', '1', '手抓饼', '手抓饼', null, null);
INSERT INTO `b_category` VALUES ('4', '2020-04-14 22:51:13', '4', '1', '富乐小吃', '富乐小吃', null, null);

-- ----------------------------
-- Table structure for `b_delivery`
-- ----------------------------
DROP TABLE IF EXISTS `b_delivery`;
CREATE TABLE `b_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `bzcost` float DEFAULT NULL COMMENT '包装费（单个）',
  `categorysid` int(255) DEFAULT NULL COMMENT '绑定商品信息',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='配送费包装费';

-- ----------------------------
-- Records of b_delivery
-- ----------------------------

-- ----------------------------
-- Table structure for `b_photo`
-- ----------------------------
DROP TABLE IF EXISTS `b_photo`;
CREATE TABLE `b_photo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `fkid` int(255) DEFAULT NULL COMMENT '关联id',
  `name` varchar(520) DEFAULT NULL COMMENT '文件名称',
  `path` varchar(520) DEFAULT NULL COMMENT '路径',
  `size` float DEFAULT '0' COMMENT '文件大小',
  `suffix` varchar(50) DEFAULT NULL COMMENT '文件后缀',
  `userid` int(255) DEFAULT NULL COMMENT '操作者id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='商品照片';

-- ----------------------------
-- Records of b_photo
-- ----------------------------
INSERT INTO `b_photo` VALUES ('1', null, '0', '1', null, '/static/fantuan/twft/rsft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('2', null, '0', '2', null, '/static/fantuan/twft/zsft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('3', null, '0', '3', null, '/static/fantuan/twft/eeljrft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('4', null, '0', '4', null, '/static/fantuan/twft/jlft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('5', null, '0', '5', null, '/static/fantuan/twft/xcft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('6', null, '0', '6', null, '/static/fantuan/twft/pgft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('7', null, '0', '7', null, '/static/fantuan/twft/jpft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('11', null, '0', '1', null, '/static/fantuan/twft/rsft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('12', null, '0', '2', null, '/static/fantuan/twft/zsft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('13', null, '0', '3', null, '/static/fantuan/twft/eeljrft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('14', null, '0', '4', null, '/static/fantuan/twft/jlft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('15', null, '0', '5', null, '/static/fantuan/twft/xcft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('16', null, '0', '6', null, '/static/fantuan/twft/pgft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('17', null, '0', '7', null, '/static/fantuan/twft/jpft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('18', null, '0', '8', null, '/static/fantuan/twft/jlxcft.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('19', null, '0', '9', null, '/static/fantuan/jb/jljb.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('20', null, '0', '10', null, '/static/fantuan/jb/rgjb.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('21', null, '0', '11', null, '/static/fantuan/jb/jpjb.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('22', null, '0', '12', null, '/static/fantuan/jb/pgjb.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('23', null, '0', '13', null, '/static/fantuan/szb/szb.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('28', null, '0', '18', null, '/static/fantuan/flxc/lf.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('29', null, '0', '19', null, '/static/fantuan/flxc/lm.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('30', null, '0', '20', null, '/static/fantuan/flxc/lmx.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('31', null, '0', '21', null, '/static/fantuan/flxc/tbcm.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('32', null, '0', '22', null, '/static/fantuan/flxc/tbcmx.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('33', null, '0', '23', null, '/static/fantuan/flxc/tbcmf.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('34', null, '0', '14', null, '/static/fantuan/szb/jl.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('35', null, '0', '15', null, '/static/fantuan/szb/rg.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('36', null, '0', '16', null, '/static/fantuan/szb/pg.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('37', null, '0', '17', null, '/static/fantuan/szb/jp.jpg', '0', 'jpg', '7');
INSERT INTO `b_photo` VALUES ('38', null, '0', '24', null, '/static/fantuan/szb/jjd.jpg', '0', 'jpg', '7');

-- ----------------------------
-- Table structure for `b_product`
-- ----------------------------
DROP TABLE IF EXISTS `b_product`;
CREATE TABLE `b_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `businessid` int(255) DEFAULT NULL COMMENT '关联商家id',
  `categoryid` int(255) DEFAULT NULL COMMENT '关联分类id',
  `memberprice` decimal(6,2) DEFAULT NULL COMMENT '会员价',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `note` varchar(255) DEFAULT NULL COMMENT '备注',
  `price` decimal(6,2) DEFAULT NULL COMMENT '单价',
  `pid` int(255) DEFAULT NULL COMMENT '关联上级id（组合套餐）',
  `packages` varchar(20) DEFAULT '0' COMMENT '是否是套餐(0否，1是)',
  `branchid` int(255) DEFAULT NULL COMMENT '关联分店id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of b_product
-- ----------------------------
INSERT INTO `b_product` VALUES ('1', '2020-04-14 23:14:45', '1', '1', '1', '5.50', '肉松饭团', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('2', '2020-04-14 23:14:45', '2', '1', '1', '7.50', '芝士饭团', null, '8.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('3', '2020-04-14 23:14:45', '3', '1', '1', '8.00', '奥尔良鸡肉饭团', null, '9.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('4', '2020-04-14 23:14:45', '4', '1', '1', '5.50', '鸡柳饭团', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('5', '2020-04-14 23:14:45', '5', '1', '1', '5.50', '香肠饭团', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('6', '2020-04-14 23:14:46', '6', '1', '1', '5.50', '培根饭团', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('7', '2020-04-14 23:14:46', '7', '1', '1', '8.00', '鸡排饭团', null, '9.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('8', '2020-04-14 23:14:46', '8', '1', '1', '8.00', '鸡柳香肠饭团', null, '9.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('9', '2020-04-14 23:36:24', '9', '1', '2', '6.00', '鸡柳煎饼', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('10', '2020-04-14 23:36:24', '10', '1', '2', '6.00', '热狗煎饼', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('11', '2020-04-14 23:36:24', '11', '1', '2', '7.00', '鸡排煎饼', null, '8.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('12', '2020-04-14 23:36:24', '12', '1', '2', '6.00', '培根煎饼', null, '6.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('13', '2020-04-14 23:36:24', '13', '1', '3', '3.50', '手抓饼', '组合套餐', '4.00', null, '1', '2');
INSERT INTO `b_product` VALUES ('14', '2020-04-14 23:36:24', '14', '1', '3', '2.00', '鸡柳', null, '2.00', '13', '0', '2');
INSERT INTO `b_product` VALUES ('15', '2020-04-14 23:36:24', '15', '1', '3', '2.00', '热狗', null, '2.00', '13', '0', '2');
INSERT INTO `b_product` VALUES ('16', '2020-04-14 23:36:24', '16', '1', '3', '2.00', '培根', null, '2.00', '13', '0', '2');
INSERT INTO `b_product` VALUES ('17', '2020-04-14 23:36:24', '17', '1', '3', '4.00', '鸡排', null, '4.00', '13', '0', '2');
INSERT INTO `b_product` VALUES ('18', '2020-04-14 23:41:42', '18', '1', '4', '7.00', '凉粉', null, '8.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('19', '2020-04-14 23:41:42', '19', '1', '4', '7.00', '凉面', null, '8.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('20', '2020-04-14 23:41:42', '20', '1', '4', '7.00', '凉米线', null, '7.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('21', '2020-04-14 23:41:42', '21', '1', '4', '8.00', '铁板炒面', null, '9.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('22', '2020-04-14 23:41:42', '22', '1', '4', '8.00', '铁板炒米线', null, '9.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('23', '2020-04-14 23:41:42', '23', '1', '4', '8.00', '铁板炒粉丝', null, '9.00', null, '0', '2');
INSERT INTO `b_product` VALUES ('24', '2020-04-14 23:45:48', '24', '1', '3', '1.50', '煎鸡蛋', null, '1.50', '13', '0', '2');

-- ----------------------------
-- Table structure for `b_spec`
-- ----------------------------
DROP TABLE IF EXISTS `b_spec`;
CREATE TABLE `b_spec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `categorysid` varchar(255) DEFAULT NULL COMMENT '关联商品id',
  `name` varchar(255) DEFAULT NULL COMMENT '规格名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='商品规格';

-- ----------------------------
-- Records of b_spec
-- ----------------------------

-- ----------------------------
-- Table structure for `u_address`
-- ----------------------------
DROP TABLE IF EXISTS `u_address`;
CREATE TABLE `u_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `area` varchar(255) DEFAULT NULL COMMENT '地区(云南昆明五华区)',
  `detailed` varchar(255) DEFAULT NULL COMMENT '几栋几单元门牌号',
  `userid` bigint(20) DEFAULT NULL COMMENT '关联用户id',
  `gender` varchar(50) DEFAULT NULL COMMENT '先生/女士',
  `name` varchar(50) DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(50) DEFAULT NULL COMMENT '收货人手机号码',
  `latitude` float DEFAULT NULL COMMENT '用户纬度',
  `longitude` float DEFAULT NULL COMMENT '用户经度',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='用户收货地址(多个地址)';

-- ----------------------------
-- Records of u_address
-- ----------------------------
INSERT INTO `u_address` VALUES ('31', '2020-04-19 17:58:23', '0', '天骄北麓19幢', '3109', '7', '先生', '严', '13888563741', '25.1151', '102.731');

-- ----------------------------
-- Table structure for `u_order`
-- ----------------------------
DROP TABLE IF EXISTS `u_order`;
CREATE TABLE `u_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `bid` int(255) DEFAULT NULL COMMENT '商家id',
  `branchid` int(255) DEFAULT NULL COMMENT '分店id',
  `completetime` datetime DEFAULT NULL COMMENT '订单完成时间',
  `discount` decimal(6,2) DEFAULT NULL COMMENT '优惠金额',
  `iscomplete` int(11) DEFAULT NULL COMMENT '是否完成(0未完成，1已完成)',
  `note` varchar(5000) DEFAULT NULL COMMENT '备注',
  `openid` varchar(250) DEFAULT NULL COMMENT '微信用户唯一标识',
  `self` int(11) DEFAULT NULL COMMENT '是否到店自取(0否，1是)',
  `status` int(11) DEFAULT NULL COMMENT '状态(0未支付，1已支付)',
  `totalfee` decimal(6,2) DEFAULT NULL COMMENT '订单总金额',
  `userid` int(255) DEFAULT NULL COMMENT '关联用户id',
  `json` text COMMENT '存储json格式字符串',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='用户订单信息';

-- ----------------------------
-- Records of u_order
-- ----------------------------
INSERT INTO `u_order` VALUES ('16', '2020-04-19 18:48:30', '1', '1', '2', null, '3.00', '0', null, 'oM_GB4rjG6k2dKlqXxrBb_0fdAWI', '0', '1', '13.00', '7', '[{\"id\":1,\"num\":1,\"name\":\"肉松饭团\",\"tprice\":6,\"tmemberprice\":5.5,\"index\":0,\"parentIndex\":0,\"imagepath\":\"https://127.0.0.1:443/static/fantuan/twft/rsft.jpg\"},{\"id\":3,\"num\":1,\"name\":\"奥尔良鸡肉饭团\",\"tprice\":9,\"tmemberprice\":8,\"index\":2,\"parentIndex\":0,\"imagepath\":\"https://127.0.0.1:443/static/fantuan/twft/eeljrft.jpg\"}]');
INSERT INTO `u_order` VALUES ('17', '2020-04-19 18:51:17', '1', '1', '2', null, '0.00', '0', null, 'oM_GB4rjG6k2dKlqXxrBb_0fdAWI', '0', '1', '16.00', '7', '[{\"id\":1,\"num\":1,\"name\":\"肉松饭团\",\"tprice\":6,\"tmemberprice\":5.5,\"index\":0,\"parentIndex\":0,\"imagepath\":\"https://127.0.0.1:443/static/fantuan/twft/rsft.jpg\"},{\"id\":3,\"num\":1,\"name\":\"奥尔良鸡肉饭团\",\"tprice\":9,\"tmemberprice\":8,\"index\":2,\"parentIndex\":0,\"imagepath\":\"https://127.0.0.1:443/static/fantuan/twft/eeljrft.jpg\"}]');

-- ----------------------------
-- Table structure for `u_orderta`
-- ----------------------------
DROP TABLE IF EXISTS `u_orderta`;
CREATE TABLE `u_orderta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `memberprice` decimal(6,2) DEFAULT NULL COMMENT '会员价',
  `orderid` int(255) DEFAULT NULL COMMENT '关联订单id',
  `pid` int(255) DEFAULT NULL COMMENT '关联上级id',
  `price` decimal(6,2) DEFAULT NULL COMMENT '单价',
  `productid` int(255) DEFAULT NULL COMMENT '关联商品id',
  `ismeal` int(11) DEFAULT '0' COMMENT '是否套餐（0否，1是）',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=78 DEFAULT CHARSET=utf8 COMMENT='用户订单明细表';

-- ----------------------------
-- Records of u_orderta
-- ----------------------------
INSERT INTO `u_orderta` VALUES ('74', '2020-04-19 18:48:30', '1', '5.50', '16', null, '6.00', '1', '0');
INSERT INTO `u_orderta` VALUES ('75', '2020-04-19 18:48:30', '2', '8.00', '16', null, '9.00', '3', '0');
INSERT INTO `u_orderta` VALUES ('76', '2020-04-19 18:51:17', '1', '5.50', '17', null, '6.00', '1', '0');
INSERT INTO `u_orderta` VALUES ('77', '2020-04-19 18:51:17', '2', '8.00', '17', null, '9.00', '3', '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buildtime` datetime DEFAULT NULL COMMENT '创建时间',
  `odr` int(11) DEFAULT '0' COMMENT '排序字段',
  `account` varchar(250) DEFAULT NULL COMMENT '账号',
  `avatarurl` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `bid` int(255) DEFAULT NULL COMMENT '关联商家',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `name` varchar(50) DEFAULT NULL COMMENT '收货姓名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '微信用户昵称',
  `openid` varchar(250) DEFAULT NULL COMMENT '微信用户唯一标识',
  `password` varchar(250) DEFAULT NULL COMMENT '密码',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `member` varchar(20) DEFAULT '0' COMMENT '是否会员(0否,1是)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='点餐用户/商家员工/老板';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7', '2020-04-14 20:22:51', '0', 'feifei_yan@.com', 'https://wx.qlogo.cn/mmopen/vi_32/Stlx6Jh69H1QH3fTxcjVfmeqe6niah7kDaic6gP1GMx8t3U1sCGHMACfWCJdZ54aMq3FQ6N4Rq3DI6P5AjmP4MIw/132', '1', '1', null, 'change their', 'oM_GB4rjG6k2dKlqXxrBb_0fdAWI', 'd13f864ba7e1b90b0b408d10c257a8da', '13888563741', '0');
