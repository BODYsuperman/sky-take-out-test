#!/bin/bash
# Sky Takeout Monitoring Setup Script

set -e

echo "🚀 Starting Sky Takeout Monitoring Stack..."

# Create monitoring directory if it doesn't exist
mkdir -p ./monitoring

# Start Prometheus and Grafana with docker-compose
docker-compose -f ../docker-compose.yml up -d prometheus grafana

echo "📊 Monitoring services started:"
echo "   - Prometheus: http://localhost:9090"
echo "   - Grafana: http://localhost:3000 (admin/admin)"
echo ""
echo "📈 Import the dashboard from ./monitoring/grafana-dashboard.json into Grafana"
echo "📋 Application logs will be available in ./logs directory"

# Create logs directory for application
mkdir -p ../logs

echo "✅ Monitoring setup complete!"