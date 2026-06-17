-- Add HR permissions
INSERT INTO permissions (name, description, created_at, updated_at) VALUES
('HR_READ',  'Read HR data (employees, leave, attendance, payroll)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('HR_WRITE', 'Create and update HR data', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('HR_ADMIN', 'Full HR administration (terminate, approve leave, manage payroll)', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BILLING_READ',  'Read billing and invoice data', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BILLING_WRITE', 'Create and update invoices and payments', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('BILLING_ADMIN', 'Full billing administration including refunds', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ANALYTICS_READ', 'Read analytics and dashboard data', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ADMIN_READ',  'Read admin system config and health', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ADMIN_WRITE', 'Update system configuration and maintenance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

-- Assign all new permissions to ADMIN role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.name = 'ADMIN'
  AND p.name IN (
    'HR_READ', 'HR_WRITE', 'HR_ADMIN',
    'BILLING_READ', 'BILLING_WRITE', 'BILLING_ADMIN',
    'ANALYTICS_READ',
    'ADMIN_READ', 'ADMIN_WRITE'
  )
ON CONFLICT DO NOTHING;
