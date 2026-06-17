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
