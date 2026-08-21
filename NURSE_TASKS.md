# 📋 Nurse Dashboard & Nursing Care Layout — Implementation Task Plan (`NURSE_TASKS.md`)

## 🎯 Task Goal
Implement a dedicated **Nurse Dashboard (`/nurse/dashboard`)** and layout-level filtering in `hms-ui` specifically designed for nursing staff. Nurses will have quick access to scheduled medication passes (MAR), vital sign entry drawers, abnormal vital sign alert banners, fluid balance charting, and shift handovers.

---

## 📌 Phase 1: Nurse Layout & Navigation Filtering

### Task 1.1: Create Nurse Route Guard (`NurseRoute.tsx`)
- [ ] **File**: `hms-ui/src/components/auth/NurseRoute.tsx`
- [ ] **Description**: Guard component ensuring only users with `NURSE`, `DOCTOR`, or `ADMIN` roles can access nursing care routes.
- [ ] **Behavior**: Redirect unauthorized users to `/dashboard`.

### Task 1.2: Sidebar Filtering for Nurse Role (`Sidebar.tsx`)
- [ ] **File**: [Sidebar.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/components/layout/Sidebar.tsx)
- [ ] **Description**: Configure navigation section filters for nurses:
  - ✅ **Visible**: Dashboard, Patients, Care Plans, Vital Signs, Nursing Tasks, MAR Medications, Nursing Notes, Incident Reports, Assessments, Wound Care, Fluid Balance, Shifts, Appointments.
  - ❌ **Hidden**: Admin Console, Role Management, User Management, HR Payroll, Inventory Procurement, Financial Billing.

---

## 👩‍⚕️ Phase 2: Dedicated Nurse Dashboard Component (`NurseDashboardPage.tsx`)

### Task 2.1: Nurse API Service (`nurseService.ts`)
- [ ] **File**: `hms-ui/src/services/nurseService.ts`
- [ ] **Endpoints to integrate**:
  - `getDueMedications()` -> Fetch scheduled medication doses due within the next 2 hours across assigned ward beds.
  - `getAbnormalVitals()` -> Fetch patients with recent abnormal vital sign alerts (e.g. fever, high BP, low SpO2).
  - `getNurseStats()` -> Fetch total ward patients, doses administered today, pending nursing tasks.
  - `getShiftHandoverNotes()` -> Fetch latest SBAR shift notes.

### Task 2.2: Nurse Dashboard Page Layout (`NurseDashboardPage.tsx`)
- [ ] **File**: `hms-ui/src/pages/nursing/NurseDashboardPage.tsx`
- [ ] **Widgets & Components**:
  1. **Abnormal Vitals Warning Banner**: Red alert card highlighting patients with critical vital signs requiring immediate nursing assessment.
  2. **Upcoming Medication Pass Schedule (MAR)**: Interactive table showing due medications, dosage, route, patient bed number, and "Record Given" button.
  3. **Nurse Stat Cards**:
     - Assigned Ward Patients
     - MAR Medications Given Today
     - Pending Nursing Tasks
     - Active Fluid Balance Charts
  4. **Nursing Quick Action Bar**:
     - 🩺 `Record Vital Signs`
     - 💊 `Log MAR Medication`
     - 📝 `Add Nursing Note`
     - 💧 `Chart Fluid Intake/Output`
  5. **Shift Handover & Shift Assignment Card**: Active shift status and SBAR handover note logger.

---

## 🩺 Phase 3: Nursing Quick Action Drawers & Forms

### Task 3.1: Vital Sign Entry Drawer (`VitalSignsDrawer.tsx`)
- [ ] **File**: `hms-ui/src/components/nursing/VitalSignsDrawer.tsx`
- [ ] **Features**: Fast form with auto-calculated BMI, color-coded abnormal threshold warnings, and submission to `POST /api/v1/nursing/vital-signs`.

### Task 3.2: MAR Medication Pass Modal (`MarMedicationModal.tsx`)
- [ ] **File**: `hms-ui/src/components/nursing/MarMedicationModal.tsx`
- [ ] **Features**: Bedside 5-rights check modal for logging `GIVEN`, `REFUSED`, or `HELD` medication statuses.

---

## 🚦 Phase 4: Route Wiring (`App.tsx`)

- [ ] **File**: [App.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/App.tsx)
- [ ] **Add Nurse Routes**:
  ```tsx
  <Route
    path="/nurse/dashboard"
    element={
      <NurseRoute>
        <NurseDashboardPage />
      </NurseRoute>
    }
  />
  <Route
    path="/nursing/vital-signs"
    element={
      <NurseRoute>
        <VitalSignsPage />
      </NurseRoute>
    }
  />
  ```

---

## 🧪 Verification & Acceptance Criteria

1. **Nurse Login Verification**:
   - Log in as **`nurse`** / **`Nurse@123`**.
   - Sidebar displays all Nursing Care items; Admin, Doctor consultation editing, and HR Payroll remain hidden.
2. **Dashboard Verification**:
   - Medication pass table lists upcoming scheduled doses for the ward.
   - Vital sign recording form correctly flags fever (>38C) or hypoxia (<95% SpO2) in real time.
