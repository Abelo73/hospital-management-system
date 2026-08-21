# 👔 Hospital Management System (HMS) — HR Manager Guide & Staffing Playbook (`HR_MANAGER.md`)

## 📋 1. Executive Overview

The HR Manager role (`HR_MANAGER`) governs human resources, employee directories, staff attendance, shift scheduling, payroll processing, department management, branch locations, and staff training in the Hospital Management System (HMS).

---

## 👔 2. HR Operations Lifecycle

```
┌─────────────────────────────────────────────────────────────────────────┐
│                                HR MANAGER                               │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│ 1. Employee  │  │ 2. Attendance│   │ 3. Payroll   │  │ 4. Branches, │
│ Directory &  │  │ & Leave      │   │ Grades &     │  │ Departments  │
│ Onboarding   │  │ Requests     │   │ Compensation │  │ & Compliance │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 👥 3. Core HR Workflows

### 3.1 Employee Directory & Staff Profiles
* **Employee Management**: Create and update employee profiles, contact details, job titles, department assignments, branch allocations, and employment contracts.
* **Positions & Salary Grades**: Define hospital positions, salary bands, and compensation structures.

### 3.2 Attendance & Leave Approvals
* **Attendance Tracking**: Monitor clock-in/clock-out logs and shift attendance across departments.
* **Leave Request Workflows**: Review and approve/reject staff leave requests (Sick Leave, Annual Leave, Maternity Leave).

### 3.3 Payroll & Compensation
* **Payroll Run Generation**: Calculate monthly salaries, tax deductions, benefits, allowances, and net pay.
* **Salary Grade Scaling**: Adjust pay scales according to seniority and medical specializations.

---

## 🔑 4. Permissions & Role Boundaries

| Permitted Action | Endpoint Scope | Description |
| :--- | :--- | :--- |
| **Manage Employees** | `GET /api/v1/hr/employees` | Manage staff records |
| **Approve Leave** | `PUT /api/v1/hr/leave-requests/{id}` | Approve staff time off |
| **Process Payroll** | `POST /api/v1/hr/payroll` | Execute monthly payroll run |
| **Manage Departments**| `POST /api/v1/hr/departments` | Add/edit hospital departments |

> ⚠️ **Restricted Actions**: HR Managers cannot alter clinical diagnosis notes, issue prescriptions, view individual patient EMRs, or modify system admin security configs.
