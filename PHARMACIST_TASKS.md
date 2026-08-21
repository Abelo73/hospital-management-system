# 📋 Pharmacist Dashboard & Pharmacy Operations Layout — Implementation Task Plan (`PHARMACIST_TASKS.md`)

## 🎯 Task Goal
Implement a dedicated **Pharmacist Dashboard (`/pharmacy/dashboard`)** and layout-level navigation filtering in `hms-ui` specifically designed for pharmacy staff. Pharmacists will have streamlined workflows for prescription verification, bedside dispensing, FEFO stock allocation, drug interaction alerts, and narcotic logs.

---

## 📌 Phase 1: Pharmacist Layout & Navigation Filtering

### Task 1.1: Create Pharmacist Route Guard (`PharmacistRoute.tsx`)
- [ ] **File**: `hms-ui/src/components/auth/PharmacistRoute.tsx`
- [ ] **Description**: Guard component ensuring only users with `PHARMACIST` or `ADMIN` roles can access pharmacy dispensing and formulary management routes.
- [ ] **Behavior**: Redirect unauthorized users to `/dashboard`.

### Task 1.2: Sidebar Filtering for Pharmacist Role (`Sidebar.tsx`)
- [ ] **File**: [Sidebar.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/components/layout/Sidebar.tsx)
- [ ] **Description**: Configure navigation section filters for pharmacy staff:
  - ✅ **Visible**: Dashboard, Prescriptions, Dispensing, Drug Catalog, Stock Levels, Patients (Read-only).
  - ❌ **Hidden**: Admin Console, Role Management, User Management, Doctor Consultation Editing, Nursing Care Plans, HR Payroll, Procurement.

---

## 💊 Phase 2: Dedicated Pharmacist Dashboard Component (`PharmacyDashboardPage.tsx`)

### Task 2.1: Pharmacy API Service (`pharmacyService.ts`)
- [ ] **File**: `hms-ui/src/services/pharmacyService.ts`
- [ ] **Endpoints to integrate**:
  - `getPendingPrescriptions()` -> Fetch active queue of doctor-issued prescriptions.
  - `getPharmacyStats()` -> Fetch total prescriptions dispensed today, pending queue count, expiring drugs count.
  - `getDrugInteractions(drugA, drugB)` -> Query contraindications database.
  - `getLowStockDrugs()` -> Fetch drugs below minimum safety threshold.

### Task 2.2: Pharmacist Dashboard Page Layout (`PharmacyDashboardPage.tsx`)
- [ ] **File**: `hms-ui/src/pages/pharmacy/PharmacyDashboardPage.tsx`
- [ ] **Widgets & Components**:
  1. **Pending Prescription Queue Card**: Live queue of prescriptions with token numbers, doctor name, patient name, and "Verify & Dispense" button.
  2. **Drug Interaction Warning Banner**: Highlight flagged prescriptions containing contra-indicated drug pairs or patient allergens.
  3. **Pharmacist Stat Cards**:
     - Prescriptions Dispensed Today
     - Pending Verification Queue
     - Expiring Drugs (Next 30 Days)
     - Controlled Substance Logs
  4. **Pharmacy Quick Action Bar**:
     - 💊 `Dispense Medication`
     - 🧪 `Check Drug Interaction`
     - 📦 `Search Drug Catalog`
     - 📜 `Register Controlled Drug`
  5. **Stock Level Alert Panel**: Low-stock alert cards integrated with the Inventory module.

---

## 🩺 Phase 3: Pharmacy Interactive Modal Forms

### Task 3.1: Dispensing & FEFO Batch Allocation Modal (`DispenseModal.tsx`)
- [ ] **File**: `hms-ui/src/components/pharmacy/DispenseModal.tsx`
- [ ] **Features**:
  - Prescription item review.
  - Batch number selection (auto-selected by FEFO).
  - Printing bottle instruction labels.
  - Submission to `POST /api/v1/pharmacy/dispense`.

---

## 🚦 Phase 4: Route Wiring (`App.tsx`)

- [ ] **File**: [App.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/App.tsx)
- [ ] **Add Pharmacy Routes**:
  ```tsx
  <Route
    path="/pharmacy/dashboard"
    element={
      <PharmacistRoute>
        <PharmacyDashboardPage />
      </PharmacistRoute>
    }
  />
  <Route
    path="/pharmacy/dispensing"
    element={
      <PharmacistRoute>
        <DispensingPage />
      </PharmacistRoute>
    }
  />
  ```

---

## 🧪 Verification & Acceptance Criteria

1. **Pharmacist Login Verification**:
   - Log in as **`pharmacist`**.
   - Sidebar displays Pharmacy & Inventory Stock items; Admin, Doctor consultations, and HR Payroll remain hidden.
2. **Dashboard Verification**:
   - Pending prescription queue updates in real-time.
   - Dispensing modal auto-suggests earliest expiring batch numbers (FEFO).
