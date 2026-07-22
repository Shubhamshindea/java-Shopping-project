-- ========================================================
-- Fashion Store - MySQL Database Schema
-- Database: fashion_store
-- Run this script in MySQL before starting the application
-- ========================================================

CREATE DATABASE IF NOT EXISTS fashion_store;
USE fashion_store;

-- ========================
-- USERS TABLE
-- ========================
CREATE TABLE IF NOT EXISTS users (
    user_id         INT AUTO_INCREMENT PRIMARY KEY,
    full_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    phone           VARCHAR(20),
    password        VARCHAR(255) NOT NULL,
    address_line1   VARCHAR(200),
    address_line2   VARCHAR(200),
    city            VARCHAR(100),
    state           VARCHAR(100),
    pincode         VARCHAR(10),
    country         VARCHAR(100) DEFAULT 'India',
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================
-- CATEGORIES TABLE
-- ========================
CREATE TABLE IF NOT EXISTS categories (
    category_id     INT AUTO_INCREMENT PRIMARY KEY,
    category_name   VARCHAR(100) NOT NULL,
    description     TEXT,
    image_url       VARCHAR(255),
    is_active       BOOLEAN DEFAULT TRUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ========================
-- PRODUCTS TABLE
-- ========================
CREATE TABLE IF NOT EXISTS products (
    product_id      INT AUTO_INCREMENT PRIMARY KEY,
    category_id     INT,
    product_name    VARCHAR(200) NOT NULL,
    brand           VARCHAR(100),
    description     TEXT,
    price           DECIMAL(10, 2) NOT NULL,
    image_url       VARCHAR(255),
    is_active       BOOLEAN DEFAULT TRUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- ========================
-- PRODUCT VARIANTS TABLE
-- ========================
CREATE TABLE IF NOT EXISTS product_variants (
    variant_id      INT AUTO_INCREMENT PRIMARY KEY,
    product_id      INT NOT NULL,
    size            VARCHAR(10),
    color           VARCHAR(50),
    stock_quantity  INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ========================
-- CART TABLE
-- ========================
CREATE TABLE IF NOT EXISTS cart (
    cart_id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id         INT NOT NULL UNIQUE,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ========================
-- CART ITEMS TABLE
-- ========================
CREATE TABLE IF NOT EXISTS cart_items (
    cart_item_id    INT AUTO_INCREMENT PRIMARY KEY,
    cart_id         INT NOT NULL,
    product_id      INT NOT NULL,
    variant_id      INT DEFAULT 1,
    quantity        INT DEFAULT 1,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ========================
-- ORDERS TABLE
-- ========================
CREATE TABLE IF NOT EXISTS orders (
    order_id                INT AUTO_INCREMENT PRIMARY KEY,
    user_id                 INT NOT NULL,
    total_amount            DECIMAL(12, 2) NOT NULL,
    payment_method          VARCHAR(50) DEFAULT 'COD',
    order_status            VARCHAR(50) DEFAULT 'PENDING',
    delivery_name           VARCHAR(100),
    delivery_phone          VARCHAR(20),
    delivery_address_line1  VARCHAR(200),
    delivery_address_line2  VARCHAR(200),
    delivery_city           VARCHAR(100),
    delivery_state          VARCHAR(100),
    delivery_pincode        VARCHAR(10),
    delivery_country        VARCHAR(100) DEFAULT 'India',
    created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- ========================
-- ORDER ITEMS TABLE
-- ========================
CREATE TABLE IF NOT EXISTS order_items (
    order_item_id   INT AUTO_INCREMENT PRIMARY KEY,
    order_id        INT NOT NULL,
    product_id      INT,
    variant_id      INT DEFAULT 1,
    quantity        INT DEFAULT 1,
    price           DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- ========================================================
-- SAMPLE DATA
-- ========================================================

-- Categories
INSERT INTO categories (category_name, description) VALUES
('Men''s Wear', 'Shirts, Trousers, Suits and more'),
('Women''s Wear', 'Dresses, Tops, Salwar Kameez'),
('Kids'' Wear', 'Clothing for children'),
('Accessories', 'Belts, Bags, Shoes');

-- Default product variant (required for cart_items FK)
-- Products
INSERT INTO products (category_id, product_name, brand, description, price, image_url, is_active) VALUES
(1, 'Classic White Shirt', 'Levi''s', 'Premium cotton formal shirt, perfect for office wear.', 1299.00, 'assets/images/white_shirt.png', TRUE),
(1, 'Blue Denim Jeans', 'Wrangler', 'Slim fit denim jeans with comfortable stretch fabric.', 1899.00, 'assets/images/blue_jeans.png', TRUE),
(2, 'Floral Kurti', 'Biba', 'Beautiful floral printed cotton kurti for festive occasions.', 999.00, 'assets/images/floral_kurti.png', TRUE),
(2, 'Black Dress', 'W', 'Elegant black dress suitable for parties and events.', 2499.00, 'assets/images/black_dress.png', TRUE),
(3, 'Kids T-Shirt Pack', 'H&M', 'Colorful 3-pack cotton t-shirts for children.', 699.00, 'assets/images/kids_tshirt.png', TRUE),
(4, 'Leather Belt', 'Woodland', 'Genuine leather belt with classic buckle design.', 549.00, 'assets/images/leather_belt.png', TRUE),
(1, 'Striped Polo Shirt', 'Arrow', 'Casual polo shirt for everyday wear.', 849.00, 'assets/images/white_shirt.png', TRUE),
(2, 'Salwar Kameez Set', 'Rangmanch', 'Traditional embroidered salwar kameez with dupatta.', 1599.00, 'assets/images/floral_kurti.png', TRUE),
(1, 'Men''s Casual Jacket', 'Puma', 'Comfortable and stylish casual jacket.', 2199.00, 'assets/images/sample.jpg', TRUE),
(2, 'Designer Saree', 'FabIndia', 'Beautiful silk designer saree for weddings.', 4999.00, 'assets/images/sample.jpg', TRUE),
(3, 'Kids Boy Suit', 'Allen Solly', 'Formal suit for boys.', 1499.00, 'assets/images/sample.jpg', TRUE),
(4, 'Aviator Sunglasses', 'Ray-Ban', 'Classic aviator sunglasses with UV protection.', 3599.00, 'assets/images/sample.jpg', TRUE);

-- Product Variants (default variant for each product)
INSERT INTO product_variants (product_id, size, color, stock_quantity) VALUES
(1, 'M', 'White', 50),
(2, '32', 'Blue', 40),
(3, 'M', 'Multi', 60),
(4, 'S', 'Black', 30),
(5, '5-6Y', 'Multi', 80),
(6, '36', 'Brown', 45),
(7, 'L', 'Navy', 35),
(8, 'M', 'Orange', 25),
(9, 'L', 'Black', 20),
(10, 'Free', 'Red', 10),
(11, 'S', 'Blue', 30),
(12, 'Free', 'Silver', 50);

-- Test User (password: test123)
INSERT INTO users (full_name, email, phone, password) VALUES
('Test User', 'test@test.com', '9876543210', 'test123');
