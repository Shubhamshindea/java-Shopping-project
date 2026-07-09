<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, com.fashionstore.model.CartItem, com.fashionstore.model.Product, com.fashionstore.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Fashion Store</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/checkout.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>

<jsp:include page="partials/navbar.jsp" />

<%
    List<CartItem> cartItems    = (List<CartItem>) request.getAttribute("cartItems");
    List<Product>  cartProducts = (List<Product>)  request.getAttribute("cartProducts");
    double totalPrice = request.getAttribute("totalPrice") != null
                        ? (double) request.getAttribute("totalPrice") : 0;
    User user = (User) session.getAttribute("user");
    String errorMsg = (String) request.getAttribute("error");
%>

<div class="checkout-page">
    <h1 class="checkout-title">🛍️ Checkout</h1>

    <% if (errorMsg != null) { %>
        <div class="alert alert-error">❌ <%=errorMsg%></div>
    <% } %>

    <div class="checkout-layout">

        <!-- ===== DELIVERY FORM ===== -->
        <div class="checkout-form-section">
            <h2>Delivery Information</h2>

            <form action="<%=request.getContextPath()%>/checkout" method="post" id="checkout-form">

                <div class="form-row">
                    <div class="form-group">
                        <label>Full Name *</label>
                        <input type="text" name="deliveryName"
                               value="<%=user != null && user.getFullName() != null ? user.getFullName() : ""%>"
                               placeholder="John Doe" required>
                    </div>
                    <div class="form-group">
                        <label>Phone Number *</label>
                        <input type="tel" name="deliveryPhone"
                               value="<%=user != null && user.getPhone() != null ? user.getPhone() : ""%>"
                               placeholder="+91 98765 43210" required>
                    </div>
                </div>

                <div class="form-group">
                    <label>Address Line 1 *</label>
                    <input type="text" name="addressLine1" placeholder="House No., Street Name" required>
                </div>

                <div class="form-group">
                    <label>Address Line 2</label>
                    <input type="text" name="addressLine2" placeholder="Apartment, Colony (Optional)">
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>City *</label>
                        <input type="text" name="city" placeholder="Mumbai" required>
                    </div>
                    <div class="form-group">
                        <label>State *</label>
                        <input type="text" name="state" placeholder="Maharashtra" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label>Pincode *</label>
                        <input type="text" name="pincode" placeholder="400001"
                               maxlength="6" pattern="[0-9]{6}" required>
                    </div>
                    <div class="form-group">
                        <label>Country</label>
                        <input type="text" name="country" value="India">
                    </div>
                </div>

                <h2 style="margin-top:30px;">Payment Method</h2>

                <div class="payment-options">
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="COD" checked>
                        <div class="payment-card">
                            <span class="payment-icon">💵</span>
                            <span>Cash on Delivery</span>
                        </div>
                    </label>
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="UPI">
                        <div class="payment-card">
                            <span class="payment-icon">📱</span>
                            <span>UPI</span>
                        </div>
                    </label>
                    <label class="payment-option">
                        <input type="radio" name="paymentMethod" value="CARD">
                        <div class="payment-card">
                            <span class="payment-icon">💳</span>
                            <span>Credit / Debit Card</span>
                        </div>
                    </label>
                </div>

            </form>
        </div>

        <!-- ===== ORDER SUMMARY ===== -->
        <div class="checkout-summary">
            <h2>Order Summary</h2>

            <div class="checkout-items">
                <% if (cartItems != null) {
                    for (int i = 0; i < cartItems.size(); i++) {
                        CartItem item = cartItems.get(i);
                        Product prod  = (cartProducts != null && i < cartProducts.size()) ? cartProducts.get(i) : null;
                        if (prod == null) continue;
                %>
                <div class="checkout-item">
                    <span class="checkout-item-name"><%=prod.getProductName()%> × <%=item.getQuantity()%></span>
                    <span class="checkout-item-price">₹ <%=Math.round(prod.getPrice().doubleValue() * item.getQuantity())%></span>
                </div>
                <% } } %>
            </div>

            <div class="checkout-divider"></div>

            <div class="checkout-total-row">
                <span>Subtotal</span>
                <span>₹ <%=String.format("%.2f", totalPrice)%></span>
            </div>
            <div class="checkout-total-row">
                <span>Shipping</span>
                <span class="free-tag">FREE</span>
            </div>
            <div class="checkout-total-row grand-total">
                <span>Total</span>
                <span>₹ <%=String.format("%.2f", totalPrice)%></span>
            </div>

            <button type="submit" form="checkout-form" class="place-order-btn">
                🎉 Place Order
            </button>

            <a href="<%=request.getContextPath()%>/cart" class="back-to-cart">← Back to Cart</a>
        </div>

    </div>
</div>

<jsp:include page="partials/footer.jsp" />

</body>
</html>
