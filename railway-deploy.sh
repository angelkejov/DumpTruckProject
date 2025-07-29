#!/bin/bash

echo "🚀 Deploying to Railway..."

# Build the application
echo "📦 Building application..."
mvn clean package -DskipTests

# Deploy to Railway
echo "🚂 Deploying to Railway..."
railway up --detach

echo "✅ Deployment completed!"
echo "🌐 Your application URL: https://transport-ltd-production.up.railway.app"
echo "📊 Check status at: https://railway.com/project/d4dfc010-77f5-4987-82d6-a5e20c1b6154" 