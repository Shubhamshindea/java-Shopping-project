package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/fashion_store";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // 🔴 change this to your MySQL password

    private static Connection connection = null;

    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {

                // Load driver (optional but safe)
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("DB Connected Successfully ✅");
            }

        } catch (Exception e) {
            System.out.println("DB Connection Failed ❌");
            e.printStackTrace();
        }

        return connection;
    }
}