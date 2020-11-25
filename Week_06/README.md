###作业

（必做）基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）。

````
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` int(255) NOT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '用户昵称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `commodity_id` int(11) NOT NULL COMMENT '商品id',
  `commodity_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0-待支付订单，1-已支付订单，2-已取消订单，99-已删除订单',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `commodity` (
  `id` int(11) NOT NULL,
  `commodity_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '商品名称',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `state` tinyint(2) NOT NULL DEFAULT '0' COMMENT '商品状态：0-待上架，1-已上架，99-已删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;