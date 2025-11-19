package com.foodapp.dao;

import com.foodapp.DBConnection;
import com.foodapp.models.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, phone, address, department, dob, joining_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getPhone());
            ps.setString(3, emp.getAddress());
            ps.setString(4, emp.getDepartment());
            ps.setDate(5, Date.valueOf(emp.getDob()));
            ps.setDate(6, Date.valueOf(emp.getJoiningDate()));
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY emp_id";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public Employee findById(int id) {
        String sql = "SELECT * FROM employees WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

    public List<Employee> findByName(String name) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    public boolean removeEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) { ex.printStackTrace(); return false; }
    }

    private Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("emp_id"),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("address"),
            rs.getString("department"),
            rs.getDate("dob").toLocalDate(),
            rs.getDate("joining_date").toLocalDate()
        );
    }
}
