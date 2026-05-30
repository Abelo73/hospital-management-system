# ================================
# HMS - Hospital Management System
# CLI Automation (Docker + Spring Boot)
# ================================

APP_NAME=hospital-management-system

# -------------------------------
# Docker Commands
# -------------------------------

up:
	@echo "🚀 Starting HMS Infrastructure..."
	docker compose up -d

down:
	@echo "🛑 Stopping HMS Infrastructure..."
	docker compose down

restart:
	@echo "🔄 Restarting HMS Infrastructure..."
	docker compose down && docker compose up -d

status:
	@echo "📊 Container Status:"
	docker compose ps

logs:
	@echo "📜 Showing logs..."
	docker compose logs -f

clean:
	@echo "⚠️  Removing ALL containers + volumes..."
	docker compose down -v


# -------------------------------
# Spring Boot Commands
# -------------------------------

run:
	@echo "🏥 Running Spring Boot App..."
	./mvnw spring-boot:run

build:
	@echo "📦 Building project..."
	./mvnw clean install -DskipTests

test:
	@echo "🧪 Running tests..."
	./mvnw test

package:
	@echo "📦 Packaging JAR..."
	./mvnw clean package -DskipTests


# -------------------------------
# Development Workflow
# -------------------------------

dev:
	@echo "🧑‍💻 Starting DEV environment (Docker + App)..."
	docker compose up -d
	./mvnw spring-boot:run

reset:
	@echo "♻️ Resetting environment..."
	docker compose down -v
	./mvnw clean


# -------------------------------
# Health Checks
# -------------------------------

health:
	@echo "🔍 Checking API Health..."
	curl -s http://localhost:8080/actuator/health | jq

db:
	@echo "🗄️ Checking PostgreSQL container..."
	docker exec -it $$(docker ps -qf "name=postgres") pg_isready

redis:
	@echo "⚡ Checking Redis..."
	docker exec -it $$(docker ps -qf "name=redis") redis-cli ping


# -------------------------------
# Help Menu
# -------------------------------

help:
	@echo ""
	@echo "🏥 HMS CLI Commands"
	@echo "-----------------------------"
	@echo "make up        -> Start infrastructure"
	@echo "make down      -> Stop infrastructure"
	@echo "make restart   -> Restart system"
	@echo "make status    -> Show containers"
	@echo "make logs      -> View logs"
	@echo "make clean     -> Delete everything (danger)"
	@echo ""
	@echo "make run       -> Run Spring Boot"
	@echo "make build     -> Build project"
	@echo "make test      -> Run tests"
	@echo "make package   -> Build JAR"
	@echo ""
	@echo "make dev       -> Full dev mode"
	@echo "make reset     -> Reset environment"
	@echo ""
	@echo "make health    -> API health check"
	@echo "make db        -> DB check"
	@echo "make redis     -> Redis check"
