-- Migration to add pricing fields to inventory items
ALTER TABLE inventory_items 
ADD COLUMN unit_price DECIMAL(12,2) DEFAULT 0.00,
ADD COLUMN purchase_price DECIMAL(12,2) DEFAULT 0.00;
