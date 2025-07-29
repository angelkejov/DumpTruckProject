# Railway Database Deployment Guide

## Overview
Your Spring Boot application is already configured to use MySQL on Railway. This guide will help you provision and connect the database service.

## Step 1: Add MySQL Database Service

1. **Navigate to Railway Dashboard**
   - Go to [railway.app](https://railway.app)
   - Select your project

2. **Create Database Service**
   - Click "New Service"
   - Select "Database" → "MySQL"
   - Name it `mysql-database` (or any name you prefer)

3. **Wait for Provisioning**
   - Railway will automatically provision the MySQL database
   - This may take a few minutes

## Step 2: Verify Environment Variables

After the database is created, Railway will automatically provide these environment variables to your app:

- `DATABASE_URL` - Full connection string
- `DATABASE_USERNAME` - Database username
- `DATABASE_PASSWORD` - Database password

**Note**: These variables are automatically shared between services in the same project.

## Step 3: Database Schema Initialization

Your application is configured to automatically create tables using Hibernate. The configuration in `application-railway.properties` includes:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This means:
- Tables will be created automatically when the app starts
- Existing tables will be updated if schema changes
- No manual database setup required

## Step 4: Manual Database Setup (Optional)

If you prefer to manually initialize the database, you can:

1. **Connect to Railway MySQL**
   - Use a MySQL client (like MySQL Workbench)
   - Use the connection details from Railway dashboard

2. **Run the initialization script**
   ```sql
   -- Use the init_railway_db.sql file
   -- This creates the users and service_orders tables
   ```

## Step 5: Verify Deployment

1. **Check Application Logs**
   - Go to your app service in Railway
   - Check the logs for database connection messages
   - Look for any connection errors

2. **Test the Application**
   - Visit your app URL
   - Test the `/api/health` endpoint
   - Try registering a new user

## Troubleshooting

### Common Issues:

1. **Database Connection Failed**
   - Verify environment variables are set
   - Check if the database service is running
   - Ensure your app is using the `railway` profile

2. **Tables Not Created**
   - Check application logs for Hibernate errors
   - Verify `spring.jpa.hibernate.ddl-auto=update` is set
   - Manually run the `init_railway_db.sql` script

3. **Environment Variables Missing**
   - Ensure both services are in the same Railway project
   - Check that the database service is properly provisioned
   - Restart your application service

### Debug Commands:

```bash
# Check if your app is using the correct profile
java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar --spring.profiles.active=railway

# View environment variables (in Railway dashboard)
echo $DATABASE_URL
echo $DATABASE_USERNAME
echo $DATABASE_PASSWORD
```

## Configuration Files

Your application is already configured with:

- **`application-railway.properties`** - Railway-specific configuration
- **`init_railway_db.sql`** - Database schema initialization
- **`railway.json`** - Railway deployment configuration

## Next Steps

1. Deploy your application to Railway
2. Add the MySQL database service
3. Verify the connection works
4. Test the application functionality

The database will be automatically initialized when your application starts for the first time. 