-- 创建数据库
CREATE DATABASE IF NOT EXISTS mydb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE mydb;

-- 1. 先删除子表（存在外键依赖的表）
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS user;

-- 2. 再删除父表（被引用的基础表）
DROP TABLE IF EXISTS department;

-- 创建部门表
CREATE TABLE IF NOT EXISTS department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建用户表（添加部门ID字段）
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES department(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建订单表
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    user_id BIGINT NOT NULL,
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建索引
CREATE INDEX idx_email ON user(email);
CREATE INDEX idx_department_id ON user(department_id);
CREATE INDEX idx_user_id ON orders(user_id);
CREATE INDEX idx_order_no ON orders(order_no);

-- 插入测试数据
-- 插入部门数据
INSERT INTO department (name, description) VALUES ('技术部', '负责技术研发工作');
INSERT INTO department (name, description) VALUES ('市场部', '负责市场推广工作');
INSERT INTO department (name, description) VALUES ('财务部', '负责财务管理');

-- 插入用户数据（关联部门）
INSERT INTO user (name, email, age, department_id) VALUES ('Alice', 'alice@example.com', 25, 1);
INSERT INTO user (name, email, age, department_id) VALUES ('Bob', 'bobb@example.com', 30, 1);
INSERT INTO user (name, email, age, department_id) VALUES ('Charlie', 'charlie@example.com', 28, 2);
INSERT INTO user (name, email, age, department_id) VALUES ('David', 'david@example.com', 35, 3);

-- 插入订单数据（关联用户）
INSERT INTO orders (order_no, amount, user_id) VALUES ('ORD001', 199.99, 1);
INSERT INTO orders (order_no, amount, user_id) VALUES ('ORD002', 299.99, 1);
INSERT INTO orders (order_no, amount, user_id) VALUES ('ORD003', 399.99, 2);
INSERT INTO orders (order_no, amount, user_id) VALUES ('ORD004', 499.99, 3);
INSERT INTO orders (order_no, amount, user_id) VALUES ('ORD005', 599.99, 3);
INSERT INTO orders (order_no, amount, user_id) VALUES ('ORD006', 699.99, 4);