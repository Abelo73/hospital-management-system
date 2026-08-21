# 👩‍⚕️ Hospital Management System (HMS) — Nurse Guide & Clinical Care Playbook

## 📋 1. Executive Overview

The Nurse role (`NURSE`) provides bedside patient care, continuous vital sign monitoring, medication administration tracking, inpatient admission support, fluid balance charting, and shift handovers in the Hospital Management System (HMS).

This guide documents all nursing care workflows, medication administration records (MAR), vital sign thresholds, endpoint APIs, and clinical documentation best practices.

---

## 🗺️ 2. Nursing Care Lifecycle & Responsibilities

```
┌─────────────────────────────────────────────────────────────────────────┐
│                             NURSE / CARE TEAM                           │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│ 1. Vital     │  │ 2. MAR &     │   │ 3. Inpatient │   │ 4. Nursing   │
│ Signs &      │  │ Medication   │   │ Ward & Fluid │   │ Notes & Shift│
│ Triage       │  │ Passes       │   │ Balance      │   │ Handovers    │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 🩺 3. Core Nursing Modules & Workflows

### 3.1 Vital Signs & Triage Monitoring
* **Vital Parameters**: Record Systolic/Diastolic Blood Pressure, Pulse Rate, Body Temperature, SpO2 (Oxygen Saturation), Respiratory Rate, Pain Scale (0-10), and BMI.
* **Automatic Abnormal Alerts**:
  * Temperature > 38.0°C (Pyrexia Warning)
  * SpO2 < 95% (Hypoxia Warning)
  * Systolic BP > 140 mmHg or < 90 mmHg
* **Triage Priority**: Assign patient triage urgency (Red: Resuscitation, Yellow: Urgent, Green: Non-urgent).

### 3.2 Medication Administration Record (MAR)
* **Medication Passes**: View physician-prescribed active medications scheduled for administration.
* **5 Rights Verification**: Verify Right Patient, Right Drug, Right Dose, Right Route, Right Time.
* **Administration Status**:
  * `GIVEN`: Dosage successfully administered.
  * `REFUSED`: Patient refused medication (reason logged).
  * `HELD`: Dose held due to clinical contraindication (e.g., low blood pressure).
  * `MISSED`: Dosage window expired.

### 3.3 Nursing Care Plans & Tasks
* **Care Plan Execution**: Implement NANDA nursing care plans assigned to inpatients.
* **Daily Nursing Tasks**: Checklist of routine care tasks (dressing changes, bed turns, blood glucose checks, hygiene care).

### 3.4 Inpatient Fluid Balance & Wound Care
* **Fluid Intake/Output**: Track IV infusion totals, oral fluid intake vs. urinary output, surgical drains, and emesis to calculate net 24-hour balance.
* **Wound Care Documentation**: Measure wound size, document tissue bed condition (granulation/slough), and log dressing change frequencies.

### 3.5 Incident Reporting & Shift Handover Notes
* **Incident Documentation**: Immediate filing of fall incidents, medication errors, or needle-stick injuries.
* **Shift Handover**: Document SBAR (Situation, Background, Assessment, Recommendation) shift handovers to ensure seamless continuity of care.

---

## 🔑 4. Permissions & Role Boundaries

The `NURSE` role is pre-configured with nursing care permissions:

| Permitted Action | Endpoint Scope | Description |
| :--- | :--- | :--- |
| **Record Vital Signs** | `POST /api/v1/nursing/vital-signs` | Log BP, pulse, temp, SpO2 |
| **Record MAR** | `POST /api/v1/nursing/medication-administrations` | Log drug administration status |
| **Create Care Plans** | `POST /api/v1/nursing/care-plans` | Create & update nursing care plans |
| **Log Nursing Notes** | `POST /api/v1/nursing/notes` | Add DAR/SOAPIE nursing notes |
| **Chart Fluid Balance**| `POST /api/v1/nursing/fluid-balance` | Log intake vs output volumes |
| **Log Incident Report**| `POST /api/v1/nursing/incident-reports` | File patient safety incident report |

> ⚠️ **Restricted Actions**: Nurses cannot write initial prescriptions, order complex diagnostic lab panels, modify billing invoices, or alter system user permissions.

---

## 🌐 5. Nursing API Reference Guide

### 1. Record Vital Signs
```http
POST /api/v1/nursing/vital-signs
Content-Type: application/json

{
  "patientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "systolicBp": 124,
  "diastolicBp": 82,
  "pulseRate": 78,
  "temperature": 37.2,
  "spo2": 98,
  "respiratoryRate": 16,
  "painScale": 2
}
```

### 2. Log Medication Administration (MAR)
```http
POST /api/v1/nursing/medication-administrations
Content-Type: application/json

{
  "patientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "prescriptionId": "7c123f64-5717-4562-b3fc-2c963f66ccc1",
  "status": "GIVEN",
  "administeredAt": "2026-08-21T19:30:00Z",
  "notes": "Administered orally with water. Patient tolerated well."
}
```

---

## 💡 6. Nursing Best Practices

1. **⏱️ Immediate Vital Charting**: Record vitals immediately upon taking them so doctors have real-time data prior to consultations.
2. **✅ Strict MAR Adherence**: Always verify MAR entries at the bedside before opening medication packaging.
3. **💧 Accurate Fluid Totals**: Calculate net fluid balance at the end of every 8-hour shift.
4. **🗣️ Clear SBAR Handover**: Complete shift handover notes prior to signing out of active shifts.
