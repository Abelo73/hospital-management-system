# 🏥 Hospital Management System (HMS)

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen?style=flat-square&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

**A comprehensive, enterprise-grade healthcare platform built with Spring Boot**

[Features](#-features) • [Architecture](#-architecture) • [Modules](#-modules) • [Installation](#-installation) • [API Documentation](#-api-documentation) • [Contributing](#-contributing)

</div>

---

## 📋 Overview

The Hospital Management System (HMS) is a comprehensive healthcare platform designed to digitize and automate hospital operations. This enterprise-grade system manages patients, appointments, consultations, laboratory services, pharmacy operations, billing, inventory, staff management, notifications, and reporting.

The project follows a **Modular Monolith Architecture** organized by business domains, providing the simplicity of a monolith while maintaining clear boundaries for future migration to microservices.

### 🎯 Key Objectives

- **Centralized Patient Management** - Complete patient lifecycle management with electronic medical records
- **Appointment Scheduling** - Intelligent doctor scheduling and appointment booking system
- **Clinical Operations** - Streamlined consultation, prescription, and laboratory workflows
- **Financial Management** - Comprehensive billing, invoicing, and insurance claims processing
- **Inventory Control** - Real-time tracking of pharmaceuticals, medical supplies, and equipment
- **Staff & HR** - Complete employee management with attendance, payroll, and shift scheduling
- **Analytics & Reporting** - Real-time dashboards and comprehensive business intelligence
- **Security & Compliance** - Role-based access control with audit trails and regulatory compliance

---

## 🏗️ Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     React Frontend                           │
│                   (TypeScript + Vite)                        │
└──────────────────────────┬──────────────────────────────────┘
                           │ REST API
┌──────────────────────────▼──────────────────────────────────┐
│                  Spring Boot Backend                        │
│              (Java 17 + Spring Security)                     │
├─────────────────────────────────────────────────────────────┤
│  Auth  │ Patient │ Appointment │ Doctor │ Lab │ Pharmacy   │
├─────────────────────────────────────────────────────────────┤
│  Billing │ Inventory │ Nursing │ HR │ Notification │ Admin   │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│              PostgreSQL Database + Redis Cache              │
└─────────────────────────────────────────────────────────────┘
```

### Future Microservices Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     React Frontend                           │
└──────────────────────────┬──────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    API Gateway                              │
└──────────────────────────┬──────────────────────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
┌───────▼──────┐  ┌───────▼──────┐  ┌───────▼──────┐
│  Auth Service│  │Patient Service│  │Doctor Service│
└──────────────┘  └──────────────┘  └──────────────┘
        │                  │                  │
        └──────────────────┼──────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Kafka Event Bus                            │
└─────────────────────────────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────┐
│              Notification Service                            │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Technology Stack

### Backend

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 | Core language |
| **Spring Boot** | 3.5.14 | Application framework |
| **Spring Security** | 6.x | Authentication & authorization |
| **Spring Data JPA** | 3.x | Database ORM |
| **PostgreSQL** | 16 | Primary database |
| **Redis** | 7.x | Caching & session management |
| **Flyway** | 10.x | Database migrations |
| **MapStruct** | 1.6.3 | Entity-DTO mapping |
| **Lombok** | Latest | Code generation |
| **JWT (jjwt)** | 0.12.6 | Token-based authentication |
| **MinIO** | 8.5.17 | Object storage (S3-compatible) |
| **Kafka** | 3.x | Event streaming (future) |
| **Prometheus** | Latest | Metrics collection |
| **Grafana** | Latest | Metrics visualization |

### Frontend (hms-ui)

| Technology | Version | Purpose |
|------------|---------|---------|
| **React** | 19.2.6 | UI framework |
| **TypeScript** | 6.x | Type safety |
| **Vite** | 8.x | Build tool |
| **TailwindCSS** | 4.3.0 | Styling |
| **shadcn/ui** | Latest | UI components |
| **TanStack Query** | 5.x | Data fetching |
| **React Router** | 7.x | Client routing |
| **Zustand** | 5.x | State management |

### DevOps & Infrastructure

| Technology | Purpose |
|------------|---------|
| **Docker** | Containerization |
| **Docker Compose** | Multi-container orchestration |
| **GitHub Actions** | CI/CD pipeline |
| **Maven** | Build automation |
| **Testcontainers** | Integration testing |

---

## 📦 Modules

The system is organized into 12 core modules, each following a consistent structure:

### Module Structure Pattern

```
module-name/
├── controller/          # REST API endpoints
├── service/            # Business logic
├── repository/         # Data access layer
├── entity/             # JPA entities
├── dto/                # Data Transfer Objects
├── mapper/             # Entity-DTO mapping
├── enums/              # Enumerations
├── event/              # Domain events
└── validator/          # Custom validators
```

---

### 🔐 Authentication & Authorization Module

**Overview**: Comprehensive authentication and authorization system with JWT-based stateless authentication and Role-Based Access Control (RBAC).

**Key Features**:
- User registration with automatic role assignment
- JWT token-based authentication with refresh tokens
- Multi-level approval workflow for medical professionals
- Document verification system (licenses, certificates)
- Role and permission management
- Password reset and account management
- Audit trail for all authentication events

**Main Entities**:
- `User` - User accounts with approval status
- `Role` - System roles (ADMIN, DOCTOR, NURSE, etc.)
- `Permission` - Granular permissions (44 default permissions)
- `ApprovalRequest` - Approval workflow tracking
- `UserDocument` - Professional document management

**Default Roles**:
| Role | Permissions | Description |
|------|-------------|-------------|
| ADMIN | All 44 permissions | Full system access |
| DOCTOR | Patient, Appointment, Consultation, Prescription, Laboratory, Pharmacy | Medical access |
| NURSE | Patient, Appointment, Consultation, Nursing, Laboratory | Patient care access |
| PHARMACIST | Patient, Prescription, Pharmacy, Inventory | Pharmacy operations |
| RECEPTIONIST | Patient, Appointment, Doctor | Front desk operations |
| LAB_TECHNICIAN | Patient, Laboratory, Prescription | Laboratory operations |
| BILLING_OFFICER | Patient, Billing, Appointment | Financial operations |
| HR_MANAGER | User, Role, HR, Doctor | HR operations |

**API Endpoints**:
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Token refresh
- `GET /api/approvals/my-status` - Check approval status
- `POST /api/documents/upload-my-document` - Upload verification documents

---

### 👤 Patient Management Module (EMR)

**Overview**: Complete patient lifecycle management with comprehensive electronic medical records.

**Key Features**:
- Patient registration and demographics
- Medical history tracking
- Allergy and chronic condition management
- Visit history and family information
- Insurance information management
- Medical record attachments
- Patient search and filtering

**Main Entities**:
- `Patient` - Patient demographics and contact information
- `PatientHistory` - Medical history records
- `Allergy` - Allergy information and severity
- `ChronicCondition` - Chronic disease tracking
- `Insurance` - Insurance policy details
- `Guardian` - Emergency contact and guardian information

**API Endpoints**:
- `POST /api/patients` - Register new patient
- `GET /api/patients/{id}` - Get patient details
- `GET /api/patients/search` - Search patients
- `PUT /api/patients/{id}` - Update patient information
- `POST /api/patients/{id}/allergies` - Add allergy
- `POST /api/patients/{id}/medical-history` - Add medical history

---

### 📅 Appointment Module

**Overview**: Intelligent appointment scheduling system with doctor availability management and queue handling.

**Key Features**:
- Doctor schedule management
- Appointment booking and rescheduling
- Calendar integration and availability checks
- Queue management and token system
- Appointment reminders and notifications
- Cancellation and no-show tracking
- Multi-doctor scheduling

**Main Entities**:
- `Appointment` - Appointment details and status
- `Schedule` - Doctor availability schedules
- `TimeSlot` - Available time slots
- `QueueToken` - Queue management tokens
- `AppointmentType` - Type of appointments (consultation, follow-up, emergency)

**API Endpoints**:
- `POST /api/appointments` - Book appointment
- `GET /api/appointments/available-slots` - Get available slots
- `PUT /api/appointments/{id}/reschedule` - Reschedule appointment
- `DELETE /api/appointments/{id}` - Cancel appointment
- `GET /api/appointments/doctor/{doctorId}` - Get doctor appointments

---

### 👨‍⚕️ Doctor Module

**Overview**: Clinical consultation management with diagnosis, prescriptions, and referral workflows.

**Key Features**:
- Consultation note management
- Diagnosis recording and coding
- Prescription creation and management
- Referral generation and tracking
- Follow-up planning
- Medical template support
- Consultation history

**Main Entities**:
- `Consultation` - Consultation records
- `Diagnosis` - Diagnosis information with ICD codes
- `Prescription` - Medication prescriptions
- `Referral` - Referral to specialists
- `FollowUpPlan` - Follow-up schedules
- `MedicalTemplate` - Consultation templates

**API Endpoints**:
- `POST /api/consultations` - Create consultation
- `GET /api/consultations/patient/{patientId}` - Get patient consultations
- `POST /api/consultations/{id}/diagnosis` - Add diagnosis
- `POST /api/consultations/{id}/prescriptions` - Create prescription
- `POST /api/consultations/{id}/referrals` - Create referral

---

### 🧪 Laboratory Module

**Overview**: Complete laboratory management system for test orders, sample collection, and result processing.

**Key Features**:
- Test order management
- Sample collection and tracking
- Result entry and validation
- Report generation and printing
- Test catalog management
- Quality control tracking
- Integration with external lab systems

**Main Entities**:
- `LabOrder` - Laboratory test orders
- `LabSample` - Sample information
- `LabResult` - Test results
- `LabTest` - Test catalog
- `LabReport` - Generated reports
- `QualityControl` - QC records

**API Endpoints**:
- `POST /api/laboratory/orders` - Create lab order
- `GET /api/laboratory/orders/{id}` - Get order details
- `POST /api/laboratory/samples` - Record sample collection
- `POST /api/laboratory/results` - Enter test results
- `GET /api/laboratory/reports/{id}` - Generate report

---

### 💊 Pharmacy Module

**Overview**: Pharmacy operations management including prescription fulfillment, drug dispensing, and inventory integration.

**Key Features**:
- Prescription processing and validation
- Drug dispensing management
- Drug interaction checking
- Inventory integration
- Dispensing history tracking
- Expiry date monitoring
- Controlled substance tracking

**Main Entities**:
- `Prescription` - Prescription records
- `DispenseRecord` - Dispensing history
- `Drug` - Drug catalog
- `DrugInteraction` - Interaction database
- `DispensingQueue` - Dispensing queue

**API Endpoints**:
- `GET /api/pharmacy/prescriptions/pending` - Get pending prescriptions
- `POST /api/pharmacy/dispense` - Dispense medication
- `GET /api/pharmacy/drug-interactions` - Check drug interactions
- `GET /api/pharmacy/dispensing-history` - Get dispensing history

---

### 💰 Billing Module

**Overview**: Comprehensive financial management system for invoicing, payments, and insurance claims.

**Key Features**:
- Automatic invoice generation
- Multiple payment methods (cash, card, insurance, transfer)
- Insurance claim submission and tracking
- Payment processing and reconciliation
- Refund management with approval workflow
- Financial reporting and analytics
- Patient billing portal

**Main Entities**:
- `Invoice` - Invoice records with line items
- `InvoiceLineItem` - Detailed service charges
- `Payment` - Payment records
- `InsuranceProvider` - Insurance company information
- `InsuranceClaim` - Insurance claim tracking
- `Refund` - Refund records

**API Endpoints**:
- `POST /api/billing/invoices` - Create invoice
- `POST /api/billing/payments` - Process payment
- `POST /api/billing/insurance/claims` - Submit insurance claim
- `GET /api/billing/reports/daily-revenue` - Daily revenue report
- `GET /api/billing/reports/aging` - Aging report

---

### 📦 Inventory Module

**Overview**: Complete inventory management system for pharmaceuticals, medical supplies, and equipment.

**Key Features**:
- Item registration and categorization
- Stock level monitoring with reorder points
- Batch/lot tracking with expiry management
- Procurement and purchase order management
- Supplier management and performance tracking
- Department requests and stock distribution
- Expiry alerts and disposal management
- Multi-location support

**Main Entities**:
- `Item` - Inventory items with specifications
- `Stock` - Stock levels by location
- `Batch` - Batch/lot tracking
- `Supplier` - Supplier information
- `PurchaseOrder` - Purchase orders
- `DepartmentRequest` - Department stock requests
- `ExpiryAlert` - Expiry notifications

**API Endpoints**:
- `POST /api/inventory/items` - Register item
- `GET /api/inventory/stock` - Get stock status
- `POST /api/inventory/procurement/purchase-orders` - Create purchase order
- `POST /api/inventory/distribution/requests` - Create department request
- `GET /api/inventory/expiry/alerts` - Get expiry alerts

---

### 🏥 Nursing Module

**Overview**: Comprehensive nursing care management including patient monitoring, care plans, and documentation.

**Key Features**:
- Patient admission and bed allocation
- Vital signs monitoring and tracking
- Medication administration records
- Nursing care plans
- Nursing notes and documentation
- Fluid balance monitoring
- Wound care management
- Incident reporting
- Shift management

**Main Entities**:
- `Admission` - Patient admissions
- `Bed` - Bed management
- `VitalSign` - Vital signs records
- `MedicationAdministration` - Medication administration
- `NursingCarePlan` - Care plans
- `NursingNote` - Nursing documentation
- `FluidBalance` - Fluid balance tracking
- `WoundCare` - Wound care records
- `IncidentReport` - Incident documentation

**API Endpoints**:
- `POST /api/nursing/admissions` - Admit patient
- `POST /api/nursing/vital-signs` - Record vital signs
- `POST /api/nursing/medication-administrations` - Record medication administration
- `POST /api/nursing/care-plans` - Create care plan
- `POST /api/nursing/notes` - Add nursing note

---

### 👥 HR Module

**Overview**: Complete human resources management system for hospital staff.

**Key Features**:
- Employee records management
- Attendance tracking
- Leave management and approval
- Payroll processing
- Shift scheduling
- Performance evaluation
- Training and certification tracking

**Main Entities**:
- `Employee` - Employee records
- `Attendance` - Attendance records
- `LeaveRequest` - Leave requests
- `Payroll` - Payroll records
- `Shift` - Shift schedules
- `PerformanceReview` - Performance evaluations

**API Endpoints**:
- `POST /api/hr/employees` - Add employee
- `GET /api/hr/attendance` - Get attendance records
- `POST /api/hr/leave-requests` - Submit leave request
- `POST /api/hr/payroll` - Process payroll
- `GET /api/hr/shifts` - Get shift schedules

---

### 🔔 Notification Module

**Overview**: Multi-channel notification system for alerts, reminders, and communications.

**Key Features**:
- Email notifications
- SMS notifications
- In-app notifications
- Emergency alerts
- Notification templates
- Notification history
- Delivery tracking

**Main Entities**:
- `Notification` - Notification records
- `NotificationTemplate` - Notification templates
- `NotificationLog` - Delivery logs

**API Endpoints**:
- `POST /api/notifications/send` - Send notification
- `GET /api/notifications/my-notifications` - Get user notifications
- `POST /api/notifications/templates` - Create template
- `GET /api/notifications/history` - Get notification history

---

### 📊 Analytics Module

**Overview**: Business intelligence and analytics platform for hospital operations.

**Key Features**:
- KPI dashboards
- Revenue reports
- Patient statistics
- Operational metrics
- Custom report generation
- Data visualization
- Export capabilities

**API Endpoints**:
- `GET /api/analytics/kpi` - Get KPIs
- `GET /api/analytics/revenue` - Revenue analytics
- `GET /api/analytics/patient-statistics` - Patient statistics
- `POST /api/analytics/custom-reports` - Generate custom report

---

### ⚙️ Admin Module

**Overview**: System administration and configuration management.

**Key Features**:
- System configuration
- Department management
- Audit log viewing
- Security policy management
- System monitoring
- Backup and restore

**Main Entities**:
- `SystemSetting` - System configurations
- `Department` - Department information
- `AuditLog` - Audit trail
- `SecurityPolicy` - Security policies

**API Endpoints**:
- `GET /api/admin/settings` - Get system settings
- `PUT /api/admin/settings` - Update settings
- `GET /api/admin/audit-logs` - View audit logs
- `POST /api/admin/departments` - Create department

---

## 🚀 Installation

### Prerequisites

- **Java 17** or higher
- **Maven 3.8+** (or use the included wrapper)
- **Docker** and **Docker Compose**
- **PostgreSQL 16** (or use Docker)
- **Redis 7** (or use Docker)
- **Node.js 18+** (for frontend)

### Quick Start with Docker

1. **Clone the repository**
```bash
git clone https://github.com/Abelo73/hospital-management-system.git
cd hospital-management-system
```

2. **Start infrastructure services**
```bash
cd docker
make up
```

This starts:
- PostgreSQL (port 5432)
- Redis (port 6380)
- MinIO (ports 9002, 9003)
- Prometheus (port 9090)
- Grafana (port 3001)
- MailHog (port 8025)

3. **Configure environment variables**
```bash
cp .env.example .env
# Edit .env with your configuration
```

4. **Run the application**
```bash
./mvnw spring-boot:run
```

Or using Maven directly:
```bash
mvn spring-boot:run
```

5. **Access the application**
- **API**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **Health Check**: http://localhost:8080/api/actuator/health
- **Grafana**: http://localhost:3001 (admin/admin)
- **Prometheus**: http://localhost:9090

### Frontend Setup (hms-ui)

1. **Navigate to frontend directory**
```bash
cd ../hms-ui
```

2. **Install dependencies**
```bash
pnpm install
```

3. **Configure environment**
```bash
cp .env.example .env
# Edit .env with API URL
```

4. **Run development server**
```bash
pnpm dev
```

5. **Access frontend**
- **Frontend**: http://localhost:5173

### Production Build

**Backend**:
```bash
./mvnw clean package
java -jar target/hospital-management-system-0.0.1-SNAPSHOT.jar
```

**Frontend**:
```bash
pnpm build
pnpm preview
```

---

## 📚 API Documentation

### Swagger UI

Once the application is running, access the interactive API documentation at:
```
http://localhost:8080/api/swagger-ui.html
```

### Authentication

Most endpoints require JWT authentication. Include the token in the Authorization header:

```http
Authorization: Bearer {access_token}
```

### Default Credentials

```
Username: admin
Password: Admin@123
Email: admin@hospital.com
Role: ADMIN
```

### Example API Calls

**Login**:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin@123"
  }'
```

**Create Patient**:
```bash
curl -X POST http://localhost:8080/api/patients \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1990-01-15",
    "gender": "MALE",
    "phoneNumber": "+1234567890",
    "email": "john.doe@example.com"
  }'
```

---

## 🧪 Testing

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Class
```bash
./mvnw test -Dtest=AuthServiceTest
```

### Run with Coverage
```bash
./mvnw test jacoco:report
```

### Integration Tests
```bash
./mvnw verify
```

---

## 📊 Monitoring

### Prometheus Metrics

Access metrics at:
```
http://localhost:8080/api/actuator/prometheus
```

### Grafana Dashboards

Access Grafana at:
```
http://localhost:3001
```

Default credentials: `admin/admin`

### Health Check

```bash
curl http://localhost:8080/api/actuator/health
```

---

## 🔒 Security

### Authentication
- JWT-based stateless authentication
- Access tokens (15 minutes expiration)
- Refresh tokens (7 days expiration)
- BCrypt password encryption

### Authorization
- Role-Based Access Control (RBAC)
- Method-level security with `@PreAuthorize`
- 44 granular permissions across modules
- 8 predefined roles

### Security Best Practices
- SQL injection prevention via JPA
- XSS protection via input validation
- CSRF protection enabled
- Secure headers configuration
- Audit logging for sensitive operations

---

## 🗄️ Database

### Migrations

Database migrations are managed by Flyway. Migrations are located in:
```
src/main/resources/db/migration/
```

### Connection Details

**Development**:
```
URL: jdbc:postgresql://localhost:5432/hospital_db
Username: hospital_user
Password: hospital_password
```

### Backup and Restore

**Backup**:
```bash
docker exec postgres pg_dump -U hospital_user hospital_db > backup.sql
```

**Restore**:
```bash
docker exec -i postgres psql -U hospital_user hospital_db < backup.sql
```

---

## 🤝 Contributing

We welcome contributions! Please follow these guidelines:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit your changes** (`git commit -m 'Add amazing feature'`)
4. **Push to the branch** (`git push origin feature/amazing-feature`)
5. **Open a Pull Request**

### Coding Standards

- Follow Java naming conventions
- Use constructor injection
- Write unit tests for new features
- Update documentation
- Ensure all tests pass

---

## 📝 Development Roadmap

### ✅ Completed
- [x] Infrastructure setup (Docker, PostgreSQL, Redis)
- [x] Authentication & Authorization module
- [x] User management with approval workflow
- [x] Role and permission system
- [x] Document verification system
- [x] Patient management module
- [x] Appointment scheduling module
- [x] Doctor consultation module
- [x] Laboratory module
- [x] Billing module
- [x] Inventory module
- [x] Nursing module

### 🚧 In Progress
- [ ] Pharmacy module integration
- [ ] Notification module
- [ ] HR module
- [ ] Analytics module

### 📋 Planned
- [ ] Telemedicine features
- [ ] Mobile app (React Native)
- [ ] AI-powered diagnosis assistance
- [ ] FHIR/HL7 integration
- [ ] Microservices migration
- [ ] Advanced analytics with ML

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Team

**Backend Developer**: Abel Adisu
- [GitHub](https://github.com/Abelo73)
- [LinkedIn](https://linkedin.com/in/abel-apiso)

---

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- The open-source community
- Healthcare professionals for domain expertise

---

## 📞 Support

For support, email support@hospital.com or open an issue in the repository.

---

<div align="center">

**Built with ❤️ for Healthcare**

[⬆ Back to Top](#-hospital-management-system-hms)

</div>
