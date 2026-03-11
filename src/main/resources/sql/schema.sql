-- 创建数据库
CREATE DATABASE IF NOT EXISTS sky_takeout DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sky_takeout;

-- 员工表
CREATE TABLE employee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL COMMENT '姓名',
    username VARCHAR(32) NOT NULL COMMENT '用户名',
    password VARCHAR(64) NOT NULL COMMENT '密码',
    phone VARCHAR(11) NOT NULL COMMENT '手机号',
    sex VARCHAR(2) NOT NULL COMMENT '性别',
    id_number VARCHAR(18) COMMENT '身份证号',
    status INT NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除 0:未删除，1:已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工表';

-- 分类表
CREATE TABLE category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type INT NOT NULL COMMENT '分类类型 1:菜品分类，2:套餐分类',
    name VARCHAR(32) NOT NULL COMMENT '分类名称',
    sort INT NOT NULL DEFAULT 0 COMMENT '顺序',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 菜品表
CREATE TABLE dish (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL COMMENT '菜品名称',
    category_id BIGINT NOT NULL COMMENT '分类 id',
    price DECIMAL(10,2) COMMENT '菜品价格',
    image VARCHAR(255) COMMENT '图片',
    description TEXT COMMENT '描述信息',
    status INT NOT NULL DEFAULT 1 COMMENT '状态 0:停售，1:起售',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品表';

-- 菜品口味表
CREATE TABLE dish_flavor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dish_id BIGINT NOT NULL COMMENT '菜品 id',
    name VARCHAR(32) COMMENT '口味名称',
    value TEXT COMMENT '口味数据',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品口味表';

-- 套餐表
CREATE TABLE setmeal (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL COMMENT '分类 id',
    name VARCHAR(32) NOT NULL COMMENT '套餐名称',
    price DECIMAL(10,2) NOT NULL COMMENT '套餐价格',
    image VARCHAR(255) COMMENT '图片',
    description TEXT COMMENT '描述信息',
    status INT NOT NULL DEFAULT 1 COMMENT '状态 0:停售，1:起售',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐表';

-- 套餐菜品关系表
CREATE TABLE setmeal_dish (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    setmeal_id BIGINT NOT NULL COMMENT '套餐 id',
    dish_id BIGINT NOT NULL COMMENT '菜品 id',
    name VARCHAR(32) COMMENT '菜品名称',
    copies INT NOT NULL COMMENT '菜品份数',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套餐菜品关系表';

-- 购物车表
CREATE TABLE shopping_cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) COMMENT '名称',
    user_id BIGINT NOT NULL COMMENT '用户 id',
    dish_id BIGINT COMMENT '菜品 id',
    setmeal_id BIGINT COMMENT '套餐 id',
    dish_flavor VARCHAR(50) COMMENT '口味',
    number INT NOT NULL DEFAULT 1 COMMENT '数量',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    image VARCHAR(255) COMMENT '图片',
    create_time DATETIME NOT NULL COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 地址簿表
CREATE TABLE address_book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户 id',
    consignee VARCHAR(50) NOT NULL COMMENT '收货人',
    phone VARCHAR(11) NOT NULL COMMENT '手机号',
    sex VARCHAR(2) COMMENT '性别',
    province_code VARCHAR(12) COMMENT '省级区划编号',
    province_name VARCHAR(32) COMMENT '省级名称',
    city_code VARCHAR(12) COMMENT '市级区划编号',
    city_name VARCHAR(32) COMMENT '市级名称',
    district_code VARCHAR(12) COMMENT '区级区划编号',
    district_name VARCHAR(32) COMMENT '区级名称',
    detail VARCHAR(200) COMMENT '详细地址',
    label VARCHAR(50) COMMENT '标签',
    is_default INT NOT NULL DEFAULT 0 COMMENT '是否默认 0:否，1:是',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地址簿表';

-- 订单表
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(50) NOT NULL COMMENT '订单号',
    status INT NOT NULL DEFAULT 1 COMMENT '订单状态 1:待付款，2:待接单，3:已接单，4:派送中，5:已完成，6:已取消',
    user_id BIGINT NOT NULL COMMENT '用户 id',
    address_book_id BIGINT NOT NULL COMMENT '地址簿 id',
    order_time DATETIME NOT NULL COMMENT '下单时间',
    checkout_time DATETIME COMMENT '结账时间',
    pay_method INT NOT NULL DEFAULT 1 COMMENT '支付方式 1:微信，2:支付宝',
    pay_status INT NOT NULL DEFAULT 0 COMMENT '支付状态 0:未支付，1:已支付，2:退款',
    remark VARCHAR(100) COMMENT '备注',
    phone INT COMMENT '手机号',
    address VARCHAR(255) COMMENT '地址',
    user_name VARCHAR(50) COMMENT '用户名称',
    phone_user VARCHAR(11) COMMENT '用户手机号',
    consignee INT COMMENT '收货人',
    cancel_reason INT COMMENT '取消原因',
    rejection_reason VARCHAR(255) COMMENT '拒收原因',
    cancel_time DATETIME COMMENT '取消时间',
    estimated_delivery_time DATETIME COMMENT '预计送达时间',
    delivery_status VARCHAR(50) COMMENT '配送状态',
    delivery_remark VARCHAR(255) COMMENT '配送备注',
    rider_id BIGINT COMMENT '骑手 id',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    note INT COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单明细表
CREATE TABLE order_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL COMMENT '名称',
    order_id BIGINT NOT NULL COMMENT '订单 id',
    dish_id BIGINT COMMENT '菜品 id',
    setmeal_id BIGINT COMMENT '套餐 id',
    dish_flavor VARCHAR(50) COMMENT '口味',
    number INT NOT NULL DEFAULT 1 COMMENT '数量',
    amount DECIMAL(10,2) NOT NULL COMMENT '金额',
    image VARCHAR(255) COMMENT '图片'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 用户表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    openid VARCHAR(45) COMMENT '微信用户唯一标识',
    name VARCHAR(50) COMMENT '姓名',
    phone VARCHAR(11) COMMENT '手机号',
    sex VARCHAR(2) COMMENT '性别',
    id_number VARCHAR(18) COMMENT '身份证号',
    avatar VARCHAR(255) COMMENT '头像',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    deleted INT NOT NULL DEFAULT 0 COMMENT '是否删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入默认员工数据
INSERT INTO employee (name, username, password, phone, sex, id_number, status, create_time, update_time) 
VALUES ('管理员', 'admin', '123456', '13800138000', '1', '110101199001011234', 1, NOW(), NOW());
