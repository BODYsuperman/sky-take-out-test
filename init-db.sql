-- Sky Takeout Database Initialization Script

-- Create database
CREATE DATABASE IF NOT EXISTS sky_takeout CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sky_takeout;

-- Initialize admin user (you should change the password in production)
INSERT INTO employee (name, username, password, phone, sex, id_number, status, create_time, update_time) 
VALUES ('Admin', 'admin', '$2a$10$9yFVqL3JQK8XmzP5Qr6T.eUvH8K7L9M2N3O4P5Q6R7S8T9U0V1W2X', '13800138000', 1, '110101199001011234', 1, NOW(), NOW())
ON DUPLICATE KEY UPDATE update_time = NOW();