# Railway Deployment Guide

This guide provides step-by-step instructions for deploying the Dump Truck Services application to Railway.

## Prerequisites

- Railway account (sign up at [railway.app](https://railway.app))
- GitHub repository with your code
- Brevo (formerly Sendinblue) account for email services

## Step 1: Prepare Your Repository

1. Ensure all files are committed to your GitHub repository
2. Verify the following files are present:
   - `Dockerfile`
   - `railway.json`
   - `railway.toml`
   - `src/main/resources/application-railway.properties`
   - `.railwayignore`

## Step 2: Create Railway Project

1. Go to [Railway Dashboard](https://railway.app/dashboard)
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Choose your repository
5. Railway will automatically detect the Dockerfile and start building

## Step 3: Add MySQL Database

1. In your Railway project dashboard, click "New Service"
2. Select "Database" → "MySQL"
3. Railway will automatically provide the following environment variables:
   - `MYSQL_URL`
   - `MYSQLUSER`
   - `MYSQLPASSWORD`

## Step 4: Configure Environment Variables

In your Railway project settings, add the following environment variables:

### Required Variables:
- `SPRING_PROFILES_ACTIVE` = `railway`
- `PORT` = `8080`

### Email Configuration (Brevo):
- `MAIL_USERNAME` = Your Brevo SMTP username
- `MAIL_PASSWORD` = Your Brevo SMTP password
- `APP_EMAIL_FROM` = Email address for sending emails
- `APP_URL` = Your Railway app URL (e.g., https://your-app.railway.app)

### Optional Variables:
- `MAIL_HOST` = `smtp-relay.brevo.com` (default)
- `MAIL_PORT` = `587` (default)

## Step 5: Deploy

1. Railway will automatically build and deploy your application
2. Monitor the build logs for any issues
3. Once deployed, Railway will provide a public URL

## Step 6: Verify Deployment

1. Check the health endpoint: `https://your-app.railway.app/api/health`
2. Test the main application: `https://your-app.railway.app`
3. Check environment variables: `https://your-app.railway.app/api/env`
4. Verify database connectivity in the application logs

## Troubleshooting

### Build Failures
- Check that Java 17 is specified in `pom.xml`
- Verify all dependencies are available
- Check Dockerfile syntax

### Database Connection Issues
- Verify `MYSQL_URL`, `MYSQLUSER`, and `MYSQLPASSWORD` are set
- Ensure MySQL service is running
- Check application logs for connection errors

### Health Check Failures
- Verify `/actuator/health` endpoint is accessible
- Check application startup logs
- Ensure all required environment variables are set

### Email Issues
- Verify Brevo credentials are correct
- Check `APP_EMAIL_FROM` is set
- Ensure `APP_URL` points to your Railway domain

## Monitoring

- View application logs in Railway dashboard
- Monitor health check status
- Check database connectivity
- Monitor resource usage

## Environment Variables Reference

| Variable | Description | Required | Default |
|----------|-------------|----------|---------|
| `SPRING_PROFILES_ACTIVE` | Spring profile to use | Yes | `railway` |
| `PORT` | Application port | Yes | `8080` |
| `MYSQL_URL` | MySQL connection string | Yes | Auto-provided |
| `MYSQLUSER` | Database username | Yes | Auto-provided |
| `MYSQLPASSWORD` | Database password | Yes | Auto-provided |
| `MAIL_USERNAME` | Brevo SMTP username | Yes | - |
| `MAIL_PASSWORD` | Brevo SMTP password | Yes | - |
| `APP_EMAIL_FROM` | Sender email address | Yes | - |
| `APP_URL` | Application URL | Yes | - |
| `MAIL_HOST` | SMTP host | No | `smtp-relay.brevo.com` |
| `MAIL_PORT` | SMTP port | No | `587` |

## Support

If you encounter issues:
1. Check Railway documentation: [docs.railway.app](https://docs.railway.app)
2. Review application logs in Railway dashboard
3. Verify all environment variables are correctly set
4. Test the build process locally using `deploy.bat` 