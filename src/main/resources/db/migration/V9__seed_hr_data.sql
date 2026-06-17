-- Seed Data for HR Module
-- This migration populates HR tables with sample data for testing and development

-- Insert Employees
INSERT INTO hr_employees (id, employee_number, first_name, last_name, middle_name, email, phone_number, date_of_birth, gender, address, city, state, country, postal_code, employee_type, department, position, hire_date, status, salary, bank_name, bank_account_number, tax_id, social_security_number, emergency_contact_name, emergency_contact_phone, emergency_contact_relationship, notes, created_by)
VALUES
(gen_random_uuid(), 'EMP001', 'John', 'Smith', 'Michael', 'john.smith@hospital.com', '+254712345678', '1985-03-15', 'MALE', '123 Medical Center Dr', 'Nairobi', 'Nairobi', 'Kenya', '00100', 'DOCTOR', 'Cardiology', 'Senior Cardiologist', '2020-01-15', 'ACTIVE', 150000.00, 'Equity Bank', '1234567890', 'A001234567', 'SS001234567', 'Jane Smith', '+254712345679', 'Spouse', 'Board certified cardiologist with 10 years experience', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP002', 'Sarah', 'Johnson', 'Elizabeth', 'sarah.johnson@hospital.com', '+254712345680', '1990-07-22', 'FEMALE', '456 Health Ave', 'Nairobi', 'Nairobi', 'Kenya', '00100', 'NURSE', 'ICU', 'Head Nurse', '2019-06-01', 'ACTIVE', 85000.00, 'KCB Bank', '0987654321', 'A009876543', 'SS009876543', 'Robert Johnson', '+254712345681', 'Father', 'Specialized in critical care nursing', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP003', 'David', 'Williams', NULL, 'david.williams@hospital.com', '+254712345682', '1988-11-30', 'MALE', '789 Clinic Rd', 'Mombasa', 'Mombasa', 'Kenya', '80100', 'PHARMACIST', 'Pharmacy', 'Chief Pharmacist', '2021-03-10', 'ACTIVE', 95000.00, 'Cooperative Bank', '1122334455', 'A011223344', 'SS011223344', 'Mary Williams', '+254712345683', 'Mother', 'Expert in pharmaceutical management', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP004', 'Emily', 'Brown', 'Rose', 'emily.brown@hospital.com', '+254712345684', '1992-05-18', 'FEMALE', '321 Hospital Ln', 'Kisumu', 'Kisumu', 'Kenya', '40100', 'LAB_TECHNICIAN', 'Laboratory', 'Senior Lab Technician', '2020-09-20', 'ACTIVE', 70000.00, 'Equity Bank', '5566778899', 'A055667788', 'SS055667788', 'James Brown', '+254712345685', 'Brother', 'Specialized in hematology and microbiology', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP005', 'Michael', 'Davis', 'Thomas', 'michael.davis@hospital.com', '+254712345686', '1980-08-25', 'MALE', '654 Wellness Blvd', 'Nakuru', 'Nakuru', 'Kenya', '20100', 'MANAGEMENT', 'Administration', 'Hospital Administrator', '2018-01-05', 'ACTIVE', 200000.00, 'Standard Chartered', '9988776655', 'A099887766', 'SS099887766', 'Linda Davis', '+254712345687', 'Spouse', 'MBA with 15 years healthcare administration experience', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP006', 'Lisa', 'Anderson', NULL, 'lisa.anderson@hospital.com', '+254712345688', '1995-02-14', 'FEMALE', '987 Care Way', 'Eldoret', 'Uasin Gishu', 'Kenya', '30100', 'NURSE', 'Pediatrics', 'Staff Nurse', '2022-02-15', 'ACTIVE', 65000.00, 'KCB Bank', '4455667788', 'A044556677', 'SS044556677', 'Tom Anderson', '+254712345689', 'Father', 'Pediatric nursing specialist', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP007', 'Robert', 'Taylor', 'James', 'robert.taylor@hospital.com', '+254712345690', '1983-12-03', 'MALE', '147 Medical Park', 'Nairobi', 'Nairobi', 'Kenya', '00100', 'DOCTOR', 'Orthopedics', 'Orthopedic Surgeon', '2019-11-12', 'ACTIVE', 160000.00, 'Cooperative Bank', '3344556677', 'A033445566', 'SS033445566', 'Susan Taylor', '+254712345691', 'Spouse', 'Specialized in sports medicine and joint replacement', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP008', 'Jennifer', 'Martinez', 'Maria', 'jennifer.martinez@hospital.com', '+254712345692', '1991-09-28', 'FEMALE', '258 Health Center', 'Nairobi', 'Nairobi', 'Kenya', '00100', 'ADMINISTRATIVE', 'HR', 'HR Manager', '2020-07-01', 'ACTIVE', 90000.00, 'Equity Bank', '7788990011', 'A077889900', 'SS077889900', 'Carlos Martinez', '+254712345693', 'Brother', 'SHRM certified HR professional', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP009', 'James', 'Wilson', NULL, 'james.wilson@hospital.com', '+254712345694', '1987-04-10', 'MALE', '369 Clinic St', 'Mombasa', 'Mombasa', 'Kenya', '80100', 'SUPPORT_STAFF', 'Maintenance', 'Maintenance Supervisor', '2021-01-20', 'ACTIVE', 55000.00, 'KCB Bank', '2233445566', 'A022334455', 'SS022334455', 'Patricia Wilson', '+254712345695', 'Spouse', 'Expert in facility management and equipment maintenance', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'EMP010', 'Amanda', 'Garcia', 'Lynn', 'amanda.garcia@hospital.com', '+254712345696', '1993-06-17', 'FEMALE', '741 Wellness Ave', 'Kisumu', 'Kisumu', 'Kenya', '40100', 'NURSE', 'Emergency', 'ER Nurse', '2021-08-15', 'ACTIVE', 75000.00, 'Standard Chartered', '6655443322', 'A066554433', 'SS066554433', 'Richard Garcia', '+254712345697', 'Father', 'Trauma and emergency care specialist', (SELECT id FROM users WHERE username = 'admin'));

-- Insert Attendance Records (for last 5 days)
INSERT INTO hr_attendance (id, employee_id, date, check_in_time, check_out_time, status, hours_worked, overtime_hours, notes, created_by)
SELECT 
    gen_random_uuid(),
    e.id,
    CURRENT_DATE - (n || ' days')::interval,
    '08:00:00',
    '17:00:00',
    CASE WHEN n = 3 THEN 'LATE' ELSE 'PRESENT' END,
    CASE WHEN n = 3 THEN 7.5 ELSE 9.0 END,
    CASE WHEN n = 3 THEN 0 ELSE 1.0 END,
    CASE WHEN n = 3 THEN 'Traffic delay' ELSE 'Regular shift' END,
    (SELECT id FROM users WHERE username = 'admin')
FROM hr_employees e
CROSS JOIN (SELECT 0 as n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4) numbers
WHERE e.employee_number IN ('EMP001', 'EMP002', 'EMP003', 'EMP004', 'EMP005');

-- Insert Leave Requests
INSERT INTO hr_leave_requests (id, employee_id, leave_type, start_date, end_date, total_days, reason, status, approved_by, approved_on, notes, created_by)
VALUES
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP002'), 'ANNUAL', CURRENT_DATE + 7, CURRENT_DATE + 14, 7, 'Family vacation', 'APPROVED', (SELECT id FROM hr_employees WHERE employee_number = 'EMP005'), CURRENT_TIMESTAMP, 'Approved in advance', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP006'), 'SICK', CURRENT_DATE - 2, CURRENT_DATE - 1, 2, 'Flu symptoms', 'APPROVED', (SELECT id FROM hr_employees WHERE employee_number = 'EMP008'), CURRENT_TIMESTAMP - INTERVAL '2 days', 'Medical certificate provided', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP004'), 'MATERNITY', CURRENT_DATE + 30, CURRENT_DATE + 119, 90, 'Expected due date', 'APPROVED', (SELECT id FROM hr_employees WHERE employee_number = 'EMP008'), CURRENT_TIMESTAMP, 'Maternity leave approved per policy', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP010'), 'COMPASSIONATE', CURRENT_DATE - 5, CURRENT_DATE - 3, 3, 'Family funeral', 'APPROVED', (SELECT id FROM hr_employees WHERE employee_number = 'EMP008'), CURRENT_TIMESTAMP - INTERVAL '5 days', 'Bereavement leave', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP009'), 'STUDY', CURRENT_DATE + 60, CURRENT_DATE + 64, 5, 'Technical certification course', 'PENDING', NULL, NULL, 'Awaiting manager approval', (SELECT id FROM users WHERE username = 'admin'));

-- Insert Payroll Records (for current month)
INSERT INTO hr_payroll (id, employee_id, pay_period_start, pay_period_end, pay_date, gross_pay, net_pay, tax_deduction, insurance_deduction, other_deductions, bonuses, overtime_pay, status, payment_method, transaction_id, notes, created_by)
SELECT 
    gen_random_uuid(),
    e.id,
    DATE_TRUNC('MONTH', CURRENT_DATE),
    DATE_TRUNC('MONTH', CURRENT_DATE) + INTERVAL '1 month - 1 day',
    DATE_TRUNC('MONTH', CURRENT_DATE) + INTERVAL '1 month',
    e.salary,
    e.salary * 0.75,
    e.salary * 0.15,
    e.salary * 0.05,
    e.salary * 0.05,
    CASE WHEN e.employee_type = 'DOCTOR' THEN e.salary * 0.1 ELSE 0 END,
    CASE WHEN e.employee_type = 'NURSE' THEN e.salary * 0.05 ELSE 0 END,
    'PAID',
    'BANK_TRANSFER',
    'TXN' || SUBSTRING(e.employee_number, 4) || TO_CHAR(CURRENT_DATE, 'YYYYMM'),
    'Monthly salary payment',
    (SELECT id FROM users WHERE username = 'admin')
FROM hr_employees e
WHERE e.employee_number IN ('EMP001', 'EMP002', 'EMP003', 'EMP004', 'EMP005', 'EMP006', 'EMP007', 'EMP008', 'EMP009', 'EMP010');

-- Insert Performance Reviews
INSERT INTO hr_performance_reviews (id, employee_id, reviewer_id, review_period_start, review_period_end, review_date, rating, goals_achieved, areas_for_improvement, strengths, comments, development_plan, created_by)
VALUES
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP001'), (SELECT id FROM hr_employees WHERE employee_number = 'EMP005'), DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 year', DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 day', CURRENT_DATE - INTERVAL '1 month', 'EXCEEDS_EXPECTATIONS', 'Achieved all KPIs, published 2 research papers', 'Time management for administrative tasks', 'Excellent clinical skills, strong leadership', 'Outstanding performance this year', 'Leadership training program', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP002'), (SELECT id FROM hr_employees WHERE employee_number = 'EMP005'), DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 year', DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 day', CURRENT_DATE - INTERVAL '1 month', 'MEETS_EXPECTATIONS', 'Maintained high patient satisfaction scores', 'Documentation accuracy', 'Compassionate patient care, team player', 'Consistent performance', 'Advanced life support certification', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP003'), (SELECT id FROM hr_employees WHERE employee_number = 'EMP005'), DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 year', DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 day', CURRENT_DATE - INTERVAL '1 month', 'EXCEEDS_EXPECTATIONS', 'Reduced medication errors by 40%', 'Staff mentoring', 'Strong attention to detail, process improvement', 'Excellent contribution to patient safety', 'Management training', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP006'), (SELECT id FROM hr_employees WHERE employee_number = 'EMP002'), DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 year', DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 day', CURRENT_DATE - INTERVAL '1 month', 'MEETS_EXPECTATIONS', 'Good patient care outcomes', 'Communication with physicians', 'Quick learner, dedicated', 'Solid performance in first year', 'Pediatric specialization course', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), (SELECT id FROM hr_employees WHERE employee_number = 'EMP008'), (SELECT id FROM hr_employees WHERE employee_number = 'EMP005'), DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 year', DATE_TRUNC('YEAR', CURRENT_DATE) - INTERVAL '1 day', CURRENT_DATE - INTERVAL '1 month', 'EXCEEDS_EXPECTATIONS', 'Implemented new HR system, reduced turnover', 'Strategic planning', 'Excellent organizational skills, innovation', 'Transformed HR operations', 'Strategic HR management certification', (SELECT id FROM users WHERE username = 'admin'));

-- Insert Recruitment (Job Postings)
INSERT INTO hr_recruitment (id, job_title, department, description, requirements, responsibilities, vacancies, posting_date, closing_date, status, salary_range, location, employment_type, notes, created_by)
VALUES
(gen_random_uuid(), 'Senior Cardiologist', 'Cardiology', 'We are seeking an experienced cardiologist to join our team', 'MBBS, MD in Cardiology, 5+ years experience', 'Diagnose and treat heart conditions, perform procedures', 2, CURRENT_DATE - INTERVAL '15 days', CURRENT_DATE + INTERVAL '15 days', 'OPEN', 'KES 150,000 - 200,000', 'Nairobi', 'FULL_TIME', 'Urgent requirement due to expansion', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Registered Nurse - ICU', 'ICU', 'Looking for dedicated ICU nurses for critical care unit', 'BSc Nursing, ICU certification, 2+ years experience', 'Monitor critically ill patients, administer medications', 5, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '20 days', 'OPEN', 'KES 70,000 - 90,000', 'Nairobi', 'FULL_TIME', 'Night shift available', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Medical Laboratory Technologist', 'Laboratory', 'Join our state-of-the-art laboratory team', 'BSc Medical Laboratory Technology, MLRB license', 'Conduct lab tests, maintain equipment, quality control', 3, CURRENT_DATE - INTERVAL '5 days', CURRENT_DATE + INTERVAL '25 days', 'OPEN', 'KES 60,000 - 80,000', 'Nairobi', 'FULL_TIME', 'Various shifts available', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Hospital Administrator', 'Administration', 'Seeking experienced administrator for hospital operations', 'MBA in Healthcare Management, 10+ years experience', 'Oversee daily operations, strategic planning, budget management', 1, CURRENT_DATE - INTERVAL '20 days', CURRENT_DATE + INTERVAL '10 days', 'OPEN', 'KES 180,000 - 250,000', 'Nairobi', 'FULL_TIME', 'Executive position', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Pharmacist', 'Pharmacy', 'Join our pharmacy team for excellent patient care', 'BPharm, valid pharmacy license', 'Dispense medications, counsel patients, manage inventory', 2, CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 'OPEN', 'KES 80,000 - 100,000', 'Mombasa', 'FULL_TIME', 'New branch opening', (SELECT id FROM users WHERE username = 'admin'));

-- Insert Training Programs
INSERT INTO hr_training (id, training_name, description, training_type, start_date, end_date, location, instructor, cost, max_participants, status, notes, created_by)
VALUES
(gen_random_uuid(), 'Advanced Cardiac Life Support', 'ACLS certification for medical staff', 'CERTIFICATION', CURRENT_DATE + INTERVAL '10 days', CURRENT_DATE + INTERVAL '12 days', 'Nairobi', 'Dr. John Smith', 15000.00, 20, 'SCHEDULED', 'Mandatory for ICU and Cardiology staff', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Patient Safety and Quality Improvement', 'Training on patient safety protocols', 'INTERNAL', CURRENT_DATE + INTERVAL '5 days', CURRENT_DATE + INTERVAL '5 days', 'Nairobi', 'Quality Assurance Team', 0.00, 50, 'SCHEDULED', 'Mandatory for all clinical staff', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Leadership and Management', 'Management skills for supervisors', 'PROFESSIONAL', CURRENT_DATE + INTERVAL '20 days', CURRENT_DATE + INTERVAL '22 days', 'Nairobi', 'External Consultant', 25000.00, 15, 'SCHEDULED', 'For department heads and supervisors', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Infection Control Training', 'Prevention of healthcare-associated infections', 'INTERNAL', CURRENT_DATE + INTERVAL '15 days', CURRENT_DATE + INTERVAL '15 days', 'Nairobi', 'Infection Control Nurse', 0.00, 30, 'SCHEDULED', 'Annual mandatory training', (SELECT id FROM users WHERE username = 'admin')),
(gen_random_uuid(), 'Electronic Medical Records System', 'Training on new EMR system', 'TECHNICAL', CURRENT_DATE + INTERVAL '7 days', CURRENT_DATE + INTERVAL '8 days', 'Nairobi', 'IT Department', 0.00, 25, 'SCHEDULED', 'Rollout training for all staff', (SELECT id FROM users WHERE username = 'admin'));
