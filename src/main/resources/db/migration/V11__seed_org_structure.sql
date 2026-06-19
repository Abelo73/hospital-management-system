-- V11: Seed data for Organisational Structure
-- Populates: hr_branches, hr_departments, hr_salary_grades, hr_positions
-- and links existing hr_employees to their new branch/position records

-- ─────────────────────────────────────────────
-- 1. Salary Grades
-- ─────────────────────────────────────────────
INSERT INTO hr_salary_grades (id, name, code, min_salary, max_salary, currency, created_by) VALUES
('a1000001-0000-0000-0000-000000000001', 'Grade 1 – Support Staff',  'G1', 30000,  60000,  'KES', 'system'),
('a1000001-0000-0000-0000-000000000002', 'Grade 2 – Technician',     'G2', 55000,  90000,  'KES', 'system'),
('a1000001-0000-0000-0000-000000000003', 'Grade 3 – Officer',        'G3', 80000,  130000, 'KES', 'system'),
('a1000001-0000-0000-0000-000000000004', 'Grade 4 – Senior Officer', 'G4', 120000, 180000, 'KES', 'system'),
('a1000001-0000-0000-0000-000000000005', 'Grade 5 – Manager',        'G5', 160000, 250000, 'KES', 'system'),
('a1000001-0000-0000-0000-000000000006', 'Grade 6 – Director',       'G6', 220000, 350000, 'KES', 'system'),
('a1000001-0000-0000-0000-000000000007', 'Grade 7 – Executive',      'G7', 300000, 500000, 'KES', 'system');

-- ─────────────────────────────────────────────
-- 2. Branches
-- ─────────────────────────────────────────────
INSERT INTO hr_branches (id, name, code, address, city, country, phone, email, branch_type, status, created_by) VALUES
('b1000001-0000-0000-0000-000000000001', 'Nairobi Main Hospital',    'NBI-MAIN',  'Argwings Kodhek Rd, Upper Hill', 'Nairobi',  'Kenya', '+254 20 722 0000', 'nairobi@medicarehms.ke',  'HOSPITAL', 'ACTIVE', 'system'),
('b1000001-0000-0000-0000-000000000002', 'Mombasa Coastal Branch',   'MBA-COAST', 'Nkrumah Rd, Mombasa CBD',        'Mombasa',  'Kenya', '+254 41 222 0000', 'mombasa@medicarehms.ke',  'HOSPITAL', 'ACTIVE', 'system'),
('b1000001-0000-0000-0000-000000000003', 'Kisumu Western Branch',    'KSM-WEST',  'Oginga Odinga St, Kisumu CBD',   'Kisumu',   'Kenya', '+254 57 202 0000', 'kisumu@medicarehms.ke',   'HOSPITAL', 'ACTIVE', 'system'),
('b1000001-0000-0000-0000-000000000004', 'Nakuru Rift Valley Clinic','NKR-RIFT',  'Kenyatta Ave, Nakuru CBD',       'Nakuru',   'Kenya', '+254 51 212 0000', 'nakuru@medicarehms.ke',   'CLINIC',   'ACTIVE', 'system'),
('b1000001-0000-0000-0000-000000000005', 'Eldoret North Clinic',     'ELD-NORTH', 'Uganda Rd, Eldoret CBD',          'Eldoret',  'Kenya', '+254 53 206 0000', 'eldoret@medicarehms.ke',  'CLINIC',   'ACTIVE', 'system'),
('b1000001-0000-0000-0000-000000000006', 'Head Office & Admin',      'HQ-ADMIN',  'Upper Hill, Nairobi CBD',         'Nairobi',  'Kenya', '+254 20 700 0001', 'hq@medicarehms.ke',       'ADMIN_OFFICE', 'ACTIVE', 'system');

-- ─────────────────────────────────────────────
-- 3. Departments  (must exist before hr_positions FK)
-- ─────────────────────────────────────────────
INSERT INTO hr_departments (id, name, code, branch_id, budget, description, status, created_by) VALUES
('c1000001-0000-0000-0000-000000000001', 'Cardiology',       'CARD', 'b1000001-0000-0000-0000-000000000001', 12000000.00, 'Cardiac diagnosis, intervention and care',         'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000002', 'Intensive Care Unit', 'ICU','b1000001-0000-0000-0000-000000000001', 8000000.00,  'Critical care for seriously ill patients',          'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000003', 'Pharmacy',         'PHRM', 'b1000001-0000-0000-0000-000000000001', 5000000.00,  'Dispensing, compounding and pharmaceutical care',   'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000004', 'Laboratory',       'LAB',  'b1000001-0000-0000-0000-000000000001', 6000000.00,  'Diagnostic laboratory services',                    'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000005', 'Administration',   'ADMN', 'b1000001-0000-0000-0000-000000000001', 3000000.00,  'Hospital administration and operations',             'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000006', 'Pediatrics',       'PEDS', 'b1000001-0000-0000-0000-000000000001', 4000000.00,  'Children health and paediatric services',            'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000007', 'Orthopedics',      'ORTH', 'b1000001-0000-0000-0000-000000000001', 7000000.00,  'Bone, joint and musculoskeletal care',               'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000008', 'Human Resources',  'HR',   'b1000001-0000-0000-0000-000000000006', 2000000.00,  'HR management, payroll and people operations',      'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000009', 'Maintenance',      'MAINT','b1000001-0000-0000-0000-000000000001', 2500000.00,  'Facilities, equipment and infrastructure',           'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000010', 'Emergency',        'ER',   'b1000001-0000-0000-0000-000000000001', 9000000.00,  'Emergency and trauma care',                          'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000011', 'Radiology',        'RADL', 'b1000001-0000-0000-0000-000000000001', 6500000.00,  'Imaging: X-Ray, MRI, CT and ultrasound',             'ACTIVE', 'system'),
('c1000001-0000-0000-0000-000000000012', 'Finance',          'FIN',  'b1000001-0000-0000-0000-000000000006', 2000000.00,  'Financial management, billing and accounting',       'ACTIVE', 'system');

-- ─────────────────────────────────────────────
-- 4. Positions
-- ─────────────────────────────────────────────
INSERT INTO hr_positions (id, title, code, department_id, grade_id, min_salary, max_salary, responsibilities, required_skills, required_qualifications, is_active, created_by) VALUES
-- Cardiology
('d1000001-0000-0000-0000-000000000001', 'Senior Cardiologist',      'SR-CARDIO',  'c1000001-0000-0000-0000-000000000001', 'a1000001-0000-0000-0000-000000000006', 130000, 200000, 'Diagnosis and treatment of cardiac conditions, performing interventional procedures', 'Clinical expertise, ECG interpretation, leadership', 'MBBS, MD Cardiology, 5+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000002', 'Cardiologist',             'CARDIO',     'c1000001-0000-0000-0000-000000000001', 'a1000001-0000-0000-0000-000000000005', 100000, 160000, 'Diagnosis and treatment of cardiac conditions',  'Clinical expertise, ECG interpretation', 'MBBS, MD Cardiology, 3+ years', TRUE, 'system'),
-- ICU
('d1000001-0000-0000-0000-000000000003', 'Head Nurse – ICU',         'HN-ICU',     'c1000001-0000-0000-0000-000000000002', 'a1000001-0000-0000-0000-000000000004', 80000, 120000,  'Lead ICU nursing team, monitor critical patients, coordinate care', 'Critical care, leadership, BLS/ACLS', 'BSc Nursing, ICU certification, 5+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000004', 'Staff Nurse – ICU',        'SN-ICU',     'c1000001-0000-0000-0000-000000000002', 'a1000001-0000-0000-0000-000000000003', 60000, 90000,   'Monitor critically ill patients, administer medications', 'Critical care, IV therapy, BLS', 'BSc Nursing, ICU certification', TRUE, 'system'),
-- Pharmacy
('d1000001-0000-0000-0000-000000000005', 'Chief Pharmacist',         'CH-PHRM',    'c1000001-0000-0000-0000-000000000003', 'a1000001-0000-0000-0000-000000000005', 90000, 140000,  'Oversee pharmacy operations, manage drug formulary, ensure compliance', 'Pharmaceutical management, leadership', 'BPharm, pharmacy licence, 5+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000006', 'Clinical Pharmacist',      'CL-PHRM',    'c1000001-0000-0000-0000-000000000003', 'a1000001-0000-0000-0000-000000000004', 70000, 110000,  'Counsel patients, review prescriptions, adverse drug monitoring', 'Clinical pharmacy, drug interactions', 'BPharm, 3+ years', TRUE, 'system'),
-- Laboratory
('d1000001-0000-0000-0000-000000000007', 'Senior Lab Technician',    'SR-LAB',     'c1000001-0000-0000-0000-000000000004', 'a1000001-0000-0000-0000-000000000004', 65000, 100000,  'Perform complex lab tests, QC, mentor junior staff', 'Haematology, microbiology, QC', 'BSc MLT, MLRB licence, 3+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000008', 'Lab Technician',           'LAB-TECH',   'c1000001-0000-0000-0000-000000000004', 'a1000001-0000-0000-0000-000000000003', 50000, 75000,   'Routine lab tests, sample handling, equipment maintenance', 'Lab analysis, safety protocols', 'BSc MLT, MLRB licence', TRUE, 'system'),
-- Administration
('d1000001-0000-0000-0000-000000000009', 'Hospital Administrator',   'HOSP-ADMIN', 'c1000001-0000-0000-0000-000000000005', 'a1000001-0000-0000-0000-000000000007', 180000, 280000, 'Oversee all hospital operations, strategic planning, budget management', 'Leadership, strategic planning, financial management', 'MBA Healthcare Management, 10+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000010', 'Administrative Officer',   'ADMIN-OFF',  'c1000001-0000-0000-0000-000000000005', 'a1000001-0000-0000-0000-000000000003', 50000, 80000,   'Administrative support, scheduling, records management', 'Administration, MS Office', 'Diploma in Business Administration', TRUE, 'system'),
-- Pediatrics
('d1000001-0000-0000-0000-000000000011', 'Paediatrician',            'PAEDS-DR',   'c1000001-0000-0000-0000-000000000006', 'a1000001-0000-0000-0000-000000000005', 100000, 160000, 'Diagnose and treat childhood diseases, preventive care', 'Paediatric medicine, communication with children', 'MBBS, MD Paediatrics, 3+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000012', 'Paediatric Nurse',         'PAEDS-RN',   'c1000001-0000-0000-0000-000000000006', 'a1000001-0000-0000-0000-000000000003', 55000, 85000,   'Provide nursing care to paediatric patients', 'Child health, paediatric nursing', 'BSc Nursing, 2+ years', TRUE, 'system'),
-- Orthopedics
('d1000001-0000-0000-0000-000000000013', 'Orthopaedic Surgeon',      'ORTH-SURG',  'c1000001-0000-0000-0000-000000000007', 'a1000001-0000-0000-0000-000000000006', 140000, 210000, 'Perform orthopaedic surgery, joint replacement, sports medicine', 'Surgical skills, orthopaedic procedures', 'MBBS, MMed Orthopaedics, 5+ years', TRUE, 'system'),
-- HR
('d1000001-0000-0000-0000-000000000014', 'HR Manager',               'HR-MGR',     'c1000001-0000-0000-0000-000000000008', 'a1000001-0000-0000-0000-000000000005', 80000, 130000,  'Manage HR operations, recruitment, employee relations', 'HR management, HRIS, labour law', 'BA HRM, SHRM certification, 5+ years', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000015', 'HR Officer',               'HR-OFF',     'c1000001-0000-0000-0000-000000000008', 'a1000001-0000-0000-0000-000000000003', 55000, 85000,   'Recruitment, onboarding, employee records management', 'HR operations, MS Office', 'BA HRM, 2+ years', TRUE, 'system'),
-- Maintenance
('d1000001-0000-0000-0000-000000000016', 'Maintenance Supervisor',   'MAINT-SUP',  'c1000001-0000-0000-0000-000000000009', 'a1000001-0000-0000-0000-000000000003', 50000, 80000,   'Supervise facilities, maintain medical equipment, safety compliance', 'Facilities management, equipment maintenance', 'Diploma Engineering or equivalent, 3+ years', TRUE, 'system'),
-- Emergency
('d1000001-0000-0000-0000-000000000017', 'Emergency Physician',      'ER-PHYS',    'c1000001-0000-0000-0000-000000000010', 'a1000001-0000-0000-0000-000000000006', 130000, 200000, 'Diagnose and treat emergency cases, trauma management', 'Emergency medicine, ATLS, BLS/ACLS', 'MBBS, MMed Emergency Medicine', TRUE, 'system'),
('d1000001-0000-0000-0000-000000000018', 'ER Nurse',                 'ER-RN',      'c1000001-0000-0000-0000-000000000010', 'a1000001-0000-0000-0000-000000000003', 60000, 95000,   'Triage patients, administer emergency care, assist physicians', 'Emergency nursing, triage, BLS', 'BSc Nursing, ER certification', TRUE, 'system');

-- ─────────────────────────────────────────────
-- 5. Link existing employees to branches & positions
-- ─────────────────────────────────────────────
UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000001',
    position_id  = 'd1000001-0000-0000-0000-000000000001',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP001';   -- John Smith – Senior Cardiologist

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000001',
    position_id  = 'd1000001-0000-0000-0000-000000000003',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP002';   -- Sarah Johnson – Head Nurse ICU

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000002',
    position_id  = 'd1000001-0000-0000-0000-000000000005',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP003';   -- David Williams – Chief Pharmacist (Mombasa)

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000003',
    position_id  = 'd1000001-0000-0000-0000-000000000007',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP004';   -- Emily Brown – Senior Lab Technician (Kisumu)

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000001',
    position_id  = 'd1000001-0000-0000-0000-000000000009',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP005';   -- Michael Davis – Hospital Administrator

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000005',
    position_id  = 'd1000001-0000-0000-0000-000000000012',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP006';   -- Lisa Anderson – Paediatric Nurse (Eldoret)

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000001',
    position_id  = 'd1000001-0000-0000-0000-000000000013',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP007';   -- Robert Taylor – Orthopaedic Surgeon

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000006',
    position_id  = 'd1000001-0000-0000-0000-000000000014',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP008';   -- Jennifer Martinez – HR Manager

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000001',
    position_id  = 'd1000001-0000-0000-0000-000000000016',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP009';   -- James Wilson – Maintenance Supervisor

UPDATE hr_employees SET
    branch_id    = 'b1000001-0000-0000-0000-000000000001',
    position_id  = 'd1000001-0000-0000-0000-000000000018',
    employment_category = 'PERMANENT'
WHERE employee_number = 'EMP010';   -- Amanda Garcia – ER Nurse

-- ─────────────────────────────────────────────
-- 6. Set department heads to the senior employee per dept
-- ─────────────────────────────────────────────
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP001') WHERE id = 'c1000001-0000-0000-0000-000000000001';  -- Cardiology
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP002') WHERE id = 'c1000001-0000-0000-0000-000000000002';  -- ICU
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP003') WHERE id = 'c1000001-0000-0000-0000-000000000003';  -- Pharmacy
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP004') WHERE id = 'c1000001-0000-0000-0000-000000000004';  -- Laboratory
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP005') WHERE id = 'c1000001-0000-0000-0000-000000000005';  -- Administration
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP008') WHERE id = 'c1000001-0000-0000-0000-000000000008';  -- HR
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP009') WHERE id = 'c1000001-0000-0000-0000-000000000009';  -- Maintenance
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP010') WHERE id = 'c1000001-0000-0000-0000-000000000010';  -- Emergency
UPDATE hr_departments SET department_head_employee_id = (SELECT id FROM hr_employees WHERE employee_number = 'EMP007') WHERE id = 'c1000001-0000-0000-0000-000000000007';  -- Orthopedics
