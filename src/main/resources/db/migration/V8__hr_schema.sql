-- HR Schema Migration
-- Create all HR-related tables

-- Employees table
CREATE TABLE IF NOT EXISTS hr_employees (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_number VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(10),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    employee_type VARCHAR(50) NOT NULL,
    department VARCHAR(100),
    position VARCHAR(100),
    hire_date DATE NOT NULL,
    termination_date DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    salary DECIMAL(15, 2),
    bank_name VARCHAR(100),
    bank_account_number VARCHAR(50),
    tax_id VARCHAR(50),
    social_security_number VARCHAR(50),
    emergency_contact_name VARCHAR(100),
    emergency_contact_phone VARCHAR(20),
    emergency_contact_relationship VARCHAR(50),
    profile_picture_url VARCHAR(500),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0
);

-- Attendance table
CREATE TABLE IF NOT EXISTS hr_attendance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    date DATE NOT NULL,
    check_in_time TIME,
    check_out_time TIME,
    status VARCHAR(50) NOT NULL,
    hours_worked DECIMAL(5, 2),
    overtime_hours DECIMAL(5, 2),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Leave Requests table
CREATE TABLE IF NOT EXISTS hr_leave_requests (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    leave_type VARCHAR(50) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_days INTEGER NOT NULL,
    reason TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    approved_by UUID,
    approved_on TIMESTAMP,
    rejection_reason TEXT,
    attachment_url VARCHAR(500),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_leave_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Payroll table
CREATE TABLE IF NOT EXISTS hr_payroll (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    pay_period_start DATE NOT NULL,
    pay_period_end DATE NOT NULL,
    pay_date DATE,
    gross_pay DECIMAL(15, 2) NOT NULL,
    net_pay DECIMAL(15, 2) NOT NULL,
    tax_deduction DECIMAL(15, 2),
    insurance_deduction DECIMAL(15, 2),
    other_deductions DECIMAL(15, 2),
    bonuses DECIMAL(15, 2),
    overtime_pay DECIMAL(15, 2),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    transaction_id VARCHAR(100),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_payroll_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Performance Reviews table
CREATE TABLE IF NOT EXISTS hr_performance_reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    reviewer_id UUID NOT NULL,
    review_period_start DATE NOT NULL,
    review_period_end DATE NOT NULL,
    review_date DATE NOT NULL,
    rating VARCHAR(50) NOT NULL,
    goals_achieved TEXT,
    areas_for_improvement TEXT,
    strengths TEXT,
    comments TEXT,
    employee_comments TEXT,
    development_plan TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_performance_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE,
    CONSTRAINT fk_performance_reviewer FOREIGN KEY (reviewer_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Recruitment table
CREATE TABLE IF NOT EXISTS hr_recruitment (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    job_title VARCHAR(200) NOT NULL,
    department VARCHAR(100),
    description TEXT,
    requirements TEXT,
    responsibilities TEXT,
    vacancies INTEGER NOT NULL,
    posting_date DATE NOT NULL,
    closing_date DATE,
    status VARCHAR(20),
    salary_range VARCHAR(100),
    location VARCHAR(100),
    employment_type VARCHAR(50),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0
);

-- Training table
CREATE TABLE IF NOT EXISTS hr_training (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    training_name VARCHAR(200) NOT NULL,
    description TEXT,
    training_type VARCHAR(50),
    start_date DATE NOT NULL,
    end_date DATE,
    location VARCHAR(100),
    instructor VARCHAR(100),
    cost DECIMAL(10, 2),
    max_participants INTEGER,
    status VARCHAR(20),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0
);

-- Training Enrollments table
CREATE TABLE IF NOT EXISTS hr_training_enrollments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    training_id UUID,
    employee_id UUID NOT NULL,
    enrollment_date DATE NOT NULL,
    completion_date DATE,
    status VARCHAR(20),
    certificate_url VARCHAR(500),
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_enrollment_training FOREIGN KEY (training_id) REFERENCES hr_training(id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollment_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Benefits table
CREATE TABLE IF NOT EXISTS hr_benefits (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    benefit_type VARCHAR(50) NOT NULL,
    plan_name VARCHAR(100),
    provider VARCHAR(100),
    coverage_amount DECIMAL(15, 2),
    employee_contribution DECIMAL(15, 2),
    employer_contribution DECIMAL(15, 2),
    enrollment_date DATE,
    effective_date DATE,
    termination_date DATE,
    status VARCHAR(20),
    dependents JSON,
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_benefits_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Compliance table
CREATE TABLE IF NOT EXISTS hr_compliance (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL,
    compliance_type VARCHAR(50) NOT NULL,
    document_name VARCHAR(200),
    document_url VARCHAR(500),
    issuing_authority VARCHAR(200),
    issue_date DATE,
    expiry_date DATE,
    status VARCHAR(20),
    reminder_date DATE,
    notes TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by UUID,
    updated_by UUID,
    version INTEGER DEFAULT 0,
    CONSTRAINT fk_compliance_employee FOREIGN KEY (employee_id) REFERENCES hr_employees(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_hr_employees_employee_number ON hr_employees(employee_number);
CREATE INDEX IF NOT EXISTS idx_hr_employees_email ON hr_employees(email);
CREATE INDEX IF NOT EXISTS idx_hr_employees_department ON hr_employees(department);
CREATE INDEX IF NOT EXISTS idx_hr_employees_status ON hr_employees(status);

CREATE INDEX IF NOT EXISTS idx_hr_attendance_employee_id ON hr_attendance(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_attendance_date ON hr_attendance(date);

CREATE INDEX IF NOT EXISTS idx_hr_leave_requests_employee_id ON hr_leave_requests(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_leave_requests_status ON hr_leave_requests(status);
CREATE INDEX IF NOT EXISTS idx_hr_leave_requests_dates ON hr_leave_requests(start_date, end_date);

CREATE INDEX IF NOT EXISTS idx_hr_payroll_employee_id ON hr_payroll(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_payroll_period ON hr_payroll(pay_period_start, pay_period_end);
CREATE INDEX IF NOT EXISTS idx_hr_payroll_status ON hr_payroll(status);

CREATE INDEX IF NOT EXISTS idx_hr_performance_reviews_employee_id ON hr_performance_reviews(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_performance_reviews_reviewer_id ON hr_performance_reviews(reviewer_id);

CREATE INDEX IF NOT EXISTS idx_hr_recruitment_status ON hr_recruitment(status);
CREATE INDEX IF NOT EXISTS idx_hr_recruitment_department ON hr_recruitment(department);

CREATE INDEX IF NOT EXISTS idx_hr_training_status ON hr_training(status);
CREATE INDEX IF NOT EXISTS idx_hr_training_dates ON hr_training(start_date, end_date);

CREATE INDEX IF NOT EXISTS idx_hr_training_enrollments_employee_id ON hr_training_enrollments(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_training_enrollments_training_id ON hr_training_enrollments(training_id);

CREATE INDEX IF NOT EXISTS idx_hr_benefits_employee_id ON hr_benefits(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_benefits_type ON hr_benefits(benefit_type);

CREATE INDEX IF NOT EXISTS idx_hr_compliance_employee_id ON hr_compliance(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_compliance_type ON hr_compliance(compliance_type);
CREATE INDEX IF NOT EXISTS idx_hr_compliance_expiry ON hr_compliance(expiry_date);
