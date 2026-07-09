package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.Product;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartDAO cartDAO = new CartDAOImpl();
    private CartItemDAO cartItemDAO = new CartItemDAOImpl();
    private ProductDAO productDAO = new ProductDAOImpl();
    private OrderDAOImpl orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    // ====================================================
    // GET - Show Checkout Page
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
            Cart cart = cartDAO.getCartByUserId(user.getUserId());

            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<CartItem> cartItems = cartItemDAO.getCartItems(cart.getCartId());

            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

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
            request.setAttribute("user", user);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp")
               .forward(request, response);
    }

    // ====================================================
    // POST - Place Order
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

        // Read delivery info from form
        String deliveryName    = request.getParameter("deliveryName");
        String deliveryPhone   = request.getParameter("deliveryPhone");
        String addressLine1    = request.getParameter("addressLine1");
        String addressLine2    = request.getParameter("addressLine2");
        String city            = request.getParameter("city");
        String state           = request.getParameter("state");
        String pincode         = request.getParameter("pincode");
        String country         = request.getParameter("country");
        String paymentMethod   = request.getParameter("paymentMethod");

        try {
            Cart cart = cartDAO.getCartByUserId(user.getUserId());

            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<CartItem> cartItems = cartItemDAO.getCartItems(cart.getCartId());

            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Calculate total
            double totalDouble = 0;
            for (CartItem item : cartItems) {
                Product p = productDAO.getProductById(item.getProductId());
                if (p != null) {
                    totalDouble += p.getPrice().doubleValue() * item.getQuantity();
                }
            }

            // Build order
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setTotalAmount(BigDecimal.valueOf(totalDouble));
            order.setPaymentMethod(paymentMethod);
            order.setOrderStatus("PENDING");
            order.setDeliveryName(deliveryName);
            order.setDeliveryPhone(deliveryPhone);
            order.setDeliveryAddressLine1(addressLine1);
            order.setDeliveryAddressLine2(addressLine2 != null ? addressLine2 : "");
            order.setDeliveryCity(city);
            order.setDeliveryState(state);
            order.setDeliveryPincode(pincode);
            order.setDeliveryCountry(country != null ? country : "India");

            int orderId = orderDAO.createOrder(order);

            if (orderId > 0) {
                // Create order items
                for (CartItem item : cartItems) {
                    Product p = productDAO.getProductById(item.getProductId());
                    if (p != null) {
                        OrderItem orderItem = new OrderItem();
                        orderItem.setOrderId(orderId);
                        orderItem.setProductId(item.getProductId());
                        orderItem.setVariantId(item.getVariantId());
                        orderItem.setQuantity(item.getQuantity());
                        orderItem.setPrice(BigDecimal.valueOf(p.getPrice()));
                        orderItemDAO.addOrderItem(orderItem);
                    }
                }

                // Clear the cart after successful order
                cartItemDAO.clearCart(cart.getCartId());

                request.setAttribute("orderId", orderId);
                request.setAttribute("totalPrice", totalDouble);
                request.setAttribute("paymentMethod", paymentMethod);
                request.getRequestDispatcher("/WEB-INF/views/order-success.jsp")
                       .forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("error", "Failed to place order. Please try again.");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp")
               .forward(request, response);
    }
}
