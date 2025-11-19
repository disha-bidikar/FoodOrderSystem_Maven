package com.foodapp;

import com.foodapp.dao.EmployeeDAO;
import com.foodapp.dao.FoodDAO;
import com.foodapp.dao.OrderDAO;
import com.foodapp.models.Employee;
import com.foodapp.models.Food;

import java.time.LocalDate;
import java.util.*;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final FoodDAO foodDAO = new FoodDAO();
    private static final EmployeeDAO empDAO = new EmployeeDAO();
    private static final OrderDAO orderDAO = new OrderDAO();

    public static void main(String[] args) {
        System.out.println("=== Food Order Management System (Console) ===");
        while (true) {
            System.out.println("\nSelect mode: 1.Admin  2.Customer  3.Exit");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            if (ch.equals("1")) adminMenu();
            else if (ch.equals("2")) customerMenu();
            else if (ch.equals("3")) {
                System.out.println("Exiting. Goodbye!");
                break;
            } else System.out.println("Invalid choice.");
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employee by Name");
            System.out.println("5. Remove Employee");
            System.out.println("6. View Last Orders");
            System.out.println("7. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            try {
                switch (ch) {
                    case "1": addEmployee(); break;
                    case "2": viewAllEmployees(); break;
                    case "3": searchEmployeeById(); break;
                    case "4": searchEmployeeByName(); break;
                    case "5": removeEmployee(); break;
                    case "6": viewLastOrders(); break;
                    case "7": return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static void addEmployee() {
        System.out.println("\nEnter employee details:");
        System.out.print("Name: "); String name = sc.nextLine().trim();
        System.out.print("Phone: "); String phone = sc.nextLine().trim();
        System.out.print("Address: "); String address = sc.nextLine().trim();
        System.out.print("Department: "); String dept = sc.nextLine().trim();
        System.out.print("Date of Birth (YYYY-MM-DD): "); LocalDate dob = LocalDate.parse(sc.nextLine().trim());
        System.out.print("Joining Date (YYYY-MM-DD): "); LocalDate joining = LocalDate.parse(sc.nextLine().trim());

        Employee emp = new Employee(name, phone, address, dept, dob, joining);
        boolean ok = empDAO.addEmployee(emp);
        System.out.println(ok ? "Employee added successfully." : "Failed to add employee.");
    }

    private static void viewAllEmployees() {
        List<Employee> list = empDAO.getAllEmployees();
        if (list.isEmpty()) System.out.println("No employees found.");
        else {
            System.out.println("Employees:");
            list.forEach(e -> System.out.println(e));
        }
    }

    private static void searchEmployeeById() {
        System.out.print("Enter employee ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        Employee e = empDAO.findById(id);
        System.out.println(e == null ? "Employee not found." : e);
    }

    private static void searchEmployeeByName() {
        System.out.print("Enter name to search: "); String name = sc.nextLine().trim();
        List<Employee> list = empDAO.findByName(name);
        if (list.isEmpty()) System.out.println("No matching employees.");
        else list.forEach(emp -> System.out.println(emp));
    }

    private static void removeEmployee() {
        System.out.print("Enter employee ID to remove: "); int id = Integer.parseInt(sc.nextLine().trim());
        boolean ok = empDAO.removeEmployee(id);
        System.out.println(ok ? "Removed." : "Failed or not found.");
    }

    private static void viewLastOrders() {
        System.out.print("How many last orders to show? (e.g., 5): ");
        int n = Integer.parseInt(sc.nextLine().trim());
        List<com.foodapp.models.Order> orders = orderDAO.getLastOrders(n);
        if (orders.isEmpty()) System.out.println("No orders found.");
        else {
            System.out.println("Last orders:");
            for (com.foodapp.models.Order o : orders) {
                System.out.println(o);
            }
            System.out.print("Enter order id to see details (or press Enter to go back): ");
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) {
                int oid = Integer.parseInt(s);
                orderDAO.printOrderDetails(oid);
            }
        }
    }

    private static void customerMenu() {
        while (true) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. List Menu");
            System.out.println("2. Order Food");
            System.out.println("3. Back");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();
            switch (ch) {
                case "1": listMenu(); break;
                case "2": orderFood(); break;
                case "3": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void listMenu() {
        System.out.println("\n-- Menu --");
        List<Food> foods = foodDAO.listAllFoods();
        foods.forEach(f -> System.out.println(f));
    }

    private static void orderFood() {
        listMenu();
        System.out.println("\nPlace your order. Enter items one by one (foodId:quantity). Enter blank line when done.");
        Map<Integer, Integer> items = new LinkedHashMap<>();
        while (true) {
            System.out.print("Item (id:qty): ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) break;
            String[] parts = line.split(":");
            try {
                int id = Integer.parseInt(parts[0].trim());
                int qty = Integer.parseInt(parts[1].trim());
                if (qty <= 0) {
                    System.out.println("Quantity must be > 0");
                    continue;
                }
                items.put(id, items.getOrDefault(id, 0) + qty);
            } catch (Exception ex) {
                System.out.println("Invalid format. Use id:qty (e.g., 1:2).");
            }
        }

        if (items.isEmpty()) {
            System.out.println("No items selected.");
            return;
        }

        System.out.print("Customer name: "); String cname = sc.nextLine().trim();
        System.out.print("Phone: "); String phone = sc.nextLine().trim();
        System.out.print("Address: "); String addr = sc.nextLine().trim();

        boolean ok = orderDAO.placeOrder(cname, phone, addr, items);
        System.out.println(ok ? "Order placed successfully." : "Failed to place order.");
    }
}
