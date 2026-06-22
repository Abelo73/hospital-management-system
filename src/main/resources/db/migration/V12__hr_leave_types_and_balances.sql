-- V12: Configurable Leave Types + Leave Balances
-- ─────────────────────────────────────────────

-- Configurable leave types (replaces hardcoded enum usage)
CREATE TABLE IF NOT EXISTS hr_leave_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) UNIQUE NOT NULL,
    annual_days INTEGER NOT NULL DEFAULT 0,
    is_paid BOOLEAN NOT NULL DEFAULT TRUE,
    requires_approval BOOLEAN NOT NULL DEFAULT TRUE,
    requires_attachment BOOLEAN NOT NULL DEFAULT FALSE,
    is_gender_specific BOOLEAN NOT NULL DEFAULT FALSE,
    applicable_gender VARCHAR(10),
    max_carryover_days INTEGER NOT NULL DEFAULT 0,
    min_service_months_required INTEGER NOT NULL DEFAULT 0,
    accrual_frequency VARCHAR(20) NOT NULL DEFAULT 'ANNUAL',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

-- Per-employee leave balances per type per year
CREATE TABLE IF NOT EXISTS hr_leave_balances (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    employee_id UUID NOT NULL REFERENCES hr_employees(id) ON DELETE CASCADE,
    leave_type_id UUID NOT NULL REFERENCES hr_leave_types(id),
    leave_cycle_year INTEGER NOT NULL,
    entitled_days INTEGER NOT NULL DEFAULT 0,
    used_days INTEGER NOT NULL DEFAULT 0,
    carried_forward_days INTEGER NOT NULL DEFAULT 0,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0,
    UNIQUE (employee_id, leave_type_id, leave_cycle_year)
);

CREATE INDEX IF NOT EXISTS idx_hr_leave_balances_employee ON hr_leave_balances(employee_id);
CREATE INDEX IF NOT EXISTS idx_hr_leave_balances_year ON hr_leave_balances(leave_cycle_year);

-- ─────────────────────────────────────────────
-- Seed default leave types
-- ─────────────────────────────────────────────
INSERT INTO hr_leave_types (id, name, code, annual_days, is_paid, requires_approval, max_carryover_days, is_active, created_by) VALUES
('e1000001-0000-0000-0000-000000000001', 'Annual Leave',       'ANNUAL',       21, TRUE,  TRUE,  5, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000002', 'Sick Leave',         'SICK',         14, TRUE,  TRUE,  0, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000003', 'Maternity Leave',    'MATERNITY',    90, TRUE,  TRUE,  0, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000004', 'Paternity Leave',    'PATERNITY',    14, TRUE,  TRUE,  0, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000005', 'Compassionate Leave','COMPASSIONATE', 5, TRUE,  TRUE,  0, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000006', 'Study Leave',        'STUDY',         5, FALSE, TRUE,  0, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000007', 'Unpaid Leave',       'UNPAID',        0, FALSE, TRUE,  0, TRUE, 'system'),
('e1000001-0000-0000-0000-000000000008', 'Emergency Leave',    'EMERGENCY',     3, TRUE,  FALSE, 0, TRUE, 'system')
ON CONFLICT (code) DO NOTHING;

-- ─────────────────────────────────────────────
-- Seed leave balances for existing employees (current year)
-- ─────────────────────────────────────────────
INSERT INTO hr_leave_balances (employee_id, leave_type_id, leave_cycle_year, entitled_days, used_days, carried_forward_days, created_by)
SELECT e.id, lt.id, EXTRACT(YEAR FROM CURRENT_DATE)::INTEGER, lt.annual_days, 0, 0, 'system'
FROM hr_employees e
CROSS JOIN hr_leave_types lt
WHERE e.deleted = FALSE AND lt.is_active = TRUE AND lt.annual_days > 0
ON CONFLICT (employee_id, leave_type_id, leave_cycle_year) DO NOTHING;
