-- Initialize Render MySQL Database for Dump Truck Services
-- This script creates the necessary tables for the application

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    verification_code VARCHAR(255),
    verification_code_expiry DATETIME,
    created_at DATETIME NOT NULL,
    INDEX idx_email (email),
    INDEX idx_verification_code (verification_code)
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
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- Insert sample data (optional)
-- You can uncomment these lines if you want to add sample data

-- INSERT INTO users (name, email, password, verified, created_at) VALUES
-- ('Admin User', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', true, NOW());

-- INSERT INTO service_orders (user_id, pickup_location, dropoff_location, preferred_datetime, material_type, notes, created_at, updated_at) VALUES
-- (1, '123 Main St, City', '456 Industrial Ave, City', '2024-01-15 10:00:00', 'Construction Debris', 'Sample order', NOW(), NOW()); 