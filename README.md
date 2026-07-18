# Fashion Store - Java Enterprise E-Commerce Application

A professional, full-stack E-Commerce web application built using Jakarta EE (Servlets, JSP), MySQL, and modern front-end design principles (Glassmorphism, CSS Grid).

## Features
- **User Authentication:** Secure Registration and Login system.
- **Product Catalog:** Browse products by categories, see trending items.
- **Shopping Cart:** Add, remove, and update quantities of items.
- **Checkout System:** Place orders with UPI (QR Code integration), Cash on Delivery, or Credit Card.
- **Modern UI:** Responsive, premium aesthetic featuring glassmorphism and smooth micro-animations.

## Technology Stack
- **Backend:** Java 17+, Jakarta EE (Servlets), JDBC
- **Frontend:** JSP, HTML5, Vanilla CSS3 (no external frameworks for maximum performance)
- **Database:** MySQL
- **Build Tool:** Maven
- **Server:** Apache Tomcat 10+

## Setup Instructions

1. **Database Configuration**
   - Create a MySQL database and run the `database/schema.sql` script to create tables and insert dummy data.
   - Update `src/main/java/com/fashionstore/util/DBConnection.java` with your local MySQL username and password.

2. **Run the Application**
   - Import the project into Eclipse as an **Existing Maven Project**.
   - Right-click the project -> **Run As** -> **Run on Server**.
   - Select Apache Tomcat 10.1 and click Finish.
   
3. **Access**
   - Navigate to `http://localhost:8080/java-Shopping-project/home` in your browser.
