/*
Navicat MySQL Data Transfer

Source Server         : 192.168.10.244
Source Server Version : 50635
Source Host           : 192.168.10.244:3306
Source Database       : xxwb3

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2018-09-10 16:12:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for JOB_EXECUTION_LOG
-- ----------------------------
DROP TABLE IF EXISTS `JOB_EXECUTION_LOG`;
CREATE TABLE `JOB_EXECUTION_LOG` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `hostname` varchar(255) NOT NULL,
  `ip` varchar(50) NOT NULL,
  `sharding_item` int(11) NOT NULL,
  `execution_source` varchar(20) NOT NULL,
  `failure_cause` varchar(4000) DEFAULT NULL,
  `is_success` int(11) NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `complete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for JOB_STATUS_TRACE_LOG
-- ----------------------------
DROP TABLE IF EXISTS `JOB_STATUS_TRACE_LOG`;
CREATE TABLE `JOB_STATUS_TRACE_LOG` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `original_task_id` varchar(255) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `slave_id` varchar(50) NOT NULL,
  `source` varchar(50) NOT NULL,
  `execution_type` varchar(20) NOT NULL,
  `sharding_item` varchar(100) NOT NULL,
  `state` varchar(20) NOT NULL,
  `message` varchar(4000) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `TASK_ID_STATE_INDEX` (`task_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_comments_dictionaries
-- ----------------------------
DROP TABLE IF EXISTS `t_comments_dictionaries`;
CREATE TABLE `t_comments_dictionaries` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `merchant_id` tinyint(20) NOT NULL COMMENT '商户id',
  `dictionaries_id` tinyint(20) NOT NULL COMMENT '评论字典表ID',
  `is_delete` bit(1) DEFAULT NULL COMMENT '是否删除',
  `comment_id` tinyint(20) DEFAULT NULL COMMENT '评论id',
  `comment_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=692 DEFAULT CHARSET=utf8 COMMENT='评论字典表';

-- ----------------------------
-- Table structure for t_comments_merchant
-- ----------------------------
DROP TABLE IF EXISTS `t_comments_merchant`;
CREATE TABLE `t_comments_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `parent_id` bigint(20) NOT NULL COMMENT '父评论id',
  `order_id` bigint(20) DEFAULT NULL,
  `comment_from` tinyint(4) DEFAULT '0' COMMENT '0.用户 1.商户',
  `merchant_id` int(11) NOT NULL COMMENT '被评论商家id',
  `lmprove_number` int(11) DEFAULT NULL COMMENT '差评数量',
  `fabulous_number` int(11) DEFAULT NULL COMMENT '好评数量',
  `user_id` int(11) NOT NULL COMMENT '评论用户id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '评论人昵称',
  `photo` varchar(255) DEFAULT NULL COMMENT '评论人头像',
  `content` text NOT NULL COMMENT '评论内容',
  `comment_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `is_delete` bit(1) DEFAULT b'0' COMMENT '是否删除 0=正常，1=删除',
  `user_top_num` bigint(20) DEFAULT NULL COMMENT '用户赞',
  PRIMARY KEY (`id`),
  KEY `merchant_id` (`merchant_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=832 DEFAULT CHARSET=utf8 COMMENT='商户评论信息表';

-- ----------------------------
-- Table structure for t_community_politics_config
-- ----------------------------
DROP TABLE IF EXISTS `t_community_politics_config`;
CREATE TABLE `t_community_politics_config` (
  `id` int(11) NOT NULL COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '栏目名称',
  `dept_id` int(11) NOT NULL COMMENT '社区ID',
  `parent_id` int(11) DEFAULT '0' COMMENT '父栏目',
  `icon` varchar(255) DEFAULT NULL COMMENT '栏目图标',
  `user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `upd_time` datetime DEFAULT NULL COMMENT '修改时间',
  `order_num` int(11) DEFAULT NULL COMMENT '排序号',
  `status` tinyint(4) NOT NULL COMMENT '状态：0禁止，1启用',
  `version` varchar(10) DEFAULT NULL COMMENT '数据版本号',
  `valid_flag` char(1) NOT NULL COMMENT '是否有效(Y/N)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_community_politics_info
-- ----------------------------
DROP TABLE IF EXISTS `t_community_politics_info`;
CREATE TABLE `t_community_politics_info` (
  `id` int(11) NOT NULL COMMENT '信息ID',
  `dept_id` int(11) NOT NULL COMMENT '所属社区ID',
  `politics_config_id` int(11) DEFAULT NULL COMMENT '所属栏目ID',
  `info_title` varchar(255) DEFAULT NULL COMMENT '标题',
  `info_content` longtext COMMENT '内容',
  `cre_user_id` int(11) NOT NULL COMMENT '创建用户',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `upd_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(10) NOT NULL COMMENT '审批状态(DSP/TG/BTG)',
  `approve_user_id` int(11) DEFAULT NULL COMMENT '审批人',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_remark` varchar(255) DEFAULT NULL COMMENT '审批备注',
  `version` varchar(10) DEFAULT NULL COMMENT '数据版本号',
  `valid_flag` char(1) NOT NULL COMMENT '是否有效(Y/N)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息管理';

-- ----------------------------
-- Table structure for t_community_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_dept`;
CREATE TABLE `t_community_sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '社区ID',
  `sys_dept_id` int(11) DEFAULT NULL COMMENT '所属分公司',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `leader` varchar(30) DEFAULT NULL COMMENT '负责人',
  `leader_tel` varchar(15) DEFAULT NULL COMMENT '负责人电话号',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `area` int(11) DEFAULT NULL COMMENT '区县',
  `town` int(11) DEFAULT NULL COMMENT '乡镇/街办',
  `address` varchar(100) DEFAULT NULL COMMENT '所属地区',
  `user_id` tinyint(11) NOT NULL COMMENT '创建人ID',
  `cre_time` datetime NOT NULL COMMENT '创建时间',
  `upd_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` varchar(10) NOT NULL COMMENT '审批状态(DSP/TG/BTG)',
  `version` varchar(255) DEFAULT NULL COMMENT '数据版本号',
  `valid_flag` char(1) DEFAULT NULL COMMENT '是否有效(Y/N)',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=782 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Table structure for t_community_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_dict`;
CREATE TABLE `t_community_sys_dict` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '字典名称',
  `type` varchar(30) NOT NULL COMMENT '字典类型',
  `code` varchar(30) NOT NULL COMMENT '字典码',
  `value` varchar(30) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Table structure for t_community_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_log`;
CREATE TABLE `t_community_sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` int(11) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for t_community_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_menu`;
CREATE TABLE `t_community_sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` tinyint(4) NOT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Table structure for t_community_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_role`;
CREATE TABLE `t_community_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for t_community_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_role_dept`;
CREATE TABLE `t_community_sys_role_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Table structure for t_community_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_role_menu`;
CREATE TABLE `t_community_sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=922 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for t_community_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_user`;
CREATE TABLE `t_community_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(20) NOT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NOT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `real_name` varchar(30) DEFAULT NULL COMMENT '用户姓名',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Table structure for t_community_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_community_sys_user_role`;
CREATE TABLE `t_community_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Table structure for t_dictionaries
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionaries`;
CREATE TABLE `t_dictionaries` (
  `id` tinyint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '字典名称',
  `type` int(3) NOT NULL COMMENT '字典类型: 1:点赞 2:改进',
  `is_delete` bit(1) DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='评论字典表';

-- ----------------------------
-- Table structure for t_enchashment_binding
-- ----------------------------
DROP TABLE IF EXISTS `t_enchashment_binding`;
CREATE TABLE `t_enchashment_binding` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `object_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '1:用户提现 2:商户提现',
  `object_id` int(11) NOT NULL COMMENT '商户Id或用户Id 根据type判断',
  `is_default` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是默认',
  `enchashment_type` tinyint(4) NOT NULL COMMENT '提现类型  1 微信 2银行卡 3 支付宝',
  `account` varchar(20) NOT NULL COMMENT '提现账户',
  `bank` varchar(30) NOT NULL COMMENT '银行',
  `bank_name` varchar(64) DEFAULT NULL,
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `subbranch` varchar(30) NOT NULL COMMENT '支行',
  `account_name` varchar(20) NOT NULL COMMENT '开户名',
  `bank_union_id` varchar(12) DEFAULT NULL COMMENT '银联号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0删除 1正常。审核状态：2:创建审核中 3:审核不通过 4:修改审核中 5:修改不通过 6禁用',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`object_type`,`object_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8 COMMENT='商户与用户的提现账户绑定';

-- ----------------------------
-- Table structure for t_enchashment_binding_check
-- ----------------------------
DROP TABLE IF EXISTS `t_enchashment_binding_check`;
CREATE TABLE `t_enchashment_binding_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `object_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '1:用户提现 2:商户提现',
  `object_id` int(11) NOT NULL COMMENT '商户Id或用户Id 根据type判断',
  `is_default` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是默认',
  `enchashment_type` tinyint(4) NOT NULL COMMENT '提现类型  1 微信 2银行卡 3 支付宝',
  `account` varchar(20) NOT NULL COMMENT '提现账户',
  `bank` varchar(30) NOT NULL COMMENT '银行',
  `bank_name` varchar(64) DEFAULT NULL,
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `subbranch` varchar(30) NOT NULL COMMENT '支行',
  `account_name` varchar(20) NOT NULL COMMENT '开户名',
  `bank_union_id` varchar(12) DEFAULT NULL COMMENT '银联号',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0删除 1正常',
  `remark` varchar(800) DEFAULT NULL COMMENT '备注',
  `enchashment_id` int(11) NOT NULL COMMENT '银行卡ID',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`object_type`,`object_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8 COMMENT='商户与用户的提现账户绑定';

-- ----------------------------
-- Table structure for t_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `goods_name` varchar(100) NOT NULL COMMENT '商品名称',
  `details` varchar(1000) DEFAULT NULL COMMENT '商品详细',
  `original_price` int(11) NOT NULL DEFAULT '0' COMMENT '成本价',
  `market_price` int(11) NOT NULL DEFAULT '0' COMMENT '市场价',
  `discount_price` int(11) NOT NULL DEFAULT '0' COMMENT '折后价',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '商品总库存',
  `sales` int(11) NOT NULL DEFAULT '0' COMMENT '商品总销量',
  `month_sales` int(11) DEFAULT '0' COMMENT '月销量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：暂停销售  7：售罄 8:删除',
  `pictures` varchar(1000) DEFAULT NULL COMMENT '商品图片',
  `is_promotion` bit(1) NOT NULL DEFAULT b'0' COMMENT '商品促销   1为促销   0为不促销',
  `promotional_price` int(11) NOT NULL DEFAULT '0' COMMENT '促销价',
  `surplus_inventory` int(11) NOT NULL DEFAULT '0' COMMENT '剩余库存',
  `quantity_purchased` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `label_id` int(11) NOT NULL COMMENT '商品标签Id',
  `is_recommend` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否推荐',
  `thumbs_up` int(11) DEFAULT '0' COMMENT '点赞数',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8 COMMENT='商品信息';

-- ----------------------------
-- Table structure for t_goods_check
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_check`;
CREATE TABLE `t_goods_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `goods_name` varchar(100) NOT NULL COMMENT '商品名称',
  `details` varchar(1000) DEFAULT NULL COMMENT '商品详细',
  `original_price` int(11) NOT NULL DEFAULT '0' COMMENT '成本价',
  `market_price` int(11) NOT NULL DEFAULT '0' COMMENT '市场价',
  `discount_price` int(11) NOT NULL DEFAULT '0' COMMENT '折后价',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '商品总库存',
  `pictures` varchar(1000) DEFAULT NULL COMMENT '商品图片',
  `is_promotion` bit(1) NOT NULL DEFAULT b'0' COMMENT '商品促销   1为促销   0为不促销',
  `promotional_price` int(11) NOT NULL DEFAULT '0' COMMENT '促销价',
  `surplus_inventory` int(11) NOT NULL DEFAULT '0' COMMENT '剩余库存',
  `quantity_purchased` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '审核意见',
  `label_id` int(11) NOT NULL COMMENT '商品标签Id',
  `is_recommend` bit(1) DEFAULT b'0' COMMENT '是否推荐',
  `thumbs_up` int(11) DEFAULT NULL COMMENT '点赞数量',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '''审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：暂停销售 7：售罄 8:删除',
  `update_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=821 DEFAULT CHARSET=utf8 COMMENT='商品信息审核';

-- ----------------------------
-- Table structure for t_goods_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_goods_sku`;
CREATE TABLE `t_goods_sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `label_id` int(11) DEFAULT NULL,
  `goods_name` varchar(30) NOT NULL COMMENT '商品名称',
  `details` varchar(255) DEFAULT NULL COMMENT '商品详细',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1为正常 2为暂停销售 3删除',
  `pictures` varchar(1000) DEFAULT NULL COMMENT '商品图片',
  `create_time` datetime NOT NULL COMMENT '开始时间',
  `user_id` int(11) NOT NULL COMMENT '创建人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `code` varchar(11) NOT NULL COMMENT '商品编号',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记：-1删除  0正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='商品信息';

-- ----------------------------
-- Table structure for t_licence_plate
-- ----------------------------
DROP TABLE IF EXISTS `t_licence_plate`;
CREATE TABLE `t_licence_plate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `licence` varchar(30) DEFAULT NULL COMMENT '车牌号码',
  `union_id` varchar(32) NOT NULL COMMENT '微信openid',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态  -1：已删除  0：正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12481 DEFAULT CHARSET=utf8 COMMENT='用户车牌表';

-- ----------------------------
-- Table structure for t_merchant
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant`;
CREATE TABLE `t_merchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id,自动增长',
  `user_id` int(11) DEFAULT NULL COMMENT '此商户所属的用户',
  `login_username` varchar(50) NOT NULL COMMENT '商户登录名;默认与user_id的mobile一致,若mobile在本表已存在则填邮箱',
  `login_pwd` varchar(32) NOT NULL COMMENT '登录密码;默认值是login_username加密后的字符串',
  `agent_id` int(10) unsigned NOT NULL COMMENT '分公司id',
  `merchant_name` varchar(30) NOT NULL COMMENT '商户名称(店铺名称)',
  `avatar` varchar(100) DEFAULT NULL COMMENT '商户头像',
  `photos` varchar(600) DEFAULT '' COMMENT '商户展示图',
  `latitude` double DEFAULT NULL COMMENT '维度坐标',
  `longitude` double DEFAULT NULL COMMENT '经度坐标',
  `contacts` varchar(20) NOT NULL COMMENT '联系人',
  `telphone` varchar(11) NOT NULL COMMENT '本店联系电话',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：禁用',
  `recommend` bit(1) NOT NULL DEFAULT b'0' COMMENT '0:未推荐 1:推荐',
  `type_id` int(11) NOT NULL COMMENT '商户分类id',
  `type_name` varchar(30) NOT NULL,
  `province` int(10) NOT NULL COMMENT '省code，来源于表t_wb_area',
  `city` int(10) NOT NULL COMMENT '市code，来源于表t_wb_system_area',
  `area` int(10) NOT NULL COMMENT '区code，来源于表t_sys_area_info',
  `town` int(10) NOT NULL COMMENT '乡镇/街办',
  `address` varchar(150) NOT NULL COMMENT '商户地址包含省市区信息',
  `simple_address` varchar(150) DEFAULT NULL COMMENT '简单地址',
  `community_id` int(11) NOT NULL COMMENT '社区id',
  `score` double NOT NULL DEFAULT '0' COMMENT '平均分',
  `push_device_id` varchar(150) DEFAULT NULL COMMENT '消息推送的设备Id',
  `device_brand` varchar(50) DEFAULT NULL COMMENT '推送设备品牌',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开户时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `merchant_code` varchar(32) NOT NULL COMMENT '收款码',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `id_card_face_photo` varchar(100) DEFAULT NULL COMMENT '身份证正面照片',
  `id_card_back_photo` varchar(100) DEFAULT NULL COMMENT '身份证背面照片',
  `licence` varchar(100) DEFAULT NULL COMMENT '营业执照',
  `account_nummber` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  `sys_user_id` int(10) NOT NULL COMMENT '后台账号ID(创建人或者业务员)',
  `support_dispatching` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启配送 （0是 1否）',
  `pay_channel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '支付通道（1.原生 2.点点客）',
  `is_voice_function` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否开启语音提示：0（关闭），1（开启）',
  `merchant_limit` int(11) DEFAULT '0' COMMENT '商户限额,0表示不限',
  `service_charge` int(11) DEFAULT '6' COMMENT '服务费,千分比',
  `remark` varchar(100) DEFAULT NULL COMMENT '审核备注信息',
  `salesman` int(11) NOT NULL COMMENT '业务员(对应t_sys_user里的id）',
  `contract_number` varchar(100) DEFAULT NULL COMMENT '合同编号',
  `wechat_public_number` varchar(50) DEFAULT NULL COMMENT '微信公众号',
  `description` varchar(800) DEFAULT NULL COMMENT '商户介绍',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_merchant_code` (`merchant_code`) USING BTREE,
  KEY `idx_mobile` (`login_username`),
  KEY `account_id` (`user_id`),
  KEY `community_id` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1122 DEFAULT CHARSET=utf8 COMMENT='商户信息表';

-- ----------------------------
-- Table structure for t_merchant_active_star
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_active_star`;
CREATE TABLE `t_merchant_active_star` (
  `star_id` tinyint(4) NOT NULL COMMENT '星级ID',
  `min_amount` int(11) DEFAULT NULL COMMENT '最小金额，单位为分',
  `max_amount` int(11) DEFAULT NULL COMMENT '最大金额，单位为分',
  `max_reward_limit` int(11) DEFAULT NULL COMMENT '最大奖励上限，单位为分',
  PRIMARY KEY (`star_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动星级表';

-- ----------------------------
-- Table structure for t_merchant_active_star_mapping
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_active_star_mapping`;
CREATE TABLE `t_merchant_active_star_mapping` (
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `star_id` tinyint(4) DEFAULT NULL COMMENT '星级ID 1-5对应1-5星级',
  `total_amount` bigint(21) DEFAULT NULL COMMENT '平台累计收入金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`merchant_id`),
  KEY `merchant_star_id` (`merchant_id`,`star_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家活动星级对应表';

-- ----------------------------
-- Table structure for t_merchant_cash_orders
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_cash_orders`;
CREATE TABLE `t_merchant_cash_orders` (
  `cash_order_id` bigint(20) NOT NULL COMMENT '提现订单号',
  `merchant_id` int(11) NOT NULL COMMENT '商家id',
  `cash_date` date NOT NULL COMMENT '提现日期',
  `total_amount` int(11) NOT NULL COMMENT '提现金额',
  `total_count` smallint(6) NOT NULL DEFAULT '0' COMMENT '提现明细总笔数',
  `service_charge` int(11) NOT NULL DEFAULT '0' COMMENT '手续费',
  `status` tinyint(4) NOT NULL DEFAULT '5' COMMENT '提现状态  2已到账 3 待处理 4失败 5已申请',
  `to_bank_card` varchar(19) NOT NULL COMMENT '提现卡号',
  `to_bank_name` varchar(50) NOT NULL COMMENT '提现银行名称',
  `to_account_name` varchar(50) NOT NULL COMMENT '提现开户名',
  `to_bank_no` varchar(20) DEFAULT NULL COMMENT '提现银行联号',
  `finish_date` timestamp NULL DEFAULT NULL COMMENT '银行转账完成的时间',
  `error_code` char(7) DEFAULT NULL COMMENT '应答返回码',
  `error_remark` varchar(500) DEFAULT NULL COMMENT '应答描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `total_amount_cerdit` int(11) DEFAULT '0' COMMENT '信用卡提现总金额',
  `total_amount_original` int(11) DEFAULT '0' COMMENT '提现原始总金额',
  PRIMARY KEY (`cash_order_id`),
  UNIQUE KEY `uq_cash_date_merchant_id` (`cash_date`,`merchant_id`),
  KEY `merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家提现打包记录订单表';

-- ----------------------------
-- Table structure for t_merchant_cash_records
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_cash_records`;
CREATE TABLE `t_merchant_cash_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NOT NULL COMMENT '商家id',
  `total_fee` int(11) NOT NULL COMMENT '提现金额',
  `poundage` int(11) DEFAULT '0',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '提现状态  1处理中 2已完成 3提现失败',
  `to_bank_card` varchar(19) NOT NULL COMMENT '提现卡号',
  `to_bank_name` varchar(100) NOT NULL COMMENT '提现银行名称',
  `to_account_name` varchar(30) NOT NULL COMMENT '提现开户名',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `finish_date` datetime DEFAULT NULL,
  `cash_order_id` bigint(20) unsigned DEFAULT NULL COMMENT '打包提现订单号',
  `total_fee_credit` int(11) NOT NULL DEFAULT '0' COMMENT '信用卡提现金额',
  PRIMARY KEY (`id`),
  KEY `idx_create_date` (`create_date`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_merchant_cashier
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_cashier`;
CREATE TABLE `t_merchant_cashier` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id,自动增长',
  `merchant_id` int(10) unsigned NOT NULL COMMENT '商户ID',
  `open_id` char(28) NOT NULL COMMENT '收银员openId t_user_wx表里的union_id',
  `is_merchant` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否商家自己，一个商家只能有一笔为1的数据',
  `in_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=882 DEFAULT CHARSET=utf8 COMMENT='商户收银员表';

-- ----------------------------
-- Table structure for t_merchant_category
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_category`;
CREATE TABLE `t_merchant_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `parent_id` int(11) DEFAULT NULL COMMENT '分类父id',
  `name` varchar(30) NOT NULL COMMENT '分类名',
  `category_img` varchar(255) DEFAULT NULL COMMENT '分类图片',
  `sort` int(10) NOT NULL DEFAULT '99',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1105 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_merchant_check
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_check`;
CREATE TABLE `t_merchant_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id,自动增长',
  `user_id` int(11) DEFAULT NULL COMMENT '此商户所属的用户',
  `login_username` varchar(50) NOT NULL COMMENT '商户登录名;默认与user_id的mobile一致,若mobile在本表已存在则填邮箱',
  `login_pwd` varchar(32) NOT NULL COMMENT '登录密码;默认值是login_username加密后的字符串',
  `agent_id` int(10) unsigned NOT NULL COMMENT '分公司id',
  `merchant_name` varchar(30) NOT NULL COMMENT '商户名称(店铺名称)',
  `avatar` varchar(100) DEFAULT NULL COMMENT '商户头像',
  `photos` varchar(300) DEFAULT '' COMMENT '商户展示图',
  `latitude` double DEFAULT NULL COMMENT '维度坐标',
  `longitude` double DEFAULT NULL COMMENT '经度坐标',
  `contacts` varchar(20) NOT NULL COMMENT '联系人',
  `telphone` varchar(11) NOT NULL COMMENT '本店联系电话',
  `recommend` bit(1) NOT NULL DEFAULT b'0' COMMENT '0:未推荐 1:推荐',
  `type_id` int(11) NOT NULL COMMENT '商户分类id',
  `type_name` varchar(30) NOT NULL,
  `province` int(10) NOT NULL COMMENT '省code，来源于表t_wb_area',
  `city` int(10) NOT NULL COMMENT '市code，来源于表t_wb_system_area',
  `area` int(10) NOT NULL COMMENT '区code，来源于表t_sys_area_info',
  `town` int(10) NOT NULL COMMENT '乡镇/街办',
  `address` varchar(150) NOT NULL COMMENT '商户地址包含省市区信息',
  `simple_address` varchar(150) DEFAULT NULL COMMENT '简单地址',
  `community_id` int(11) NOT NULL COMMENT '社区id',
  `score` double NOT NULL DEFAULT '0' COMMENT '平均分',
  `push_device_id` varchar(64) DEFAULT NULL COMMENT '消息推送的设备Id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开户时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `merchant_code` varchar(32) NOT NULL COMMENT '收款码',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号码',
  `id_card_face_photo` varchar(100) DEFAULT NULL COMMENT '身份证正面照片',
  `id_card_back_photo` varchar(100) DEFAULT NULL COMMENT '身份证背面照片',
  `licence` varchar(100) DEFAULT NULL COMMENT '营业执照',
  `account_nummber` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  `sys_user_id` int(10) NOT NULL COMMENT '后台账号ID(创建人或者业务员)',
  `support_dispatching` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否开启配送 （0是 1否）',
  `pay_channel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '支付通道（1.原生 2.点点客）',
  `is_voice_function` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否开启语音提示：0（关闭），1（开启）',
  `merchant_limit` int(11) DEFAULT '0' COMMENT '商户限额,0表示不限',
  `service_charge` int(11) DEFAULT '6' COMMENT '服务费,千分比',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用 6：禁用',
  `remark` varchar(255) DEFAULT NULL COMMENT '审核备注信息',
  `salesman` int(11) NOT NULL,
  `contract_number` varchar(100) DEFAULT NULL COMMENT '合同编号',
  `wechat_public_number` varchar(50) DEFAULT NULL COMMENT '微信公众号',
  `description` varchar(800) DEFAULT NULL COMMENT '店铺介绍',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`login_username`),
  KEY `account_id` (`user_id`),
  KEY `community_id` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1412 DEFAULT CHARSET=utf8 COMMENT='商户审核信息表';

-- ----------------------------
-- Table structure for t_merchant_extends_ddk
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_extends_ddk`;
CREATE TABLE `t_merchant_extends_ddk` (
  `merchant_id` int(11) NOT NULL COMMENT '商家id',
  `merchant_member_id` varchar(32) DEFAULT NULL COMMENT '商户进件成功后在点点客保存的会员id',
  `merchant_class` tinyint(4) DEFAULT '2' COMMENT '商户性质    必填 选项：1.企业商户；2.个体商户',
  `merchant_type` tinyint(4) DEFAULT '2' COMMENT '商户类型 必填  选项：1.连锁商户；2.单店',
  `related_merchant_name` varchar(32) DEFAULT NULL COMMENT '关联商户号 非必填	1.填写连锁商户的主商户商户号2.非连锁商户不填写',
  `merchant_name` varchar(32) DEFAULT NULL COMMENT '商户注册名称 必填	1.填写营业执照上面的商户名称 2.个体商户填写商户简称',
  `merchant_short_name` varchar(32) DEFAULT NULL COMMENT '商户简称 必填  简称格式：地区+商户名称+行业',
  `user_defined_name` varchar(32) DEFAULT NULL COMMENT '小票名称 必填  如没有特殊需求，可以同商户简称。',
  `reg_prov` varchar(4) DEFAULT NULL COMMENT '注册地址所在省 必填	1.参考附录1：比如河北省，请填写河北 2.个体商户不填写',
  `reg_city` varchar(12) DEFAULT NULL COMMENT '注册地址所在市 必填	1.参考附录1：比如唐山市，请填写唐山市 2.个体商户不填写河北 2.个体商户不填写',
  `reg_area` varchar(12) DEFAULT NULL COMMENT '注册地址所在区县 必填  参考附录1：比如XX区，请填写XX区2.个体商户不填写',
  `reg_addr` varchar(100) DEFAULT NULL COMMENT '商户注册详细地址 必填  填写商户实际的注册详细地址',
  `prov` varchar(4) DEFAULT NULL COMMENT '经营地址所在省 必填  参考附录1：比如河北省，请填写河北',
  `city` varchar(12) DEFAULT NULL COMMENT '经营地址所在市 必填  参考附录1：比如唐山市，请填写唐山市',
  `area` varchar(12) DEFAULT NULL COMMENT '经营地址所在区县 必填  参考附录1：比如XX区，请填写XX区',
  `merchant_addr` varchar(100) DEFAULT NULL COMMENT '商户经营详细地址 必填  填写商户实际的经营详细地址',
  `business_start_hours` char(5) DEFAULT '09:00' COMMENT '营业开始时间 必填  格式要求：09：00',
  `business_end_hours` char(5) DEFAULT '22:00' COMMENT '营业结束时间  必填 格式要求：22：00',
  `merchant_header` varchar(10) DEFAULT NULL COMMENT '商户所属总部  非必填 连锁商户如果有自定义组织架构的填写',
  `bool_credit_code` bit(1) DEFAULT NULL COMMENT '是否三证合一  非必填 企业商户必填，选项：0，否；1，是。默认选择是。选择是：填写信用代码证编号；选择否：填写营业执照编号。',
  `credit_code` varchar(32) DEFAULT NULL COMMENT '统一社会信用代码证   非必填 三证合一选择是填写',
  `reg_code` varchar(32) DEFAULT NULL COMMENT '营业执照编号  非必填 三证合一选择否填写',
  `lic_type` tinyint(4) DEFAULT NULL COMMENT '证照有效期   必填 选项：1，非长期；2，长期',
  `lic_start_date` varchar(10) DEFAULT NULL COMMENT '证照开始日期 必填  日期格式要求：20180115',
  `lic_end_date` varchar(10) DEFAULT NULL COMMENT '证照结束日期  必填 选择非长期填写，日期格式要求：20180115115',
  `org_code` varchar(32) DEFAULT NULL COMMENT '组织机构代码证 非必填 三证合一选择否填写',
  `cate_type` tinyint(4) DEFAULT NULL COMMENT '商户经营类型  必填 选项：1.实体，2.虚拟。',
  `legal_name` varchar(15) DEFAULT NULL COMMENT '法人  非必填 企业用户必填，法人姓名。支持少数名族,最大支持15个汉字。',
  `id_type` tinyint(4) DEFAULT NULL COMMENT '法人证件类型  非必填 企业用户必填，选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他',
  `id_no` varchar(32) DEFAULT NULL COMMENT '法人证件号码 非必填  企业用户必填',
  `id_valid_type` tinyint(4) DEFAULT NULL COMMENT '法人证件有效期 非必填 企业用户必填，选项：1，非长期；2，长期',
  `id_start_date` varchar(10) DEFAULT NULL COMMENT '法人证件有效期开始日期 非必填 企业用户必填，日期格式要求：20180115',
  `id_end_date` varchar(10) DEFAULT NULL COMMENT '法人证件有效期证件结束日期   非必填 选择非长期填写，日期格式要求：20180115',
  `contact_name` varchar(15) DEFAULT NULL COMMENT '商户联系人   必填 商户联系人姓名。支持少数名族,最大支持15个汉字',
  `contact_id_type` tinyint(4) DEFAULT '1' COMMENT '联系人证件类型 必填  选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他',
  `contact_id_no` varchar(32) DEFAULT NULL COMMENT '联系人证件号码 必填',
  `contact_id_valid_type` tinyint(4) DEFAULT NULL COMMENT '联系人证件有效期    必填 选项：1，非长期；2，长期',
  `contact_id_start_date` varchar(10) DEFAULT NULL COMMENT '联系人证件有效期开始日期 必填  日期格式要求：20180115',
  `contact_id_end_date` varchar(10) DEFAULT NULL COMMENT '联系人证件有效期结束日期    非必填 选择非长期填写，日期格式要求：20180115',
  `contact_tel_no` char(11) DEFAULT NULL COMMENT '联系人手机   必填 手机号格式',
  `contact_email` varchar(30) DEFAULT NULL COMMENT '联系人邮箱   必填 邮箱格式',
  `contact_num` varchar(30) DEFAULT NULL COMMENT '合同编号    必填 填写协议上的实际合同编号（个体商户默认代理商统一的合同编号）',
  `sign_name` varchar(15) DEFAULT NULL COMMENT '签约业务经理  必填 实际签约的业务经理。',
  `sign_date` varchar(10) DEFAULT NULL COMMENT '签约日期    必填 实际签约的日期。',
  `sign_start_date` varchar(10) DEFAULT NULL COMMENT '合同有效期开始日期   必填 日期格式要求：20180115',
  `sign_end_date` varchar(10) DEFAULT NULL COMMENT '合同有效期结束日期   必填 日期格式要求：20180115',
  `teller_id` varchar(30) DEFAULT NULL COMMENT '管理员账号   必填 必填，建议使用手机号或联系人姓名拼音+生日，避免重复',
  `bool_send_mes` bit(1) DEFAULT NULL COMMENT '是否发送短信通知商户 必填  发送短信通知商户联系人，包含控台地址、管理员账号、密码 //1：是，0：否',
  `bool_private` bit(1) DEFAULT NULL COMMENT '结算账户类型 必填  选项：0.对公 1.对私',
  `bank_act_name` varchar(15) DEFAULT NULL COMMENT '结算账户名 必填  结算账户名称，对公结算时，结算账户名称需与商户注册名称一致',
  `bank_act_id` varchar(19) DEFAULT NULL COMMENT '结算账号 必填  结账账号',
  `bank_prov` varchar(4) DEFAULT NULL COMMENT '银行所在省 必填  参考附录1：比如河北省，请填写河北',
  `bank_city` varchar(12) DEFAULT NULL COMMENT '银行所在市 必填  参考附录1：比如唐山市，请填写唐山市',
  `bank_area` varchar(12) DEFAULT NULL COMMENT '银行所在区县 必填  参考附录1：比如XX区，请填写XX区',
  `bank_name` varchar(20) DEFAULT NULL COMMENT '银行名称 必填  参考附录2:填写',
  `settle_term` tinyint(4) DEFAULT NULL COMMENT '结算周期 必填  选项：1代表T+1； 2代表D+1',
  `fee01` double DEFAULT NULL COMMENT 'D+1结算手续费（%）	非必填 选择：D+1时必填',
  `account_id_type` tinyint(4) DEFAULT '1' COMMENT '持卡人证件类型 非必填 对私结算时必填        //选项：01:身份证、02:护照、04:军官证、03:港澳台通行证、05:回乡证，06:工商登记号 09:其他',
  `account_id_no` varchar(32) DEFAULT NULL COMMENT '持卡人证件号码 非必填 对私结算时必填 ',
  `account_id_valid_type` tinyint(4) DEFAULT NULL COMMENT '证件有效期   非必填 对私结算时必填 //选项：1，非长期；2，长期',
  `account_id_start_date` varchar(10) DEFAULT NULL COMMENT '证件有效期开始日期 非必填 对私结算时必填  日期格式要求：20180115',
  `account_id_end_date` varchar(10) DEFAULT NULL COMMENT '证件有效期结束日期   非必填 选择非长期填写，日期格式要求：20180115',
  `auth24` bit(1) DEFAULT b'0' COMMENT '是否开通TS业务(秒到业务)    必填 选项：0：否 1：是',
  `fee20` double DEFAULT NULL COMMENT 'TS结算手续费（固定）	非必填 选择固定手续费时填写',
  `fee21` double DEFAULT NULL COMMENT 'TS结算手续费（%）	非必填 选择百分比手续费时填写',
  `fee22` double DEFAULT NULL COMMENT '封顶值 非必填 选择百分比手续费时填写',
  `ts_cash_limit` double DEFAULT NULL COMMENT 'TS起结金额（元）	非必填 选项开通TS业务时填写',
  `auth23` bit(1) DEFAULT b'0' COMMENT '是否开通银行卡支付   必填 选项：0：否 1：是',
  `charge_cate_code` tinyint(4) DEFAULT NULL COMMENT '手续费类型 非必填 开通银行卡支付必填 选项：01-减免类 02-优惠类 03-标准类',
  `fee02` double DEFAULT NULL COMMENT '借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533',
  `fee04` double DEFAULT NULL COMMENT '封顶  非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55元',
  `fee03` double DEFAULT NULL COMMENT '贷记卡手续费（%）	非必填 开通银行卡支付必填 减免类为0，其他类型手续费保留小数点后4位。如：0.5533',
  `union_reg_code` varchar(32) DEFAULT NULL COMMENT '银联现场注册码 非必填 银联现场注册商户填写',
  `fee05` double DEFAULT NULL COMMENT '云闪付借记卡手续费（%）	非必填 开通银行卡支付必填 减免类手续费为0，其他类型手续费保留小数点后4位。如：0.5533',
  `fee07` double DEFAULT NULL COMMENT '封顶(元)   非必填 减免类为0，其他封顶值精确点小数点后2位。如：18.55',
  `fee06` double DEFAULT NULL COMMENT '云闪付贷记卡手续费（%）	非必填 开通银行卡支付必填         //减免类为0，其他类型手续费保留小数点后4位。如：0.5533',
  `auth20` bit(1) DEFAULT b'0' COMMENT '开通银联二维码 非必填 选项：0：否 1：是',
  `fee23` double DEFAULT NULL COMMENT '借记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533',
  `fee24` double DEFAULT NULL COMMENT '封顶(元)   非必填 精确点小数点后2位。如：18.55元',
  `fee25` double DEFAULT NULL COMMENT '贷记卡手续费（%）	非必填 选择开通银联二维码业务后必填。手续费保留小数点后4位。如：0.5533',
  `auth18` bit(1) DEFAULT b'1' COMMENT '是否开通微信支付    必填 选项：0：否 1：是',
  `wechat_cate_code` varchar(8) DEFAULT NULL COMMENT '商户经营类目（微信）	非必填 开通微信支付业务时必填，参照附录4微信支付宝经营类目对照表',
  `fee08` double DEFAULT NULL COMMENT '微信支付手续费 非必填 开通微信支付业务时必填，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写',
  `wechat_pub_num_appid` varchar(18) DEFAULT NULL COMMENT '公众号支付appid 非必填 微信公众号支付必填 如：wx191306eb02bea157',
  `wechat_pub_num_auth` varchar(50) DEFAULT NULL COMMENT '公众号支付授权目录 非必填 微信公众号支付必填 如：http://mtest2.dzq.com/qcode',
  `wechat_pub_num` char(18) DEFAULT NULL COMMENT '关注公众号   非必填 如果不填写默认关注汇付微信公众号',
  `is_merchant_wechat` bit(1) DEFAULT b'0' COMMENT '是否为商户的公众号   必填 微信公众号支付必填        //选项：1）是；0）否',
  `auth19` bit(1) DEFAULT b'1' COMMENT '是否开通支付宝支付 必填  选项：1.是；0.否',
  `alipay_cate_code` varchar(16) DEFAULT NULL COMMENT '商户经营类目（支付宝）	非必填 开通支付宝支付业务时必填，参照附录4微信支付宝经营类目对照表',
  `fee12` double DEFAULT NULL COMMENT '支付宝手续费 非必填 选择是，填写相应的手续费，精确到小数点后4位。如：0.5533        //选择否，不填写',
  `member_id` char(18) DEFAULT NULL COMMENT '代理商会员号	必填	代理商会员号18位 310000015000017897',
  `op_teller_id` char(18) DEFAULT NULL COMMENT '代理商操作员	必填	代理商操作员号 CUXXWB',
  `bg_ret_url` varchar(100) DEFAULT NULL COMMENT '异步通知地址	非必填	审核结果异步通知推送地址',
  `req_serial_num` varchar(30) DEFAULT NULL COMMENT '请求流水号	非必填	由接入方生成且保证唯一，查询时会用到',
  `wx_appid` char(18) DEFAULT NULL COMMENT '非必填	APP支付必填        //如：wx191306eb02bea157',
  `pnr_pay_merchant_type` tinyint(4) DEFAULT NULL COMMENT '商户种类	必填	选项: 1-政府机构//2-国营企业//3-私营企业//4-外资企业//5-个体工商户//7-事业单位',
  `pic_upload_way` tinyint(4) DEFAULT NULL COMMENT '图片上传方式	必填	01 控台上传（默认）;        //02 接口上传',
  `picture_ids` varchar(1000) DEFAULT NULL COMMENT '图片文件ID	非必填	当图片上传方式picUploadWay= “02”(接口上传)时，picture IDs （图片文件ID）字段必填，通过json串儿的方式，指定图片类型及图片文件编号//pictureIDs内容格式： {" archFlph01":"afc24351-b58c-4261-950f-64571b064f36"," archFlph02":"bfc24351-b58c-4261-950f-64571b064f35",...}若某个字段没图片，则赋值为空。例：{“archFlph02”: “”}',
  `auth29` tinyint(4) DEFAULT NULL COMMENT '是否开通延迟入账	非必填	选项：0：否 1：是',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 0=申请 1=审核成功 2=失败 3=冻结',
  `prod_id` varchar(32) DEFAULT NULL COMMENT '产品号',
  `sys_id` varchar(32) DEFAULT NULL COMMENT '系统号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `archFlph01` varchar(255) DEFAULT NULL COMMENT '营业执照图片.企业商户必填，格式：数字+数字，如：1_1，表示第一个商户的营业执照照片，与zip包中的图片名称对应',
  `archFlph02` varchar(255) DEFAULT NULL COMMENT '税务登记证.企业商户必填，格式：数字+数字，如：1_2，表示第一个商户的税务登记证照片，与zip包中的图片名称对应',
  `archFlph03` varchar(255) DEFAULT NULL COMMENT '组织机构代码证.企业商户必填，格式：数字+数字，如：1_3，表示第一个商户的组织机构代码证照片，与zip包中的图片名称对应',
  `archFlph04` varchar(255) DEFAULT NULL COMMENT '开户许可证.对公结算必填，格式：数字+数字，如：1_4，表示第一个商户的开户许可证照片，与zip包中的图片名称对应',
  `archFlph05` varchar(255) DEFAULT NULL COMMENT '法人身份证正面.企业商户必填，格式：数字+数字，如：1_5，表示第一个商户的法人身份证正面照片，与zip包中的图片名称对应',
  `archFlph06` varchar(255) DEFAULT NULL COMMENT '法人身份证反面.企业商户必填，格式：数字+数字，如：1_6，表示第一个商户的法人身份证反面照片，与zip包中的图片名称对应',
  `archFlph07` varchar(255) DEFAULT NULL COMMENT '结算人身份证正面.个体商户必填，格式：数字+数字，如：1_7，表示第一个商户的结算人身份证正面照片，与zip包中的图片名称对应',
  `archFlph08` varchar(255) DEFAULT NULL COMMENT '结算人身份证反面.个体商户必填，格式：数字+数字，如：1_8，表示第一个商户的结算人身份证反面照片，与zip包中的图片名称对应',
  `archFlph09` varchar(255) DEFAULT NULL COMMENT '商务协议.格式：数字+数字，如：1_9，表示第一个商户的商务协议照片，与zip包中的图片名称对应',
  `archFlph10` varchar(255) DEFAULT NULL COMMENT '公司照片一.企业商户必填，格式：数字+数字，如：1_10，表示第一个商户的公司照片一，与zip包中的图片名称对应',
  `archFlph11` varchar(255) DEFAULT NULL COMMENT '公司照片二.企业商户必填，格式：数字+数字，如：1_11，表示第一个商户的公司照片二，与zip包中的图片名称对应',
  `archFlph12` varchar(255) DEFAULT NULL COMMENT '公司照片三.企业商户必填，格式：数字+数字，如：1_12，表示第一个商户的公司照片三，与zip包中的图片名称对应',
  `archFlph13` varchar(255) DEFAULT NULL COMMENT '联系人身份证正面.个体商户必填，格式：数字+数字，如：1_13，表示第一个商户的联系人身份证正面照片，与zip包中的图片名称对应',
  `archFlph14` varchar(255) DEFAULT NULL COMMENT '联系人身份证反面.个体商户必填，格式：数字+数字，如：1_14，表示第一个商户的联系人身份证反面照片，与zip包中的图片名称对应',
  `archFlph15` varchar(255) DEFAULT NULL COMMENT '店铺门头照片.开通微信支付宝时必填，格式：数字+数字，如：1_15，表示第一个商户的店铺门头照片，与zip包中的图片名称对应',
  `archFlph16` varchar(255) DEFAULT NULL COMMENT '店铺收银台照片.开通微信支付宝时必填，格式：数字+数字，如：1_16，表示第一个商户的店铺收银台照片，与zip包中的图片名称对应',
  `archFlph17` varchar(255) DEFAULT NULL COMMENT '店内照片.开通微信支付宝时必填，格式：数字+数字，如：1_17，表示第一个商户的店内照片，与zip包中的图片名称对应',
  `archFlph18` varchar(255) DEFAULT NULL COMMENT '结算卡正面.对私结算必填，格式：数字+数字，如：1_18，表示第一个结算银行卡正面照片，与zip包中的图片名称对应',
  `archFlph19` varchar(255) DEFAULT NULL COMMENT '结算卡反面.对私结算必填，格式：数字+数字，如：1_19，表示第一个结算银行卡反面照片，与zip包中的图片名称对应',
  `check_status` bit(1) DEFAULT NULL COMMENT '审核状态：0（成功），1（失败）',
  PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家进件信息表-点点客';

-- ----------------------------
-- Table structure for t_merchant_finance
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_finance`;
CREATE TABLE `t_merchant_finance` (
  `merchant_id` int(11) NOT NULL,
  `balance` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '总余额',
  `balance_from_credit` bigint(20) NOT NULL DEFAULT '0' COMMENT '来自信用卡的收入',
  `balance_freeze_up` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结金额(备用)',
  PRIMARY KEY (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户财务表';

-- ----------------------------
-- Table structure for t_merchant_finance_flow
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_finance_flow`;
CREATE TABLE `t_merchant_finance_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交易流水号',
  `merchant_finance_id` int(11) NOT NULL COMMENT '商户账号id',
  `transaction_type` bit(1) NOT NULL DEFAULT b'0' COMMENT '交易类型 0支出 1收入',
  `transaction_way_value` tinyint(4) NOT NULL COMMENT '交易方式;1线下支付 2领取红包 3在线订单 4提现',
  `transaction_way_name` varchar(20) DEFAULT NULL COMMENT '交易方式value值对应的name值',
  `transaction_amount` bigint(20) NOT NULL COMMENT '交易金额',
  `balance` bigint(20) DEFAULT NULL COMMENT '当前金额',
  `transaction_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '交易时间',
  PRIMARY KEY (`id`),
  KEY `merchant_finance_id` (`merchant_finance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4981 DEFAULT CHARSET=utf8 COMMENT='商户财务流水记录';

-- ----------------------------
-- Table structure for t_merchant_product
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_product`;
CREATE TABLE `t_merchant_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `label_id` int(11) DEFAULT NULL COMMENT '商品标签分类id',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `details` text COMMENT '商品详细',
  `original_price` int(11) NOT NULL DEFAULT '0' COMMENT '成本价',
  `market_price` int(11) NOT NULL DEFAULT '0' COMMENT '市场价',
  `discount_price` int(11) NOT NULL DEFAULT '0' COMMENT '折后价',
  `discount` int(11) NOT NULL DEFAULT '0' COMMENT '折扣',
  `total_stock` int(11) NOT NULL DEFAULT '0' COMMENT '商品总库存',
  `total_sales` int(11) NOT NULL DEFAULT '0' COMMENT '商品总销量',
  `month_sales` int(11) NOT NULL DEFAULT '0' COMMENT '商品月销量',
  `thumbs_up` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1为正常 2为暂停销售 3删除 4售罄 5下架 6审核',
  `is_promotion` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品促销   0为不促销 1为促销   2力推',
  `promotional_price` int(11) NOT NULL DEFAULT '0' COMMENT '促销价',
  `quantity_purchased` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `sell_start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `sell_end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息表';

-- ----------------------------
-- Table structure for t_merchant_product_check
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_product_check`;
CREATE TABLE `t_merchant_product_check` (
  `check_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `label_id` int(11) DEFAULT NULL COMMENT '商品标签分类id',
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `details` text COMMENT '商品详细',
  `original_price` int(11) NOT NULL DEFAULT '0' COMMENT '成本价',
  `market_price` int(11) NOT NULL DEFAULT '0' COMMENT '市场价',
  `discount_price` int(11) NOT NULL DEFAULT '0' COMMENT '折后价',
  `discount` int(11) NOT NULL DEFAULT '0' COMMENT '折扣',
  `total_stock` int(11) NOT NULL DEFAULT '0' COMMENT '商品总库存',
  `surplus_stock` int(11) NOT NULL DEFAULT '0' COMMENT '剩余库存',
  `total_sales` int(11) NOT NULL DEFAULT '0' COMMENT '商品总销量',
  `month_sales` int(11) NOT NULL DEFAULT '0' COMMENT '商品月销量',
  `thumbs_up` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态1为正常 2为暂停销售 3删除 4售罄 5下架 6审核',
  `is_promotion` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品促销   0为不促销 1为促销   2力推',
  `promotional_price` int(11) NOT NULL DEFAULT '0' COMMENT '促销价',
  `quantity_purchased` int(11) NOT NULL DEFAULT '0' COMMENT '限购数量',
  `sell_start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `sell_end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`check_id`),
  KEY `product_id` (`product_id`),
  KEY `merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品信息审核表';

-- ----------------------------
-- Table structure for t_merchant_product_customer_label
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_product_customer_label`;
CREATE TABLE `t_merchant_product_customer_label` (
  `label_id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` int(11) NOT NULL COMMENT '商家id 若为0即可看作是通用的商品标签',
  `label_name` varchar(10) NOT NULL DEFAULT '' COMMENT '标签名称',
  `sort_no` tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0=正常，1=删除',
  `status` tinyint(4) DEFAULT '1' COMMENT '审核状态：1:创建审核中 2:审核不通过 3:修改审核中 4:修改不通过 5:正常使用',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`label_id`),
  KEY `merchant_id` (`merchant_id`,`label_name`)
) ENGINE=InnoDB AUTO_INCREMENT=672 DEFAULT CHARSET=utf8 COMMENT='商户商品自定义分类表';

-- ----------------------------
-- Table structure for t_merchant_product_photos
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_product_photos`;
CREATE TABLE `t_merchant_product_photos` (
  `photos_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `merchant_id` int(11) NOT NULL COMMENT '商户ID',
  `photos_url` char(255) NOT NULL COMMENT '图片url',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否使用 0=正常 1=删除',
  `sort_no` int(11) NOT NULL DEFAULT '0' COMMENT '排序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`photos_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `t_merchant_product_photos_picture_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `t_merchant_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片表';

-- ----------------------------
-- Table structure for t_pay_notify_al
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_notify_al`;
CREATE TABLE `t_pay_notify_al` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '交易创建时间',
  `charset` varchar(8) NOT NULL COMMENT '编码格式',
  `seller_email` varchar(100) DEFAULT NULL COMMENT '卖家账号',
  `subject` varchar(256) DEFAULT NULL COMMENT '订单标题',
  `body` varchar(400) DEFAULT NULL COMMENT '商品描述',
  `buyer_id` char(16) DEFAULT NULL COMMENT '买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字',
  `invoice_amount` int(11) DEFAULT NULL COMMENT '开票金额',
  `notify_id` varchar(128) NOT NULL COMMENT '通知校验ID',
  `fund_bill_list` varchar(512) DEFAULT NULL COMMENT '支付金额信息',
  `notify_type` varchar(64) NOT NULL COMMENT '通知类型',
  `trade_status` varchar(32) DEFAULT NULL COMMENT '交易状态',
  `receipt_amount` int(11) DEFAULT NULL COMMENT '实收金额',
  `buyer_pay_amount` int(11) DEFAULT NULL COMMENT '付款金额',
  `app_id` varchar(16) NOT NULL COMMENT '开发者的app_id',
  `seller_id` varchar(30) DEFAULT NULL COMMENT '卖家支付宝用户号 ex:2088101106499364',
  `gmt_payment` timestamp NULL DEFAULT NULL COMMENT '交易付款时间',
  `notify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通知时间',
  `version` varchar(3) NOT NULL COMMENT '接口版本',
  `out_trade_no` varchar(64) NOT NULL COMMENT '商户订单号',
  `total_amount` int(11) DEFAULT NULL COMMENT '订单金额',
  `trade_no` varchar(64) NOT NULL COMMENT '交易号',
  `auth_app_id` varchar(16) DEFAULT NULL,
  `buyer_logon_id` varchar(100) DEFAULT NULL COMMENT '买家账号',
  `point_amount` int(11) DEFAULT NULL COMMENT '集分宝金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8 COMMENT='支付通知详情';

-- ----------------------------
-- Table structure for t_pay_notify_wx
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_notify_wx`;
CREATE TABLE `t_pay_notify_wx` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id主键',
  `transaction_id` varchar(32) NOT NULL COMMENT '微信支付订单号',
  `nonce_str` varchar(32) NOT NULL COMMENT '随机字符串',
  `bank_type` varchar(16) NOT NULL COMMENT '付款银行',
  `openid` varchar(28) NOT NULL COMMENT '用户标识',
  `sign` varchar(64) NOT NULL COMMENT '签名',
  `fee_type` varchar(8) DEFAULT NULL COMMENT '货币种类	',
  `mch_id` varchar(32) NOT NULL COMMENT '商户号',
  `sub_mch_id` varchar(32) DEFAULT NULL COMMENT '子商户号',
  `cash_fee` int(11) NOT NULL COMMENT '现金支付金额',
  `device_info` varchar(32) DEFAULT NULL COMMENT '设备号',
  `out_trade_no` varchar(32) NOT NULL COMMENT '商户订单号	',
  `appid` varchar(32) NOT NULL COMMENT '公众账号ID',
  `total_fee` int(11) NOT NULL COMMENT '总金额',
  `trade_type` varchar(16) NOT NULL COMMENT '交易类型',
  `result_code` varchar(16) NOT NULL COMMENT '业务结果',
  `time_end` varchar(14) NOT NULL COMMENT '支付完成时间',
  `is_subscribe` char(1) DEFAULT NULL COMMENT '是否关注公众账号',
  `return_code` varchar(16) NOT NULL COMMENT '返回状态码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=691 DEFAULT CHARSET=utf8 COMMENT='微信支付通知详情';

-- ----------------------------
-- Table structure for t_sys_app
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_app`;
CREATE TABLE `t_sys_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `version` varchar(255) NOT NULL COMMENT '版本号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `is_force_update` tinyint(1) DEFAULT NULL COMMENT '是否强制更新',
  `app_type` bit(1) NOT NULL COMMENT 'app类型（0：安卓；1：ios）',
  `app_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1432 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) NOT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `user_id` int(11) NOT NULL COMMENT '创建人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `status` int(11) NOT NULL COMMENT '状态',
  `leader` varchar(30) NOT NULL COMMENT '负责人',
  `leader_tel` varchar(15) DEFAULT NULL COMMENT '负责人电话号',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=372 DEFAULT CHARSET=utf8 COMMENT='部门管理';

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `type` varchar(100) NOT NULL COMMENT '字典类型',
  `code` varchar(100) NOT NULL COMMENT '字典码',
  `value` varchar(1000) NOT NULL COMMENT '字典值',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标记  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`,`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1314312 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` int(11) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14591 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NOT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=812 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Table structure for t_sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_notice`;
CREATE TABLE `t_sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(64) NOT NULL COMMENT '标题',
  `content` longtext NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `send_time` datetime NOT NULL COMMENT '发送时间',
  `user_id` int(11) NOT NULL COMMENT '创建人ID',
  `is_top` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否置顶:0不置顶，1置顶',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=311 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_id` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Table structure for t_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE `t_sys_role_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `dept_id` int(11) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=322 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `menu_id` int(11) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7132 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `real_name` varchar(30) DEFAULT NULL COMMENT '用户姓名',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=442 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Table structure for t_user_address
-- ----------------------------
DROP TABLE IF EXISTS `t_user_address`;
CREATE TABLE `t_user_address` (
  `id` int(11) NOT NULL COMMENT '详情地址id',
  `user_id` int(11) NOT NULL COMMENT 'userId',
  `province_code` int(11) NOT NULL COMMENT '省市区编号',
  `city_code` int(11) NOT NULL,
  `area_code` int(11) NOT NULL,
  `details_address` varchar(255) NOT NULL COMMENT '详情地址',
  `receiver_name` varchar(20) NOT NULL COMMENT '用户名称',
  `reveiver_phone` varchar(11) DEFAULT NULL COMMENT '收货人电话',
  `open_id` varchar(32) DEFAULT NULL COMMENT '是openid不是unionid',
  `is_default` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是默认收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货人地址详情';

-- ----------------------------
-- Table structure for t_user_ali
-- ----------------------------
DROP TABLE IF EXISTS `t_user_ali`;
CREATE TABLE `t_user_ali` (
  `ali_user_id` varchar(32) NOT NULL COMMENT '支付宝用户id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户正式ID',
  `mobile` varchar(11) DEFAULT NULL COMMENT '用户手机号码',
  `alipay_user_id` varchar(32) DEFAULT NULL COMMENT '支付宝支付用户id',
  `province` varchar(10) DEFAULT NULL COMMENT '省份',
  `city` varchar(12) DEFAULT NULL COMMENT '城市',
  `gender` tinyint(4) DEFAULT '0' COMMENT '0:未知 1:男 2:女',
  `head_img_url` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户呢称',
  `user_type_value` tinyint(4) NOT NULL DEFAULT '2' COMMENT '用户类型:1代表公司账户2代表个人账户',
  `user_status` char(1) DEFAULT NULL COMMENT '用户状态:Q代表快速注册用户 T代表已认证用户 B代表被冻结账户 W代表已注册，未激活的账户	可空	T',
  `is_certified` char(1) DEFAULT NULL COMMENT '是否通过实名认证:T是通过 F是没有实名认证	可空	T',
  `is_licence_auth` char(1) DEFAULT NULL COMMENT '是否通过实名认证:T是通过 F是没有实名认证	可空	T',
  `is_student_certified` char(1) DEFAULT NULL COMMENT '是否是学生:T是学生 F不是学生	可空	T',
  `is_bank_auth` char(1) DEFAULT NULL COMMENT '是否授权:T是 F否	可空	T',
  `is_mobile_auth` char(1) DEFAULT NULL COMMENT '是否授权:T是 F否	可空	T',
  `is_id_auth` char(1) DEFAULT NULL COMMENT '是否授权:T是 F否	可空	T',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`ali_user_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付宝临时用户信息表';

-- ----------------------------
-- Table structure for t_user_coupon_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_coupon_record`;
CREATE TABLE `t_user_coupon_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `card_id` int(11) NOT NULL COMMENT '优惠卷Id',
  `merchant_id` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '领取时间',
  `begin_time` datetime DEFAULT NULL COMMENT '使用开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '过期时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态 1可用 2过期 3已使用',
  `logo` varchar(255) DEFAULT NULL COMMENT '卡券logo',
  `description` varchar(2000) DEFAULT NULL COMMENT '使用说明(如:全场通用;不可与打折商品一起使用)',
  `type` tinyint(4) NOT NULL COMMENT '卡券种类(0,代金卷;1,折扣券;2,满减券)',
  `discount` int(11) DEFAULT NULL COMMENT '折扣(1至99的整数)',
  `full_money` int(11) DEFAULT '0' COMMENT '满足金额(满减券使用,满金额,满后所减金额用cardmoney保存)',
  `card_money` int(11) DEFAULT '0' COMMENT '卡券金额',
  `card_content` varchar(255) DEFAULT NULL COMMENT '优惠券名称(如:满100减10;全场8折)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7261 DEFAULT CHARSET=utf8 COMMENT='用户领取的优惠券';

-- ----------------------------
-- Table structure for t_user_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_user_shopping_cart`;
CREATE TABLE `t_user_shopping_cart` (
  `cart_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `open_id` varchar(28) NOT NULL COMMENT '用户open_id 临时用户有值',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id 正式用户有值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户购物车信息表';

-- ----------------------------
-- Table structure for t_user_shopping_cart_details
-- ----------------------------
DROP TABLE IF EXISTS `t_user_shopping_cart_details`;
CREATE TABLE `t_user_shopping_cart_details` (
  `cart_id` bigint(20) unsigned NOT NULL COMMENT '用户购物车信息表id',
  `merchant_id` int(11) NOT NULL COMMENT '商户id',
  `product_id` int(11) NOT NULL COMMENT '商品id',
  `product_count` int(11) NOT NULL COMMENT '商品数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cart_id`),
  KEY `merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户购物车信息明细表';

-- ----------------------------
-- Table structure for t_user_top
-- ----------------------------
DROP TABLE IF EXISTS `t_user_top`;
CREATE TABLE `t_user_top` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `union_id` varchar(32) NOT NULL COMMENT '微信openid',
  `comment_id` bigint(20) NOT NULL COMMENT '评论ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1262 DEFAULT CHARSET=utf8 COMMENT='用户赞表';

-- ----------------------------
-- Table structure for t_user_weibao
-- ----------------------------
DROP TABLE IF EXISTS `t_user_weibao`;
CREATE TABLE `t_user_weibao` (
  `user_id` int(11) NOT NULL,
  `weibao_withdrawals` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '可提现金额',
  `weibao_no_withdrawals` bigint(20) NOT NULL DEFAULT '0' COMMENT '不能提现',
  `weibao_freeze_up` bigint(20) NOT NULL DEFAULT '0' COMMENT '冻结金额',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user_wx
-- ----------------------------
DROP TABLE IF EXISTS `t_user_wx`;
CREATE TABLE `t_user_wx` (
  `union_id` varchar(32) NOT NULL COMMENT '微信openid',
  `user_id` int(11) DEFAULT NULL COMMENT '用户正式ID',
  `mobile` varchar(20) DEFAULT NULL COMMENT '用户手机号码',
  `country` varchar(10) DEFAULT NULL COMMENT '用户所在国家',
  `province` varchar(30) DEFAULT NULL COMMENT '省份',
  `city` varchar(30) DEFAULT NULL COMMENT '城市',
  `group_id` tinyint(4) DEFAULT NULL COMMENT '分组',
  `head_img_url` varchar(255) DEFAULT '' COMMENT '用户头像',
  `nick_name` varchar(512) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '用户呢称',
  `sex` tinyint(4) DEFAULT '0' COMMENT '0:未知 1:男 2:女',
  `open_id` varchar(32) DEFAULT NULL COMMENT '微信openid',
  `subscribe` bit(1) DEFAULT b'0' COMMENT '0:未关注 1:已关注',
  `subscribe_time` timestamp NULL DEFAULT NULL COMMENT '关注微信号时间',
  `language` varchar(10) DEFAULT NULL COMMENT '返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`union_id`),
  UNIQUE KEY `uq_open_id` (`open_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信临时用户信息表';

-- ----------------------------
-- Table structure for t_vyicoo_area
-- ----------------------------
DROP TABLE IF EXISTS `t_vyicoo_area`;
CREATE TABLE `t_vyicoo_area` (
  `area_code` int(11) NOT NULL COMMENT '地区码',
  `area_name` varchar(255) DEFAULT NULL COMMENT '区域名称',
  `area_parent_id` int(11) DEFAULT NULL COMMENT '上级区域编码',
  `area_type` varchar(255) DEFAULT NULL COMMENT '区域类型',
  PRIMARY KEY (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_vyicoo_category
-- ----------------------------
DROP TABLE IF EXISTS `t_vyicoo_category`;
CREATE TABLE `t_vyicoo_category` (
  `id` int(11) NOT NULL COMMENT '经营类目id',
  `category` varchar(255) NOT NULL COMMENT '经营类目',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_vyicoo_jinjian
-- ----------------------------
DROP TABLE IF EXISTS `t_vyicoo_jinjian`;
CREATE TABLE `t_vyicoo_jinjian` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `merchant_id` int(11) NOT NULL COMMENT '商户id',
  `type` varchar(255) NOT NULL COMMENT '商户类型 1：企业 2：个体工商户',
  `name` varchar(255) NOT NULL COMMENT '商户名称',
  `shortname` varchar(255) NOT NULL COMMENT '商户简称（4-15字）',
  `realname` varchar(255) NOT NULL COMMENT '商户真实姓名',
  `mobile` varchar(255) NOT NULL COMMENT '联系人手机号',
  `id_no` varchar(255) NOT NULL COMMENT '身份证号',
  `gb_province_no` varchar(255) NOT NULL COMMENT '省',
  `gb_city_no` varchar(255) NOT NULL COMMENT '市',
  `gb_district_no` varchar(255) NOT NULL COMMENT '区',
  `address` varchar(255) NOT NULL COMMENT '经营地址',
  `email` varchar(255) NOT NULL COMMENT '联系邮箱',
  `category` varchar(255) NOT NULL COMMENT '经营类别',
  `license_type` varchar(255) NOT NULL COMMENT '营业执照类型',
  `license_no` varchar(255) NOT NULL COMMENT '营业执照编号',
  `license_address` varchar(255) NOT NULL COMMENT '营业执照地址',
  `license_start_date` varchar(255) NOT NULL COMMENT '注册号开始日期 （例：2017-12-01）',
  `license_end_date` varchar(255) NOT NULL COMMENT '注册号结束日期 （例：2029-12-01 永久为-1）',
  `bank_id` varchar(255) NOT NULL COMMENT '开户支行ID',
  `account_city` varchar(255) NOT NULL COMMENT '开户支行所在城市编码',
  `bank_no` varchar(255) NOT NULL COMMENT '开户支行行号',
  `account_type` varchar(255) NOT NULL COMMENT '帐户类型 0：对私 1：对公',
  `account_name` varchar(255) NOT NULL COMMENT '开户名称',
  `account_mobile` varchar(255) NOT NULL COMMENT '银行预留手机号',
  `self_appid` varchar(255) NOT NULL COMMENT '有无公众号 1：有公众号 2：无公众号',
  `wx_appid` varchar(255) DEFAULT NULL COMMENT '有公众号必填（公众号主体需同营业执照名称一致）',
  `wechat_id` varchar(255) NOT NULL COMMENT '商户微信号',
  `bankcard_no` varchar(255) NOT NULL COMMENT '法人银行卡号',
  `license_pic` varchar(255) NOT NULL COMMENT '营业执照照片',
  `id_front_pic` varchar(255) NOT NULL COMMENT '法人身份证正面照片',
  `id_back_pic` varchar(255) NOT NULL COMMENT '法人身份证反面照片',
  `bankcard_pic` varchar(255) NOT NULL COMMENT '对私：法人结算银行卡照片 对公：开户许可证',
  `shop_pic` varchar(255) NOT NULL COMMENT '店铺门头照片',
  `extra_pic1` varchar(255) NOT NULL COMMENT '经营场所内照片',
  `extra_pic2` varchar(255) NOT NULL COMMENT '收银台招牌照片',
  `payment` varchar(255) NOT NULL COMMENT '支付方式配置json串',
  `mch_id` varchar(255) DEFAULT NULL COMMENT '易客付商户号',
  `verify_status` int(11) DEFAULT NULL COMMENT '商户进件状态 0：申请中， 1：启用 2 : 认证中 3：失败 4：处理中',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_wb_area
-- ----------------------------
DROP TABLE IF EXISTS `t_wb_area`;
CREATE TABLE `t_wb_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_code` int(10) unsigned NOT NULL,
  `parent_code` int(10) unsigned NOT NULL COMMENT '上一级的id值',
  `name` varchar(50) NOT NULL COMMENT '地区名称',
  `sort` mediumint(8) unsigned NOT NULL DEFAULT '99' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=662292 DEFAULT CHARSET=utf8 COMMENT='地区信息';

-- ----------------------------
-- Table structure for t_wb_card
-- ----------------------------
DROP TABLE IF EXISTS `t_wb_card`;
CREATE TABLE `t_wb_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '发行方类型 1:平台 2:商户',
  `creator_id` int(11) NOT NULL COMMENT '商户id',
  `begin_time` datetime DEFAULT NULL COMMENT '可使用开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '卡券过期时间',
  `card_num` int(10) NOT NULL COMMENT '卡卷数量',
  `left_num` int(10) NOT NULL COMMENT '剩余卡券数量',
  `limit_num` int(10) DEFAULT '0' COMMENT '用户最多持有张数',
  `logo` varchar(255) DEFAULT NULL COMMENT '卡券logo',
  `description` varchar(2000) DEFAULT NULL COMMENT '使用说明(如:全场通用;不可与打折商品一起使用)',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：1.发放，2.停用',
  `create_date` datetime NOT NULL COMMENT '发行时间',
  `type` tinyint(4) NOT NULL COMMENT '卡券种类(0,代金卷;1,折扣券;2,满减券)',
  `discount` int(11) DEFAULT NULL COMMENT '折扣(1至99的整数)',
  `full_money` int(11) DEFAULT NULL COMMENT '满足金额(满减券使用,满金额,满后所减金额用cardmoney保存)',
  `card_money` int(11) DEFAULT NULL COMMENT '卡券金额',
  `card_content` varchar(255) DEFAULT NULL COMMENT '优惠券名称(如:满100减10;全场8折)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12912 DEFAULT CHARSET=utf8 COMMENT='卡券表';

-- ----------------------------
-- Table structure for t_wb_order
-- ----------------------------
DROP TABLE IF EXISTS `t_wb_order`;
CREATE TABLE `t_wb_order` (
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `parent_order_id` int(11) DEFAULT NULL COMMENT '父订单id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `merchant_id` int(11) NOT NULL COMMENT '商家id',
  `total_amount` int(11) NOT NULL DEFAULT '0' COMMENT '订单总价即原价',
  `settle_amount` int(11) NOT NULL DEFAULT '0' COMMENT '实际给商家的结算价',
  `final_amount` int(11) NOT NULL DEFAULT '0' COMMENT '实际支付价',
  `poundage` int(11) NOT NULL DEFAULT '0' COMMENT '手续费',
  `is_final_amount_from_credit` bit(1) DEFAULT b'0' COMMENT '判断最后支付金额是不是来自信用卡',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态 -2.已退款 -1.已取消 0:待支付 1:已支付 2:已结算 3:已完成',
  `union_id` varchar(32) DEFAULT NULL COMMENT '如果order_from值是微信那么这里是union_id;order_from值是支付宝那么这里是获取支付宝的唯一id',
  `order_from` tinyint(4) NOT NULL COMMENT '订单来源(1.微信 2.支付宝3.微信反扫.4支付宝反扫)',
  `order_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '订单类型(1.扫码支付2.扫码收款 3.在线下单 4.在线交费 5.免单)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付完成时间',
  `user_delete` bit(1) DEFAULT b'0' COMMENT '用户删除',
  `merchant_delete` bit(1) DEFAULT b'0' COMMENT '商户删除',
  `pay_channel` tinyint(4) NOT NULL DEFAULT '1' COMMENT '银行通道类型；1=原生，2=点点客',
  `encrypted_data` varchar(64) DEFAULT NULL COMMENT '重要信息加密后存放于此，方便后面的验证订单数据完整性',
  `rank` int(11) DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单数据表0';

-- ----------------------------
-- Table structure for t_wb_order_bill
-- ----------------------------
DROP TABLE IF EXISTS `t_wb_order_bill`;
CREATE TABLE `t_wb_order_bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id,自动增长',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `total_fee` int(11) NOT NULL DEFAULT '0' COMMENT '该笔单据的金额',
  `type` tinyint(4) NOT NULL COMMENT '1.微宝 2.优惠券 3.支付宝 4.微信',
  `coupon_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11881 DEFAULT CHARSET=utf8 COMMENT='结算单据表0';

-- ----------------------------
-- Table structure for t_wb_order_details
-- ----------------------------
DROP TABLE IF EXISTS `t_wb_order_details`;
CREATE TABLE `t_wb_order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id,自动增长',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(30) NOT NULL COMMENT '商品名称',
  `price` int(11) NOT NULL COMMENT '原始单价',
  `final_price` int(11) NOT NULL COMMENT '最终价',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_order_product_id` (`order_id`,`goods_id`),
  KEY `product_id` (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4072 DEFAULT CHARSET=utf8 COMMENT='订单明细数据表0';

-- ----------------------------
-- Table structure for t_wb_user
-- ----------------------------
DROP TABLE IF EXISTS `t_wb_user`;
CREATE TABLE `t_wb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL COMMENT '用户手机号码',
  `real_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `id_card` varchar(18) DEFAULT NULL COMMENT '用户身份证号码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:正常 1:异常 2:冻结',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `delFlag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记：0正常   -1删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_mobile` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=1221 DEFAULT CHARSET=utf8 COMMENT='用户基础信息表';

-- ----------------------------
-- Table structure for z_t_comments
-- ----------------------------
DROP TABLE IF EXISTS `z_t_comments`;
CREATE TABLE `z_t_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `comment_user_id` int(11) DEFAULT NULL COMMENT '评论用户ID',
  `comment_object_id` int(11) DEFAULT NULL COMMENT '评论对象ID',
  `comment_content` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论内容',
  `comment_time` datetime DEFAULT NULL COMMENT '评论时间',
  `user_nickName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论人昵称',
  `user_photo` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论人头像',
  `comment_user_open_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论用户openId',
  `to_open_id` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '回复目标openId',
  `comment_images` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论图片  多个;',
  `score` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评分',
  `comment_label` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '评论标签',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`comment_object_id`) USING BTREE,
  KEY `idx_comment_time` (`comment_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='评论表';
