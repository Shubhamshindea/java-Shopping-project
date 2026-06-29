package com.fashionstore.controller;

import java.io.IOException;
import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpSession;
@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAOImpl();
        System.out.println("✅ ProductServlet Initialized");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login");
        return;
    }

        System.out.println("🔥 ProductServlet HIT");

        try {
            // 🔍 Get parameters
            String categoryId = request.getParameter("categoryId");
            String keyword = request.getParameter("keyword");

            List<Product> products;

            // 🎯 Logic
            if (keyword != null && !keyword.trim().isEmpty()) {
                products = productDAO.searchProducts(keyword);
            } else if (categoryId != null && !categoryId.isEmpty()) {
                products = productDAO.getProductsByCategory(Integer.parseInt(categoryId));
            } else {
                products = productDAO.getAllProducts();
            }

            // 📦 Send data to JSP
            request.setAttribute("products", products);

            // 🔁 Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/products.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            // ❗ fallback error response
            response.setContentType("text/html");
            response.getWriter().println("<h2>Error loading products</h2>");
        }
    }
}