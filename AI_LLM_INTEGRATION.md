# 🤖 Hospital Management System (HMS) — AI & LLM Integration Architecture (`AI_LLM_INTEGRATION.md`)

## 📋 1. Executive Overview

Integrating Artificial Intelligence (AI) and Large Language Models (LLMs) into the Hospital Management System (HMS) enhances clinical decision support, automates administrative overhead, streamlines patient triage, and powers intelligent medical record synthesis.

This document details the AI/LLM system architecture, Spring AI integration strategy, Retrieval-Augmented Generation (RAG) over clinical knowledge bases, prompt engineering guardrails, and HIPAA security compliance standards.

---

## 🏗️ 2. AI & LLM System Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           HMS FRONTEND (hms-ui)                         │
└───────┬─────────────────────────┬─────────────────────────┬─────────────┘
        │                         │                         │
┌───────▼──────────────┐  ┌───────▼──────────────┐  ┌───────▼─────────────┐
│ Clinical AI Copilot  │  │ Patient Triage Bot   │  │ Admin AI Analytics  │
│ (Doctor Consultation)│  │ (Front Desk/Portal)  │  │ (Billing & Coding)  │
└───────┬──────────────┘  └───────┬──────────────┘  └───────┬─────────────┘
        │                         │                         │
┌───────▼─────────────────────────▼─────────────────────────▼─────────────┐
│                 Spring AI Backend (Spring Boot Service)                 │
├─────────────────────────────────────────────────────────────────────────┤
│  Prompt Guardrails │ Context Builder │ Vector DB (pgvector / Redis Search)│
└───────┬───────────────────────────────────────────────────┬─────────────┘
        │                                                   │
┌───────▼────────────────┐                         ┌───────▼─────────────┐
│ LLM Provider           │                         │ Vector Database     │
│ (Gemini 1.5 Pro /      │                         │ (ICD-10 Guidelines, │
│ OpenAI GPT-4o / Local) │                         │ Drug Interaction DB)│
└────────────────────────┘                         └─────────────────────┘
```

---

## 🤖 3. Core AI Use Cases & Capabilities

### 3.1 👨‍⚕️ Clinical AI Copilot (Doctor Assistant)
* **Automated Clinical Summary**: Summarizes a patient's multi-year EMR history, allergy risks, past surgeries, and chronic conditions in 3 bullet points before a consultation starts.
* **ICD-10 Code Recommendation**: Analyzes free-text chief complaints and SOAP notes to suggest exact matching ICD-10 diagnostic codes.
* **Prescription Safety & Interaction Explainer**: Explains complex multi-drug contraindications and suggests safer alternative formulary medications.

### 3.2 💬 Patient Triage & Symptom Checker Bot
* **Intelligent Pre-Intake**: Asks natural language questions to patients in the lobby or patient portal to assess symptom urgency (Red/Yellow/Green triage level).
* **Automated Appointment Routing**: Suggests appropriate medical specialties based on described symptoms (e.g. recommending Dermatology vs. Rheumatology).

### 3.3 💳 Administrative & Billing AI Optimizer
* **Medical Coding Validation**: Scans consultation notes to detect missing billable procedures or unbilled diagnostic codes.
* **Insurance Claim Appeal Generator**: Drafts automated claim appeal letters for denied insurance claims based on clinical documentation.

---

## 🔒 4. AI Guardrails, Data Privacy & HIPAA Compliance

1. **Zero Data Retention (ZDR)**: Enforce BAA (Business Associate Agreements) with LLM API vendors so patient health data is NEVER used for public LLM training.
2. **De-Identification / Anonymization Filter**: Strip Direct Identifiers (Patient Name, Social Security Number, Address, Phone) before sending prompt context to external LLM APIs.
3. **Human-in-the-Loop (HITL)**: AI recommendations (ICD codes, prescriptions, diagnoses) MUST be reviewed and explicitly signed off by a licensed physician before entering official medical records.

---

## 🌐 5. Spring AI Backend Implementation Reference

### 1. Spring AI Configuration (`pom.xml`)
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    <version>1.0.0-M1</version>
</dependency>
```

### 2. Clinical AI Copilot Controller (`AiCopilotController.java`)
```http
POST /api/v1/ai/clinical-summary
Content-Type: application/json

{
  "patientId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "chiefComplaint": "Shortness of breath, chest tightness, wheezing"
}
```
