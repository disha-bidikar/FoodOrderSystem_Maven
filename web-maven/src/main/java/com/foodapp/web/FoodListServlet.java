package com.foodapp.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/foods")
public class FoodListServlet extends HttpServlet {
    private FoodDAO dao = new FoodDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Food> list = dao.listAllFoods();
        req.setAttribute("foods", list);
        req.getRequestDispatcher("/WEB-INF/views/foods.jsp").forward(req, resp);
    }
}
