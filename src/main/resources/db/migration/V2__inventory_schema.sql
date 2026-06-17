-- Migration V2: Inventory Schema
-- This migration creates all tables for the Inventory module

-- Inventory Locations table
CREATE TABLE IF NOT EXISTS inventory_locations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    location_code VARCHAR(50) UNIQUE NOT NULL,
    location_name VARCHAR(255) NOT NULL,
    location_type VARCHAR(50) NOT NULL,
    parent_location_id UUID,
    address TEXT,
    capacity INTEGER,
    current_utilization INTEGER,
    manager UUID,
    is_active BOOLEAN NOT NULL DEFAULT true,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (parent_location_id) REFERENCES inventory_locations(id)
);

-- Inventory Suppliers table
CREATE TABLE IF NOT EXISTS inventory_suppliers (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    supplier_code VARCHAR(50) UNIQUE NOT NULL,
    supplier_name VARCHAR(255) NOT NULL,
    contact_person VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    postal_code VARCHAR(20),
    tax_id VARCHAR(50),
    payment_terms VARCHAR(50),
    delivery_terms VARCHAR(50),
    credit_limit DOUBLE PRECISION,
    rating DOUBLE PRECISION,
    is_active BOOLEAN NOT NULL DEFAULT true,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Inventory Items table
CREATE TABLE IF NOT EXISTS inventory_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    item_code VARCHAR(50) UNIQUE NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_type VARCHAR(50) NOT NULL,
    category VARCHAR(255),
    description TEXT,
    manufacturer VARCHAR(255),
    brand VARCHAR(255),
    model VARCHAR(255),
    unit_of_measure VARCHAR(20),
    pack_size INTEGER,
    minimum_order_quantity INTEGER,
    reorder_level INTEGER,
    safety_stock INTEGER,
    maximum_stock INTEGER,
    lead_time_days INTEGER,
    shelf_life_days INTEGER,
    storage_conditions TEXT,
    is_controlled_substance BOOLEAN NOT NULL DEFAULT false,
    requires_prescription BOOLEAN NOT NULL DEFAULT false,
    is_cold_chain BOOLEAN NOT NULL DEFAULT false,
    image_url VARCHAR(255),
    specifications JSONB,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Purchase Orders table (Main)
CREATE TABLE IF NOT EXISTS inventory_purchase_orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    po_number VARCHAR(50) UNIQUE NOT NULL,
    supplier_id UUID NOT NULL,
    order_date DATE NOT NULL,
    expected_delivery_date DATE,
    actual_delivery_date DATE,
    status VARCHAR(50) NOT NULL,
    total_amount DOUBLE PRECISION,
    currency VARCHAR(10) DEFAULT 'ETB',
    payment_status VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (supplier_id) REFERENCES inventory_suppliers(id)
);

-- Inventory Batches table
CREATE TABLE IF NOT EXISTS inventory_batches (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    batch_number VARCHAR(100) NOT NULL,
    item_id UUID NOT NULL,
    manufacturer VARCHAR(255),
    manufacture_date DATE,
    expiry_date DATE,
    quantity INTEGER NOT NULL,
    received_date DATE,
    supplier_id UUID,
    purchase_order_id UUID,
    cost_per_unit DOUBLE PRECISION,
    total_cost DOUBLE PRECISION,
    storage_location VARCHAR(255),
    quality_check_status VARCHAR(20),
    quality_check_date DATE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (item_id) REFERENCES inventory_items(id),
    FOREIGN KEY (supplier_id) REFERENCES inventory_suppliers(id),
    FOREIGN KEY (purchase_order_id) REFERENCES inventory_purchase_orders(id)
);

-- Inventory Stock table
CREATE TABLE IF NOT EXISTS inventory_stock (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    item_id UUID NOT NULL,
    location_id UUID NOT NULL,
    batch_id UUID,
    quantity INTEGER NOT NULL,
    available_quantity INTEGER NOT NULL,
    reserved_quantity INTEGER NOT NULL DEFAULT 0,
    unit_cost DOUBLE PRECISION,
    total_cost DOUBLE PRECISION,
    expiry_date DATE,
    manufacture_date DATE,
    last_received_date DATE,
    last_issued_date DATE,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (item_id) REFERENCES inventory_items(id),
    FOREIGN KEY (location_id) REFERENCES inventory_locations(id),
    FOREIGN KEY (batch_id) REFERENCES inventory_batches(id)
);

-- Department Requests table
CREATE TABLE IF NOT EXISTS inventory_department_requests (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    request_number VARCHAR(50) UNIQUE NOT NULL,
    department VARCHAR(100) NOT NULL,
    required_date DATE,
    priority VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    purpose TEXT,
    requested_by VARCHAR(100),
    approved_by VARCHAR(100),
    approval_date TIMESTAMP,
    approval_notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0
);

-- Inventory Request Items table
CREATE TABLE IF NOT EXISTS inventory_request_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    request_id UUID NOT NULL,
    item_id UUID NOT NULL,
    quantity_requested INTEGER NOT NULL,
    quantity_approved INTEGER,
    quantity_issued INTEGER DEFAULT 0,
    unit_of_measure VARCHAR(20),
    status VARCHAR(50),
    remarks TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (request_id) REFERENCES inventory_department_requests(id),
    FOREIGN KEY (item_id) REFERENCES inventory_items(id)
);

-- Stock Issues table
CREATE TABLE IF NOT EXISTS inventory_stock_issues (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    issue_number VARCHAR(50) UNIQUE NOT NULL,
    request_id UUID,
    source_location_id UUID NOT NULL,
    destination_department VARCHAR(100),
    issue_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    issued_by VARCHAR(100),
    received_by UUID,
    status VARCHAR(50) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (request_id) REFERENCES inventory_department_requests(id),
    FOREIGN KEY (source_location_id) REFERENCES inventory_locations(id)
);

-- Expiry Alerts table
CREATE TABLE IF NOT EXISTS inventory_expiry_alerts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    stock_id UUID NOT NULL,
    batch_id UUID,
    expiry_date DATE NOT NULL,
    days_until_expiry INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    action_taken VARCHAR(255),
    acknowledged_by VARCHAR(100),
    acknowledged_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    deleted BOOLEAN NOT NULL DEFAULT false,
    version BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (stock_id) REFERENCES inventory_stock(id),
    FOREIGN KEY (batch_id) REFERENCES inventory_batches(id)
);

-- Indexes for Inventory Module
CREATE INDEX idx_inv_items_code ON inventory_items(item_code);
CREATE INDEX idx_inv_items_type ON inventory_items(item_type);
CREATE INDEX idx_inv_stock_item ON inventory_stock(item_id);
CREATE INDEX idx_inv_stock_location ON inventory_stock(location_id);
CREATE INDEX idx_inv_batches_num ON inventory_batches(batch_number);
CREATE INDEX idx_inv_batches_expiry ON inventory_batches(expiry_date);
