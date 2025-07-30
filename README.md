# Dump Truck Services

A Spring Boot web application for dump truck service management, deployed on Railway.

## Features

- User registration and authentication
- Service order management
- Email verification
- Responsive web interface
- **Admin Dashboard** with full access control

## Admin Access

The application includes a dedicated admin user with full access to the admin dashboard.

### Admin Credentials
- **Email**: `admin@shteotkacha.com`
- **Password**: `admin123`
- **Access URL**: `/admin`

### Admin Dashboard Features
- **Overview**: Key statistics and business metrics
- **Order Management**: View, filter, and update order statuses
- **Customer Management**: View all registered customers and their details
- **Analytics**: Business statistics and order analytics

### How to Access Admin Dashboard
1. Start the application: `mvn spring-boot:run`
2. Go to: `http://localhost:8080/login`
3. Login with admin credentials
4. Navigate to: `http://localhost:8080/admin`

**Note**: The admin user is automatically created when the application starts for the first time.

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Database**: MySQL (Railway)
- **Frontend**: Thymeleaf, Bootstrap
- **Security**: Spring Security
- **Email**: Brevo SMTP
- **Deployment**: Railway

```properties
spring_profiles_active=prod
PROD_DB_HOST=mysql.railway.internal
PROD_DB_PORT=3306
PROD_DB_NAME=railway
PROD_DB_PASSWORD=KSfzpQUuqnZyWGqrvTzjMNmiLuThzEzW
PROD_DB_USERNAME=root


## Railway Deployment

This application is configured for deployment on Railway with MySQL database.

### Prerequisites

1. Railway account
2. MySQL database service on Railway
3. Environment variables configured

### Deployment Steps

1. **Connect your repository to Railway:**
   - Go to Railway dashboard
   - Click "New Project" → "Deploy from GitHub repo"
   - Select your repository

2. **Add MySQL Database:**
   - In your Railway project, click "New Service" → "Database" → "MySQL"
   - Railway will automatically provide the database connection variables

3. **Configure Environment Variables:**
   Railway automatically provides:
   - `MYSQL_URL` - MySQL connection string
   - `MYSQLUSER` - Database username
   - `MYSQLPASSWORD` - Database password
   - `PORT` - Application port

   **Required custom environment variables:**
   - `MAIL_USERNAME` - Your Brevo SMTP username
   - `MAIL_PASSWORD` - Your Brevo SMTP password
   - `APP_EMAIL_FROM` - Email address for sending emails
   - `APP_URL` - Your Railway app URL (e.g., https://your-app.railway.app)

4. **Deploy:**
   - Railway will automatically build and deploy your application
   - The health check will verify the application is running at `/actuator/health`

### Database

The application automatically creates the required tables on startup using Hibernate DDL.

## Local Development

To run locally:

1. Clone the repository
2. Configure MySQL database
3. Set environment variables
4. Run: `./mvnw spring-boot:run`

## Project Structure

```
src/
├── main/
│   ├── java/com/shteotkacha/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access
│   │   ├── entity/         # JPA entities
│   │   ├── config/         # Configuration
│   │   └── dto/           # Data transfer objects
│   └── resources/
│       ├── templates/      # Thymeleaf templates
│       ├── static/         # CSS, JS, images
│       ├── application.properties
│       └── application-railway.properties
```

## Troubleshooting Railway Deployment

### Common Issues

1. **Build Failures:**
   - Ensure Java 17 is specified in pom.xml
   - Check that all dependencies are available
   - Verify Dockerfile syntax

2. **Database Connection Issues:**
   - Verify `MYSQL_URL`, `MYSQLUSER`, and `MYSQLPASSWORD` are set
   - Check that MySQL service is running
   - Ensure database is accessible from the application

3. **Health Check Failures:**
   - Verify `/actuator/health` endpoint is accessible
   - Check application logs for startup errors
   - Ensure all required environment variables are set

4. **Email Configuration:**
   - Verify `MAIL_USERNAME` and `MAIL_PASSWORD` are set
   - Check Brevo SMTP credentials
   - Ensure `APP_EMAIL_FROM` is configured

### Logs and Monitoring

- View application logs in Railway dashboard
- Monitor health check status at `/actuator/health`
- Check database connectivity in logs

## License

© 2024 Dump Truck Services. All rights reserved. 