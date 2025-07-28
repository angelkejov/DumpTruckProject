#!/bin/bash

# Railway Start Script for Dump Truck Services

echo "🚀 Starting Dump Truck Services..."

# Check if JAR file exists
if [ ! -f "target/dump-truck-services-0.0.1-SNAPSHOT.jar" ]; then
    echo "❌ JAR file not found. Building application..."
    ./mvnw clean package -DskipTests
fi

# Set default environment variables if not set
export SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-prod}
export SERVER_PORT=${PORT:-8080}

echo "✅ Environment: $SPRING_PROFILES_ACTIVE"
echo "✅ Port: $SERVER_PORT"

# Start the application
exec java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar 