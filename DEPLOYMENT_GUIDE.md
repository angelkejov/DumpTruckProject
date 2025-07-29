# Dump Truck Services - Deployment Guide

This guide provides step-by-step instructions for deploying the Dump Truck Services application to different environments.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Local Development Setup](#local-development-setup)
3. [Docker Deployment](#docker-deployment)
4. [Production Deployment](#production-deployment)
5. [Cloud Platform Deployments](#cloud-platform-deployments)
6. [Troubleshooting](#troubleshooting)

## Prerequisites

### Required Software
- **Java 17 or higher**
- **Maven 3.6 or higher**
- **MySQL 8.0 or higher** (for local development)
- **Docker & Docker Compose** (optional, for containerized deployment)

### Verify Installation
```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Docker version (if using Docker)
docker --version
docker-compose --version
```

## Local Development Setup

### Option 1: Traditional Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd dump-truck-services
   ```

2. **Set up MySQL database**
   ```bash
   # Start MySQL service
   # On Windows: Start MySQL service from Services
   # On Linux: sudo systemctl start mysql
   # On macOS: brew services start mysql
   
   # Create database
   mysql -u root -p
   CREATE DATABASE dump_truck_db;
   ```

3. **Configure environment variables**
   ```bash
   # Copy example environment file
   cp env.example .env
   
   # Edit .env file with your settings
   nano .env
   ```

4. **Build and run the application**
   ```bash
   # Build the application
   mvn clean package -DskipTests
   
   # Run the application
   java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
   ```

5. **Access the application**
   - Open browser and go to: `http://localhost:8080`

### Option 2: Using the Deployment Script

1. **Make the script executable** (Linux/macOS)
   ```bash
   chmod +x deploy.sh
   ```

2. **Check prerequisites**
   ```bash
   ./deploy.sh check
   ```

3. **Build the application**
   ```bash
   ./deploy.sh build
   ```

4. **Run locally**
   ```bash
   ./deploy.sh run
   ```

## Docker Deployment

### Quick Start with Docker Compose

1. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

2. **Access the application**
   - Application: `http://localhost:8080`
   - Database: `localhost:3306`

### Manual Docker Deployment

1. **Build the Docker image**
   ```bash
   docker build -t dump-truck-services .
   ```

2. **Run the container**
   ```bash
   docker run -p 8080:8080 \
     -e DATABASE_URL=jdbc:mysql://host.docker.internal:3306/dump_truck_db \
     -e DATABASE_USERNAME=root \
     -e DATABASE_PASSWORD=1234 \
     dump-truck-services
   ```

### Docker Compose with Custom Environment

1. **Create custom environment file**
   ```bash
   cp env.example .env.docker
   # Edit .env.docker with your settings
   ```

2. **Run with custom environment**
   ```bash
   docker-compose --env-file .env.docker up --build
   ```

## Production Deployment

### Environment Variables for Production

Create a `.env.production` file with the following variables:

```bash
# Database Configuration
DATABASE_URL=jdbc:mysql://your-production-db:3306/dump_truck_db
DATABASE_USERNAME=your_db_user
DATABASE_PASSWORD=your_secure_password

# Email Configuration
MAIL_HOST=your-smtp-server.com
MAIL_PORT=587
MAIL_USERNAME=your-email@domain.com
MAIL_PASSWORD=your-email-password

# Application Configuration
APP_EMAIL_FROM=your-email@domain.com
APP_URL=https://your-domain.com

# Server Configuration
PORT=8080

# Spring Profile
SPRING_PROFILES_ACTIVE=prod
```

### Production Deployment Steps

1. **Prepare the server**
   ```bash
   # Install Java 17
   sudo apt update
   sudo apt install openjdk-17-jdk
   
   # Install MySQL (if using local database)
   sudo apt install mysql-server
   ```

2. **Deploy the application**
   ```bash
   # Build the application
   mvn clean package -DskipTests
   
   # Copy JAR file to server
   scp target/dump-truck-services-0.0.1-SNAPSHOT.jar user@server:/opt/app/
   
   # Copy environment file
   scp .env.production user@server:/opt/app/.env
   ```

3. **Create systemd service**
   ```bash
   # Create service file
   sudo nano /etc/systemd/system/dump-truck-services.service
   ```

   Add the following content:
   ```ini
   [Unit]
   Description=Dump Truck Services Application
   After=network.target

   [Service]
   Type=simple
   User=appuser
   WorkingDirectory=/opt/app
   ExecStart=/usr/bin/java -jar dump-truck-services-0.0.1-SNAPSHOT.jar
   EnvironmentFile=/opt/app/.env
   Restart=always
   RestartSec=10

   [Install]
   WantedBy=multi-user.target
   ```

4. **Start the service**
   ```bash
   sudo systemctl daemon-reload
   sudo systemctl enable dump-truck-services
   sudo systemctl start dump-truck-services
   sudo systemctl status dump-truck-services
   ```

### Using the Deployment Script for Production

```bash
./deploy.sh deploy
```

## Cloud Platform Deployments

### Railway Deployment

1. **Create railway.json**
   ```json
   {
     "build": {
       "builder": "DOCKERFILE"
     },
     "deploy": {
       "restartPolicyType": "ON_FAILURE",
       "restartPolicyMaxRetries": 10
     }
   }
   ```

2. **Deploy to Railway**
   ```bash
   # Install Railway CLI
   npm install -g @railway/cli
   
   # Login to Railway
   railway login
   
   # Deploy
   railway up
   ```

### Render Deployment

1. **Create render.yaml**
   ```yaml
   services:
     - type: web
       name: dump-truck-services
       env: docker
       plan: free
       dockerfilePath: ./Dockerfile
       envVars:
         - key: SPRING_PROFILES_ACTIVE
           value: prod
         - key: DATABASE_URL
           fromDatabase:
             name: dump-truck-db
             property: connectionString
   ```

2. **Deploy to Render**
   - Connect your GitHub repository to Render
   - Render will automatically deploy from the repository

### Heroku Deployment

1. **Create Procfile**
   ```
   web: java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
   ```

2. **Deploy to Heroku**
   ```bash
   # Install Heroku CLI
   # Login to Heroku
   heroku login
   
   # Create Heroku app
   heroku create your-app-name
   
   # Add MySQL addon
   heroku addons:create jawsdb:kitefin
   
   # Set environment variables
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   heroku config:set MAIL_HOST=your-smtp-host
   heroku config:set MAIL_USERNAME=your-email
   heroku config:set MAIL_PASSWORD=your-password
   
   # Deploy
   git push heroku main
   ```

### AWS EC2 Deployment

1. **Launch EC2 instance**
   - Choose Amazon Linux 2 or Ubuntu
   - Configure security groups to allow ports 22 (SSH) and 80/443 (HTTP/HTTPS)

2. **Install dependencies**
   ```bash
   # Update system
   sudo yum update -y
   
   # Install Java 17
   sudo yum install java-17-amazon-corretto -y
   
   # Install MySQL (if using local database)
   sudo yum install mysql-server -y
   sudo systemctl start mysqld
   sudo systemctl enable mysqld
   ```

3. **Deploy application**
   ```bash
   # Create application directory
   sudo mkdir -p /opt/app
   sudo chown ec2-user:ec2-user /opt/app
   
   # Copy application files
   scp target/dump-truck-services-0.0.1-SNAPSHOT.jar ec2-user@your-ec2-ip:/opt/app/
   scp .env.production ec2-user@your-ec2-ip:/opt/app/.env
   
   # Create systemd service (as shown in Production Deployment section)
   ```

### Google Cloud Platform (GCP) Deployment

1. **Create app.yaml**
   ```yaml
   runtime: java17
   env: flex
   
   env_variables:
     SPRING_PROFILES_ACTIVE: prod
     DATABASE_URL: ${DATABASE_URL}
     MAIL_HOST: ${MAIL_HOST}
     MAIL_USERNAME: ${MAIL_USERNAME}
     MAIL_PASSWORD: ${MAIL_PASSWORD}
   
   manual_scaling:
     instances: 1
   ```

2. **Deploy to GCP**
   ```bash
   # Install Google Cloud SDK
   # Initialize gcloud
   gcloud init
   
   # Deploy
   gcloud app deploy
   ```

## Database Setup

### MySQL Database Setup

1. **Create database and user**
   ```sql
   CREATE DATABASE dump_truck_db;
   CREATE USER 'appuser'@'%' IDENTIFIED BY 'secure_password';
   GRANT ALL PRIVILEGES ON dump_truck_db.* TO 'appuser'@'%';
   FLUSH PRIVILEGES;
   ```

2. **Run initialization script**
   ```bash
   mysql -u root -p dump_truck_db < init.sql
   ```

### Cloud Database Options

- **AWS RDS**: Use MySQL 8.0 instance
- **Google Cloud SQL**: Use MySQL 8.0 instance
- **Azure Database for MySQL**: Use MySQL 8.0 instance
- **PlanetScale**: Serverless MySQL platform
- **Railway**: Managed MySQL database

## Email Configuration

### SMTP Providers

1. **Gmail**
   ```bash
   MAIL_HOST=smtp.gmail.com
   MAIL_PORT=587
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   ```

2. **SendGrid**
   ```bash
   MAIL_HOST=smtp.sendgrid.net
   MAIL_PORT=587
   MAIL_USERNAME=apikey
   MAIL_PASSWORD=your-sendgrid-api-key
   ```

3. **Brevo (formerly Sendinblue)**
   ```bash
   MAIL_HOST=smtp-relay.brevo.com
   MAIL_PORT=587
   MAIL_USERNAME=your-username
   MAIL_PASSWORD=your-api-key
   ```

## Monitoring and Logging

### Application Logs

1. **View application logs**
   ```bash
   # If using systemd
   sudo journalctl -u dump-truck-services -f
   
   # If running directly
   tail -f /opt/app/application.log
   ```

2. **Configure log rotation**
   ```bash
   # Create logrotate configuration
   sudo nano /etc/logrotate.d/dump-truck-services
   ```

   Add:
   ```
   /opt/app/*.log {
       daily
       missingok
       rotate 7
       compress
       delaycompress
       notifempty
       create 644 appuser appuser
   }
   ```

### Health Checks

The application includes health check endpoints:
- `GET /actuator/health` - Application health status
- `GET /actuator/info` - Application information

## Security Considerations

### Production Security Checklist

- [ ] Use strong database passwords
- [ ] Enable HTTPS/SSL
- [ ] Configure firewall rules
- [ ] Use environment variables for sensitive data
- [ ] Enable CSRF protection
- [ ] Configure proper CORS settings
- [ ] Use secure session management
- [ ] Implement rate limiting
- [ ] Regular security updates

### SSL/HTTPS Setup

1. **Using Let's Encrypt**
   ```bash
   # Install Certbot
   sudo apt install certbot
   
   # Obtain certificate
   sudo certbot certonly --standalone -d your-domain.com
   
   # Configure Nginx as reverse proxy
   ```

2. **Using Cloud Load Balancer**
   - AWS Application Load Balancer
   - Google Cloud Load Balancer
   - Azure Application Gateway

## Troubleshooting

### Common Issues

1. **Application won't start**
   ```bash
   # Check Java version
   java -version
   
   # Check port availability
   netstat -tulpn | grep :8080
   
   # Check logs
   tail -f /opt/app/application.log
   ```

2. **Database connection issues**
   ```bash
   # Test database connection
   mysql -h your-db-host -u your-user -p
   
   # Check database URL format
   echo $DATABASE_URL
   ```

3. **Email not sending**
   ```bash
   # Test SMTP connection
   telnet your-smtp-host 587
   
   # Check email credentials
   echo $MAIL_USERNAME
   echo $MAIL_PASSWORD
   ```

4. **Docker issues**
   ```bash
   # Check Docker logs
   docker logs container-name
   
   # Check container status
   docker ps -a
   
   # Restart containers
   docker-compose down && docker-compose up --build
   ```

### Performance Optimization

1. **JVM Tuning**
   ```bash
   # Add JVM options
   java -Xms512m -Xmx1024m -jar application.jar
   ```

2. **Database Optimization**
   ```sql
   -- Add indexes for better performance
   CREATE INDEX idx_users_email ON users(email);
   CREATE INDEX idx_service_orders_user_id ON service_orders(user_id);
   ```

3. **Connection Pool Tuning**
   ```properties
   # Optimize HikariCP settings
   spring.datasource.hikari.maximum-pool-size=10
   spring.datasource.hikari.minimum-idle=5
   ```

## Support and Maintenance

### Regular Maintenance Tasks

1. **Database backups**
   ```bash
   # Create backup script
   mysqldump -u root -p dump_truck_db > backup_$(date +%Y%m%d).sql
   ```

2. **Application updates**
   ```bash
   # Stop application
   sudo systemctl stop dump-truck-services
   
   # Backup current version
   cp dump-truck-services-0.0.1-SNAPSHOT.jar dump-truck-services-0.0.1-SNAPSHOT.jar.backup
   
   # Deploy new version
   cp new-version.jar dump-truck-services-0.0.1-SNAPSHOT.jar
   
   # Start application
   sudo systemctl start dump-truck-services
   ```

3. **Log rotation**
   ```bash
   # Configure automatic log rotation
   sudo logrotate -f /etc/logrotate.d/dump-truck-services
   ```

### Monitoring Tools

- **Application Performance Monitoring**: New Relic, Datadog
- **Log Management**: ELK Stack, Splunk
- **Infrastructure Monitoring**: Prometheus, Grafana
- **Uptime Monitoring**: Pingdom, UptimeRobot

## Conclusion

This deployment guide covers various deployment scenarios for the Dump Truck Services application. Choose the deployment method that best fits your infrastructure and requirements.

For additional support or questions, please refer to the project documentation or contact the development team. 