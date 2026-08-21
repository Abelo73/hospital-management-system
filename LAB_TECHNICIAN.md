# 🧪 Hospital Management System (HMS) — Lab Technician Guide & Laboratory Playbook

## 📋 1. Executive Overview

The Laboratory Technician role (`LAB_TECHNICIAN`) operates laboratory diagnostic services, specimen collection tracking, test result entry, reference range validation, panic value notifications, and quality control (QC) in the Hospital Management System (HMS).

This guide documents laboratory workflows, specimen tracking, panic alert protocols, API endpoints, and diagnostic quality standards.

---

## 🗺️ 2. Laboratory Diagnostic Lifecycle

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           LABORATORY TECHNICIAN                         │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│ 1. Lab Order │  │ 2. Specimen  │   │ 3. Testing & │   │ 4. Panic     │
│ Queue        │  │ Collection & │   │ Result Entry │   │ Alerts &     │
│ Ingestion    │  │ Barcoding    │   │ Verification │   │ Report Sync  │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 🧪 3. Core Laboratory Workflows

### 3.1 Order Queue & Specimen Collection
* **Lab Worklist**: Ingestion of physician-ordered tests (Hematology, Biochemistry, Microbiology, Serology).
* **Specimen Collection**:
  * Specimen Types: Whole Blood, Serum, Plasma, Urine, Swab, Tissue.
  * Barcode Generation: Assign unique specimen barcode numbers to test tubes.
  * Rejection Protocols: Reject hemolyzed, clotted, or insufficient specimens with explicit rejection logs.

### 3.2 Result Entry & Reference Range Validation
* **Result Input**: Enter numeric findings (e.g. Hemoglobin `13.5 g/dL`) or qualitative findings (e.g. `Negative`).
* **Automated Range Flagging**:
  * `NORMAL`: Within healthy range.
  * `HIGH` / `LOW`: Outside normal reference range.
  * `PANIC` / `CRITICAL`: Life-threatening value requiring immediate escalation.

### 3.3 Panic Value Escalation Protocol
* **Panic Triggers**: E.g. Potassium `<2.5` or `>6.5 mmol/L`, Hemoglobin `<6.0 g/dL`, Glucose `<40` or `>500 mg/dL`.
* **Immediate Escalation**: System alerts treating physician and ward nurse via push/SMS notifications.
* **Read-Back Verification**: Mandatory verbal read-back logging with attending nurse/physician timestamp.

### 3.4 Quality Control (QC) & Reagent Tracking
* **Daily Analyzer Calibration**: Log daily QC runs (Level 1 & Level 2 controls).
* **Reagent Expiry & Lot Management**: Ensure testing kits and reagents are unexpired and linked to test lot numbers.

---

## 🔑 4. Permissions & Role Boundaries

The `LAB_TECHNICIAN` role is pre-configured with laboratory permissions:

| Permitted Action | Endpoint Scope | Description |
| :--- | :--- | :--- |
| **View Pending Orders** | `GET /api/v1/laboratory/requests/pending` | Fetch unfulfilled lab requests |
| **Log Specimen Collection**| `POST /api/v1/laboratory/samples` | Register specimen collection & barcode |
| **Create Lab Result** | `POST /api/v1/lab-results` | Enter diagnostic test findings |
| **Update Lab Result** | `PUT /api/v1/lab-results/{id}` | Edit & verify test result details |
| **View Test Catalog** | `GET /api/v1/laboratory/tests` | Query available lab test definitions |

> ⚠️ **Restricted Actions**: Lab Technicians cannot write clinical prescriptions, alter nursing vital sign charts, edit billing tariffs, or change system admin user roles.

---

## 🌐 5. Laboratory API Reference Guide

### 1. Fetch Pending Lab Requests
```http
GET /api/v1/laboratory/requests/pending
```

### 2. Create Lab Result
```http
POST /api/v1/lab-results
Content-Type: application/json

{
  "patientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "testName": "Complete Blood Count (CBC)",
  "testCategory": "HEMATOLOGY",
  "results": {
    "Hemoglobin": "14.2 g/dL",
    "WBC": "7.5 x10^3/uL",
    "Platelets": "250 x10^3/uL"
  },
  "status": "FINAL",
  "flag": "NORMAL",
  "technicianNotes": "Sample processed without hemolysis."
}
```

---

## 💡 6. Laboratory Best Practices

1. **🏷️ Double Barcode Check**: Scan specimen barcode before entering results to eliminate patient misidentification.
2. **🚨 Mandatory Panic Logging**: Never delay logging panic values; verify attending physician notification immediately.
3. **📊 Daily QC Validation**: Run quality control checks before processing morning inpatient batches.
