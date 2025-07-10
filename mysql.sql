-- docker exec -it <Cont-Id> bash
-- mysql -u root -p

-- SELECT user, host FROM mysql.user WHERE user = 'OrderManagement_user';
-- Grant access to OrderManagement_user into OrderManagement DB, 	% : any host, must be IP
CREATE USER 'OrderManagement_user'@'%' IDENTIFIED BY 'OrderManagement_admin_password';
-- CREATE USER 'OrderManagement_user'@'172.20.0.3' IDENTIFIED BY 'OrderManagement_admin_password';
GRANT ALL PRIVILEGES ON OrderManagement.* TO 'OrderManagement_user'@'%';
FLUSH PRIVILEGES;


SHOW DATABASES;
CREATE DATABASE OrderManagement;
USE OrderManagement;
SHOW TABLES;

DROP TABLE IF EXISTS customers;
CREATE TABLE customers (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    tax_id VARCHAR(30),
    notes TEXT
);

INSERT INTO customers (first_name, last_name, email, phone, date_of_birth, registration_date, is_active, tax_id, notes
) VALUES
('Alice',    'Johnson',    'alice.johnson@example.com',     '0401 234 567', '1985-03-14', NOW(), TRUE,  'TAX123456A', 'Frequent buyer, prefers email contact.'),
('Bob',      'Smith',      'bob.smith@example.com',         '0402 345 678', '1990-07-22', NOW(), TRUE,  'TAX987654B', 'Premium member.'),
('Charlie',  'Nguyen',     'charlie.nguyen@example.com',    '0403 456 789', '1978-12-01', NOW(), FALSE, 'TAX456789C', 'Account suspended.'),
('Diana',    'Chen',       'diana.chen@example.com',        '0404 567 890', '1995-10-11', NOW(), TRUE,  'TAX112233D', 'Likes phone support.'),
('Ethan',    'Brown',      'ethan.brown@example.com',       NULL,          '1988-05-09', NOW(), TRUE,  'TAX334455E', NULL),
('Fiona',    'Williams',   'fiona.williams@example.com',    '0405 678 901', '2000-01-25', NOW(), TRUE,  'TAX667788F', 'New customer.'),
('George',   'Lee',        'george.lee@example.com',        '0406 789 012', '1972-06-17', NOW(), FALSE, 'TAX998877G', 'Do not contact.'),
('Hannah',   'Davis',      'hannah.davis@example.com',      NULL,          '1993-08-03', NOW(), TRUE,  'TAX112211H', NULL),
('Isaac',    'Kumar',      'isaac.kumar@example.com',       '0407 890 123', '1980-02-28', NOW(), TRUE,  'TAX223344I', 'Works for a corporate client.'),
('Jasmine',  'Taylor',     'jasmine.taylor@example.com',    '0408 901 234', '1998-11-30', NOW(), TRUE,  'TAX556677J', 'Prefers weekend deliveries.');

DROP TABLE IF EXISTS customer_addresses;
CREATE TABLE customer_addresses (
    address_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL REFERENCES customers(customer_id),
    address_type VARCHAR(20) NOT NULL CHECK (address_type IN ('billing', 'shipping', 'both')),
    street_address1 VARCHAR(200) NOT NULL,
    street_address2 VARCHAR(100),
    city VARCHAR(50) NOT NULL,
    state_province VARCHAR(50) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(50) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

INSERT INTO customer_addresses (customer_id, address_type, street_address1, street_address2,city, state_province, postal_code, country, is_default, created_at) VALUES
(1, 'both', '123 George St', 'Apt 4B', 'Sydney', 'NSW', '2000', 'Australia', TRUE, '2024-01-10 09:20:00'),
(1, 'shipping', '456 Distribution Rd', NULL, 'Parramatta', 'NSW', '2150', 'Australia', FALSE, '2024-01-10 09:25:00'),
(2, 'both', '789 Collins St', 'Suite 1200', 'Melbourne', 'VIC', '3000', 'Australia', TRUE, '2025-01-12 14:35:00'),
(3, 'billing', '321 Kangaroo Lane', NULL, 'Brisbane', 'QLD', '4000', 'Australia', TRUE, '2023-01-15 11:25:00'),
(3, 'shipping', '654 Eucalyptus Rd', 'Building C', 'Brisbane', 'QLD', '4101', 'Australia', FALSE, '2023-01-15 11:30:00'),
(4, 'both', '987 Esplanade', 'Penthouse', 'Gold Coast', 'QLD', '4217', 'Australia', TRUE, '2024-02-01 16:50:00'),
(5, 'both', '159 Tech Park Dr', 'Unit 200', 'Adelaide', 'SA', '5000', 'Australia', TRUE, '2024-02-05 10:15:00'),
(6, 'billing', '753 Innovation Ave', 'Level 3', 'Canberra', 'ACT', '2601', 'Australia', TRUE, '2024-02-10 13:30:00'),
(7, 'both', '456 Industry Way', NULL, 'Perth', 'WA', '6000', 'Australia', TRUE, '2024-02-15 08:45:00'),
(8, 'both', '852 University Blvd', 'Dorm 12', 'Hobart', 'TAS', '7000', 'Australia', TRUE, '2024-03-01 17:35:00');


DROP TABLE IF EXISTS product_categories;
CREATE TABLE product_categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    parent_category_id BIGINT,
    description TEXT,
    FOREIGN KEY (parent_category_id) REFERENCES product_categories(category_id)
);

# Insert parent first
INSERT INTO product_categories (name, parent_category_id, description) VALUES
('Electronics', NULL, 'All electronic devices and components'),
('Furniture', NULL, 'Home and office furniture'),
('Appliances', NULL, 'Home and kitchen appliances'),
('Clothing', NULL, 'Apparel and accessories');

SELECT * FROM product_categories;

# Insert child catefories
INSERT INTO product_categories (name, parent_category_id, description) VALUES
('Computers', 1, 'Desktop and laptop computers'),
('Smartphones', 1, 'Mobile phones with advanced features'),
('Office Chairs', 2, 'Ergonomic and standard office chairs'),
('Refrigerators', 3, 'Cooling appliances for food storage'),
('Men''s Clothing', 4, 'Clothing for men'),
('Women''s Clothing', 4, 'Clothing for women');

INSERT INTO product_categories (name, parent_category_id, description)
SELECT 'Children''s Clothing', category_id, 'Clothing for children'
FROM product_categories 
WHERE name = 'Clothing';

DROP TABLE IF EXISTS products;
CREATE TABLE products (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sku VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category_id BIGINT,
    unit_price DECIMAL(10,2) NOT NULL,
    weight DECIMAL(10,2),
    dimensions VARCHAR(50),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES product_categories(category_id)
);

INSERT INTO products (sku, name, description, category_id, unit_price, weight, dimensions, is_active, created_at) VALUES
('LTP-1001', 'Premium Laptop', '15.6" FHD, 16GB RAM, 512GB SSD', 
(SELECT category_id FROM product_categories WHERE NAME='Computers'), 1299.99, 3.5, '14.2 x 9.8 x 0.7', TRUE, '2024-01-01 00:00:00'),
('SPH-2001', 'Flagship Smartphone', '6.7" AMOLED, 128GB storage',
(SELECT category_id FROM product_categories WHERE NAME= 'Smartphones'), 899.99, 0.4, '6.3 x 3.0 x 0.3', TRUE, '2024-01-01 00:00:00'),
('OFC-3001', 'Ergonomic Office Chair', 'Adjustable height and lumbar support',
(SELECT category_id FROM product_categories WHERE NAME= 'Office Chairs'), 249.99, 35.0, '26.0 x 26.0 x 45.0', TRUE, '2024-01-01 00:00:00'),
('REF-4001', 'French Door Refrigerator', '25 cu.ft. with ice maker',
(SELECT category_id FROM product_categories WHERE NAME= 'Refrigerators') , 1899.99, 250.0, '35.8 x 68.9 x 35.0', TRUE, '2024-01-01 00:00:00'),
('MJC-5001', 'Men''s Jeans', 'Slim fit, dark wash',
(SELECT category_id FROM product_categories WHERE NAME= 'Men''s Clothing') , 59.99, 1.2, NULL, TRUE, '2024-01-01 00:00:00'),
('WDR-6001', 'Women''s Dress', 'Floral print, knee-length',
(SELECT category_id FROM product_categories WHERE NAME= 'Women''s Clothing'), 79.99, 0.8, NULL, TRUE, '2024-01-01 00:00:00'),
('LTP-1002', 'HP Laptop', '14" HD, 8GB RAM, 256GB SSD',
(SELECT category_id FROM product_categories WHERE NAME= 'Computers'), 599.99, 3.2, '13.3 x 9.1 x 0.8', TRUE, '2024-01-15 00:00:00'),
('SPH-2002', 'Mid-range Smartphone', '6.5" LCD, 64GB storage',
(SELECT category_id FROM product_categories WHERE NAME= 'Smartphones'),499.99, 0.38, '6.2 x 2.9 x 0.35', TRUE, '2024-01-15 00:00:00'),
('OFC-3002', 'Executive Chair', 'Leather upholstery, high back',
(SELECT category_id FROM product_categories WHERE NAME= 'Office Chairs') , 399.99, 42.0, '28.0 x 28.0 x 48.0', TRUE, '2024-01-15 00:00:00'),
('REF-4002', 'Top Freezer Refrigerator', '18 cu.ft. energy star', 
(SELECT category_id FROM product_categories WHERE NAME= 'Refrigerators') , 799.99, 180.0, '30.0 x 65.5 x 32.5', TRUE, '2024-01-15 00:00:00');


DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'processing', 'shipped', 'delivered', 'cancelled', 'returned')),
    shipping_address_id BIGINT NOT NULL,
    billing_address_id BIGINT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    tax_amount DECIMAL(10,2) NOT NULL,
    shipping_cost DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    tracking_number VARCHAR(100),
    notes TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (shipping_address_id) REFERENCES customer_addresses(address_id),
    FOREIGN KEY (billing_address_id) REFERENCES customer_addresses(address_id)
);

-- subtotal : SELECT SUM(extended_price) FROM order_items WHERE order_id =1;
-- tax_amount SELECT SUM(extended_price)*0.08 FROM order_items WHERE order_id =1;
-- shipping_cost: for now, dummy. 
-- total_amount = subtotal + tax_amount + shipping_cost
INSERT INTO orders (customer_id, order_date, status, shipping_address_id, billing_address_id, subtotal, tax_amount, shipping_cost, total_amount, payment_method, tracking_number, notes) VALUES
(1, '2025-06-01 10:15:30', 'delivered', 1, 1, 1299.99, 103.99, 15.00, 1418.98, 'credit_card', '1Z12345E0291980793', 'Customer requested signature confirmation'),
(2, '2025-06-05 14:30:45', 'shipped', 3, 3, 249.99, 19.99, 8.50, 278.48, 'paypal', '1Z12345E6692920134', NULL),
(3, '2025-06-10 09:20:15', 'processing', 5, 4, 899.99, 71.99, 12.00, 983.98, 'credit_card', NULL, 'Waiting for stock to arrive'),
(5, '2025-06-15 11:45:30', 'delivered', 7, 7, 59.99, 4.79, 5.00, 69.78, 'credit_card', '1Z12345E0205396835', 'Left at front door'),
(1, '2025-06-20 16:20:10', 'delivered', 2, 1, 69.99, 5.59, 12.00, 87.58, 'credit_card', '1Z12345E0395212345', 'Gift wrapping requested'),
(6, '2025-06-25 13:15:20', 'shipped', 8, 8, 499.99, 39.99, 25.00, 294.99, 'paypal', '1Z12345E0298765432', 'Heavy item - extra handling'),
(7, '2025-07-01 10:30:45', 'pending', 9, 9, 399.99, 31.99, 30.00, 461.98, 'bank_transfer', NULL, 'Awaiting payment confirmation'),
(8, '2025-07-05 15:40:30', 'cancelled', 10, 10, 799.99, 63.99, 50.00, 913.98, 'credit_card', NULL, 'Customer changed mind'),
(4, '2025-07-10 12:25:15', 'returned', 6, 6, 1899.99, 151.99, 15.00, 2066.98, 'credit_card', '1Z12345E0309876543', 'Product damaged during shipping');


-- Order Items, line items for each order, for now each order contains only one item
DROP TABLE IF EXISTS order_items;
CREATE TABLE order_items (
    order_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    discount_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    extended_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- TODO: Create Discount table
INSERT INTO order_items (order_id, product_id, quantity, unit_price, discount_amount, extended_price) VALUES
(1, 1, 1, (SELECT unit_price FROM products WHERE product_id=1), 0.00, 1299.99),
(2, 3, 1, (SELECT unit_price FROM products WHERE product_id=3), 0.00, 249.99),
(3, 2, 1, (SELECT unit_price FROM products WHERE product_id=2) , 0.00, 899.99),
(4, 5, 1, (SELECT unit_price FROM products WHERE product_id=5) , 0.00, 59.99),
(5, 6, 1, (SELECT unit_price FROM products WHERE product_id=6), 10.00, 69.99),
(6, 8, 1, (SELECT unit_price FROM products WHERE product_id=8), 0.00, 499.99),
(7, 9, 1, (SELECT unit_price FROM products WHERE product_id=9), 0.00, 399.99),
(8, 10,1 ,(SELECT unit_price FROM products WHERE product_id=10), 0.00, 799.99),
(9, 4, 1, (SELECT unit_price FROM products WHERE product_id=4), 0.00, 1899.99);


CREATE TABLE payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    payment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    transaction_id VARCHAR(100),
    status VARCHAR(20) NOT NULL CHECK (status IN ('pending', 'completed', 'failed', 'refunded')),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

INSERT INTO payments (order_id, payment_date, amount, payment_method, transaction_id, status) VALUES
(1, '2025-06-01 10:16:45', 1418.98, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dE', 'completed'),
(2, '2025-06-05 14:31:20', 872.49, 'paypal', 'PAYID-MB5N3ZI0XJ12345678901234A', 'completed'),
(3, '2025-06-10 09:21:30', 659.99, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dF', 'pending'),
(4, '2025-06-15 11:46:15', 544.99, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dG', 'completed'),
(5, '2025-06-20 16:21:45', 152.39, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dH', 'completed'),
(6, '2025-06-25 13:16:30', 294.99, 'paypal', 'PAYID-MB5N3ZI0XJ12345678901234B', 'completed'),
(7, '2025-07-01 10:32:00', 461.99, 'bank_transfer', 'BANKTR123456789', 'pending'),
(8, '2025-07-05 15:41:45', 1021.99, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dI', 'refunded'),
(9, '2025-07-10 12:26:30', 230.99, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dJ', 'refunded'),
(10, '2025-07-15 09:11:45', 1088.49, 'credit_card', 'ch_1Kj5Zt2eZvKYlo2C0XJXr4dK', 'completed');

-- Inventory table
CREATE TABLE inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    warehouse_location VARCHAR(50) NOT NULL,
    quantity_on_hand INT NOT NULL DEFAULT 0,
    quantity_allocated INT NOT NULL DEFAULT 0,
    last_stock_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO inventory (product_id, warehouse_location, quantity_on_hand, quantity_allocated, last_stock_update) VALUES
(1, 'NY-WH-01', 25, 3, '2022-07-20 10:00:00'),
(2, 'NY-WH-01', 42, 7, '2022-07-20 10:00:00'),
(3, 'CA-WH-02', 18, 2, '2022-07-20 10:00:00'),
(4, 'CA-WH-02', 35, 5, '2022-07-20 10:00:00'),
(5, 'TX-WH-03', 60, 12, '2022-07-20 10:00:00'),
(6, 'TX-WH-03', 28, 4, '2022-07-20 10:00:00'),
(7, 'IL-WH-04', 50, 6, '2022-07-20 10:00:00'),
(8, 'IL-WH-04', 15, 1, '2022-07-20 10:00:00'),
(9, 'AZ-WH-05', 22, 3, '2022-07-20 10:00:00'),
(10, 'AZ-WH-05', 8, 0, '2022-07-20 10:00:00'),
(11, 'PA-WH-06', 30, 2, '2022-07-20 10:00:00'),
(12, 'PA-WH-06', 0, 0, '2022-07-20 10:00:00');

-- Shipping methods table
CREATE TABLE shipping_methods (
    method_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT,
    base_cost DECIMAL(10,2) NOT NULL,
    cost_per_item DECIMAL(10,2) NOT NULL,
    estimated_delivery_days INT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

INSERT INTO shipping_methods (name, description, base_cost, cost_per_item, estimated_delivery_days, is_active) VALUES
('Standard Shipping', 'Ground shipping with delivery in 5-7 business days', 8.50, 1.50, 5, TRUE),
('Expedited Shipping', '2-3 business day delivery', 15.00, 2.50, 2, TRUE),
('Overnight Shipping', 'Next business day delivery', 25.00, 5.00, 1, TRUE),
('Free Shipping', 'Free standard shipping on eligible orders', 0.00, 0.00, 7, TRUE),
('Heavy Item Shipping', 'Special handling for heavy/bulky items', 30.00, 10.00, 5, TRUE);

-- Promotions table
CREATE TABLE promotions (
    promotion_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    discount_type VARCHAR(20) NOT NULL CHECK (discount_type IN ('percentage', 'fixed_amount')),
    discount_value DECIMAL(10,2) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    min_order_amount DECIMAL(10,2),
    applies_to_category_id BIGINT,
    applies_to_product_id BIGINT,
    max_uses INT,
    current_uses INT NOT NULL DEFAULT 0,
    FOREIGN KEY (applies_to_category_id) REFERENCES product_categories(category_id),
    FOREIGN KEY (applies_to_product_id) REFERENCES products(product_id)
);

INSERT INTO promotions (name, description, discount_type, discount_value, start_date, end_date, is_active, min_order_amount, applies_to_category_id, applies_to_product_id, max_uses, current_uses) VALUES
('Summer Sale', '10% off all electronics', 'percentage', 10.00, '2022-06-01 00:00:00', '2022-08-31 23:59:59', TRUE, NULL, 1, NULL, NULL, 42),
('Back to School', '$50 off laptops', 'fixed_amount', 50.00, '2022-07-15 00:00:00', '2022-09-15 23:59:59', TRUE, 500.00, 3, NULL, 100, 18),
('New Customer Discount', '15% off first order', 'percentage', 15.00, '2022-01-01 00:00:00', '2022-12-31 23:59:59', TRUE, 100.00, NULL, NULL, NULL, 27),
('Blender Special', '$20 off Professional Blender', 'fixed_amount', 20.00, '2022-05-01 00:00:00', '2022-07-31 23:59:59', FALSE, NULL, NULL, 6, 50, 50),
('Free Shipping Over $100', 'Free standard shipping on orders over $100', 'fixed_amount', 8.50, '2022-01-01 00:00:00', '2022-12-31 23:59:59', TRUE, 100.00, NULL, NULL, NULL, 89);

-- Order promotions table (to track which promotions were applied to which orders)
CREATE TABLE order_promotions (
    order_promotion_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    promotion_id BIGINT NOT NULL,
    discount_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (promotion_id) REFERENCES promotions(promotion_id)
);

INSERT INTO order_promotions (order_id, promotion_id, discount_amount) VALUES
(5, 4, 10.00),
(5, 5, 8.50),
(10, 1, 100.00);

-- Returns table
CREATE TABLE returns (
    return_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    return_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL CHECK (status IN ('requested', 'approved', 'rejected', 'processed', 'refunded')),
    reason TEXT NOT NULL,
    refund_amount DECIMAL(10,2),
    restocking_fee DECIMAL(10,2),
    notes TEXT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

INSERT INTO returns (order_id, customer_id, return_date, status, reason, refund_amount, restocking_fee, notes) VALUES
(9, 4, '2022-07-12 14:30:00', 'refunded', 'Product damaged during shipping', 230.99, 0.00, 'Issued full refund including shipping'),
(8, 8, '2022-07-06 10:15:00', 'refunded', 'Customer changed mind', 1021.99, 50.00, 'Charged 5% restocking fee');

-- Return items table
CREATE TABLE return_items (
    return_item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    return_id BIGINT NOT NULL,
    order_item_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    refund_amount DECIMAL(10,2) NOT NULL,
    reason VARCHAR(100) NOT NULL,
    condition VARCHAR(50) NOT NULL CHECK (condition IN ('new', 'like_new', 'opened', 'damaged')),
    FOREIGN KEY (return_id) REFERENCES returns(return_id),
    FOREIGN KEY (order_item_id) REFERENCES order_items(order_item_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

INSERT INTO return_items (return_id, order_item_id, product_id, quantity, refund_amount, reason, condition) VALUES
(1, 9, 11, 1, 199.99, 'Damaged during shipping', 'damaged'),
(2, 8, 10, 1, 899.99, 'Customer changed mind', 'like_new');

-- Reviews table
CREATE TABLE reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    order_id BIGINT,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    title VARCHAR(100),
    comment TEXT,
    review_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_approved BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

INSERT INTO reviews (customer_id, product_id, order_id, rating, title, comment, review_date, is_approved) VALUES
(1, 1, 1, 5, 'Excellent laptop!', 'This laptop is fast and has great battery life. Perfect for my business needs.', '2022-06-10 15:30:00', TRUE),
(1, 6, 5, 4, 'Powerful blender', 'Makes smooth smoothies, but a bit noisy. Overall happy with the purchase.', '2022-06-25 11:20:00', TRUE),
(2, 3, 2, 3, 'Good but not perfect', 'The tablet works well but the battery life could be better.', '2022-06-15 14:45:00', TRUE),
(5, 5, 4, 5, 'Great mid-range phone', 'Excellent value for the price. Camera quality is surprisingly good.', '2022-06-20 09:10:00', TRUE),
(10, 4, 10, 2, 'Disappointed', 'Battery drains too fast. Expected better from a flagship phone.', '2022-07-20 16:25:00', FALSE);




