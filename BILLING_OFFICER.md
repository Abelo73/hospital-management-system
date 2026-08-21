# 💳 Hospital Management System (HMS) — Billing Officer Guide & Financial Playbook

## 📋 1. Executive Overview

The Billing Officer role (`BILLING_OFFICER`) manages financial operations, invoice generation, payment processing, insurance claim submissions, refund workflows, and revenue reporting in the Hospital Management System (HMS).

This guide documents financial workflows, insurance claim cycles, payment channels, endpoint APIs, and fiscal compliance standards.

---

## 🗺️ 2. Financial & Billing Operations Lifecycle

```
┌─────────────────────────────────────────────────────────────────────────┐
│                             BILLING OFFICER                             │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│ 1. Invoice   │  │ 2. Payment   │   │ 3. Insurance │   │ 4. Financial │
│ Generation   │  │ Processing   │   │ Claims &     │   │ Reports &    │
│ & Line Items │  │ & Receipts   │   │ Coverage     │   │ Revenue      │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 💰 3. Core Billing Workflows

### 3.1 Invoice Generation & Charge Capture
* **Automated Invoicing**: Charges captured automatically from consultations, lab tests, pharmacy dispenses, and ward stays.
* **Invoice Line Items**: Itemized fees for professional consultation, procedures, drugs, diagnostics, and room rates.
* **Invoice Statuses**: `DRAFT`, `PENDING_PAYMENT`, `PARTIALLY_PAID`, `PAID`, `OVERDUE`, `CANCELLED`.

### 3.2 Multi-Channel Payment Processing
* **Payment Methods**: Cash, Credit/Debit Card, Electronic Transfer, Mobile Money, Insurance Co-pay.
* **Payment Reconciliation**: Matching received funds against outstanding invoice balances.
* **Receipt Issuance**: Generating digital and printable official payment receipts.

### 3.3 Insurance Claims & Coverage Validation
* **Pre-Authorization**: Verify active insurance coverage and policy copay limits.
* **Claim Submission**: Submit itemized claims to insurance providers (`GET /api/v1/billing/insurance/claims`).
* **Claim Adjudication Tracking**: Monitor claim statuses (`SUBMITTED`, `UNDER_REVIEW`, `APPROVED`, `REJECTED`).

### 3.4 Refunds & Financial Adjustments
* **Refund Requests**: Initiate refund requests for cancelled services or duplicate payments.
* **Multi-Level Approval**: Admin approval required for refunds exceeding designated monetary thresholds.

---

## 🔑 4. Permissions & Role Boundaries

The `BILLING_OFFICER` role is pre-configured with financial permissions:

| Permitted Action | Endpoint Scope | Description |
| :--- | :--- | :--- |
| **Create Invoice** | `POST /api/v1/billing/invoices` | Generate patient invoice |
| **Process Payment** | `POST /api/v1/billing/payments` | Record received payment |
| **Submit Insurance Claim**| `POST /api/v1/billing/insurance/claims` | Submit claim to insurer |
| **View Revenue Reports** | `GET /api/v1/billing/reports/daily-revenue` | Access daily revenue metrics |
| **View Aging Reports** | `GET /api/v1/billing/reports/aging` | View unpaid overdue invoices |

> ⚠️ **Restricted Actions**: Billing Officers cannot write medical prescriptions, enter lab diagnostic results, modify nursing care plans, or alter system user permissions.

---

## 🌐 5. Billing API Reference Guide

### 1. Create Invoice
```http
POST /api/v1/billing/invoices
Content-Type: application/json

{
  "patientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "items": [
    { "description": "Specialist Consultation Fee", "amount": 150.00, "quantity": 1 },
    { "description": "Complete Blood Count (CBC)", "amount": 45.00, "quantity": 1 }
  ],
  "taxAmount": 15.60,
  "discountAmount": 0.00
}
```

### 2. Record Payment
```http
POST /api/v1/billing/payments
Content-Type: application/json

{
  "invoiceId": "9d234f64-5717-4562-b3fc-2c963f66eee9",
  "amountPaid": 210.60,
  "paymentMethod": "CREDIT_CARD",
  "transactionReference": "TXN-2026-99482"
}
```

---

## 💡 6. Financial Best Practices

1. **🧾 Immediate Invoice Verification**: Ensure all clinical charges are captured before finalizing patient discharge.
2. **📑 Complete Claim Attachments**: Attach doctor notes and diagnostic reports when submitting insurance claims to prevent rejections.
3. **📊 Daily End-of-Day Reconciliation**: Perform daily cash box drawer balancing against logged EOD collections.
