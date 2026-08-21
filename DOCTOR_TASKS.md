# 📋 Doctor Dashboard & Clinical Layout — Implementation Task Plan (`DOCTOR_TASKS.md`)

## 🎯 Task Goal
Implement a dedicated **Doctor Dashboard (`/doctor/dashboard`)** and enforce layout-level filtering in `hms-ui` specifically tailored for physicians. Doctors will have rapid access to today's patient queue, active consultation forms, e-prescribing, lab result reviews, and patient EMR search.

---

## 📌 Phase 1: Doctor Layout & Navigation Filtering

### Task 1.1: Create Doctor Route Guard (`DoctorRoute.tsx`)
- [ ] **File**: `hms-ui/src/components/auth/DoctorRoute.tsx`
- [ ] **Description**: Guard component ensuring only users with the `DOCTOR` or `ADMIN` role can access doctor clinical pages.
- [ ] **Behavior**: Redirect unauthorized users (e.g. Receptionists, Billing Officers) to `/dashboard`.

### Task 1.2: Sidebar Filtering for Doctor Role (`Sidebar.tsx`)
- [ ] **File**: [Sidebar.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/components/layout/Sidebar.tsx)
- [ ] **Description**: Ensure Doctors see a clinical-focused navigation layout:
  - ✅ **Visible**: Dashboard, Patient EMR, Consultations, Diagnoses, Prescriptions, Lab Results, Medical History, Appointments.
  - ❌ **Hidden**: Admin Console, Role Management, User Management, HR Payroll, Inventory Procurement, Financial Invoicing.

---

## 🩺 Phase 2: Dedicated Doctor Dashboard Component (`DoctorDashboardPage.tsx`)

### Task 2.1: Doctor API Service (`doctorService.ts`)
- [ ] **File**: `hms-ui/src/services/doctorService.ts`
- [ ] **Endpoints to integrate**:
  - `getTodayQueue(doctorId)` -> Fetch assigned appointments for today sorted by token number.
  - `getDoctorStats(doctorId)` -> Fetch completed consultations count, pending lab results, scheduled follow-ups.
  - `getRecentConsultations(doctorId)` -> Fetch doctor's recent consultation records.
  - `getPendingLabResults(doctorId)` -> Fetch lab orders with newly returned results ready for physician review.

### Task 2.2: Doctor Dashboard Page Layout (`DoctorDashboardPage.tsx`)
- [ ] **File**: `hms-ui/src/pages/doctors/DoctorDashboardPage.tsx`
- [ ] **Widgets & Components**:
  1. **Today's Patient Queue Banner**: Interactive queue card displaying patients currently waiting, token numbers, vital sign quick preview, and "Start Consultation" button.
  2. **Doctor Stat Cards**:
     - Consultations Completed Today
     - Pending Patients in Queue
     - Lab Results Awaiting Review
     - Follow-Up Appointments Scheduled
  3. **Clinical Quick Action Bar**:
     - ➕ `Start New Consultation`
     - 💊 `Write Prescription`
     - 🧪 `Order Lab Test`
     - 🔍 `Lookup Patient EMR`
  4. **Lab Results Ready for Review Card**: Badge highlighting recent lab reports ready for doctor sign-off.
  5. **My Recent Consultations Feed**: Summary list of today's finalized consultations.

---

## 📝 Phase 3: Clinical Consultation & EMR Workflow Enhancements

### Task 3.1: Interactive Consultation Form Modal / Drawer (`ConsultationFormModal.tsx`)
- [ ] **File**: `hms-ui/src/components/doctors/ConsultationFormModal.tsx`
- [ ] **Features**:
  - SOAP format inputs (Subjective, Objective, Assessment, Plan).
  - ICD-10 diagnosis picker with search auto-complete.
  - One-click prescription item builder.
  - Finalize consultation button calling `POST /api/v1/consultations/{id}/finalize`.

### Task 3.2: EMR Patient Quick View Drawer (`PatientEmrDrawer.tsx`)
- [ ] **File**: `hms-ui/src/components/doctors/PatientEmrDrawer.tsx`
- [ ] **Features**: Slide-over panel displaying allergy list, chronic conditions, and past consultation history without leaving the active queue.

---

## 🚦 Phase 4: Route Wiring (`App.tsx`)

- [ ] **File**: [App.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/App.tsx)
- [ ] **Add Doctor Routes**:
  ```tsx
  <Route
    path="/doctor/dashboard"
    element={
      <DoctorRoute>
        <DoctorDashboardPage />
      </DoctorRoute>
    }
  />
  <Route
    path="/doctors/consultations"
    element={
      <DoctorRoute>
        <ConsultationsPage />
      </DoctorRoute>
    }
  />
  ```

---

## 🧪 Verification & Acceptance Criteria

1. **Doctor Login Verification**:
   - Log in as **`doctor`** / **`Doctor@123`**.
   - Sidebar displays clinical sections; Admin, HR, and Billing options remain hidden.
2. **Dashboard Verification**:
   - Today's Queue shows patient appointments with active token numbers.
   - Clicking "Start Consultation" opens the SOAP consultation drawer pre-filled with patient details.
   - Lab results ready for review alert badge displays test status accurately.
