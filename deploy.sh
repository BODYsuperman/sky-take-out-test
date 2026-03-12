#!/bin/bash

# Sky Takeout Deployment Script
# Usage: ./deploy.sh [environment]
# environment: dev, staging, prod (default: dev)

set -e

ENVIRONMENT=${1:-dev}
APP_NAME="sky-takeout"
VERSION=$(git rev-parse --short HEAD)

echo "🚀 Deploying Sky Takeout to $ENVIRONMENT environment..."
echo "📦 Version: $VERSION"

# Build the application
echo "🔨 Building application..."
./mvnw clean package -DskipTests

# Build Docker image
echo "🐳 Building Docker image..."
docker build -t $APP_NAME:$VERSION .

# Tag for environment
docker tag $APP_NAME:$VERSION $APP_NAME:$ENVIRONMENT

# Stop existing containers if any
echo "⏹️  Stopping existing containers..."
docker-compose -f docker-compose.yml down || true

# Start new deployment
echo "▶️  Starting new deployment..."
docker-compose -f docker-compose.yml up -d

# Health check
echo "✅ Deployment completed!"
echo "📊 Check application status with: docker-compose ps"
echo "🌐 Application should be available on http://localhost:8080"