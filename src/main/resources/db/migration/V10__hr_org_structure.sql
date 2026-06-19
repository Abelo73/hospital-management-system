-- V10: HR Organisational Structure
-- Adds: branches, salary grades, positions, employee profile extensions,
--       employee documents, transfer history, promotion history

-- ─────────────────────────────────────────────
-- 1. Branches / Hospital locations
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS hr_branches (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    address TEXT,
    city VARCHAR(100),
    country VARCHAR(100),
    phone VARCHAR(30),
    email VARCHAR(255),
    branch_type VARCHAR(50) NOT NULL DEFAULT 'HOSPITAL',
    parent_branch_id UUID REFERENCES hr_branches(id) ON DELETE SET NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_hr_branches_status ON hr_branches(status);
CREATE INDEX IF NOT EXISTS idx_hr_branches_parent ON hr_branches(parent_branch_id);

-- ─────────────────────────────────────────────
-- 1b. Departments
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS hr_departments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(200) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    branch_id UUID REFERENCES hr_branches(id) ON DELETE SET NULL,
    department_head_employee_id UUID,
    budget DECIMAL(15,2),
    description TEXT,
    parent_department_id UUID REFERENCES hr_departments(id) ON DELETE SET NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_hr_departments_branch ON hr_departments(branch_id);
CREATE INDEX IF NOT EXISTS idx_hr_departments_status ON hr_departments(status);

-- ─────────────────────────────────────────────
-- 2. Salary Grades
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS hr_salary_grades (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    min_salary DECIMAL(15,2) NOT NULL,
    max_salary DECIMAL(15,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

-- ─────────────────────────────────────────────
-- 3. Positions  (references hr_departments from V8 and hr_salary_grades)
-- ─────────────────────────────────────────────
-- Note: hr_departments already exists from V8 migration
CREATE TABLE IF NOT EXISTS hr_positions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(200) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    department_id UUID REFERENCES hr_departments(id) ON DELETE SET NULL,
    grade_id UUID REFERENCES hr_salary_grades(id) ON DELETE SET NULL,
    min_salary DECIMAL(15,2),
    max_salary DECIMAL(15,2),
    responsibilities TEXT,
    required_skills TEXT,
    required_qualifications TEXT,
    reporting_position_id UUID REFERENCES hr_positions(id) ON DELETE SET NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_hr_positions_department ON hr_positions(department_id);
CREATE INDEX IF NOT EXISTS idx_hr_positions_grade ON hr_positions(grade_id);

-- ─────────────────────────────────────────────
-- 4. Extend hr_employees with new profile fields
-- ─────────────────────────────────────────────
ALTER TABLE hr_employees
    ADD COLUMN IF NOT EXISTS photo_url VARCHAR(500),
    ADD COLUMN IF NOT EXISTS marital_status VARCHAR(30),
    ADD COLUMN IF NOT EXISTS nationality VARCHAR(100),
    ADD COLUMN IF NOT EXISTS religion VARCHAR(100),
    ADD COLUMN IF NOT EXISTS blood_group VARCHAR(10),
    ADD COLUMN IF NOT EXISTS disability_flag BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS work_location VARCHAR(100),
    ADD COLUMN IF NOT EXISTS branch_id UUID REFERENCES hr_branches(id) ON DELETE SET NULL,
    ADD COLUMN IF NOT EXISTS position_id UUID REFERENCES hr_positions(id) ON DELETE SET NULL,
    ADD COLUMN IF NOT EXISTS supervisor_employee_id UUID REFERENCES hr_employees(id) ON DELETE SET NULL,
    ADD COLUMN IF NOT EXISTS employment_category VARCHAR(30),
    ADD COLUMN IF NOT EXISTS passport_number VARCHAR(50),
    ADD COLUMN IF NOT EXISTS national_id VARCHAR(50),
    ADD COLUMN IF NOT EXISTS driver_licence_number VARCHAR(50),
    ADD COLUMN IF NOT EXISTS bank_account_holder VARCHAR(100),
    ADD COLUMN IF NOT EXISTS bank_branch VARCHAR(100),
    ADD COLUMN IF NOT EXISTS housing_allowance DECIMAL(15,2),
    ADD COLUMN IF NOT EXISTS transport_allowance DECIMAL(15,2),
    ADD COLUMN IF NOT EXISTS medical_allowance DECIMAL(15,2),
    ADD COLUMN IF NOT EXISTS meal_allowance DECIMAL(15,2),
    ADD COLUMN IF NOT EXISTS tax_group_id UUID;

CREATE INDEX IF NOT EXISTS idx_hr_employees_branch_id ON hr_employees(branch_id);
CREATE INDEX IF NOT EXISTS idx_hr_employees_position_id ON hr_employees(position_id);
CREATE INDEX IF NOT EXISTS idx_hr_employees_supervisor ON hr_employees(supervisor_employee_id);

-- ─────────────────────────────────────────────
-- 5. Employee Documents
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS hr_employee_documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id) ON DELETE CASCADE,
    document_type VARCHAR(50) NOT NULL,
    file_url VARCHAR(500) NOT NULL,
    file_name VARCHAR(200),
    upload_date DATE NOT NULL DEFAULT CURRENT_DATE,
    expiry_date DATE,
    notes TEXT,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_hr_employee_docs_employee ON hr_employee_documents(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_employee_docs_type ON hr_employee_documents(document_type);

-- ─────────────────────────────────────────────
-- 6. Employee Transfer History
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS hr_employee_transfers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id) ON DELETE CASCADE,
    from_branch_id UUID REFERENCES hr_branches(id) ON DELETE SET NULL,
    to_branch_id UUID REFERENCES hr_branches(id) ON DELETE SET NULL,
    from_department_id UUID REFERENCES hr_departments(id) ON DELETE SET NULL,
    to_department_id UUID REFERENCES hr_departments(id) ON DELETE SET NULL,
    effective_date DATE NOT NULL,
    reason TEXT,
    approved_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_hr_transfers_employee ON hr_employee_transfers(employee_id);

-- ─────────────────────────────────────────────
-- 7. Employee Promotion History
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS hr_employee_promotions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id) ON DELETE CASCADE,
    from_position_id UUID REFERENCES hr_positions(id) ON DELETE SET NULL,
    to_position_id UUID REFERENCES hr_positions(id) ON DELETE SET NULL,
    old_salary DECIMAL(15,2),
    new_salary DECIMAL(15,2),
    effective_date DATE NOT NULL,
    justification TEXT,
    approved_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE INDEX IF NOT EXISTS idx_hr_promotions_employee ON hr_employee_promotions(employee_id);
