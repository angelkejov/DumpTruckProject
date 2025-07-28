#!/bin/bash

# Heroku Deployment Script
# Usage: ./deploy-heroku.sh your-app-name

set -e

APP_NAME=$1

if [ -z "$APP_NAME" ]; then
    echo "Usage: ./deploy-heroku.sh your-app-name"
    echo "Please provide your Heroku app name"
    exit 1
fi

echo "🚀 Starting Heroku deployment for: $APP_NAME"

# Step 1: Build the application
echo "📦 Building application..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi

echo "✅ Build successful!"

# Step 2: Check if Heroku CLI is installed
if ! command -v heroku &> /dev/null; then
    echo "❌ Heroku CLI not found. Please install it first:"
    echo "https://devcenter.heroku.com/articles/heroku-cli"
    exit 1
fi

# Step 3: Login to Heroku
echo "🔐 Logging into Heroku..."
heroku login

# Step 4: Create or use existing app
echo "🏗️ Setting up Heroku app..."
if heroku apps:info --app $APP_NAME &> /dev/null; then
    echo "✅ App $APP_NAME already exists"
else
    echo "📝 Creating new app: $APP_NAME"
    heroku create $APP_NAME
fi

# Step 5: Add PostgreSQL if not exists
echo "🗄️ Setting up PostgreSQL database..."
if ! heroku addons:info --app $APP_NAME heroku-postgresql &> /dev/null; then
    echo "📊 Adding PostgreSQL database..."
    heroku addons:create heroku-postgresql:mini --app $APP_NAME
else
    echo "✅ PostgreSQL already configured"
fi

# Step 6: Add SendGrid if not exists
echo "📧 Setting up email service..."
if ! heroku addons:info --app $APP_NAME sendgrid &> /dev/null; then
    echo "📨 Adding SendGrid for email..."
    heroku addons:create sendgrid:starter --app $APP_NAME
else
    echo "✅ SendGrid already configured"
fi

# Step 7: Set environment variables
echo "⚙️ Configuring environment variables..."

# Get SendGrid API key
SENDGRID_API_KEY=$(heroku config:get SENDGRID_API_KEY --app $APP_NAME)
if [ -z "$SENDGRID_API_KEY" ]; then
    echo "⚠️ SendGrid API key not found. Please set it manually:"
    echo "heroku config:set MAIL_PASSWORD=your-sendgrid-api-key --app $APP_NAME"
fi

# Set email configuration
heroku config:set MAIL_HOST=smtp.sendgrid.net --app $APP_NAME
heroku config:set MAIL_PORT=587 --app $APP_NAME
heroku config:set MAIL_USERNAME=apikey --app $APP_NAME

# Set app URL
APP_URL="https://$APP_NAME.herokuapp.com"
heroku config:set APP_URL=$APP_URL --app $APP_NAME

echo "✅ Environment variables configured"

# Step 8: Deploy to Heroku
echo "🚀 Deploying to Heroku..."

# Add Heroku remote if not exists
if ! git remote | grep -q heroku; then
    heroku git:remote -a $APP_NAME
fi

# Commit and push
git add .
git commit -m "Deploy to Heroku - $(date)"
git push heroku main

# Step 9: Scale dynos
echo "⚡ Scaling dynos..."
heroku ps:scale web=1 --app $APP_NAME

# Step 10: Open the app
echo "🌐 Opening application..."
heroku open --app $APP_NAME

echo ""
echo "🎉 Deployment completed!"
echo "📱 Your app is now live at: $APP_URL"
echo ""
echo "📋 Next steps:"
echo "1. Test user registration and email verification"
echo "2. Check logs: heroku logs --tail --app $APP_NAME"
echo "3. Monitor performance: heroku ps --app $APP_NAME"
echo ""
echo "🔧 Useful commands:"
echo "- View logs: heroku logs --tail --app $APP_NAME"
echo "- Restart app: heroku restart --app $APP_NAME"
echo "- Check config: heroku config --app $APP_NAME"
echo "- Access database: heroku pg:psql --app $APP_NAME" 