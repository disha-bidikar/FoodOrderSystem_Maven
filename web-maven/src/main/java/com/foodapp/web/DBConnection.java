package com.foodapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/food_order_db?useSSL=false&serverTimezone=UTC";
    private static String USER = "root";
    private static String PASS = "";

    static {
        try (InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                Properties p = new Properties();
                p.load(in);
                URL = p.getProperty("db.url", URL);
                USER = p.getProperty("db.user", USER);
                PASS = p.getProperty("db.password", PASS);
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("DB connection initialization error: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
