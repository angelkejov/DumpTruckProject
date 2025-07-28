# Dump Truck Services Web Application

A full-stack web application for a dump truck services company that provides transportation solutions using small trucks (up to 3.5 tons).

## Features

### Authentication System
- User registration with email verification
- Secure login with Spring Security
- Password encryption using BCrypt
- Email verification with 24-hour token expiry
- Protected routes for authenticated users

### Public Pages
- **Home Page**: Company information, services overview, and features
- **Contact Page**: Company contact information and service areas

### Authenticated User Features
- **Service Order Form**: Submit service requests with pickup/drop-off locations, preferred date/time, material type, and notes
- **Profile Page**: View user information and order history
- **Order Management**: Track order status and history

### Email Integration
- Automatic email verification for new registrations
- Order confirmation emails
- Configurable SMTP settings

## Tech Stack

- **Backend**: Java 17 with Spring Boot 3.2.0
- **Frontend**: HTML + CSS (Thymeleaf templates)
- **Database**: MySQL 8.0
- **Security**: Spring Security with BCrypt
- **Email**: Spring Mail with SMTP
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE dump_truck_services;
```

2. The application will automatically create tables on startup using JPA/Hibernate.

### 2. Environment Configuration

Create a `.env` file in the project root or set environment variables:

```bash
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_NAME=dump_truck_services
DB_USERNAME=your_mysql_username
DB_PASSWORD=your_mysql_password

# Email Configuration (Gmail example)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password

# Application Configuration
SERVER_PORT=8080
APP_URL=http://localhost:8080
```

**Note**: For Gmail, you'll need to:
1. Enable 2-factor authentication
2. Generate an App Password
3. Use the App Password in `MAIL_PASSWORD`

### 3. Build and Run

1. Clone the repository:
```bash
git clone <repository-url>
cd dump-truck-services
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application at `http://localhost:8080`

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    verified BOOLEAN DEFAULT FALSE,
    verification_token VARCHAR(255),
    verification_token_expiry DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### Service Orders Table
```sql
CREATE TABLE service_orders (
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
```

## Application Structure

```
src/
├── main/
│   ├── java/com/shteotkacha/
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── MainController.java
│   │   │   └── ServiceOrderController.java
│   │   ├── dto/
│   │   │   ├── ServiceOrderDto.java
│   │   │   └── UserRegistrationDto.java
│   │   ├── entity/
│   │   │   ├── ServiceOrder.java
│   │   │   └── User.java
│   │   ├── repository/
│   │   │   ├── ServiceOrderRepository.java
│   │   │   └── UserRepository.java
│   │   ├── service/
│   │   │   ├── EmailService.java
│   │   │   ├── ServiceOrderService.java
│   │   │   └── UserService.java
│   │   └── DumpTruckServicesApplication.java
│   └── resources/
│       ├── static/css/
│       │   └── style.css
│       ├── templates/
│       │   ├── auth/
│       │   │   ├── login.html
│       │   │   └── register.html
│       │   ├── main/
│       │   │   ├── contact.html
│       │   │   └── home.html
│       │   ├── service/
│       │   │   └── order.html
│       │   └── user/
│       │       └── profile.html
│       └── application.yml
```

## Features in Detail

### User Registration
- Form validation (name, email, password)
- Password confirmation check
- Email uniqueness validation
- Automatic email verification

### Service Order System
- Comprehensive order form with validation
- Material type selection
- Date/time picker
- Optional notes field
- Order status tracking

### Security Features
- BCrypt password hashing
- CSRF protection
- Session management
- Role-based access control
- Email verification requirement

### Email System
- SMTP configuration
- HTML email templates
- Verification token generation
- Order confirmation emails

## Configuration Options

### Database Configuration
- Configurable database host, port, name, username, and password
- Automatic table creation with JPA/Hibernate
- Connection pooling with HikariCP

### Email Configuration
- Configurable SMTP settings
- Support for Gmail, Outlook, and other providers
- Customizable email templates

### Application Configuration
- Configurable server port
- Environment-based configuration
- Logging configuration

## Running the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Local Development
1. **Start MySQL** and create a database
2. **Configure email settings** in `application.properties`
3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```
4. **Access the application** at `http://localhost:8080`

### Building for Production
```bash
mvn clean package -DskipTests
java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /home` - Home page
- `GET /contact` - Contact page
- `GET /login` - Login page
- `GET /register` - Registration page
- `GET /verify` - Email verification

### Protected Endpoints
- `GET /order` - Service order form
- `POST /order` - Submit service order
- `GET /profile` - User profile page

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials
   - Ensure database exists

2. **Email Not Sending**
   - Verify SMTP settings
   - Check email credentials
   - Ensure network connectivity

3. **Application Won't Start**
   - Check Java version (requires 17+)
   - Verify Maven installation
   - Check port availability

### Logs
Application logs are available at:
- Console output during development
- Log files in production (configure in `application.yml`)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please contact:
- Email: support@dumptruckservices.com
- Phone: +1 (555) 123-4567 