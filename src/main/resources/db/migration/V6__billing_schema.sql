-- Billing Module Tables

CREATE TABLE IF NOT EXISTS billing_invoices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_number VARCHAR(50) UNIQUE NOT NULL,
    patient_id UUID NOT NULL,
    patient_name VARCHAR(200),
    invoice_date DATE NOT NULL,
    due_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    subtotal DECIMAL(12,2) NOT NULL DEFAULT 0,
    tax DECIMAL(12,2) DEFAULT 0,
    discount DECIMAL(12,2) DEFAULT 0,
    total_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    paid_amount DECIMAL(12,2) DEFAULT 0,
    balance_amount DECIMAL(12,2) DEFAULT 0,
    payment_method VARCHAR(20),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS billing_invoice_line_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id UUID NOT NULL,
    service_type VARCHAR(50),
    service_id UUID,
    description VARCHAR(500) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    unit_price DECIMAL(12,2) NOT NULL,
    line_total DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (invoice_id) REFERENCES billing_invoices(id)
);

CREATE TABLE IF NOT EXISTS billing_payments (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id UUID NOT NULL,
    patient_id UUID NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    reference_number VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (invoice_id) REFERENCES billing_invoices(id)
);

CREATE TABLE IF NOT EXISTS billing_insurance_providers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(200) NOT NULL,
    contact_person VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    address TEXT,
    claim_submission_method VARCHAR(20),
    standard_co_pay DECIMAL(10,2),
    standard_deductible DECIMAL(10,2),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS billing_insurance_claims (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id UUID,
    provider_id UUID,
    patient_id UUID NOT NULL,
    claim_number VARCHAR(100) UNIQUE,
    submission_date DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    approved_amount DECIMAL(12,2),
    rejection_reason VARCHAR(1000),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (invoice_id) REFERENCES billing_invoices(id),
    FOREIGN KEY (provider_id) REFERENCES billing_insurance_providers(id)
);

CREATE TABLE IF NOT EXISTS billing_refunds (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    payment_id UUID,
    invoice_id UUID,
    patient_id UUID NOT NULL,
    refund_date DATE NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    reason VARCHAR(1000),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    processed_by VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (payment_id) REFERENCES billing_payments(id),
    FOREIGN KEY (invoice_id) REFERENCES billing_invoices(id)
);

CREATE INDEX IF NOT EXISTS idx_invoices_patient_id ON billing_invoices(patient_id);
CREATE INDEX IF NOT EXISTS idx_invoices_status ON billing_invoices(status);
CREATE INDEX IF NOT EXISTS idx_payments_invoice_id ON billing_payments(invoice_id);
CREATE INDEX IF NOT EXISTS idx_payments_patient_id ON billing_payments(patient_id);
CREATE INDEX IF NOT EXISTS idx_claims_status ON billing_insurance_claims(status);
