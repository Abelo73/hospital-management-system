# 📋 Lab Technician Dashboard & Laboratory Worklist Layout — Implementation Task Plan (`LAB_TECHNICIAN_TASKS.md`)

## 🎯 Task Goal
Implement a dedicated **Lab Technician Dashboard (`/laboratory/dashboard`)** and layout-level navigation filtering in `hms-ui` specifically designed for laboratory staff. Technicians will have streamlined access to pending lab orders, specimen barcode scanning, result entry drawers, automated reference range flags, and panic value escalation.

---

## 📌 Phase 1: Lab Technician Layout & Navigation Filtering

### Task 1.1: Create Lab Technician Route Guard (`LabTechnicianRoute.tsx`)
- [ ] **File**: `hms-ui/src/components/auth/LabTechnicianRoute.tsx`
- [ ] **Description**: Guard component ensuring only users with `LAB_TECHNICIAN`, `DOCTOR`, or `ADMIN` roles can access laboratory processing and result entry pages.
- [ ] **Behavior**: Redirect unauthorized users to `/dashboard`.

### Task 1.2: Sidebar Filtering for Lab Technician Role (`Sidebar.tsx`)
- [ ] **File**: [Sidebar.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/components/layout/Sidebar.tsx)
- [ ] **Description**: Configure navigation section filters for laboratory staff:
  - ✅ **Visible**: Dashboard, Lab Worklist, Lab Results, Test Catalog, Patients (Read-only).
  - ❌ **Hidden**: Admin Console, Role Management, User Management, Doctor Consultation Editing, Nursing Care Plans, Pharmacy Dispensing, HR Payroll, Procurement.

---

## 🧪 Phase 2: Dedicated Lab Technician Dashboard Component (`LabDashboardPage.tsx`)

### Task 2.1: Laboratory API Service (`labService.ts`)
- [ ] **File**: `hms-ui/src/services/labService.ts`
- [ ] **Endpoints to integrate**:
  - `getPendingRequests()` -> Fetch active queue of doctor-ordered laboratory tests.
  - `getLabStats()` -> Fetch total tests completed today, samples in-testing, critical values flagged.
  - `createLabResult(data)` -> Post numerical/qualitative findings.
  - `getAllTests()` -> Fetch diagnostic test catalog and reference ranges.

### Task 2.2: Lab Technician Dashboard Page Layout (`LabDashboardPage.tsx`)
- [ ] **File**: `hms-ui/src/pages/laboratory/LabDashboardPage.tsx`
- [ ] **Widgets & Components**:
  1. **Pending Diagnostic Worklist Table**: Interactive table showing patient name, test requested (CBC, Lipid Panel, Blood Glucose), priority (Stat/Routine), specimen status, and "Enter Results" button.
  2. **Panic / Critical Value Warning Banner**: Red alert card for flagged panic results requiring doctor read-back verification.
  3. **Lab Technician Stat Cards**:
     - Tests Completed Today
     - Samples Pending Collection
     - In-Testing Samples
     - Critical Panic Values Flagged
  4. **Laboratory Quick Action Bar**:
     - 🧪 `Log Specimen Collection`
     - 📝 `Enter Test Results`
     - 🔍 `Lookup Test Catalog`
     - 📊 `Record Daily QC Run`

---

## 🩺 Phase 3: Interactive Result Entry Drawers

### Task 3.1: Specimen Collection & Barcode Modal (`SpecimenModal.tsx`)
- [ ] **File**: `hms-ui/src/components/laboratory/SpecimenModal.tsx`
- [ ] **Features**: Barcode tube assignment, specimen type selection (Blood, Urine, Swab), sample collection timestamp.

### Task 3.2: Result Entry & Reference Range Auto-Flag Drawer (`LabResultEntryDrawer.tsx`)
- [ ] **File**: `hms-ui/src/components/laboratory/LabResultEntryDrawer.tsx`
- [ ] **Features**: Parameter result fields, automated `NORMAL` / `HIGH` / `LOW` / `PANIC` color badge indicators, submission to `POST /api/v1/lab-results`.

---

## 🚦 Phase 4: Route Wiring (`App.tsx`)

- [ ] **File**: [App.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/App.tsx)
- [ ] **Add Lab Technician Routes**:
  ```tsx
  <Route
    path="/laboratory/dashboard"
    element={
      <LabTechnicianRoute>
        <LabDashboardPage />
      </LabTechnicianRoute>
    }
  />
  <Route
    path="/laboratory/worklist"
    element={
      <LabTechnicianRoute>
        <LabWorklistPage />
      </LabTechnicianRoute>
    }
  />
  ```

---

## 🧪 Verification & Acceptance Criteria

1. **Lab Technician Login Verification**:
   - Log in as **`labtech`** (or account with `LAB_TECHNICIAN` role).
   - Sidebar displays Lab Worklist & Results; Admin, Doctor consultations, and Pharmacy remain hidden.
2. **Dashboard Verification**:
   - Diagnostic worklist updates in real-time.
   - Result entry drawer auto-calculates High/Low/Panic flags against test reference ranges.
