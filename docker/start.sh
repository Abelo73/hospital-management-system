#!/bin/bash

set -e

echo "======================================"
echo "🏥 HMS - Starting Docker Infrastructure"
echo "======================================"

# Move to project root (important if script is called from anywhere)
cd "$(dirname "$0")/../.."

echo ""
echo "📦 Starting all Docker containers..."
docker compose up -d

echo ""
echo "⏳ Waiting for services to stabilize..."

sleep 5

echo ""
echo "🔍 Checking running containers..."
docker compose ps

echo ""
echo "======================================"
echo "🧪 Health Checks"
echo "======================================"

# PostgreSQL check
echo -n "PostgreSQL: "
docker exec -i $(docker ps -qf "name=postgres") pg_isready > /dev/null 2>&1 && echo "OK ✅" || echo "NOT READY ❌"

# Redis check
echo -n "Redis: "
docker exec -i $(docker ps -qf "name=redis") redis-cli ping > /dev/null 2>&1 && echo "OK ✅" || echo "NOT READY ❌"

echo ""
echo "======================================"
echo "🚀 HMS Infrastructure Started Successfully"
echo "======================================"

echo ""
echo "🌐 Service URLs:"
echo "--------------------------------------"
echo "📌 Spring Boot API : http://localhost:8080/api"
echo "📌 Swagger UI      : http://localhost:8080/api/swagger-ui.html"
echo "📌 PostgreSQL      : localhost:5432"
echo "📌 Redis           : localhost:6379"
echo "📌 pgAdmin         : http://localhost:5050"
echo "📌 MinIO Console   : http://localhost:9001"
echo "📌 MailHog         : http://localhost:8025"
echo "📌 Prometheus      : http://localhost:9090"
echo "📌 Grafana         : http://localhost:3001"

echo ""
echo "======================================"
echo "🎯 System is READY for development"
echo "======================================"
