#!/bin/bash

# Railway Database Setup Script
# This script helps you set up the database connection for your Railway deployment

echo "🚀 Railway Database Setup"
echo "=========================="

echo ""
echo "📋 Steps to deploy your database to Railway:"
echo ""

echo "1. Go to your Railway project dashboard"
echo "2. Click 'New Service'"
echo "3. Select 'Database' → 'MySQL'"
echo "4. Name it 'mysql-database' or similar"
echo ""

echo "🔗 After creating the MySQL service:"
echo "- Railway will automatically provide DATABASE_URL, DATABASE_USERNAME, and DATABASE_PASSWORD"
echo "- These will be available in your app's environment variables"
echo ""

echo "📊 To initialize your database schema:"
echo "- Your app is configured with 'spring.jpa.hibernate.ddl-auto=update'"
echo "- This will automatically create tables when the app starts"
echo "- Alternatively, you can run the init_railway_db.sql script manually"
echo ""

echo "🔍 To verify the database connection:"
echo "- Check your app logs in Railway dashboard"
echo "- Look for database connection messages"
echo "- Test the /api/health endpoint"
echo ""

echo "📝 Environment variables you need in Railway:"
echo "DATABASE_URL=mysql://host:port/database_name"
echo "DATABASE_USERNAME=your_username"
echo "DATABASE_PASSWORD=your_password"
echo ""

echo "✅ Your application is already configured to use these variables!"
echo "   Check application-railway.properties for the configuration." 