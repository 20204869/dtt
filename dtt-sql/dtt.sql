/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : dtt

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 09/02/2022 18:19:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '主框架页-默认皮肤样式名称', 'sys.index.skinName', 'skin-blue', 'Y', 'admin', '2022-02-09 09:48:42', '', NULL, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow');
INSERT INTO `sys_config` VALUES (2, '用户管理-账号初始密码', 'sys.user.initPassword', '123456', 'Y', 'admin', '2022-02-09 09:48:42', '', NULL, '初始化密码 123456');
INSERT INTO `sys_config` VALUES (3, '主框架页-侧边栏主题', 'sys.index.sideTheme', 'theme-dark', 'Y', 'admin', '2022-02-09 09:48:42', '', NULL, '深色主题theme-dark，浅色主题theme-light');
INSERT INTO `sys_config` VALUES (4, '账号自助-验证码开关', 'sys.account.captchaOnOff', 'true', 'Y', 'admin', '2022-02-09 09:48:42', '', NULL, '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `sys_config` VALUES (5, '账号自助-是否开启用户注册功能', 'sys.account.registerUser', 'false', 'Y', 'admin', '2022-02-09 09:48:42', '', NULL, '是否开启注册用户功能（true开启，false关闭）');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `leader` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 200 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '上市科技', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', '深圳总公司', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', '长沙分公司', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', '2022-02-09 09:48:36', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_label` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '通知');
INSERT INTO `sys_dict_data` VALUES (15, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '公告');
INSERT INTO `sys_dict_data` VALUES (16, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2022-02-09 14:26:37', '', NULL, '停用状态');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2022-02-09 09:48:42', '', NULL, '登录状态列表');

-- ----------------------------
-- Table structure for sys_load_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_load_file`;
CREATE TABLE `sys_load_file`  (
  `file_id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件id',
  `conf_id` int NULL DEFAULT NULL COMMENT '配置id',
  `file_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `load_time` datetime(6) NULL DEFAULT NULL COMMENT '上传时间',
  `load_user` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件上传人',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除 0 存在 1删除',
  `file_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径',
  PRIMARY KEY (`file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_load_file
-- ----------------------------
INSERT INTO `sys_load_file` VALUES (21, 2, '额外补充不到但2021-20220112.xlsx', '2022-02-09 17:52:09.000000', '张闪闪', '0', '/root/uploadPath/load/data/finance/out_order_data/im_financial_out_order_data_20220112.csv');
INSERT INTO `sys_load_file` VALUES (22, 3, '经销商分区明细表-2021.xlsx', '2022-02-09 17:52:12.000000', '张闪闪', '0', '/root/uploadPath/load/data/finance/dealer_area/im_4S_dealer_area_detail_2021.csv');
INSERT INTO `sys_load_file` VALUES (23, 4, 'PDCA冠销风险敞口-20211231.xlsx', '2022-02-09 17:52:14.000000', '张闪闪', '0', '/root/uploadPath/load/data/finance/crown_sell_risk_data/im_financial_risk_exposure_cp_2022.csv');
INSERT INTO `sys_load_file` VALUES (24, 5, '车品牌分类表-2021.xlsx', '2022-02-09 17:52:17.000000', '张闪闪', '0', '/root/uploadPath/load/data/finance/car_brand/im_4S_car_brand_class_2021.csv');
INSERT INTO `sys_load_file` VALUES (25, 6, '保险预算2020-2021.xlsx', '2022-02-09 17:52:20.000000', '张闪闪', '0', '/root/uploadPath/load/data/finance/insurance_budget/im_financial_insurance_budget_2022.csv');
INSERT INTO `sys_load_file` VALUES (26, 7, '2022年PDCA预算-20220209.xlsx', '2022-02-09 17:52:22.000000', '张闪闪', '0', '/root/uploadPath/load/data/finance/income_cost_budget/im_financial_income_cost_budget_2022.csv');

-- ----------------------------
-- Table structure for sys_load_file_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_load_file_conf`;
CREATE TABLE `sys_load_file_conf`  (
  `conf_id` int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `business` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入文件对应的业务系统',
  `file_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入文件类别',
  `hive_table` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入文件对应hive表',
  `hdfs_path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入文件写入hdfs路径',
  `table_prefix` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '导入文件对应的hive表名前缀',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(6) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '是否删除 0 存在 1 删除',
  PRIMARY KEY (`conf_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_load_file_conf
-- ----------------------------
INSERT INTO `sys_load_file_conf` VALUES (2, '财务', '额外补充不到单', 'hive_import.im_financial_out_order_data', '/load/data/finance/out_order_data', 'im_financial', '财务PDCA线下补充数据', 'admin', NULL, '2022-02-09 17:21:36.000000', NULL, '0');
INSERT INTO `sys_load_file_conf` VALUES (3, '财务', '经销商分区', 'hive_import.im_4s_dealer_area_detail', '/load/data/finance/dealer_area', 'im_4s', '4S渠道，孙颖萱 给出经销商分区对应线下数据', 'admin', NULL, '2022-02-09 17:23:13.000000', NULL, '0');
INSERT INTO `sys_load_file_conf` VALUES (4, '财务', 'PDCA冠销风险敞口数据', 'hive_import.im_financial_risk_exposure_cp', '/load/data/finance/crown_sell_risk_data/', 'im_financial', 'PDCA Excel离线数据 财务进行线下补充', 'admin', 'admin', '2022-02-09 17:40:07.000000', '2022-02-09 17:42:10.000000', '0');
INSERT INTO `sys_load_file_conf` VALUES (5, '财务', '车品牌分类', 'hive_import.im_4s_car_brand_class', '/load/data/finance/car_brand', 'im_4s', '4S渠道 孙颖萱 提供车品牌及车品牌分类对应关系 Excel线下补充数据', 'admin', NULL, '2022-02-09 17:41:40.000000', NULL, '0');
INSERT INTO `sys_load_file_conf` VALUES (6, '保险', '保险预算', 'hive_import.im_financial_insurance_budget', '/load/data/finance/insurance_budget/', 'im_financial', '财务进行数据观测的保险数据，财务线下补充', 'admin', NULL, '2022-02-09 17:43:33.000000', NULL, '0');
INSERT INTO `sys_load_file_conf` VALUES (7, '财务', 'PDCA成本费用预算', 'hive_import.im_financial_income_costs_budget', '/load/data/finance/income_cost_budget/', 'im_financial', 'PDCA 财务线下提供结算数据', 'admin', NULL, '2022-02-09 17:44:38.000000', NULL, '0');

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-02-09 13:04:46');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2022-02-09 15:42:46');
INSERT INTO `sys_logininfor` VALUES (102, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-02-09 15:42:52');
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '退出成功', '2022-02-09 15:43:22');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-02-09 15:43:26');
INSERT INTO `sys_logininfor` VALUES (105, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2022-02-09 16:56:43');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由参数',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2000 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 'system', NULL, '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2022-02-09 09:48:38', '', NULL, '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 'monitor', NULL, '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2022-02-09 09:48:38', '', NULL, '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, 'load-file', 0, 'load', NULL, NULL, 1, 0, 'M', '0', '0', NULL, 'upload', 'admin', '2022-02-09 15:45:18', '', NULL, '');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2022-02-09 09:48:38', '', NULL, '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2022-02-09 09:48:38', '', NULL, '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2022-02-09 09:48:38', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2022-02-09 09:48:38', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2022-02-09 09:48:38', '', NULL, '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '配置管理', 1, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2022-02-09 09:48:38', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '系统参数', 1, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2022-02-09 09:48:38', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 2, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2022-02-09 09:48:38', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2022-02-09 09:48:38', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2022-02-09 09:48:38', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务器监控', 2, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2022-02-09 09:48:38', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2022-02-09 09:48:38', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2022-02-09 09:48:38', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2022-02-09 09:48:38', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2022-02-09 09:48:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2001, '配置管理', 3, 'conf', 'load/conf/index', NULL, 1, 0, 'M', '0', '0', 'load:conf:list', 'conf', 'admin', '2022-02-09 15:46:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2002, 'Excel2Hive', 3, 'finance', 'load/finance/index', NULL, 1, 0, 'M', '0', '0', 'load:file:list', 'finance', 'admin', '2022-02-09 15:47:59', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2003, '文件删除', 2002, '', NULL, NULL, 1, 0, 'F', '0', '0', 'load:file:delete', '#', 'admin', '2022-02-09 17:04:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2004, '上传文件', 2002, '', NULL, NULL, 1, 0, 'F', '0', '0', 'load:file:load', '#', 'admin', '2022-02-09 17:05:28', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2005, '配置新增', 2001, '', NULL, NULL, 1, 0, 'F', '0', '0', 'load:conf:add', '#', 'admin', '2022-02-09 17:06:06', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2006, '配置修改', 2001, '', NULL, NULL, 1, 0, 'F', '0', '0', 'load:conf:edit', '#', 'admin', '2022-02-09 17:06:29', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2007, '配置删除', 2001, '', NULL, NULL, 1, 0, 'F', '0', '0', 'load:conf:remove', '#', 'admin', '2022-02-09 17:06:55', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2008, '配置查询', 2001, '', NULL, NULL, 1, 0, 'F', '0', '0', 'load:conf:query', '#', 'admin', '2022-02-09 17:07:16', '', NULL, '');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (100, '用户管理', 1, 'com.example.dtt.controller.system.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"password\":\"$2a$10$Jo/hq5OkNthHPD2mLh6zeOrJ6D0N/bZ1CdNTQXOUQZ3jGNK6CbAG2\",\"postIds\":[4],\"nickName\":\"zhangshanshan\",\"sex\":\"1\",\"deptId\":100,\"params\":{},\"userName\":\"张闪闪\",\"userId\":100,\"createBy\":\"admin\",\"roleIds\":[2],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 11:31:49');
INSERT INTO `sys_oper_log` VALUES (101, '用户管理', 2, 'com.example.dtt.controller.system.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":2,\"admin\":false,\"dataScope\":\"2\",\"params\":{},\"roleSort\":\"2\",\"deptCheckStrictly\":false,\"menuCheckStrictly\":false,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"status\":\"0\"}],\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"\",\"nickName\":\"zhangshanshan\",\"sex\":\"1\",\"deptId\":100,\"avatar\":\"\",\"dept\":{\"deptName\":\"若依科技\",\"leader\":\"若依\",\"deptId\":100,\"orderNum\":\"0\",\"params\":{},\"parentId\":0,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"张闪闪\",\"userId\":100,\"createBy\":\"admin\",\"phoneNumber\":\"13285650981\",\"roleIds\":[2],\"createTime\":1644377509000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 11:46:34');
INSERT INTO `sys_oper_log` VALUES (102, '用户管理', 2, 'com.example.dtt.controller.system.SysUserController.edit()', 'PUT', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"roles\":[{\"flag\":false,\"roleId\":2,\"admin\":false,\"dataScope\":\"2\",\"params\":{},\"roleSort\":\"2\",\"deptCheckStrictly\":false,\"menuCheckStrictly\":false,\"roleKey\":\"common\",\"roleName\":\"普通角色\",\"status\":\"0\"}],\"admin\":false,\"delFlag\":\"0\",\"password\":\"\",\"updateBy\":\"admin\",\"postIds\":[4],\"loginIp\":\"\",\"email\":\"\",\"nickName\":\"张闪闪\",\"sex\":\"1\",\"deptId\":100,\"avatar\":\"\",\"dept\":{\"deptName\":\"若依科技\",\"leader\":\"若依\",\"deptId\":100,\"orderNum\":\"0\",\"params\":{},\"parentId\":0,\"children\":[],\"status\":\"0\"},\"params\":{},\"userName\":\"张闪闪\",\"userId\":100,\"createBy\":\"admin\",\"phoneNumber\":\"13285650981\",\"roleIds\":[2],\"createTime\":1644377509000,\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 13:13:21');
INSERT INTO `sys_oper_log` VALUES (103, '岗位管理', 3, 'com.example.dtt.controller.system.SysPostController.remove()', 'DELETE', 1, 'admin', NULL, '/system/post/2', '127.0.0.1', '内网IP', '{postIds=2}', NULL, 1, '项目经理已分配,不能删除', '2022-02-09 13:53:47');
INSERT INTO `sys_oper_log` VALUES (104, '岗位管理', 3, 'com.example.dtt.controller.system.SysPostController.remove()', 'DELETE', 1, 'admin', NULL, '/system/post/3', '127.0.0.1', '内网IP', '{postIds=3}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 13:53:53');
INSERT INTO `sys_oper_log` VALUES (105, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"upload\",\"menuName\":\"load-file\",\"params\":{},\"parentId\":0,\"isCache\":\"0\",\"path\":\"load\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:45:18');
INSERT INTO `sys_oper_log` VALUES (106, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"conf\",\"menuName\":\"配置管理\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"conf\",\"component\":\"load/conf/index\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"perms\":\"load:conf:list\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:46:45');
INSERT INTO `sys_oper_log` VALUES (107, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"icon\":\"finance\",\"menuName\":\"Excel2Hive\",\"params\":{},\"parentId\":2000,\"isCache\":\"0\",\"path\":\"finance\",\"component\":\"load/finance/index\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"M\",\"perms\":\"load:file:list\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:47:59');
INSERT INTO `sys_oper_log` VALUES (108, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/1', '127.0.0.1', '内网IP', '{fileIds=1}', NULL, 1, 'nested exception is org.apache.ibatis.binding.BindingException: Parameter \'file_id\' not found. Available parameters are [array, fileIds]', '2022-02-09 15:48:22');
INSERT INTO `sys_oper_log` VALUES (109, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/1', '127.0.0.1', '内网IP', '{fileIds=1}', NULL, 1, 'nested exception is org.apache.ibatis.binding.BindingException: Parameter \'file_id\' not found. Available parameters are [array, fileIds]', '2022-02-09 15:50:06');
INSERT INTO `sys_oper_log` VALUES (110, '删除配置', 3, 'com.example.dtt.controller.load.LoadConfController.remove()', 'DELETE', 1, 'admin', NULL, '/load/conf/1', '127.0.0.1', '内网IP', '{confIds=1}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:51:10');
INSERT INTO `sys_oper_log` VALUES (111, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/1,20', '127.0.0.1', '内网IP', '{fileIds=1,20}', NULL, 1, 'nested exception is org.apache.ibatis.binding.BindingException: Parameter \'file_id\' not found. Available parameters are [array, fileIds]', '2022-02-09 15:51:53');
INSERT INTO `sys_oper_log` VALUES (112, '用户管理', 1, 'com.example.dtt.controller.system.SysUserController.add()', 'POST', 1, 'admin', NULL, '/system/user', '127.0.0.1', '内网IP', '{\"admin\":false,\"password\":\"$2a$10$OSHiYM4ITmZYW3ZKd17Nm.c3RTc6jujVkSYhmILKSt0oMkrbnwQUe\",\"postIds\":[],\"nickName\":\"张三\",\"deptId\":103,\"params\":{},\"userName\":\"zhangsan\",\"userId\":101,\"createBy\":\"admin\",\"roleIds\":[],\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:53:49');
INSERT INTO `sys_oper_log` VALUES (113, '用户管理', 3, 'com.example.dtt.controller.system.SysUserController.remove()', 'DELETE', 1, 'admin', NULL, '/system/user/100', '127.0.0.1', '内网IP', '{userIds=100}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:53:54');
INSERT INTO `sys_oper_log` VALUES (114, '用户管理', 3, 'com.example.dtt.controller.system.SysUserController.remove()', 'DELETE', 1, 'admin', NULL, '/system/user/100,101', '127.0.0.1', '内网IP', '{userIds=100,101}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:54:15');
INSERT INTO `sys_oper_log` VALUES (115, '用户管理', 3, 'com.example.dtt.controller.system.SysUserController.remove()', 'DELETE', 1, 'admin', NULL, '/system/user/100', '127.0.0.1', '内网IP', '{userIds=100}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:54:51');
INSERT INTO `sys_oper_log` VALUES (116, '用户管理', 3, 'com.example.dtt.controller.system.SysUserController.remove()', 'DELETE', 1, 'admin', NULL, '/system/user/100,101', '127.0.0.1', '内网IP', '{userIds=100,101}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 15:55:14');
INSERT INTO `sys_oper_log` VALUES (117, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/20', '127.0.0.1', '内网IP', '{fileIds=20}', NULL, 1, 'nested exception is org.apache.ibatis.binding.BindingException: Parameter \'file_id\' not found. Available parameters are [array, fileIds]', '2022-02-09 15:59:42');
INSERT INTO `sys_oper_log` VALUES (118, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/1', '127.0.0.1', '内网IP', '{fileIds=1}', NULL, 1, '', '2022-02-09 16:00:40');
INSERT INTO `sys_oper_log` VALUES (119, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/1', '127.0.0.1', '内网IP', '{fileIds=1}', NULL, 1, '', '2022-02-09 16:02:01');
INSERT INTO `sys_oper_log` VALUES (120, '删除文件', 3, 'com.example.dtt.controller.load.LoadController.remove()', 'DELETE', 1, 'admin', NULL, '/load/file/1', '127.0.0.1', '内网IP', '{fileIds=1}', NULL, 1, '', '2022-02-09 16:03:46');
INSERT INTO `sys_oper_log` VALUES (121, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"menuName\":\"文件删除\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"load:file:delete\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:04:52');
INSERT INTO `sys_oper_log` VALUES (122, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"menuName\":\"上传文件\",\"params\":{},\"parentId\":2002,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"load:file:load\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:05:28');
INSERT INTO `sys_oper_log` VALUES (123, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"menuName\":\"配置新增\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"load:conf:add\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:06:06');
INSERT INTO `sys_oper_log` VALUES (124, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"menuName\":\"配置修改\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"load:conf:edit\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:06:29');
INSERT INTO `sys_oper_log` VALUES (125, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"menuName\":\"配置删除\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"load:conf:remove\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:06:55');
INSERT INTO `sys_oper_log` VALUES (126, '菜单管理', 1, 'com.example.dtt.controller.system.SysMenuController.add()', 'POST', 1, 'admin', NULL, '/system/menu', '127.0.0.1', '内网IP', '{\"visible\":\"0\",\"menuName\":\"配置查询\",\"params\":{},\"parentId\":2001,\"isCache\":\"0\",\"createBy\":\"admin\",\"children\":[],\"isFrame\":\"1\",\"menuType\":\"F\",\"perms\":\"load:conf:query\",\"status\":\"0\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:07:16');
INSERT INTO `sys_oper_log` VALUES (127, '新增配置', 1, 'com.example.dtt.controller.load.LoadConfController.add()', 'POST', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"business\":\"财务\",\"remark\":\"财务PDCA线下补充数据\",\"params\":{},\"hiveTable\":\"hive_import.im_financial_out_order_data\",\"createBy\":\"admin\",\"createTime\":1644398496170,\"tablePrefix\":\"im_financial\",\"hdfsPath\":\"/load/data/finance/out_order_data\",\"fileType\":\"额外补充不到单\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:21:36');
INSERT INTO `sys_oper_log` VALUES (128, '新增配置', 1, 'com.example.dtt.controller.load.LoadConfController.add()', 'POST', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"business\":\"财务\",\"remark\":\"4S渠道，孙颖萱 给出经销商分区对应线下数据\",\"params\":{},\"hiveTable\":\"hive_import.im_4s_dealer_area_detail\",\"createBy\":\"admin\",\"createTime\":1644398593351,\"tablePrefix\":\"im_4s\",\"hdfsPath\":\"/load/data/finance/dealer_area\",\"fileType\":\"经销商分区\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:23:13');
INSERT INTO `sys_oper_log` VALUES (129, '新增配置', 1, 'com.example.dtt.controller.load.LoadConfController.add()', 'POST', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"business\":\"财务\",\"params\":{},\"hiveTable\":\"hive_import.im_financial_risk_exposure_cp\",\"createBy\":\"admin\",\"createTime\":1644399607137,\"tablePrefix\":\"im_financial\",\"hdfsPath\":\"/load/data/finance/crown_sell_risk_data/\",\"fileType\":\"PDCA冠销风险敞口数据\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:40:07');
INSERT INTO `sys_oper_log` VALUES (130, '新增配置', 1, 'com.example.dtt.controller.load.LoadConfController.add()', 'POST', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"business\":\"财务\",\"remark\":\"4S渠道 孙颖萱 提供车品牌及车品牌分类对应关系 Excel线下补充数据\",\"params\":{},\"hiveTable\":\"hive_import.im_4s_car_brand_class\",\"createBy\":\"admin\",\"createTime\":1644399700832,\"tablePrefix\":\"im_4s\",\"hdfsPath\":\"/load/data/finance/car_brand\",\"fileType\":\"车品牌分类\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:41:40');
INSERT INTO `sys_oper_log` VALUES (131, '修改配置', 2, 'com.example.dtt.controller.load.LoadConfController.edit()', 'PUT', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"confId\":4,\"business\":\"财务\",\"remark\":\"PDCA Excel离线数据 财务进行线下补充\",\"params\":{},\"hiveTable\":\"hive_import.im_financial_risk_exposure_cp\",\"createBy\":\"admin\",\"createTime\":1644399607000,\"tablePrefix\":\"im_financial\",\"updateBy\":\"admin\",\"hdfsPath\":\"/load/data/finance/crown_sell_risk_data/\",\"fileType\":\"PDCA冠销风险敞口数据\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:42:10');
INSERT INTO `sys_oper_log` VALUES (132, '新增配置', 1, 'com.example.dtt.controller.load.LoadConfController.add()', 'POST', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"business\":\"保险\",\"remark\":\"财务进行数据观测的保险数据，财务线下补充\",\"params\":{},\"hiveTable\":\"hive_import.im_financial_insurance_budget\",\"createBy\":\"admin\",\"createTime\":1644399813333,\"tablePrefix\":\"im_financial\",\"hdfsPath\":\"/load/data/finance/insurance_budget/\",\"fileType\":\"保险预算\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:43:33');
INSERT INTO `sys_oper_log` VALUES (133, '新增配置', 1, 'com.example.dtt.controller.load.LoadConfController.add()', 'POST', 1, 'admin', NULL, '/load/conf', '127.0.0.1', '内网IP', '{\"business\":\"财务\",\"remark\":\"PDCA 财务线下提供结算数据\",\"params\":{},\"hiveTable\":\"hive_import.im_financial_income_costs_budget\",\"createBy\":\"admin\",\"createTime\":1644399878823,\"tablePrefix\":\"im_financial\",\"hdfsPath\":\"/load/data/finance/income_cost_budget/\",\"fileType\":\"PDCA成本费用预算\"}', '{\"msg\":\"操作成功\",\"code\":200}', 0, NULL, '2022-02-09 17:44:38');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '岗位名称',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', '0', 'admin', '2022-02-09 09:48:37', '', NULL, '');
INSERT INTO `sys_post` VALUES (2, 'se', '项目经理', '0', 'admin', '2022-02-09 09:48:37', '', NULL, '');
INSERT INTO `sys_post` VALUES (4, 'user', '普通员工', '0', 'admin', '2022-02-09 09:48:37', '', NULL, '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '1', 1, 1, '0', '0', 'admin', '2022-02-09 09:48:37', '', NULL, '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', '2', 1, 1, '0', '0', 'admin', '2022-02-09 09:48:37', '', NULL, '普通角色');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 111);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 116);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
INSERT INTO `sys_role_menu` VALUES (2, 1058);
INSERT INTO `sys_role_menu` VALUES (2, 1059);
INSERT INTO `sys_role_menu` VALUES (2, 1060);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 103, 'admin', '管理员', 'renpengyuan@cangoonline.com', '', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2022-02-09 16:56:43', 'admin', '2022-02-09 09:48:37', '', '2022-02-09 16:56:43', '管理员');
INSERT INTO `sys_user` VALUES (100, 100, 'zhangshanshan', '张闪闪', '', '13285650981', '1', '', '$2a$10$Jo/hq5OkNthHPD2mLh6zeOrJ6D0N/bZ1CdNTQXOUQZ3jGNK6CbAG2', '0', '2', '', NULL, 'admin', '2022-02-09 11:31:49', 'admin', '2022-02-09 13:13:21', NULL);
INSERT INTO `sys_user` VALUES (101, 103, 'zhangsan', '张三', '', '', '0', '', '$2a$10$OSHiYM4ITmZYW3ZKd17Nm.c3RTc6jujVkSYhmILKSt0oMkrbnwQUe', '0', '2', '', NULL, 'admin', '2022-02-09 15:53:49', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
