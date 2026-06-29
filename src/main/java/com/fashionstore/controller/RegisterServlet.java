package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // 🔁 DUPLICATE CHECK
        if (userDAO.getUserByEmail(email) != null) {
            req.setAttribute("error", "Email already exists");
            req.setAttribute("mode", "register");

            req.getRequestDispatcher("/WEB-INF/views/login.jsp")
               .forward(req, res);
            return;
        }

        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(password);

        userDAO.registerUser(user);

        req.setAttribute("success", "Account created! Login now");

        req.getRequestDispatcher("/WEB-INF/views/login.jsp")
           .forward(req, res);
    }
}