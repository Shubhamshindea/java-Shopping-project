package com.fashionstore.controller;

import java.io.IOException;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

```
private static final long serialVersionUID = 1L;

private CartDAO cartDAO = new CartDAOImpl();
private CartItemDAO cartItemDAO = new CartItemDAOImpl();

@Override
protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession(false);

    if (session == null || session.getAttribute("user") == null) {

        response.sendRedirect(
                request.getContextPath() + "/login");

        return;
    }

    User user = (User) session.getAttribute("user");

    int productId =
            Integer.parseInt(
                    request.getParameter("productId"));

    Cart cart =
            cartDAO.getOrCreateCartByUserId(
                    user.getUserId());

    if (cart == null) {

        response.sendRedirect(
                request.getContextPath() + "/products");

        return;
    }

    CartItem item = new CartItem();

    item.setCartId(cart.getCartId());
    item.setProductId(productId);
    item.setVariantId(1);
    item.setQuantity(1);

    cartItemDAO.addCartItem(item);

    response.sendRedirect(
            request.getContextPath() + "/cart");
}

@Override
protected void doGet(HttpServletRequest request,
                     HttpServletResponse response)
        throws ServletException, IOException {

    request.getRequestDispatcher(
            "/WEB-INF/views/cart.jsp")
            .forward(request, response);
}
```

}
