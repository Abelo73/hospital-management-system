# 🏥 Hospital Management System (HMS) — Project Setup Guide

This document explains how to set up, run, and understand the Hospital Management System (HMS) project from scratch.

---

# 📌 1. Project Overview

The Hospital Management System is a backend platform built using Spring Boot to manage core hospital operations such as:

- Patient registration and management
- Appointment scheduling
- Doctor and staff management
- Medical records tracking
- Billing and invoicing
- File storage for medical documents
- Notification system (Email/SMS)
- Monitoring and observability

The system is designed as a **modular monolith initially**, with future migration into **microservices architecture**.

---

# 🧱 2. Tech Stack

## Backend
- Java 17
- Spring Boot 3.x
- Spring Web (REST APIs)
- Spring Data JPA
- Spring Security (JWT planned)
- Hibernate ORM

## Database
- PostgreSQL 16

## Caching
- Redis

## File Storage
- MinIO (S3-compatible storage)

## Messaging (Future / optional)
- Kafka

## Observability
- Prometheus
- Grafana
- Spring Boot Actuator

## Email Service
- MailHog (development email testing)

## Build Tool
- Maven Wrapper (`./mvnw`)

## Containerization
- Docker
- Docker Compose

---

# 📁 3. Project Structure

```text
hospital-management-system/
│
├── docker/                     # Infrastructure configs
│   ├── prometheus/
│   ├── start.sh
│   ├── stop.sh
│   └── Makefile
│
├── src/
│   ├── main/
│   │   ├── java/com/act/hospitalmanagementsystem/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   ├── dto/
│   │   │   ├── security/
│   │   │   └── HospitalManagementSystemApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.yaml
│   │       └── db/migration/   # (Flyway migrations - future)
│   │
│   └── test/
│
├── docker-compose.yml
├── pom.xml
├── README.md
└── TASKS.md
🚀 4. How to Run the Project
Step 1: Clone repository
git clone https://github.com/Abelo73/hospital-management-system.git
cd hospital-management-system
Step 2: Start infrastructure (Docker)

This starts all required services:

make up

Services started:

PostgreSQL
Redis
MinIO
Prometheus
Grafana
MailHog
Step 3: Run backend application
./mvnw spring-boot:run

OR via IDE (IntelliJ / VS Code)

🌐 5. Service URLs
Service	URL
Backend API	http://localhost:8080/api
Swagger UI	http://localhost:8080/api/swagger-ui.html
PostgreSQL	localhost:5432
Redis	localhost:6380
MinIO API	http://localhost:9002
MinIO Console	http://localhost:9003
Grafana	http://localhost:3001
Prometheus	http://localhost:9090
MailHog	http://localhost:8025
⚙️ 6. Environment Configuration

All configurations are located in:

src/main/resources/application.yaml

Key configurations:

Database connection (PostgreSQL)
Redis cache settings
JWT configuration
File storage (MinIO)
Mail configuration
Logging levels
Actuator monitoring
🐳 7. Docker Infrastructure
Start all services
make up
Stop all services
make down
View logs
docker compose logs -f
🔐 8. Security (Current State)
Spring Security is enabled
Default authentication is active (development mode)
JWT authentication is planned but not yet implemented
📊 9. Monitoring

The system includes:

Prometheus for metrics collection
Grafana for visualization
Spring Boot Actuator for health endpoints
Health Check
http://localhost:8080/api/actuator/health
📦 10. Development Workflow

Recommended workflow:

Start infrastructure → make up
Run backend → ./mvnw spring-boot:run
Develop modules in order:
Patient Module
Appointment System
Doctor Module
Billing Module
Security Layer
🧪 11. Testing

Run tests using:

./mvnw test
📌 12. Current Project Status

✔ Infrastructure setup completed
✔ Docker environment ready
✔ Database connected
✔ Redis configured
✔ Monitoring stack running
❌ Business modules not yet implemented

🚀 13. Next Development Phase

The next steps are:

Phase 1: Core Domain
Patient Management
Doctor Management
Appointment System
Phase 2: Clinical Operations
Consultation
Prescription
Laboratory
Phase 3: Financial System
Billing
Invoicing
Insurance
Phase 4: Security & Scaling
JWT Authentication
Role-based access control
Microservices migration (optional)
👨‍💻 Author

Abel Adisu
Backend Developer | Spring Boot | Microservices Enthusiast


---

# 🚀 Next step (recommended)

Now your project is **portfolio-grade already**.

Next I can help you build:

### 👉 Patient Module (REAL enterprise design)
- DB schema
- REST APIs
- Service layer
- DTO + validation
- Exception handling
- Clean architecture

Just say:

👉 **":contentReference[oaicite:0]{index=0}"**