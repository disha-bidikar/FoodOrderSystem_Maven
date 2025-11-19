package com.foodapp.dao;

import com.foodapp.DBConnection;
import com.foodapp.models.Order;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO {

    public boolean placeOrder(String customerName, String phone, String address, Map<Integer, Integer> items) {
        String insertOrder = "INSERT INTO orders (customer_name, phone, address, total_amount) VALUES (?, ?, ?, ?)";
        String insertItem = "INSERT INTO order_items (order_id, food_id, quantity, price) VALUES (?, ?, ?, ?)";
        String selectPrice = "SELECT price FROM foods WHERE food_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            BigDecimal total = BigDecimal.ZERO;

            try (PreparedStatement psPrice = conn.prepareStatement(selectPrice)) {
                for (Map.Entry<Integer, Integer> e : items.entrySet()) {
                    int foodId = e.getKey();
                    int qty = e.getValue();
                    psPrice.setInt(1, foodId);
                    try (ResultSet rs = psPrice.executeQuery()) {
                        if (rs.next()) {
                            BigDecimal price = rs.getBigDecimal("price");
                            total = total.add(price.multiply(BigDecimal.valueOf(qty)));
                        } else {
                            throw new SQLException("Food id " + foodId + " not found.");
                        }
                    }
                }
            }

            try (PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setString(1, customerName);
                psOrder.setString(2, phone);
                psOrder.setString(3, address);
                psOrder.setBigDecimal(4, total);
                int affected = psOrder.executeUpdate();
                if (affected != 1) throw new SQLException("Failed to insert order");

                int orderId;
                try (ResultSet gk = psOrder.getGeneratedKeys()) {
                    if (gk.next()) orderId = gk.getInt(1);
                    else throw new SQLException("Failed to obtain order id");
                }

                try (PreparedStatement psItem = conn.prepareStatement(insertItem);
                     PreparedStatement psPrice = conn.prepareStatement(selectPrice)) {
                    for (Map.Entry<Integer, Integer> e : items.entrySet()) {
                        int foodId = e.getKey();
                        int qty = e.getValue();
                        psPrice.setInt(1, foodId);
                        try (ResultSet rs = psPrice.executeQuery()) {
                            if (rs.next()) {
                                BigDecimal price = rs.getBigDecimal("price");
                                psItem.setInt(1, orderId);
                                psItem.setInt(2, foodId);
                                psItem.setInt(3, qty);
                                psItem.setBigDecimal(4, price);
                                psItem.addBatch();
                            } else {
                                throw new SQLException("Food id " + foodId + " not found (during item insert).");
                            }
                        }
                    }
                    psItem.executeBatch();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Order> getLastOrders(int limit) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT order_id, customer_name, phone, address, total_amount, order_date FROM orders ORDER BY order_date DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Order(
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getBigDecimal("total_amount"),
                        rs.getTimestamp("order_date")
                    ));
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public void printOrderDetails(int orderId) {
        String orderSql = "SELECT * FROM orders WHERE order_id = ?";
        String itemsSql = "SELECT oi.item_id, f.name, oi.quantity, oi.price FROM order_items oi JOIN foods f ON oi.food_id = f.food_id WHERE oi.order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psOrder = conn.prepareStatement(orderSql);
             PreparedStatement psItems = conn.prepareStatement(itemsSql)) {
            psOrder.setInt(1, orderId);
            try (ResultSet rs = psOrder.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Order: " + rs.getInt("order_id") + " | Customer: " + rs.getString("customer_name")
                            + " | Phone: " + rs.getString("phone") + " | Address: " + rs.getString("address")
                            + " | Total: $" + rs.getBigDecimal("total_amount") + " | Date: " + rs.getTimestamp("order_date"));
                } else {
                    System.out.println("Order not found.");
                    return;
                }
            }

            psItems.setInt(1, orderId);
            try (ResultSet rs2 = psItems.executeQuery()) {
                System.out.println("Items:");
                while (rs2.next()) {
                    System.out.println("  - " + rs2.getString("name") + " x " + rs2.getInt("quantity") + " @ $" + rs2.getBigDecimal("price"));
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }
}
