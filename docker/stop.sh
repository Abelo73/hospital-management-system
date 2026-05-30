#!/bin/bash

set -e

echo "======================================"
echo "🏥 HMS - Stopping Docker Infrastructure"
echo "======================================"

# Move to project root (safe execution from any directory)
cd "$(dirname "$0")/../.."

echo ""
echo "🛑 Stopping all running containers..."
docker compose down

echo ""
echo "🔍 Verifying containers are stopped..."
RUNNING=$(docker compose ps -q)

if [ -z "$RUNNING" ]; then
  echo "All containers stopped successfully ✅"
else
  echo "Some containers are still running ❌"
  docker compose ps
fi

echo ""
echo "======================================"
echo "🧹 HMS Infrastructure Stopped"
echo "======================================"

echo ""
echo "📌 Note:"
echo "- Volumes (DB data) are preserved"
echo "- To fully wipe system use: clean.sh"
echo "======================================"
