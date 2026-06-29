package com.fashionstore.util;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("DB Connected Successfully ✅");
            } else {
                System.out.println("Connection Failed ❌");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}