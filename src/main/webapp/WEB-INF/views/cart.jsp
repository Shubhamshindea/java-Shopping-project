<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.CartItem, com.fashionstore.model.Product" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/theme.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/cart.css">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<%
    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
    List<Product> cartProducts = (List<Product>) request.getAttribute("cartProducts");
    double totalPrice = 0;
    if (request.getAttribute("totalPrice") != null) {
        totalPrice = (double) request.getAttribute("totalPrice");
    }
%>

<div class="page-container cart-page">

    <div class="cart-header">
        <h1>Your Cart</h1>
        <a href="<%=request.getContextPath()%>/products" class="continue-btn">← Continue Shopping</a>
    </div>

    <%
        String successMsg = (String) request.getAttribute("success");
        String errorMsg   = (String) request.getAttribute("error");
        if (successMsg != null) {
    %>
        <div class="alert alert-success">✅ <%= successMsg %></div>
    <%  } %>
    <%  if (errorMsg != null) { %>
        <div class="alert alert-error">❌ <%= errorMsg %></div>
    <%  } %>

    <% if (cartItems == null || cartItems.isEmpty()) { %>

        <div class="empty-cart">
            <div class="empty-icon">🛍️</div>
            <h2>Your cart is empty</h2>
            <p>Looks like you haven't added anything yet.</p>
            <a href="<%=request.getContextPath()%>/products" class="shop-btn">Start Shopping</a>
        </div>

    <% } else { %>

        <div class="cart-layout">

            <!-- CART ITEMS -->
            <div class="cart-items-section">
                <% for (int i = 0; i < cartItems.size(); i++) {
                    CartItem item = cartItems.get(i);
                    Product prod  = (cartProducts != null && i < cartProducts.size()) ? cartProducts.get(i) : null;
                    if (prod == null) continue;
                %>

                <div class="cart-item-card">

                    <div class="cart-item-image">
                        <a href="<%=request.getContextPath()%>/product-details?id=<%=prod.getProductId()%>">
                            <img src="<%=request.getContextPath()%>/<%= (prod.getImageUrl() != null && !prod.getImageUrl().isEmpty()) ? prod.getImageUrl() : "assets/images/sample.jpg" %>" alt="<%=prod.getProductName()%>">
                        </a>
                    </div>

                    <div class="cart-item-info">
                        <p class="brand"><%=prod.getBrand()%></p>
                        <h3><a href="<%=request.getContextPath()%>/product-details?id=<%=prod.getProductId()%>"><%=prod.getProductName()%></a></h3>
                        <p class="unit-price">₹ <%=prod.getPrice()%></p>
                    </div>

                    <!-- QUANTITY FORM -->
                    <div class="cart-item-qty">
                        <form action="<%=request.getContextPath()%>/cart" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="cartItemId" value="<%=item.getCartItemId()%>">
                            <input type="number" name="quantity" value="<%=item.getQuantity()%>"
                                   min="1" max="99" class="qty-input">
                            <button type="submit" class="update-btn">Update</button>
                        </form>
                    </div>

                    <!-- ITEM TOTAL -->
                    <div class="cart-item-total">
                        <strong>₹ <%=Math.round(prod.getPrice().doubleValue() * item.getQuantity())%></strong>
                    </div>

                    <!-- REMOVE -->
                    <form action="<%=request.getContextPath()%>/cart" method="post" class="cart-item-remove">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="cartItemId" value="<%=item.getCartItemId()%>">
                        <button type="submit" class="remove-btn" title="Remove Item">✕</button>
                    </form>

                </div>

                <% } %>
            </div>

            <!-- ORDER SUMMARY -->
            <div class="cart-summary">
                <h2>Order Summary</h2>

                <div class="summary-row">
                    <span>Subtotal (<%=cartItems.size()%> items)</span>
                    <span>₹ <%=String.format("%.2f", totalPrice)%></span>
                </div>
                <div class="summary-row">
                    <span>Shipping</span>
                    <span class="free-tag">FREE</span>
                </div>
                <div class="summary-row summary-total">
                    <span>Total</span>
                    <span>₹ <%=String.format("%.2f", totalPrice)%></span>
                </div>

                <form action="<%=request.getContextPath()%>/checkout" method="get">
                    <button type="submit" class="checkout-btn">Proceed to Checkout</button>
                </form>

                <form action="<%=request.getContextPath()%>/cart" method="post">
                    <input type="hidden" name="action" value="clear">
                    <button type="submit" class="clear-btn">Clear Cart</button>
                </form>
            </div>

        </div>

    <% } %>

</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>
