# Heroku Deployment Guide

This guide will help you deploy your Spring Boot application to Heroku.

## Prerequisites

1. **Heroku Account**: Sign up at [heroku.com](https://heroku.com)
2. **Heroku CLI**: Install from [devcenter.heroku.com/articles/heroku-cli](https://devcenter.heroku.com/articles/heroku-cli)
3. **Git**: Make sure your project is in a Git repository

## Step 1: Prepare Your Application

### 1.1 Build the Application
```bash
mvn clean package -DskipTests
```

### 1.2 Test Locally
```bash
java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
```

## Step 2: Create Heroku App

### 2.1 Login to Heroku
```bash
heroku login
```

### 2.2 Create New App
```bash
heroku create your-app-name
```
Replace `your-app-name` with your desired app name.

## Step 3: Add PostgreSQL Database

### 3.1 Add PostgreSQL Add-on
```bash
heroku addons:create heroku-postgresql:mini
```

### 3.2 Verify Database
```bash
heroku pg:info
```

## Step 4: Configure Email Service

### 4.1 Add SendGrid Add-on (Recommended)
```bash
heroku addons:create sendgrid:starter
```

### 4.2 Or Use Gmail SMTP
If you prefer Gmail, you'll need to set up environment variables manually.

## Step 5: Set Environment Variables

### 5.1 Set Email Configuration
```bash
# For SendGrid
heroku config:set MAIL_HOST=smtp.sendgrid.net
heroku config:set MAIL_PORT=587
heroku config:set MAIL_USERNAME=apikey
heroku config:set MAIL_PASSWORD=your-sendgrid-api-key

# For Gmail
heroku config:set MAIL_HOST=smtp.gmail.com
heroku config:set MAIL_PORT=587
heroku config:set MAIL_USERNAME=your-email@gmail.com
heroku config:set MAIL_PASSWORD=your-app-password
```

### 5.2 Set App URL
```bash
heroku config:set APP_URL=https://your-app-name.herokuapp.com
```

### 5.3 Verify Configuration
```bash
heroku config
```

## Step 6: Deploy to Heroku

### 6.1 Add Git Remote
```bash
heroku git:remote -a your-app-name
```

### 6.2 Deploy
```bash
git add .
git commit -m "Deploy to Heroku"
git push heroku main
```

## Step 7: Verify Deployment

### 7.1 Check Logs
```bash
heroku logs --tail
```

### 7.2 Open App
```bash
heroku open
```

## Step 8: Database Migration

### 8.1 Run Database Migration
```bash
heroku run java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

### Common Issues

1. **Build Failures**
   - Check logs: `heroku logs --tail`
   - Ensure all dependencies are in `pom.xml`

2. **Database Connection Issues**
   - Verify PostgreSQL add-on is active
   - Check database credentials: `heroku pg:info`

3. **Email Issues**
   - Verify SendGrid/Gmail configuration
   - Check email credentials in environment variables

4. **Port Issues**
   - Heroku sets `PORT` environment variable automatically
   - Application should use `${PORT:8080}`

### Useful Commands

```bash
# View logs
heroku logs --tail

# Check app status
heroku ps

# Restart app
heroku restart

# Check environment variables
heroku config

# Access database
heroku pg:psql

# Scale dynos
heroku ps:scale web=1
```

## Post-Deployment

### 1. Test All Features
- User registration
- Email verification
- Service ordering
- Profile management

### 2. Monitor Performance
```bash
heroku logs --tail
```

### 3. Set Up Monitoring (Optional)
```bash
heroku addons:create papertrail:choklad
```

## Security Considerations

1. **Environment Variables**: Never commit sensitive data
2. **HTTPS**: Heroku provides SSL certificates automatically
3. **Database**: Use Heroku's managed PostgreSQL
4. **Logs**: Monitor for security issues

## Cost Optimization

1. **Free Tier**: No longer available, but mini dynos are affordable
2. **Database**: Start with mini PostgreSQL ($5/month)
3. **Email**: SendGrid starter plan is free for low volume

## Support

- [Heroku Documentation](https://devcenter.heroku.com/)
- [Spring Boot on Heroku](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-on-heroku)
- [PostgreSQL on Heroku](https://devcenter.heroku.com/articles/heroku-postgresql) 