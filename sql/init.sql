CREATE DATABASE account_system
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

CREATE TABLE `sys_user` (
     `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
     `username` VARCHAR(64) NOT NULL COMMENT '账号',
     `password` VARCHAR(128) NOT NULL COMMENT '加密后的密码',
     `salt` VARCHAR(32) DEFAULT NULL COMMENT '盐（如果用BCrypt可不需要）',
     `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态 1启用 0禁用',
      `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除 1已删除',
      `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (`id`),
       UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB COMMENT='系统用户表';
