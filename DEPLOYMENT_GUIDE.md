# 🚀 Deployment Guide - Dump Truck Services

## Quick Start (Choose Your Platform)

### 🎯 **Railway (Recommended for Beginners)**

**Step 1: Prepare Your Code**
```bash
# Make sure your code is on GitHub
git add .
git commit -m "Ready for deployment"
git push origin main
```

**Step 2: Deploy to Railway**
1. Go to [railway.app](https://railway.app)
2. Sign up with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your repository
5. Railway will automatically detect Spring Boot and deploy

**Step 3: Configure Environment Variables**
In Railway dashboard, add these variables:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=your_mysql_url
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
MAIL_HOST=smtp-relay.brevo.com
MAIL_USERNAME=your_email
MAIL_PASSWORD=your_password
APP_EMAIL_FROM=your_email
APP_VERIFICATION_URL=https://your-app.railway.app/verify
```

**Step 4: Add Database**
1. In Railway dashboard, click "New"
2. Select "Database" → "MySQL"
3. Railway will provide the DATABASE_URL automatically

### 🎯 **Render (Free Tier Available)**

**Step 1: Deploy to Render**
1. Go to [render.com](https://render.com)
2. Sign up with GitHub
3. Click "New" → "Web Service"
4. Connect your GitHub repository
5. Configure:
   - **Name**: dump-truck-services
   - **Environment**: Java
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar`

**Step 2: Add Environment Variables**
In Render dashboard, add:
```
SPRING_PROFILES_ACTIVE=prod
DATABASE_URL=your_mysql_url
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
MAIL_HOST=smtp-relay.brevo.com
MAIL_USERNAME=your_email
MAIL_PASSWORD=your_password
APP_EMAIL_FROM=your_email
APP_VERIFICATION_URL=https://your-app.onrender.com/verify
```

### 🎯 **Heroku (Popular Choice)**

**Step 1: Install Heroku CLI**
```bash
# Download from https://devcenter.heroku.com/articles/heroku-cli
```

**Step 2: Deploy**
```bash
# Login to Heroku
heroku login

# Create app
heroku create your-app-name

# Add MySQL database
heroku addons:create cleardb:ignite

# Set environment variables
heroku config:set SPRING_PROFILES_ACTIVE=prod
heroku config:set DATABASE_URL=$(heroku config:get CLEARDB_DATABASE_URL)
heroku config:set MAIL_HOST=smtp-relay.brevo.com
heroku config:set MAIL_USERNAME=your_email
heroku config:set MAIL_PASSWORD=your_password
heroku config:set APP_EMAIL_FROM=your_email
heroku config:set APP_VERIFICATION_URL=https://your-app.herokuapp.com/verify

# Deploy
git push heroku main
```

### 🎯 **DigitalOcean App Platform**

**Step 1: Deploy**
1. Go to [digitalocean.com](https://digitalocean.com)
2. Sign up and go to "Apps"
3. Click "Create App"
4. Connect your GitHub repository
5. Configure:
   - **Environment**: Java
   - **Build Command**: `mvn clean package -DskipTests`
   - **Run Command**: `java -jar target/dump-truck-services-0.0.1-SNAPSHOT.jar`

**Step 2: Add Environment Variables**
In DigitalOcean dashboard, add all required environment variables.

### 🎯 **Local Docker Deployment**

**Step 1: Configure Environment**
```bash
# Copy environment template
cp env.example .env

# Edit .env with your actual values
nano .env
```

**Step 2: Deploy**
```bash
# Build and run
docker-compose up -d

# Check logs
docker-compose logs -f
```

## 🔧 **Environment Variables Reference**

### Required Variables
```bash
# Database
DATABASE_URL=jdbc:mysql://your-db-host:3306/dump_truck_prod
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_secure_password

# Email
MAIL_HOST=smtp-relay.brevo.com
MAIL_PORT=587
MAIL_USERNAME=your_email_username
MAIL_PASSWORD=your_email_password
APP_EMAIL_FROM=your_from_email@domain.com

# Application
APP_VERIFICATION_URL=https://your-domain.com/verify
SPRING_PROFILES_ACTIVE=prod
```

### Optional Variables
```bash
SERVER_PORT=8080
SERVER_CONTEXT_PATH=
```

## 🗄️ **Database Setup**

### Railway/Render (Managed MySQL)
- Platform provides MySQL automatically
- Use the provided DATABASE_URL

### Heroku (ClearDB)
```bash
heroku addons:create cleardb:ignite
heroku config:get CLEARDB_DATABASE_URL
```

### External MySQL
1. Set up MySQL server
2. Create database: `CREATE DATABASE dump_truck_prod;`
3. Create user: `CREATE USER 'user'@'%' IDENTIFIED BY 'password';`
4. Grant privileges: `GRANT ALL ON dump_truck_prod.* TO 'user'@'%';`

## 📧 **Email Configuration**

### Brevo (Recommended)
1. Sign up at [brevo.com](https://brevo.com)
2. Create SMTP credentials
3. Use these settings:
   - Host: smtp-relay.brevo.com
   - Port: 587
   - Username: your_brevo_username
   - Password: your_brevo_password

### Gmail
- Host: smtp.gmail.com
- Port: 587
- Username: your_gmail@gmail.com
- Password: app-specific password

## 🔍 **Verification Steps**

### 1. Check Application Health
```bash
# Your app URL + /actuator/health
https://your-app.railway.app/actuator/health
```

### 2. Test Registration
1. Go to your app URL
2. Click "Register"
3. Fill out the form
4. Check email for verification code
5. Verify email

### 3. Test Login
1. Go to login page
2. Enter credentials
3. Should redirect to profile page

### 4. Test Order Service
1. Login to your account
2. Go to "Order Service"
3. Fill out order form
4. Submit order
5. Check profile for order history

## 🚨 **Troubleshooting**

### Common Issues

**1. Database Connection Error**
- Check DATABASE_URL format
- Verify database credentials
- Ensure database exists

**2. Email Not Sending**
- Verify SMTP credentials
- Check MAIL_HOST and MAIL_PORT
- Test with simple email client

**3. Application Won't Start**
- Check Java version (17+ required)
- Verify environment variables
- Check application logs

**4. Build Failures**
- Ensure Maven is installed
- Check Java version
- Verify pom.xml is correct

### Log Analysis
```bash
# View application logs
docker-compose logs -f

# Check specific service
docker-compose logs app

# View Railway logs
railway logs

# View Heroku logs
heroku logs --tail
```

## 🔒 **Security Checklist**

- [ ] Environment variables set (not hardcoded)
- [ ] Strong database passwords
- [ ] Secure email credentials
- [ ] HTTPS enabled (platform handles this)
- [ ] CSRF protection enabled
- [ ] Secure session cookies
- [ ] Proper error handling
- [ ] Logging configured

## 📞 **Support**

If you encounter issues:

1. **Check logs** for error messages
2. **Verify environment variables** are set correctly
3. **Test database connection** separately
4. **Check email configuration** with simple test
5. **Review platform documentation** for specific issues

## 🎉 **Success!**

Once deployed, your application will be available at:
- **Railway**: `https://your-app.railway.app`
- **Render**: `https://your-app.onrender.com`
- **Heroku**: `https://your-app.herokuapp.com`
- **DigitalOcean**: `https://your-app.ondigitalocean.app`

Your dump truck services application is now live and ready for customers! 🚛✨ 