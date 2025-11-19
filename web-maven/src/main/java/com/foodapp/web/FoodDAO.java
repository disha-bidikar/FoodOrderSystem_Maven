package com.foodapp.web;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.foodapp.web.Food;

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
}
