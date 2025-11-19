-- food_order_schema.sql
CREATE DATABASE IF NOT EXISTS food_order_db;
USE food_order_db;

CREATE TABLE IF NOT EXISTS foods (
    food_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    department VARCHAR(100),
    dob DATE,
    joining_date DATE
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    total_amount DECIMAL(10,2),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    food_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (food_id) REFERENCES foods(food_id)
);

INSERT INTO foods (name, description, price) VALUES
('Veg Burger', 'Veg patty, lettuce, tomato', 3.50),
('Chicken Burger', 'Grilled chicken patty', 4.50),
('French Fries', 'Crispy fries (medium)', 2.00),
('Coke', '330ml can', 1.00),
('Pasta Alfredo', 'Creamy white sauce pasta', 6.00);

INSERT INTO employees (name, phone, address, department, dob, joining_date) VALUES
('Alice Smith', '9876543210', '12, Baker Street', 'Manager', '1990-05-12', '2020-01-15');
