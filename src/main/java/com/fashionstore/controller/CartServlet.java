package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Product;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartDAO cartDAO = new CartDAOImpl();
    private CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();

    // ====================================================
    // GET - Show Cart
    // ====================================================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            // Get or create cart for user
            Cart cart = cartDAO.getOrCreateCartByUserId(user.getUserId());

            if (cart != null) {
                // Fetch all cart items
                List<CartItem> cartItems = cartItemDAO.getCartItems(cart.getCartId());

                // Fetch product info for each cart item
                List<Product> cartProducts = new ArrayList<>();
                double totalPrice = 0;

                for (CartItem item : cartItems) {
                    Product p = productDAO.getProductById(item.getProductId());
                    if (p != null) {
                        cartProducts.add(p);
                        totalPrice += p.getPrice().doubleValue() * item.getQuantity();
                    }
                }

                request.setAttribute("cartItems", cartItems);
                request.setAttribute("cartProducts", cartProducts);
                request.setAttribute("totalPrice", totalPrice);
                request.setAttribute("cartItemCount", cartItems.size());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
               .forward(request, response);
    }

    // ====================================================
    // POST - Add / Update / Remove / Clear Cart Items
    // ====================================================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        try {
            Cart cart = cartDAO.getOrCreateCartByUserId(user.getUserId());

            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            if ("add".equals(action) || action == null) {
                // ADD item to cart
                int productId = Integer.parseInt(request.getParameter("productId"));
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                } catch (Exception ignored) {}

                CartItem item = new CartItem();
                item.setCartId(cart.getCartId());
                item.setProductId(productId);
                item.setVariantId(1); // default variant
                item.setQuantity(quantity);

                cartItemDAO.addCartItem(item);

            } else if ("update".equals(action)) {
                // UPDATE quantity
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (quantity < 1) quantity = 1;
                cartItemDAO.updateCartItem(cartItemId, quantity);

            } else if ("remove".equals(action)) {
                // REMOVE single item
                int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
                cartItemDAO.removeCartItem(cartItemId);

            } else if ("clear".equals(action)) {
                // CLEAR entire cart
                cartItemDAO.clearCart(cart.getCartId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
