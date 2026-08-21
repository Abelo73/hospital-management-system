# 📋 Billing Officer Dashboard & Financial Layout — Implementation Task Plan (`BILLING_OFFICER_TASKS.md`)

## 🎯 Task Goal
Implement a dedicated **Billing Officer Dashboard (`/billing/dashboard`)** and layout-level navigation filtering in `hms-ui` specifically designed for financial and cashier staff. Billing Officers will have fast access to uncollected invoice queues, multi-channel payment posting, insurance claim tracking, receipt printing, and daily revenue reports.

---

## 📌 Phase 1: Billing Officer Layout & Navigation Filtering

### Task 1.1: Create Billing Route Guard (`BillingRoute.tsx`)
- [ ] **File**: `hms-ui/src/components/auth/BillingRoute.tsx`
- [ ] **Description**: Guard component ensuring only users with `BILLING_OFFICER` or `ADMIN` roles can access financial billing and payment processing pages.
- [ ] **Behavior**: Redirect unauthorized users to `/dashboard`.

### Task 1.2: Sidebar Filtering for Billing Officer Role (`Sidebar.tsx`)
- [ ] **File**: [Sidebar.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/components/layout/Sidebar.tsx)
- [ ] **Description**: Configure navigation section filters for billing staff:
  - ✅ **Visible**: Dashboard, Invoices, Payments, Insurance Claims, Financial Reports, Patients (Read-only).
  - ❌ **Hidden**: Admin Console, Role Management, User Management, Doctor Consultation Editing, Nursing Care Plans, Pharmacy Dispensing, HR Payroll, Procurement.

---

## 💳 Phase 2: Dedicated Billing Officer Dashboard Component (`BillingDashboardPage.tsx`)

### Task 2.1: Billing API Service (`billingService.ts`)
- [ ] **File**: `hms-ui/src/services/billingService.ts`
- [ ] **Endpoints to integrate**:
  - `getPendingInvoices()` -> Fetch active uncollected patient invoices.
  - `getBillingStats()` -> Fetch today's collection revenue, pending invoices count, aging overdue totals.
  - `processPayment(data)` -> Record payment transaction.
  - `getDailyRevenueReport()` -> Fetch daily revenue breakdown by payment method.

### Task 2.2: Billing Officer Dashboard Page Layout (`BillingDashboardPage.tsx`)
- [ ] **File**: `hms-ui/src/pages/billing/BillingDashboardPage.tsx`
- [ ] **Widgets & Components**:
  1. **Uncollected Invoices Table**: Interactive queue of pending patient invoices with patient name, itemized services, total due, and "Record Payment" action button.
  2. **Insurance Claims Status Banner**: Status banner showing pending vs approved insurance claims.
  3. **Financial Stat Cards**:
     - Revenue Collected Today
     - Pending Invoices Count
     - Overdue Accounts (Aging)
     - Insurance Claim Approvals
  4. **Billing Quick Action Bar**:
     - 🧾 `Create New Invoice`
     - 💳 `Record Payment`
     - 📑 `Submit Insurance Claim`
     - 📊 `Generate EOD Report`

---

## 🩺 Phase 3: Financial Action Drawers & Receipts

### Task 3.1: Payment Settlement Modal (`PaymentModal.tsx`)
- [ ] **File**: `hms-ui/src/components/billing/PaymentModal.tsx`
- [ ] **Features**: Multi-channel payment selection (Cash, Card, Mobile), discount calculation, receipt generation.

---

## 🚦 Phase 4: Route Wiring (`App.tsx`)

- [ ] **File**: [App.tsx](file:///home/abel/Desktop/PROJECTS/2026-projects/hms/hms-ui/src/App.tsx)
- [ ] **Add Billing Routes**:
  ```tsx
  <Route
    path="/billing/dashboard"
    element={
      <BillingRoute>
        <BillingDashboardPage />
      </BillingRoute>
    }
  />
  ```

---

## 🧪 Verification & Acceptance Criteria

1. **Billing Officer Login Verification**:
   - Log in as **`billing`** (or account with `BILLING_OFFICER` role).
   - Sidebar displays Billing & Report pages; Admin, Doctor consultations, and Pharmacy remain hidden.
2. **Dashboard Verification**:
   - Uncollected invoice list updates in real-time.
   - Payment settlement modal records transactions and generates printable receipts.
