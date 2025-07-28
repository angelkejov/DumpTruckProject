# 🚀 Railway Deployment Guide

## Quick Fix for Docker Registry Error

If you're getting the Docker registry error, follow these steps:

### **Step 1: Update Your Repository**
```bash
git add .
git commit -m "Fix Railway deployment configuration"
git push origin main
```

### **Step 2: Deploy to Railway**
1. Go to [railway.app](https://railway.app)
2. Sign up with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select your repository
5. Railway will use the `nixpacks.toml` configuration instead of Docker

### **Step 3: Add Environment Variables**
In Railway dashboard, add these variables:

```bash
# Required Environment Variables
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

### **Step 4: Add MySQL Database**
1. In Railway dashboard, click "New"
2. Select "Database" → "MySQL"
3. Railway will automatically provide the `DATABASE_URL`

## 🔧 **What We Fixed**

### **1. Docker Registry Issue**
- ✅ **Removed Docker dependency** - Using Nixpacks instead
- ✅ **Added `nixpacks.toml`** - Direct build configuration
- ✅ **Updated `railway.json`** - Better Railway configuration

### **2. Build Process**
- ✅ **Maven wrapper** - Ensures consistent Maven version
- ✅ **Retry mechanisms** - Handles network issues
- ✅ **Health checks** - Better monitoring

## 🎯 **Alternative Solutions**

### **If Railway Still Has Issues:**

**Option 1: Use Render Instead**
1. Go to [render.com](https://render.com)
2. Create new Web Service
3. Connect your GitHub repo
4. Use the `render.yaml` configuration

**Option 2: Use Heroku**
```bash
# Install Heroku CLI
# Create app
heroku create your-app-name
# Add database
heroku addons:create cleardb:ignite
# Deploy
git push heroku main
```

**Option 3: Use DigitalOcean App Platform**
1. Go to [digitalocean.com](https://digitalocean.com)
2. Create App
3. Connect GitHub repo
4. Configure environment variables

## 🔍 **Troubleshooting**

### **Common Railway Issues:**

**1. Build Timeout**
- Railway has a 15-minute build limit
- Our build should complete in ~5-10 minutes

**2. Memory Issues**
- Railway provides 512MB RAM
- Our app is configured for 512MB max

**3. Port Issues**
- Railway automatically assigns port
- Our app uses `$PORT` environment variable

### **If Build Still Fails:**
1. **Check logs** in Railway dashboard
2. **Verify environment variables** are set
3. **Try Render or Heroku** as alternatives

## ✅ **Success Indicators**

Once deployed successfully:
- ✅ **Health check passes**: `/actuator/health`
- ✅ **Home page loads**: Your app URL
- ✅ **Registration works**: Can create accounts
- ✅ **Email verification works**: Receives verification codes

## 🎉 **You're Ready!**

Your application is now configured to avoid the Docker registry issue. Railway will use Nixpacks to build your application directly without Docker.

**Deploy and go live!** 🚛✨ 