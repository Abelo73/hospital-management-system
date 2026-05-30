# Hospital Management System (HMS)

## Overview

Hospital Management System (HMS) is a comprehensive healthcare platform designed to digitize and automate hospital operations. The system manages patients, appointments, consultations, laboratory services, pharmacy operations, billing, inventory, staff management, notifications, and reporting.

The project follows a **Modular Monolith Architecture** that is organized by business domains. This approach provides the simplicity of a monolith while maintaining clear boundaries that allow future migration to microservices.

---

# Architecture

## High-Level Architecture

```text
React Frontend
      |
Spring Boot Backend
      |
PostgreSQL Database
      |
Redis Cache
```

Future Architecture:

```text
Frontend
    |
API Gateway
    |
------------------------------------------------
| Auth | Patient | Appointment | Doctor | Lab |
------------------------------------------------
                    |
                 Kafka
                    |
             Notification
```

---

# Project Goals

* Centralized patient management
* Electronic Medical Records (EMR)
* Appointment scheduling
* Doctor consultation workflows
* Laboratory management
* Pharmacy management
* Billing and finance
* Inventory management
* Staff and HR management
* Analytics and reporting
* Secure role-based access control

---

# Technology Stack

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* PostgreSQL
* Redis
* MapStruct
* Lombok
* Maven

## Frontend

* React
* TypeScript
* Ant Design
* React Query
* React Router

## Infrastructure

* Docker
* Nginx
* GitHub Actions

---

# Project Structure

```text
src/main/java/com/hms

├── auth
├── patient
├── appointment
├── doctor
├── nursing
├── pharmacy
├── laboratory
├── billing
├── inventory
├── hr
├── notification
├── analytics
├── admin
├── common
└── HospitalApplication.java
```

---

# Module Structure Pattern

Every module follows the same structure.

```text
patient

├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── event
└── validator
```

---

# Core Modules

## Authentication Module

Responsibilities:

* Login
* Logout
* JWT Authentication
* Refresh Tokens
* User Management
* Role Management
* Permission Management

Main Entities:

* User
* Role
* Permission

---

## Patient Management Module (EMR)

Responsibilities:

* Patient Registration
* Medical History
* Allergies
* Chronic Conditions
* Visit History
* Family Information
* Insurance Information

Main Entities:

* Patient
* PatientHistory
* Allergy
* Insurance
* Guardian

---

## Appointment Module

Responsibilities:

* Doctor Scheduling
* Appointment Booking
* Rescheduling
* Cancellation
* Queue Management
* Calendar Management

Main Entities:

* Appointment
* Schedule
* TimeSlot
* QueueToken

---

## Doctor Module

Responsibilities:

* Consultation Notes
* Diagnosis
* Prescriptions
* Referrals
* Follow-up Plans

Main Entities:

* Consultation
* Diagnosis
* Prescription
* Referral

---

## Nursing Module

Responsibilities:

* Admission
* Bed Allocation
* Vital Signs
* Medication Administration
* Nursing Notes

Main Entities:

* Admission
* Bed
* Ward
* VitalRecord

---

## Laboratory Module

Responsibilities:

* Test Orders
* Sample Collection
* Result Processing
* Report Generation

Main Entities:

* LabOrder
* Sample
* LabResult

---

## Pharmacy Module

Responsibilities:

* Inventory
* Prescription Fulfillment
* Drug Dispensing
* Stock Monitoring

Main Entities:

* Medicine
* InventoryItem
* DispenseRecord

---

## Billing Module

Responsibilities:

* Invoice Generation
* Payments
* Insurance Claims
* Refunds

Main Entities:

* Invoice
* Payment
* Claim

---

## Inventory Module

Responsibilities:

* Equipment Tracking
* Consumables Tracking
* Procurement
* Supplier Management

Main Entities:

* Asset
* Consumable
* Supplier
* PurchaseOrder

---

## HR Module

Responsibilities:

* Employee Records
* Attendance
* Leave Management
* Payroll
* Shift Scheduling

Main Entities:

* Employee
* Attendance
* LeaveRequest
* Payroll

---

## Notification Module

Responsibilities:

* SMS
* Email
* In-App Notifications
* Emergency Alerts

Main Entities:

* Notification
* NotificationTemplate

---

## Analytics Module

Responsibilities:

* KPI Dashboards
* Revenue Reports
* Patient Statistics
* Operational Metrics

---

## Admin Module

Responsibilities:

* System Configuration
* Audit Logs
* Security Policies
* Department Management

Main Entities:

* AuditLog
* Department
* SystemSetting

---

# Database Design Principles

* Single PostgreSQL database initially
* UUID-based primary keys
* Soft delete for business entities
* Audit columns on all tables

Common Columns:

```sql
id
created_at
created_by
updated_at
updated_by
deleted
version
```

---

# Security Architecture

Authentication:

* JWT Access Token
* Refresh Token

Authorization:

* Role Based Access Control (RBAC)

Example Roles:

* ADMIN
* DOCTOR
* NURSE
* RECEPTIONIST
* LAB_TECHNICIAN
* PHARMACIST
* BILLING_OFFICER
* HR_MANAGER

---

# Event Driven Design

The application uses domain events internally.

Examples:

```java
PatientRegisteredEvent
AppointmentBookedEvent
ConsultationCompletedEvent
PrescriptionCreatedEvent
LabResultReadyEvent
InvoiceGeneratedEvent
PaymentCompletedEvent
```

Example Flow:

```text
Doctor Creates Prescription
            |
PrescriptionCreatedEvent
            |
    ---------------------
    |         |         |
 Pharmacy  Billing Notification
```

---

# Development Roadmap

## Phase 1

* Authentication
* User Management
* Patient Management

## Phase 2

* Appointment Scheduling
* Doctor Consultation

## Phase 3

* Laboratory Module
* Pharmacy Module

## Phase 4

* Billing Module
* Notification Module

## Phase 5

* Nursing Module
* Inventory Module

## Phase 6

* HR Module
* Analytics Module
* Admin Module

---

# Coding Standards

* Constructor Injection Only
* DTO-Based APIs
* No Entity Exposure
* Service Layer Business Logic
* Domain-Based Packaging
* Global Exception Handling
* Audit Logging

---

# Future Enhancements

* Telemedicine
* Mobile Application
* AI Diagnosis Assistance
* Multi-Hospital Support
* FHIR Integration
* HL7 Integration
* Microservice Migration
* Kafka Event Streaming

---

# Author

Hospital Management System

Enterprise Healthcare Platform
Built using Spring Boot, React, PostgreSQL, and Redis.
