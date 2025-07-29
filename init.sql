-- Database initialization script for Dump Truck Services
-- This script will be executed when the MySQL container starts

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS dump_truck_db;
USE dump_truck_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    verification_code VARCHAR(255),
    verification_code_expiry DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Create service_orders table
CREATE TABLE IF NOT EXISTS service_orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    pickup_location VARCHAR(500) NOT NULL,
    dropoff_location VARCHAR(500) NOT NULL,
    preferred_datetime DATETIME NOT NULL,
    material_type VARCHAR(100) NOT NULL,
    notes TEXT,
    status ENUM('PENDING', 'CONFIRMED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_verification_code ON users(verification_code);
CREATE INDEX IF NOT EXISTS idx_service_orders_user_id ON service_orders(user_id);
CREATE INDEX IF NOT EXISTS idx_service_orders_status ON service_orders(status);
CREATE INDEX IF NOT EXISTS idx_service_orders_created_at ON service_orders(created_at); 