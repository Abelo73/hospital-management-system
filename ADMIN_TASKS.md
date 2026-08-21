# 📋 Admin Dashboard & Layout-Level Visibility — Implementation Task Plan (`ADMIN_TASKS.md`)

## 🎯 Task Goal
Implement a dedicated **Admin Dashboard** and enforce **Layout-Level Role-Based Access Control (RBAC)** in the React frontend (`hms-ui`). Un-authorized menu items, stats, and routes will be dynamically hidden based on the user's active role (`ADMIN`, `DOCTOR`, `NURSE`, `HR_MANAGER`, etc.).

---

## 📌 Phase 1: Layout-Level Role-Based Hiding & Protection

### Task 1.1: Create Role-Based Route Guard (`AdminRoute.tsx` & `RoleRoute.tsx`)
- [ ] **File**: `hms-ui/src/components/auth/AdminRoute.tsx`
- [ ] **Description**: Create a dedicated higher-order route guard that checks if `user.roles.includes('ADMIN')`.
- [ ] **Behavior**: If authenticated but not an Admin, redirect to `/dashboard` with an error toast ("Access Denied: Admin privileges required").

### Task 1.2: Layout Navigation Filtering (`Sidebar.tsx`)
- [ ] **File**: [Sidebar.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/components/layout/Sidebar.tsx)
- [ ] **Description**: Update `sections` navigation schema to filter entire sections and items based on role flags (`isAdmin`, `isDoctor`, `isNurse`, `isHR`, `isPharmacist`).
- [ ] **Implementation Logic**:
  ```typescript
  const isAdmin = user?.roles?.includes('ADMIN');
  const isDoctor = user?.roles?.includes('DOCTOR');
  const isNurse = user?.roles?.includes('NURSE');
  const isHR = user?.roles?.includes('HR_MANAGER');

  // Filter sections dynamically:
  const allowedSections = sections
    .map(section => ({
      ...section,
      items: section.items.filter(item => !item.roles || item.roles.some(r => user?.roles?.includes(r)))
    }))
    .filter(section => section.items.length > 0);
  ```

### Task 1.3: TopNav Role Badge & Action Hiding (`TopNav.tsx`)
- [ ] **File**: `hms-ui/src/components/layout/TopNav.tsx`
- [ ] **Description**: Display role badges in the header profile menu. Hide administrative quick-action shortcuts from non-admin users.

---

## 🖥️ Phase 2: Dedicated Admin Dashboard (`/admin/dashboard`)

The Admin Dashboard is distinct from clinical dashboards, focusing on infrastructure health, staff approvals, system configuration, user metrics, and audit activities.

### Task 2.1: Admin API Service (`adminService.ts`)
- [ ] **File**: `hms-ui/src/services/adminService.ts`
- [ ] **Description**: Create API integration endpoints for administrative functions:
  - `getSystemHealth()` -> Fetch Actuator database, Redis, MinIO, and Disk health.
  - `getAdminStats()` -> Fetch pending approvals count, total users count, active sessions, system alerts count.
  - `getSystemConfigs()` / `updateSystemConfig(key, value)` -> Manage feature flags and limits.
  - `getRecentAuditLogs()` -> Fetch top 10 recent administrative/security events.
  - `triggerDatabaseBackup()` / `clearRedisCache()` -> One-click maintenance utilities.

### Task 2.2: Admin Dashboard Page Component (`AdminDashboardPage.tsx`)
- [ ] **File**: `hms-ui/src/pages/admin/AdminDashboardPage.tsx`
- [ ] **Widgets & Layout**:
  1. **System Health Bar**: Live indicators for PostgreSQL (`UP`), Redis (`UP`), MinIO (`UP`), MailHog (`UP`).
  2. **Pending Staff Approvals Card**: Alert card displaying doctor/nurse registrations awaiting verification with direct link to `/approvals`.
  3. **User Governance Stat Cards**:
     - Total Registered Users
     - Active Medical Staff (Doctors & Nurses)
     - Suspended Accounts
     - Active JWT Sessions
  4. **Feature Toggles Panel**: Switches to instantly toggle feature flags:
     - `FEATURE_TELEMEDICINE`
     - `FEATURE_KAFKA_STREAMING`
     - `FEATURE_MAINTENANCE_MODE`
  5. **Recent Security & Audit Trail Feed**: Interactive stream displaying latest log entries (user actions, IP addresses, HTTP status codes).
  6. **Quick Admin Action Bar**: Buttons for "Backup Database", "Flush Redis Cache", "Export Audit Trail".

---

## ⚙️ Phase 3: Dedicated Admin Management Sub-Pages

### Task 3.1: System Configuration & Feature Flags Page (`SystemConfigPage.tsx`)
- [x] **File**: `hms-ui/src/pages/admin/SystemConfigPage.tsx`
- [x] **Description**: Page to view and edit system-wide settings, application limits (`50MB` max upload size), ID prefixes, and CORS origins.

### Task 3.2: System Audit Logs Viewer (`AuditLogsPage.tsx`)
- [x] **File**: `hms-ui/src/pages/admin/AuditLogsPage.tsx`
- [x] **Description**: Searchable and filterable table displaying full audit trail logs (Action, User, Entity ID, Timestamp, IP Address, HTTP Method).

---

## 🔐 Phase 5: Role & Permission Management (`/roles` & `/users`)

### Task 5.1: Interactive Role Creation & Permission Matrix Modal (`RoleModal.tsx`)
- [ ] **File**: `hms-ui/src/pages/roles/components/RoleModal.tsx`
- [ ] **Description**: Create an interactive modal/drawer for creating custom roles and editing permission assignments:
  - Role Name input (e.g. `CHIEF_MEDICAL_OFFICER`, `RADIOLOGIST`).
  - Description textarea.
  - Granular Permission Matrix grouped by domain categories:
    * **User & Access**: `USER_READ`, `USER_WRITE`, `USER_DELETE`
    * **Patient Management**: `PATIENT_READ`, `PATIENT_WRITE`, `PATIENT_DELETE`
    * **Medical Services**: `MEDICAL_RECORD_READ`, `MEDICAL_RECORD_WRITE`, `CONSULTATION_READ`, `CONSULTATION_WRITE`
    * **Nursing Care**: `NURSING_READ`, `NURSING_WRITE`
    * **Pharmacy**: `PHARMACY_READ`, `PHARMACY_WRITE`, `PRESCRIPTION_READ`, `PRESCRIPTION_WRITE`
    * **Laboratory**: `LABORATORY_READ`, `LABORATORY_WRITE`
    * **Billing & Finance**: `BILLING_READ`, `BILLING_WRITE`
    * **Human Resources**: `HR_READ`, `HR_WRITE`
    * **System Admin**: `ADMIN_READ`, `ADMIN_WRITE`, `ROLE_READ`, `ROLE_WRITE`, `PERMISSION_READ`
  - Integration with `POST /api/v1/roles`, `PUT /api/v1/roles/{id}`, `POST /api/v1/roles/{id}/permissions/{name}`, and `DELETE /api/v1/roles/{id}/permissions/{name}`.

### Task 5.2: Staff Registration Approvals & Role Assignment (`ApprovalsPage.tsx`)
- [ ] **File**: [ApprovalsPage.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/pages/approvals/ApprovalsPage.tsx)
- [ ] **Description**: Dedicated approval queue for newly registered staff accounts (`DOCTOR`, `NURSE`, `PHARMACIST`, etc.):
  - View pending staff verification requests (`PENDING`).
  - Approve or Reject staff requests with optional rejection reason.
  - Modify assigned user roles before final account activation.
  - Enable or Disable staff accounts instantly.

---

## 🚦 Phase 4: App Route Wiring (`App.tsx`)

- [x] **File**: [App.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/App.tsx)
- [x] **Route Updates**:
  ```tsx
  {/* Admin Protected Routes */}
  <Route path="/admin/dashboard" element={<AdminRoute><AdminDashboardPage /></AdminRoute>} />
  <Route path="/admin/config" element={<AdminRoute><SystemConfigPage /></AdminRoute>} />
  <Route path="/admin/audit-logs" element={<AdminRoute><AuditLogsPage /></AdminRoute>} />
  <Route path="/users" element={<AdminRoute><UsersPage /></AdminRoute>} />
  <Route path="/roles" element={<AdminRoute><RolesPage /></AdminRoute>} />
  <Route path="/approvals" element={<AdminRoute><ApprovalsPage /></AdminRoute>} />
  ```

---

## 🧪 Verification & Acceptance Criteria

1. **Role Visibility Check**:
   - Log in as **`nurse`**: Administration, Users, Roles, and Admin Dashboard options must be **hidden** from the Sidebar. Navigating directly to `/admin/dashboard` redirects to `/dashboard` with an error message.
   - Log in as **`admin`**: Full Admin Dashboard, Users, Roles, Approvals, System Config, and Audit Log links are **visible**.
2. **Admin Dashboard Functionality**:
   - System health indicators dynamically show green (`UP`) status.
   - Pending verification count accurately matches database approval requests.
   - Dynamic feature toggles correctly send requests to `/api/v1/admin/config`.
3. **Role & Permission Management**:
   - Admin can create a custom role, assign granular permissions, and modify existing role permission matrices in real time.

