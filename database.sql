-- 创建数据库
CREATE DATABASE IF NOT EXISTS mydb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE mydb;
-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    age INT,
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建索引
CREATE INDEX idx_email ON user(email);

-- 插入测试数据（可选）
INSERT INTO user (name, email, age) VALUES ('Alice', 'alice@example.com', 25);
INSERT INTO user (name, email, age) VALUES ('Bob', 'bobb@example.com', 30);