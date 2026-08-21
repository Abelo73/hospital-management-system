# 🛎️ Hospital Management System (HMS) — Receptionist Guide & Front Desk Playbook (`RECEPTIONIST.md`)

## 📋 1. Executive Overview

The Receptionist role (`RECEPTIONIST`) manages front desk operations, patient intake registration, appointment scheduling, queue token issuance, visitor check-in, and initial patient support in the Hospital Management System (HMS).

---

## 🗺️ 2. Front Desk Operations Lifecycle

```
┌─────────────────────────────────────────────────────────────────────────┐
│                               RECEPTIONIST                              │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│ 1. Patient   │  │ 2. Appointment│  │ 3. Queue     │  │ 4. Visitor & │
│ Registration │  │ Scheduling & │   │ Tokens &     │  │ Live Support │
│ & Intake     │  │ Calendar     │   │ Check-In     │  │ Assistance   │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 🛎️ 3. Core Receptionist Workflows

### 3.1 Patient Registration & Intake
* **New Patient Onboarding**: Register new walk-in or phone-in patients, capturing personal demographics, contact info, guardian info, and insurance details.
* **Patient Lookup**: Search existing patient records by National ID, Phone Number, MRN, or Name.

### 3.2 Appointment Booking & Doctor Schedules
* **Slot Allocation**: View doctor availability calendars and book consultation slots.
* **Rescheduling & Cancellations**: Handle patient rescheduling requests and notify attending physicians.

### 3.3 Queue Token Generation & Lobby Check-In
* **Check-In Processing**: Mark arriving patients as `ARRIVED` in the clinic queue.
* **Token Printing**: Issue daily queue token numbers (e.g. `APP-2026-042`) for waiting room display boards.

---

## 🔑 4. Permissions & Role Boundaries

| Permitted Action | Endpoint Scope | Description |
| :--- | :--- | :--- |
| **Register Patient** | `POST /api/v1/patients` | Create patient profile |
| **Search Patients** | `GET /api/v1/patients/search` | Lookup patient records |
| **Book Appointment** | `POST /api/v1/appointments` | Schedule consultation slot |
| **Issue Queue Token** | `POST /api/v1/appointments/check-in` | Change status to ARRIVED |

> ⚠️ **Restricted Actions**: Receptionists cannot view clinical diagnosis details, write prescriptions, view lab results, or alter system user permissions.
