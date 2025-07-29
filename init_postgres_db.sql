-- Initialize Render PostgreSQL Database for Dump Truck Services
-- This script creates the necessary tables for the application

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN NOT NULL DEFAULT FALSE,
    verification_code VARCHAR(255),
    verification_code_expiry TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create service_orders table
CREATE TABLE IF NOT EXISTS service_orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    pickup_location VARCHAR(500) NOT NULL,
    dropoff_location VARCHAR(500) NOT NULL,
    preferred_datetime TIMESTAMP NOT NULL,
    material_type VARCHAR(100) NOT NULL,
    notes TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_verification_code ON users(verification_code);
CREATE INDEX IF NOT EXISTS idx_service_orders_user_id ON service_orders(user_id);
CREATE INDEX IF NOT EXISTS idx_service_orders_status ON service_orders(status);
CREATE INDEX IF NOT EXISTS idx_service_orders_created_at ON service_orders(created_at);

-- Insert sample data (optional)
-- You can uncomment these lines if you want to add sample data

-- INSERT INTO users (name, email, password, verified, created_at) VALUES
-- ('Admin User', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', true, CURRENT_TIMESTAMP);

-- INSERT INTO service_orders (user_id, pickup_location, dropoff_location, preferred_datetime, material_type, notes, created_at, updated_at) VALUES
-- (1, '123 Main St, City', '456 Industrial Ave, City', '2024-01-15 10:00:00', 'Construction Debris', 'Sample order', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 