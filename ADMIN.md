# 🛡️ Hospital Management System (HMS) — Administrator Guide & Configuration Playbook

## 📋 1. Executive Overview

The Administrator role (`ADMIN`) is the central authority in the Hospital Management System (HMS). Administrators maintain operational readiness, security compliance, user privilege governance, system configuration, and infrastructure health across the enterprise healthcare platform.

This guide outlines all administrative duties, configuration management responsibilities, user approval workflows, monitoring procedures, and recommended operational best practices.

---

## 🗺️ 2. Admin Responsibilities & Task Matrix

```
┌─────────────────────────────────────────────────────────────────────────┐
│                      HOSPITAL SYSTEM ADMINISTRATOR                      │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│  1. System   │  │  2. User     │   │ 3. Security  │  │ 4. Admin &   │
│ Config &     │  │ Governance   │   │ & Audit      │  │ Operational  │
│ Features     │  │ & Approvals  │   │ Compliance   │  │ Monitoring   │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 🛠️ 3. System Configuration & Parameter Management

The Administrator is responsible for managing system-wide behavior without requiring source code rebuilds.

### 3.1 Global Application Parameters
Configurations are stored in the database (`SystemConfig` entity) and `application.yaml` / `.env`.

* **Hospital Metadata**: Official facility name, registration code, address, primary phone, emergency hotline.
* **System Identifiers & Prefixes**:
  * Patient ID Prefix: `PAT` (e.g., `PAT-2026-00001`)
  * Appointment Token Prefix: `APP`
  * Consultation Prefix: `CON`
  * Invoice/Billing Prefix: `INV`
* **File Storage Limits**:
  * Max file upload size: `50MB` (Medical reports, imaging, verification licenses).
  * Max HTTP payload size: `100MB`.
  * Allowed document mime-types: PDF, PNG, JPEG.

### 3.2 Dynamic Feature Flags & Toggles
Administrators can enable or disable functional modules on-demand:
* `FEATURE_TELEMEDICINE`: Enable/disable remote video consultation modules.
* `FEATURE_KAFKA_STREAMING`: Toggle event bus streaming.
* `FEATURE_SMS_NOTIFICATIONS`: Toggle automated patient SMS reminders.
* `FEATURE_ONLINE_BILLING_PORTAL`: Toggle public patient billing portal.

### 3.3 Cache & Session Controls
* **JWT Access Token Validity**: Default `3600000 ms` (1 hour).
* **JWT Refresh Token Validity**: Default `604800000 ms` (7 days).
* **Redis Cache Expiration**: Manage TTL for session tokens and API responses.

---

## 👥 4. User Access, Role & Approval Governance

### 4.1 User Lifecycle Management
The Admin manages all system accounts across 8 default operational roles:
`ADMIN`, `DOCTOR`, `NURSE`, `PHARMACIST`, `RECEPTIONIST`, `LAB_TECHNICIAN`, `BILLING_OFFICER`, `HR_MANAGER`.

* **Account Actions**:
  * **Create Internal Users**: Provision initial accounts for administrative and operational staff.
  * **Disable/Enable Accounts**: Instantly revoke system access for departed staff.
  * **Lock/Unlock Accounts**: Unlock accounts triggered by consecutive failed login attempts.
  * **Password Resets**: Force password resets for users when credentials are compromised.

### 4.2 Medical Staff Approval & Document Verification Workflow
To prevent unauthorized access to Electronic Medical Records (EMR), self-registered doctors and nurses enter an **Approval Workflow**:

```
Registration ──> Pending Verification ──> Doc Upload ──> Admin Approval ──> Active User
```

1. **Review Pending Registrations**:
   * Navigate to **Admin Console -> Staff Approvals**.
   * Endpoint: `GET /api/v1/approvals/pending`
2. **Inspect Professional Documentation**:
   * Check uploaded medical licenses, state board certificates, and photo IDs stored in MinIO.
   * Endpoint: `GET /api/v1/documents/user/{userId}`
3. **Approve or Reject Account**:
   * **Approve**: Assigns official role permissions (`DOCTOR`, `NURSE`) and sets status to `APPROVED`.
   * **Reject**: Requires entering a explicit rejection reason (e.g., *"Expired License"*). Status set to `REJECTED`.

### 4.3 Permission Synchronization & Matrix Governance
HMS enforces granular control with **52 system permissions**.
* Admin must periodically review and synchronize role permissions via `RoleController` or the `DataInitializer` tool.
* Ensure non-clinical staff (e.g., Receptionists, Billing Officers) do NOT possess clinical diagnosis or lab result entry permissions.

---

## 🔒 5. Security, Compliance & Audit Control

### 5.1 Audit Trail & User Activity Logs
All sensitive actions are recorded in the `AuditLog` database table for HIPAA and regulatory compliance.

* **Audited Actions**:
  * Authentication (Logins, Logouts, Failed Attempts, Token Refresh)
  * Patient EMR record creations and modifications
  * Prescription issuance and pharmacy dispensing
  * Financial refunds and invoice adjustments
  * Admin configuration changes
* **Admin Log Inspection Endpoint**: `GET /api/v1/admin/audit-logs`
* **Filtering Capabilities**: Search by `userId`, `actionType`, `entityType`, `dateRange`, or `ipAddress`.

### 5.2 Rate Limiting & Network Threat Mitigation
* Rate limiting filter (`RateLimitFilter`) protects sensitive endpoints (e.g., `/api/v1/auth/login`).
* Admin monitors IP access logs for brute-force patterns or unusual request bursts.

### 5.3 CORS & Allowed Origins Management
Maintain approved domain origins in `.env`:
```env
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173,http://localhost:5174
```

---

## 🛢️ 6. Infrastructure & Database Operations

### 6.1 Database Migration & Schema Maintenance
HMS uses **Flyway** for database migrations (`db/migration/`).

* **Check Migration Status**: Verify database version matches application baseline (e.g., Schema version `12`).
* **Backup Execution**:
  * Execute PostgreSQL dump prior to major system updates:
  ```bash
  docker exec -t hms-postgres pg_dump -U postgres hospital_db > backup_$(date +%Y%m%d).sql
  ```
* **Restore Execution**:
  ```bash
  cat backup_file.sql | docker exec -i hms-postgres psql -U postgres -d hospital_db
  ```

### 6.2 Object Storage (MinIO) Bucket Governance
* Storage Bucket: `hospital-files`
* Console Access: `http://localhost:9003` (Credentials: `minioadmin` / `minioadmin`)
* Admin tasks include setting storage quota alerts, lifecycle eviction rules for temporary exports, and verifying backup replication.

### 6.3 Redis Cache Flush & Maintenance
When system configuration or permission matrices are updated, clearing stale Redis caches may be required:
```bash
docker exec -it hms-redis redis-cli FLUSHALL
```

---

## 📊 7. Observability & System Health Dashboard

Administrators oversee system performance using integrated telemetry metrics:

| Tool / Interface | URL / Command | Admin Purpose |
| :--- | :--- | :--- |
| **Spring Actuator** | `http://localhost:8080/api/v1/actuator` | Application internal state |
| **Prometheus** | `http://localhost:9090` | Time-series metrics collection |
| **Grafana** | `http://localhost:3001` | Visual health & memory dashboards |
| **MailHog** | `http://localhost:8025` | Email notification log inspection |
| **pgAdmin** | `http://localhost:5050` | Direct database query & management |

---

## 📅 8. Administrator Routine Playbook

### ☀️ Daily Tasks
1. Check **Staff Approval Queue** and process pending doctor/nurse verification documents.
2. Review **Global Error Logs** for unhandled exceptions or connection timeouts.
3. Verify backup execution and MinIO storage availability.

### 📅 Weekly Tasks
1. Review **Audit Logs** for suspicious access patterns or unauthorized attempts.
2. Inspect database performance (connection pool usage in HikariPool).
3. Monitor system disk usage and Redis memory footprint.

### 🗓️ Monthly / Quarterly Tasks
1. Perform a **Disaster Recovery (DR) Drill** (restore database backup to test environment).
2. Audit active user accounts and revoke privileges for inactive staff.
3. Review and update CORS allowed origins and security secrets.

---

## 💡 9. Recommended Admin Suggestions & Best Practices

1. **🔐 Enforce Multi-Factor Authentication (MFA)**
   * *Suggestion*: Implement TOTP/MFA (Google Authenticator) specifically for `ADMIN` and `DOCTOR` accounts to prevent identity takeover.

2. **🔔 Automated Slack / Email Alerts for Critical Events**
   * *Suggestion*: Configure Spring Boot Actuator alerts or Prometheus Alertmanager to notify Admin immediately on:
     * High CPU/Memory usage (>85%)
     * Database connection pool exhaustion
     * Multiple failed admin login attempts

3. **📜 Immutable Audit Log Archival**
   * *Suggestion*: Periodically stream audit logs to an external append-only cloud bucket (e.g., AWS S3 Object Lock or cold storage) for HIPAA compliance.

4. **⚡ Environment Secret Rotation Policy**
   * *Suggestion*: Rotate `JWT_SECRET` and database passwords every 90 days. Store credentials in a vault solution (HashiCorp Vault or AWS Secrets Manager) instead of plain-text `.env` files in production.

5. **🧪 Staging Testing Before Schema Migration**
   * *Suggestion*: Always test Flyway migration scripts on a staging clone before executing `docker compose up` on production database.
