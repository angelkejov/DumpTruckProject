# Render Deployment Guide

This guide will help you deploy your Spring Boot application and PostgreSQL database to Render.

## Prerequisites

1. A Render account (free tier available)
2. Your application code pushed to a Git repository (GitHub, GitLab, etc.)

## Deployment Steps

### 1. Database Setup

1. Go to your Render dashboard
2. Click "New +" and select "PostgreSQL"
3. Configure the database:
   - **Name**: `shteotkacha-db`
   - **Plan**: Free
   - **Region**: Oregon (or your preferred region)
4. Click "Create Database"
5. Once created, note down the connection details

### 2. Web Service Setup

1. In your Render dashboard, click "New +" and select "Web Service"
2. Connect your Git repository
3. Configure the service:
   - **Name**: `shteotkacha-app`
   - **Environment**: Docker
   - **Region**: Same as your database
   - **Branch**: `main` (or your default branch)
   - **Root Directory**: Leave empty (if your code is in the root)
   - **Build Command**: Leave empty (Docker will handle this)
   - **Start Command**: Leave empty (Docker will handle this)

### 3. Environment Variables

Set the following environment variables in your web service:

#### Database Variables (Auto-configured)
- `DATABASE_URL` - Automatically set by Render
- `DB_USERNAME` - Automatically set by Render  
- `DB_PASSWORD` - Automatically set by Render

#### Application Variables
- `SPRING_PROFILES_ACTIVE` = `render`
- `MAIL_HOST` = `smtp-relay.brevo.com`
- `MAIL_PORT` = `587`
- `MAIL_USERNAME` = Your Brevo SMTP username
- `MAIL_PASSWORD` = Your Brevo SMTP password
- `APP_EMAIL_FROM` = `anikejov003@gmail.com`
- `APP_URL` = Your Render app URL (e.g., `https://shteotkacha-app.onrender.com`)

### 4. Database Initialization

After your database is created, you can run the initialization script:

1. Go to your PostgreSQL database in Render
2. Click on "Connect" → "External Database"
3. Use a PostgreSQL client to connect and run the `init_postgres_db.sql` script

### 5. Deploy

1. Click "Create Web Service"
2. Render will automatically build and deploy your application
3. Monitor the build logs for any issues
4. Once deployed, your app will be available at the provided URL

## Health Check

Your application includes a health check endpoint at `/api/health` which Render will use to monitor the service.

## Troubleshooting

### Common Issues

1. **Build Failures**: Check that your Dockerfile is correct and all dependencies are properly specified
2. **Database Connection**: Ensure the database environment variables are correctly set
3. **Email Configuration**: Verify your Brevo SMTP credentials are correct
4. **Port Issues**: The application is configured to use port 8080, which Render will automatically map

### Logs

- Check the build logs in Render dashboard for build issues
- Check the runtime logs for application issues
- Use the "Shell" feature to access the container if needed

## Environment Configuration

The application uses the `render` profile which is configured in `application-render.properties`. This profile:

- Uses PostgreSQL database configuration
- Disables H2 console
- Configures connection pooling
- Sets up email configuration
- Configures security settings

## Free Tier Limitations

- **Database**: 90 days free, then $7/month
- **Web Service**: Free tier available with some limitations
- **Custom Domains**: Available on paid plans

## Monitoring

- Use Render's built-in monitoring dashboard
- Set up alerts for downtime
- Monitor database usage and performance

## Scaling

When you're ready to scale:
1. Upgrade to a paid plan
2. Configure auto-scaling rules
3. Set up load balancing if needed
4. Monitor performance metrics

## Security Notes

- All sensitive data should be stored in environment variables
- Database credentials are automatically managed by Render
- HTTPS is automatically enabled
- Consider setting up IP allowlists for production use 