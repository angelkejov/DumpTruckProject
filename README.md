# Dump Truck Services

A Spring Boot web application for dump truck service management, deployed on Railway.

## Features

- User registration and authentication
- Service order management
- Email verification
- Responsive web interface

## Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Database**: MySQL (Railway)
- **Frontend**: Thymeleaf, Bootstrap
- **Security**: Spring Security
- **Email**: Brevo SMTP
- **Deployment**: Railway

## Railway Deployment

This application is configured for deployment on Railway with MySQL database.

### Environment Variables

Railway automatically provides:
- `MYSQL_URL` - MySQL connection string
- `MYSQLUSER` - Database username
- `MYSQLPASSWORD` - Database password
- `PORT` - Application port

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

## License

© 2024 Dump Truck Services. All rights reserved. 