package com.foodapp.dao;

import com.foodapp.DBConnection;
import com.foodapp.models.Food;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {
    public List<Food> listAllFoods() {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT food_id, name, description, price FROM foods ORDER BY food_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                foods.add(new Food(rs.getInt("food_id"), rs.getString("name"), rs.getString("description"), rs.getBigDecimal("price")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return foods;
    }

    public Food findById(int id) {
        String sql = "SELECT food_id, name, description, price FROM foods WHERE food_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Food(rs.getInt("food_id"), rs.getString("name"), rs.getString("description"), rs.getBigDecimal("price"));
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }
}
