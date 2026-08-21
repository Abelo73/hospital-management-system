# 👨‍⚕️ Hospital Management System (HMS) — Doctor Guide & Clinical Playbook

## 📋 1. Executive Overview

The Doctor role (`DOCTOR`) is the primary clinical authority in the Hospital Management System (HMS). Doctors conduct patient consultations, diagnose medical conditions using ICD coding, issue electronic prescriptions, request laboratory tests, review clinical histories, manage follow-up care, and generate specialist referrals.

This guide outlines all clinical workflows, permission boundaries, API endpoint contracts, EMR integration rules, and clinical best practices.

---

## 🗺️ 2. Doctor Responsibilities & Clinical Lifecycle Matrix

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           PHYSICIAN / DOCTOR                            │
└───────┬─────────────────┬──────────────────┬─────────────────┬──────────┘
        │                 │                  │                 │
┌───────▼──────┐  ┌───────▼──────┐   ┌───────▼──────┐  ┌───────▼──────┐
│ 1. Patient   │  │ 2. Clinical  │   │ 3. Orders &  │  │ 4. EMR &     │
│ Queue &      │  │ Consultation │   │ Prescriptions│  │ Specialist   │
│ Appointments │  │ & Diagnoses  │   │ & Lab Tests  │  │ Referrals    │
└──────────────┘  └──────────────┘   └──────────────┘  └──────────────┘
```

---

## 🩺 3. Core Clinical Workflows & Modules

### 3.1 Patient Queue & Consultation Intake
1. **Daily Queue Review**: View assigned patients for the day sorted by token number and appointment time slot.
2. **Consultation Start**: Change appointment status to `IN_CONSULTATION`.
3. **Chief Complaint Recording**: Capture primary patient complaints, symptom duration, and severity.
4. **Vital Signs Inspection**: Review nurse-entered vital signs (BP, Pulse, SpO2, Temperature, Respiratory Rate, BMI).

### 3.2 Clinical Consultation & ICD Diagnoses
* **Consultation Notes**: Structured clinical documentation using SOAP format (Subjective, Objective, Assessment, Plan).
* **ICD Diagnosis Coding**: Assign primary and secondary ICD-10 diagnostic codes.
* **Chronic Condition Tagging**: Flag long-term conditions (e.g., Type 2 Diabetes, Hypertension) for automated EMR history updates.
* **Consultation Finalization**: Lock consultation notes (`POST /api/v1/consultations/{id}/finalize`) to ensure medical record integrity and prevent retroactive edits.

### 3.3 Electronic Prescriptions (e-Prescribing)
* **Medication Selection**: Select generic or brand drugs from the internal hospital formulary.
* **Dosage & Instructions**: Specify dose quantity, frequency (e.g., `TDS`, `BD`, `PRN`), duration, and administration route (Oral, IV, Topical).
* **Drug Interaction Verification**: System alerts when prescribing contra-indicated medications or known patient allergens.
* **Pharmacy Routing**: Sent automatically to the Pharmacy module queue for dispensing.

### 3.4 Laboratory & Diagnostic Orders
* **Lab Test Placement**: Request diagnostic tests (CBC, Lipid Panel, Renal Function, X-Ray, MRI).
* **Result Review**: View returned laboratory findings directly from the Lab Technicians with automated High/Low flags.
* **Critical Result Alerts**: Instant notification for panic-level laboratory values.

### 3.5 Referrals & Follow-Up Planning
* **Specialist Referral**: Generate referral orders to internal departments (Cardiology, Orthopedics) or external specialists.
* **Follow-Up Scheduling**: Specify follow-up duration (e.g., "Return in 2 weeks") and auto-reserve follow-up queue slots.

---

## 🔑 4. Permissions & Role Boundaries

The `DOCTOR` role is pre-configured with clinical permissions:

| Permitted Action | Endpoint Scope | Description |
| :--- | :--- | :--- |
| **View EMR Records** | `GET /api/v1/patients/{id}` | Read patient history, allergies, past diagnoses |
| **Create Consultations** | `POST /api/v1/consultations` | Initiate SOAP clinical consultation |
| **Finalize Consultations**| `POST /api/v1/consultations/{id}/finalize` | Permanently lock consultation record |
| **Add Diagnoses** | `POST /api/v1/medical/diagnoses` | Attach ICD codes to patient record |
| **Write Prescriptions** | `POST /api/v1/pharmacy/prescriptions` | Generate e-prescription for fulfillment |
| **Order Lab Tests** | `POST /api/v1/laboratory/orders` | Place diagnostic lab order |
| **View Lab Results** | `GET /api/v1/medical/lab-results` | Access lab result reports |

> ⚠️ **Restricted Actions**: Doctors do **NOT** have permission to perform administrative user management, alter billing invoices, delete audit logs, or edit global system configurations.

---

## 🌐 5. Doctor API Reference Guide

### 1. Create Consultation
```http
POST /api/v1/consultations
Content-Type: application/json

{
  "patientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "appointmentId": "8b234f64-5717-4562-b3fc-2c963f66bbb9",
  "chiefComplaint": "Severe headache and elevated fever for 3 days",
  "examinationNotes": "Temp 38.5C, BP 120/80, no neck stiffness",
  "treatmentPlan": "Hydration, Paracetamol 500mg, rest for 48 hours"
}
```

### 2. Finalize Consultation
```http
POST /api/v1/consultations/{consultationId}/finalize
```

### 3. Fetch Patient Consultations
```http
GET /api/v1/consultations/patient/{patientId}
```

---

## 💡 6. Clinical Best Practices & Recommendations

1. **🔒 Lock Notes Promptly**: Finalize consultation notes within 24 hours to enforce clinical compliance and EMR accuracy.
2. **⚠️ Allergy Pre-Check**: Always verify the **Allergies** tab in the patient profile before issuing prescriptions.
3. **📋 Clear Dosage Instructions**: Avoid ambiguous prescription frequencies; specify explicit daily dosages and food intake conditions (e.g., "Take after meals").
4. **🤝 Multidisciplinary Coordination**: Review Nurse Vital Sign records (`/nursing/vital-signs`) prior to examining the patient.
