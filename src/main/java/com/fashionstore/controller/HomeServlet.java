package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAOImpl();
        productDAO = new ProductDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 🔹 Fetch categories
            List<Category> categories = categoryDAO.getAllCategories();

            // 🔹 Fetch products
            List<Product> products = productDAO.getAllProducts();

            // 🔹 Set data to request
            request.setAttribute("categories", categories);
            request.setAttribute("products", products);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 🔹 Forward to JSP
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
        rd.forward(request, response);
    }
}
