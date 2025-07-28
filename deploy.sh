#!/bin/bash

# Dump Truck Services - Deployment Script
# This script helps you deploy the application to various platforms

echo "🚀 Dump Truck Services - Deployment Helper"
echo "=========================================="

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  .env file not found!"
    echo "📝 Creating .env file from template..."
    cp env.example .env
    echo "✅ .env file created from env.example"
    echo "🔧 Please edit .env file with your actual values before deploying"
    echo ""
fi

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo "🔍 Checking prerequisites..."

if command_exists docker; then
    echo "✅ Docker is installed"
else
    echo "❌ Docker is not installed"
    echo "   Install Docker from: https://docs.docker.com/get-docker/"
fi

if command_exists docker-compose; then
    echo "✅ Docker Compose is installed"
else
    echo "❌ Docker Compose is not installed"
    echo "   Install Docker Compose from: https://docs.docker.com/compose/install/"
fi

if command_exists java; then
    echo "✅ Java is installed"
    java -version
else
    echo "❌ Java is not installed"
    echo "   Install Java 17+ from: https://adoptium.net/"
fi

if command_exists mvn; then
    echo "✅ Maven is installed"
    mvn -version
else
    echo "❌ Maven is not installed"
    echo "   Install Maven from: https://maven.apache.org/install.html"
fi

echo ""
echo "📋 Deployment Options:"
echo "1. Local Docker Compose"
echo "2. Railway (Recommended for beginners)"
echo "3. Render"
echo "4. Heroku"
echo "5. DigitalOcean"
echo "6. AWS EC2"

echo ""
echo "🎯 Quick Start Commands:"
echo ""
echo "🔧 Build the application:"
echo "   mvn clean package -DskipTests"
echo ""
echo "🐳 Run with Docker Compose:"
echo "   docker-compose up -d"
echo ""
echo "☕ Run locally:"
echo "   java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar"
echo ""
echo "📚 For detailed deployment instructions, see:"
echo "   - PRODUCTION_DEPLOYMENT.md"
echo "   - README.md"
echo ""
echo "🔒 Security Reminder:"
echo "   - Never commit .env file to Git"
echo "   - Use strong passwords"
echo "   - Enable HTTPS in production"
echo ""
echo "✅ Setup complete! Choose your deployment option above." 