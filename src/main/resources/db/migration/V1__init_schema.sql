-- Initial schema migration for Hospital Management System
-- This migration creates all the base tables for the application

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ============================================
-- AUTH MODULE TABLES
-- ============================================

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    enabled BOOLEAN NOT NULL DEFAULT true,
    account_non_expired BOOLEAN NOT NULL DEFAULT true,
    account_non_locked BOOLEAN NOT NULL DEFAULT true,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT true,
    approval_status VARCHAR(30) NOT NULL DEFAULT 'PENDING_SUBMISSION',
    submitted_at TIMESTAMP,
    approved_at TIMESTAMP,
    approved_by VARCHAR(100),
    rejection_reason VARCHAR(500),
    requires_verification BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Roles table
CREATE TABLE IF NOT EXISTS roles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Permissions table
CREATE TABLE IF NOT EXISTS permissions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) UNIQUE NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- User-Role join table
CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Role-Permission join table
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id UUID NOT NULL,
    permission_id UUID NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(id) ON DELETE CASCADE
);

-- Approval Requests table
CREATE TABLE IF NOT EXISTS approval_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    request_type VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING_SUBMISSION',
    requested_role VARCHAR(50),
    priority INTEGER NOT NULL DEFAULT 5,
    submitted_at TIMESTAMP,
    submitted_by VARCHAR(100),
    reviewed_at TIMESTAMP,
    reviewed_by VARCHAR(100),
    approved_at TIMESTAMP,
    approved_by VARCHAR(100),
    rejection_reason VARCHAR(1000),
    notes VARCHAR(1000),
    documents_required BOOLEAN NOT NULL DEFAULT false,
    documents_verified BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- User Documents table
CREATE TABLE IF NOT EXISTS user_documents (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    document_status VARCHAR(30) NOT NULL DEFAULT 'PENDING_UPLOAD',
    document_name VARCHAR(255),
    file_path VARCHAR(500),
    file_size BIGINT,
    file_type VARCHAR(100),
    issue_date DATE,
    expiry_date DATE,
    issuing_authority VARCHAR(255),
    document_number VARCHAR(100),
    verified_at TIMESTAMP,
    verified_by VARCHAR(100),
    verification_notes VARCHAR(500),
    rejection_reason VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- ============================================
-- PATIENT MODULE TABLES
-- ============================================

-- Patients table
CREATE TABLE IF NOT EXISTS patients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    medical_record_number VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(30) NOT NULL,
    blood_type VARCHAR(20),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(500),
    city VARCHAR(100),
    state VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    emergency_contact_relationship VARCHAR(50),
    allergies VARCHAR(1000),
    chronic_conditions VARCHAR(1000),
    current_medications VARCHAR(1000),
    insurance_provider VARCHAR(100),
    insurance_policy_number VARCHAR(50),
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    notes TEXT,
    deleted BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

-- ============================================
-- APPOINTMENT MODULE TABLES
-- ============================================

-- Appointments table
CREATE TABLE IF NOT EXISTS appointments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    appointment_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    appointment_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    duration_minutes INTEGER NOT NULL,
    reason VARCHAR(500),
    notes TEXT,
    symptoms VARCHAR(1000),
    priority VARCHAR(20),
    is_virtual BOOLEAN NOT NULL DEFAULT false,
    meeting_link VARCHAR(500),
    reminder_sent BOOLEAN NOT NULL DEFAULT false,
    check_in_time TIMESTAMP,
    check_out_time TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- ============================================
-- DOCTOR MODULE TABLES
-- ============================================

-- Consultations table
CREATE TABLE IF NOT EXISTS consultations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    consultation_date TIMESTAMP NOT NULL,
    chief_complaint VARCHAR(1000),
    history_of_present_illness TEXT,
    physical_examination TEXT,
    plan TEXT,
    notes TEXT,
    is_finalized BOOLEAN NOT NULL DEFAULT false,
    finalized_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES users(id)
);

-- Consultation Diagnoses table
CREATE TABLE IF NOT EXISTS consultation_diagnoses (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    consultation_id UUID NOT NULL,
    icd10_code VARCHAR(20),
    description VARCHAR(500) NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT false,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (consultation_id) REFERENCES consultations(id)
);

-- Prescriptions table
CREATE TABLE IF NOT EXISTS prescriptions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    consultation_id UUID NOT NULL,
    medication_name VARCHAR(200) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    frequency VARCHAR(100) NOT NULL,
    duration VARCHAR(100) NOT NULL,
    route VARCHAR(100),
    instructions TEXT,
    is_dispensed BOOLEAN NOT NULL DEFAULT false,
    dispensed_at TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (consultation_id) REFERENCES consultations(id)
);

-- ============================================
-- LABORATORY MODULE TABLES
-- ============================================

-- Lab Tests (Catalog) table
CREATE TABLE IF NOT EXISTS lab_tests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    test_code VARCHAR(20) UNIQUE NOT NULL,
    test_name VARCHAR(100) NOT NULL,
    test_category VARCHAR(50) NOT NULL,
    specimen_type VARCHAR(50) NOT NULL,
    unit VARCHAR(20),
    reference_range VARCHAR(500),
    price DECIMAL(10,2),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Lab Test Requests table
CREATE TABLE IF NOT EXISTS lab_test_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    request_number VARCHAR(50) UNIQUE NOT NULL,
    patient_id UUID NOT NULL,
    ordering_provider_id UUID NOT NULL,
    request_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    priority VARCHAR(20) NOT NULL DEFAULT 'ROUTINE',
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    clinical_information TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (ordering_provider_id) REFERENCES users(id)
);

-- Lab Test Request Items table
CREATE TABLE IF NOT EXISTS lab_test_request_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    request_id UUID NOT NULL,
    test_id UUID NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    result_value VARCHAR(500),
    result_flag VARCHAR(20),
    performed_by UUID,
    performed_at TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (request_id) REFERENCES lab_test_requests(id),
    FOREIGN KEY (test_id) REFERENCES lab_tests(id)
);

-- ============================================
-- MEDICAL MODULE TABLES
-- ============================================

-- Medical Records table
CREATE TABLE IF NOT EXISTS medical_records (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    record_type VARCHAR(50) NOT NULL,
    record_date DATE NOT NULL,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(1000),
    clinical_notes TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Lab Results table
CREATE TABLE IF NOT EXISTS lab_results (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    test_type VARCHAR(100),
    test_name VARCHAR(200) NOT NULL,
    test_date DATE NOT NULL,
    result_value VARCHAR(500),
    unit VARCHAR(50),
    reference_range VARCHAR(500),
    status VARCHAR(50) NOT NULL,
    performed_by VARCHAR(200),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Allergies table
CREATE TABLE IF NOT EXISTS allergies (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    allergen VARCHAR(200) NOT NULL,
    severity VARCHAR(50),
    reaction VARCHAR(500),
    diagnosed_date DATE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Medications table
CREATE TABLE IF NOT EXISTS medications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    medication_name VARCHAR(200) NOT NULL,
    dosage VARCHAR(100),
    frequency VARCHAR(100),
    start_date DATE,
    end_date DATE,
    prescribed_by VARCHAR(200),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Vaccinations table
CREATE TABLE IF NOT EXISTS vaccinations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    vaccine_name VARCHAR(200) NOT NULL,
    vaccine_type VARCHAR(100),
    administration_date DATE,
    next_due_date DATE,
    administered_by VARCHAR(200),
    lot_number VARCHAR(100),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- ============================================
-- NURSING MODULE TABLES
-- ============================================

-- Vital Signs table
CREATE TABLE IF NOT EXISTS vital_signs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    admission_id UUID,
    recorded_date DATE NOT NULL,
    recorded_time TIME NOT NULL,
    recorded_by UUID,
    temperature DECIMAL(5,2),
    temperature_unit VARCHAR(50),
    temperature_site VARCHAR(50),
    systolic_bp INTEGER,
    diastolic_bp INTEGER,
    blood_pressure_site VARCHAR(50),
    heart_rate INTEGER,
    heart_rate_rhythm VARCHAR(50),
    respiratory_rate INTEGER,
    oxygen_saturation DECIMAL(5,2),
    oxygen_supplement BOOLEAN,
    oxygen_flow_rate DECIMAL(5,2),
    oxygen_delivery_method VARCHAR(50),
    blood_glucose DECIMAL(10,2),
    blood_glucose_unit VARCHAR(50),
    blood_glucose_timing VARCHAR(50),
    pain_score INTEGER,
    pain_scale VARCHAR(50),
    height DECIMAL(5,2),
    weight DECIMAL(5,2),
    bmi DECIMAL(5,2),
    head_circumference DECIMAL(5,2),
    notes TEXT,
    is_abnormal BOOLEAN,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Nursing Assessments table
CREATE TABLE IF NOT EXISTS nursing_assessments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    assessment_date TIMESTAMP NOT NULL,
    assessment_type VARCHAR(50) NOT NULL,
    findings TEXT,
    nursing_diagnosis VARCHAR(500),
    interventions TEXT,
    outcome TEXT,
    assessed_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Nursing Care Plans table
CREATE TABLE IF NOT EXISTS nursing_care_plans (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    plan_type VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    goals TEXT,
    interventions TEXT,
    evaluation TEXT,
    created_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by_name VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Nursing Notes table
CREATE TABLE IF NOT EXISTS nursing_notes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    note_date TIMESTAMP NOT NULL,
    note_type VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    created_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by_name VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Medication Administration table
CREATE TABLE IF NOT EXISTS medication_administration (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    medication_name VARCHAR(200) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    route VARCHAR(100),
    scheduled_time TIMESTAMP NOT NULL,
    administered_time TIMESTAMP,
    administered_by UUID,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Fluid Balance table
CREATE TABLE IF NOT EXISTS fluid_balance (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    record_date DATE NOT NULL,
    shift VARCHAR(50) NOT NULL,
    intake_oral DECIMAL(10,2),
    intake_iv DECIMAL(10,2),
    intake_other DECIMAL(10,2),
    intake_total DECIMAL(10,2),
    output_urine DECIMAL(10,2),
    output_stool DECIMAL(10,2),
    output_other DECIMAL(10,2),
    output_total DECIMAL(10,2),
    balance DECIMAL(10,2),
    notes TEXT,
    recorded_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Nursing Tasks table
CREATE TABLE IF NOT EXISTS nursing_tasks (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID,
    task_type VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    priority VARCHAR(20) NOT NULL,
    due_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    assigned_to UUID,
    completed_at TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Incident Reports table
CREATE TABLE IF NOT EXISTS incident_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID,
    incident_type VARCHAR(50) NOT NULL,
    incident_date TIMESTAMP NOT NULL,
    location VARCHAR(200),
    description TEXT NOT NULL,
    severity VARCHAR(50),
    actions_taken TEXT,
    reported_by UUID,
    reported_at TIMESTAMP NOT NULL,
    reviewed_by UUID,
    reviewed_at TIMESTAMP,
    follow_up_actions TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Wound Care table
CREATE TABLE IF NOT EXISTS wound_care (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patient_id UUID NOT NULL,
    wound_location VARCHAR(200) NOT NULL,
    wound_type VARCHAR(50) NOT NULL,
    wound_size VARCHAR(100),
    wound_stage VARCHAR(50),
    assessment_date DATE NOT NULL,
    assessment TEXT,
    treatment TEXT,
    dressing_type VARCHAR(100),
    next_assessment_date DATE,
    assessed_by UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

-- Nursing Shift table
CREATE TABLE IF NOT EXISTS nursing_shifts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    shift_date DATE NOT NULL,
    shift_type VARCHAR(50) NOT NULL,
    nurse_id UUID NOT NULL,
    unit VARCHAR(100),
    patient_count INTEGER,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (nurse_id) REFERENCES users(id)
);

-- ============================================
-- INDEXES FOR PERFORMANCE
-- ============================================

-- Users indexes
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_approval_status ON users(approval_status);

-- Patients indexes
CREATE INDEX IF NOT EXISTS idx_patients_medical_record_number ON patients(medical_record_number);
CREATE INDEX IF NOT EXISTS idx_patients_name ON patients(first_name, last_name);
CREATE INDEX IF NOT EXISTS idx_patients_status ON patients(status);

-- Appointments indexes
CREATE INDEX IF NOT EXISTS idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX IF NOT EXISTS idx_appointments_doctor_id ON appointments(doctor_id);
CREATE INDEX IF NOT EXISTS idx_appointments_date ON appointments(appointment_date);
CREATE INDEX IF NOT EXISTS idx_appointments_status ON appointments(status);

-- Consultations indexes
CREATE INDEX IF NOT EXISTS idx_consultations_patient_id ON consultations(patient_id);
CREATE INDEX IF NOT EXISTS idx_consultations_doctor_id ON consultations(doctor_id);
CREATE INDEX IF NOT EXISTS idx_consultations_date ON consultations(consultation_date);

-- Vital Signs indexes
CREATE INDEX IF NOT EXISTS idx_vital_signs_patient_id ON vital_signs(patient_id);
CREATE INDEX IF NOT EXISTS idx_vital_signs_recorded_date ON vital_signs(recorded_date);

-- Medical Records indexes
CREATE INDEX IF NOT EXISTS idx_medical_records_patient_id ON medical_records(patient_id);
CREATE INDEX IF NOT EXISTS idx_medical_records_type ON medical_records(record_type);
CREATE INDEX IF NOT EXISTS idx_medical_records_date ON medical_records(record_date);

-- Lab Results indexes
CREATE INDEX IF NOT EXISTS idx_lab_results_patient_id ON lab_results(patient_id);
CREATE INDEX IF NOT EXISTS idx_lab_results_test_date ON lab_results(test_date);
CREATE INDEX IF NOT EXISTS idx_lab_results_status ON lab_results(status);

-- Approval Requests indexes
CREATE INDEX IF NOT EXISTS idx_approval_requests_user_id ON approval_requests(user_id);
CREATE INDEX IF NOT EXISTS idx_approval_requests_status ON approval_requests(status);

-- User Documents indexes
CREATE INDEX IF NOT EXISTS idx_user_documents_user_id ON user_documents(user_id);
CREATE INDEX IF NOT EXISTS idx_user_documents_status ON user_documents(document_status);

-- ============================================
-- INSERT DEFAULT DATA
-- ============================================

-- Insert default roles
INSERT INTO roles (name, description, created_at, updated_at) VALUES
('ADMIN', 'Administrator with full system access', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('DOCTOR', 'Doctor with patient management permissions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('NURSE', 'Nurse with patient care permissions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RECEPTIONIST', 'Receptionist with appointment management permissions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PATIENT', 'Patient with limited access to own records', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- Insert default permissions
INSERT INTO permissions (name, description, created_at, updated_at) VALUES
('USER_READ', 'Read user information', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('USER_WRITE', 'Create and update users', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('USER_DELETE', 'Delete users', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PATIENT_READ', 'Read patient information', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PATIENT_WRITE', 'Create and update patients', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PATIENT_DELETE', 'Delete patients', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('APPOINTMENT_READ', 'Read appointments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('APPOINTMENT_WRITE', 'Create and update appointments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('APPOINTMENT_DELETE', 'Delete appointments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('MEDICAL_RECORD_READ', 'Read medical records', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('MEDICAL_RECORD_WRITE', 'Create and update medical records', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('MEDICAL_RECORD_DELETE', 'Delete medical records', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PRESCRIPTION_READ', 'Read prescriptions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PRESCRIPTION_WRITE', 'Create and update prescriptions', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('APPROVAL_READ', 'Read approval requests', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('APPROVAL_WRITE', 'Process approval requests', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- Assign permissions to admin role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

-- Assign basic permissions to doctor role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'DOCTOR' AND p.name IN ('PATIENT_READ', 'PATIENT_WRITE', 'APPOINTMENT_READ', 'APPOINTMENT_WRITE', 'MEDICAL_RECORD_READ', 'MEDICAL_RECORD_WRITE', 'PRESCRIPTION_READ', 'PRESCRIPTION_WRITE')
ON CONFLICT DO NOTHING;

-- Assign basic permissions to nurse role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'NURSE' AND p.name IN ('PATIENT_READ', 'PATIENT_WRITE', 'APPOINTMENT_READ', 'MEDICAL_RECORD_READ', 'MEDICAL_RECORD_WRITE')
ON CONFLICT DO NOTHING;

-- Assign basic permissions to receptionist role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p 
WHERE r.name = 'RECEPTIONIST' AND p.name IN ('PATIENT_READ', 'PATIENT_WRITE', 'APPOINTMENT_READ', 'APPOINTMENT_WRITE')
ON CONFLICT DO NOTHING;
