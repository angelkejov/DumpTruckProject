#!/bin/bash

# Dump Truck Services Deployment Script
# This script helps deploy the application to different environments

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if required tools are installed
check_prerequisites() {
    print_status "Checking prerequisites..."
    
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed. Please install Java 17 or higher."
        exit 1
    fi
    
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed. Please install Maven 3.6 or higher."
        exit 1
    fi
    
    if ! command -v docker &> /dev/null; then
        print_warning "Docker is not installed. Docker deployment will not be available."
    fi
    
    print_status "Prerequisites check completed."
}

# Build the application
build_application() {
    print_status "Building the application..."
    mvn clean package -DskipTests
    print_status "Build completed successfully."
}

# Run the application locally
run_local() {
    print_status "Starting the application locally..."
    java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
}

# Build and run with Docker
run_docker() {
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed. Cannot run with Docker."
        exit 1
    fi
    
    print_status "Building and running with Docker Compose..."
    docker-compose up --build
}

# Deploy to production
deploy_production() {
    print_status "Deploying to production..."
    
    # Build the application
    build_application
    
    # Create production environment file if it doesn't exist
    if [ ! -f .env.production ]; then
        print_warning "Creating .env.production file. Please update it with your production settings."
        cp env.example .env.production
    fi
    
    # Load production environment variables
    if [ -f .env.production ]; then
        export $(cat .env.production | grep -v '^#' | xargs)
    fi
    
    # Set production profile
    export SPRING_PROFILES_ACTIVE=prod
    
    print_status "Starting production application..."
    java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
}

# Show usage information
show_usage() {
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  build       Build the application"
    echo "  run         Run the application locally"
    echo "  docker      Run with Docker Compose"
    echo "  deploy      Deploy to production"
    echo "  check       Check prerequisites"
    echo "  help        Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 build"
    echo "  $0 run"
    echo "  $0 docker"
    echo "  $0 deploy"
}

# Main script logic
case "${1:-help}" in
    "build")
        check_prerequisites
        build_application
        ;;
    "run")
        check_prerequisites
        build_application
        run_local
        ;;
    "docker")
        check_prerequisites
        run_docker
        ;;
    "deploy")
        check_prerequisites
        deploy_production
        ;;
    "check")
        check_prerequisites
        ;;
    "help"|*)
        show_usage
        ;;
esac 