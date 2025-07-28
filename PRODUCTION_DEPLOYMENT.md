# Production Deployment Guide

## Overview
This guide provides step-by-step instructions for deploying the Dump Truck Services application to production.

## Prerequisites
- Docker and Docker Compose installed
- MySQL 8.0+ (if not using Docker)
- Java 17+ (if not using Docker)
- Maven 3.6+ (if not using Docker)

## Security Checklist

### ✅ Environment Variables
Set the following environment variables in production:

```bash
# Database Configuration
DATABASE_URL=jdbc:mysql://your-db-host:3306/dump_truck_prod?useSSL=true&requireSSL=true
DATABASE_USERNAME=your_db_username
DATABASE_PASSWORD=your_secure_db_password

# Email Configuration
MAIL_HOST=smtp-relay.brevo.com
MAIL_PORT=587
MAIL_USERNAME=your_email_username
MAIL_PASSWORD=your_email_password
APP_EMAIL_FROM=your_from_email@domain.com

# Application Configuration
APP_VERIFICATION_URL=https://yourdomain.com/verify
SERVER_PORT=8080
SERVER_CONTEXT_PATH=
```

### ✅ Security Measures
- [ ] Change default database passwords
- [ ] Use HTTPS in production
- [ ] Enable CSRF protection
- [ ] Configure secure session cookies
- [ ] Set up proper logging
- [ ] Configure firewall rules
- [ ] Use environment variables for secrets

## Deployment Options

### Option 1: Docker Compose (Recommended)

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd dump-truck-services
   ```

2. **Configure environment variables:**
   ```bash
   cp docker-compose.yml docker-compose.prod.yml
   # Edit docker-compose.prod.yml with your production values
   ```

3. **Deploy:**
   ```bash
   docker-compose -f docker-compose.prod.yml up -d
   ```

### Option 2: Traditional Deployment

1. **Build the application:**
   ```bash
   mvn clean package -DskipTests
   ```

2. **Set environment variables:**
   ```bash
   export SPRING_PROFILES_ACTIVE=prod
   export DATABASE_URL=your_db_url
   export DATABASE_USERNAME=your_username
   export DATABASE_PASSWORD=your_password
   # ... other environment variables
   ```

3. **Run the application:**
   ```bash
   java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
   ```

### Option 3: Cloud Deployment

#### AWS EC2
1. Launch EC2 instance with Ubuntu
2. Install Docker and Docker Compose
3. Follow Docker Compose deployment steps

#### Google Cloud Run
1. Build and push Docker image to Google Container Registry
2. Deploy using Cloud Run service

#### Azure App Service
1. Configure Java 17 runtime
2. Set environment variables in App Service configuration
3. Deploy JAR file

## Monitoring and Health Checks

### Health Endpoints
- `/actuator/health` - Application health
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics

### Logging
- Logs are written to `logs/application.log`
- Log rotation: 10MB max file size, 30 days retention
- Log levels: WARN for production

### Monitoring
- Enable Prometheus metrics: `management.metrics.export.prometheus.enabled=true`
- Configure monitoring tools (Grafana, Prometheus, etc.)

## Database Setup

### Production Database
1. Create MySQL database:
   ```sql
   CREATE DATABASE dump_truck_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. Create dedicated user:
   ```sql
   CREATE USER 'dump_truck_user'@'%' IDENTIFIED BY 'secure_password';
   GRANT ALL PRIVILEGES ON dump_truck_prod.* TO 'dump_truck_user'@'%';
   FLUSH PRIVILEGES;
   ```

### Database Migration
- The application uses `spring.jpa.hibernate.ddl-auto=validate` in production
- Run database migrations manually or use Flyway/Liquibase for schema management

## SSL/HTTPS Configuration

### Using Reverse Proxy (Nginx)
```nginx
server {
    listen 80;
    server_name yourdomain.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl;
    server_name yourdomain.com;
    
    ssl_certificate /path/to/certificate.crt;
    ssl_certificate_key /path/to/private.key;
    
    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### Using Spring Boot SSL
Add to `application-prod.properties`:
```properties
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=your_keystore_password
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=tomcat
```

## Backup Strategy

### Database Backup
```bash
# Daily backup script
mysqldump -u username -p dump_truck_prod > backup_$(date +%Y%m%d).sql
```

### Application Backup
- Backup configuration files
- Backup logs directory
- Backup Docker volumes (if using Docker)

## Troubleshooting

### Common Issues

1. **Database Connection Issues**
   - Check database credentials
   - Verify network connectivity
   - Check firewall rules

2. **Email Sending Issues**
   - Verify SMTP credentials
   - Check email provider settings
   - Review application logs

3. **Memory Issues**
   - Adjust JVM heap size: `-Xmx1g -Xms512m`
   - Monitor memory usage with `jstat` or monitoring tools

4. **Performance Issues**
   - Enable database connection pooling
   - Configure proper JPA settings
   - Monitor slow queries

### Log Analysis
```bash
# View application logs
tail -f logs/application.log

# Search for errors
grep "ERROR" logs/application.log

# Monitor real-time logs
docker-compose logs -f app
```

## Security Best Practices

1. **Regular Updates**
   - Keep dependencies updated
   - Monitor security advisories
   - Apply security patches promptly

2. **Access Control**
   - Use strong passwords
   - Implement IP whitelisting
   - Enable database SSL connections

3. **Monitoring**
   - Set up intrusion detection
   - Monitor failed login attempts
   - Track suspicious activities

4. **Backup and Recovery**
   - Test backup restoration procedures
   - Maintain multiple backup copies
   - Document recovery procedures

## Support and Maintenance

### Regular Maintenance Tasks
- [ ] Monitor application logs
- [ ] Check database performance
- [ ] Review security updates
- [ ] Backup verification
- [ ] Performance monitoring

### Emergency Procedures
1. **Application Down**
   - Check application logs
   - Verify database connectivity
   - Restart application if necessary

2. **Database Issues**
   - Check database logs
   - Verify disk space
   - Restore from backup if needed

3. **Security Incident**
   - Isolate affected systems
   - Review access logs
   - Update credentials
   - Notify stakeholders

## Contact Information
For production support, contact:
- Technical Support: support@yourdomain.com
- Emergency: +1-XXX-XXX-XXXX 